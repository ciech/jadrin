package main.jadrin.waiter;

import java.util.ArrayList;
import java.util.LinkedList;

import main.jadrin.ontology.CheckElement;
import main.jadrin.ontology.Drink;
import main.jadrin.ontology.DrinkOntology;
import main.jadrin.ontology.DrinkRequest;
import main.jadrin.ontology.DrinkRequestType;
import main.jadrin.ontology.DrinkResponse;
import main.jadrin.ontology.DrinkResponseType;
import main.jadrin.ontology.Ingredient;
import main.jadrin.ontology.QueryOntology;
import main.jadrin.ontology.Recipe;
import main.jadrin.ontology.Type;
import main.jadrin.tools.AlphabetNormalizer;
import main.jadrin.tools.Tokenizer;
import morfologik.stemming.IStemmer;
import morfologik.stemming.PolishStemmer;
import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BuildQueryBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7602120656772135185L;
	private String query;
	private AID[] bartenders;
	private WaiterAgentGui gui;
	BuildQueryBehaviour(Agent agent, String query, AID[] bartenders, WaiterAgentGui gui)
	{
		super(agent);
		this.query = query;
		this.bartenders = bartenders;		
		this.gui = gui;
	}
	
	@Override
	public void action() {
		boolean drinkQuery = false;
		boolean ingredientQuery = false;
		
		IStemmer stemmer = new PolishStemmer();
		String[] notPermitted = {"prep","siebie","conj","comp"};
		String[] tokens = Tokenizer.getTokens(stemmer, query, notPermitted);
		
		String drink = "";
        ArrayList<String> ingredients = new ArrayList<String>();
        String cache= "";
		for (int i=0; i<tokens.length; ++i)
		{
			
			String str = AlphabetNormalizer.unAccent(tokens[i]);
			str = str.toLowerCase();
			ACLMessage query = new ACLMessage(ACLMessage.REQUEST);
			query.setOntology(QueryOntology.NAME);
			query.setLanguage(((WaiterAgent)myAgent).getCodecName());
			CheckElement toCheck = new CheckElement();
			if (cache.length()>0)
			{
				str = cache + " " +  str;
			}
			toCheck.setName(str);
			toCheck.setPartOf(false);
			toCheck.setType(Type.UNKNOWN);
			AgentAction act = toCheck;
			for(AID bartender: bartenders)
			{
				Action actOperator = new Action(bartender, act);
				try {
					myAgent.getContentManager().fillContent(query,actOperator);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
				
				query.addReceiver(bartender);
				myAgent.send(query);
				MessageTemplate messType = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				MessageTemplate ontoType = MessageTemplate.MatchOntology(QueryOntology.NAME);
				
				ACLMessage msg = myAgent.blockingReceive(MessageTemplate.and(messType,ontoType));
				try {
					ContentElement ce =  myAgent.getContentManager().extractContent(msg);
					if (ce instanceof Action)
					{
						 toCheck = (CheckElement)((Action) ce).getAction(); 
					}
				} catch (UngroundedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (toCheck != null && toCheck.isPartOf())
				{
					cache = toCheck.getName();
					query.removeReceiver(bartender);
					break;
				}
				
				cache = "";
				
				if (toCheck != null && toCheck.getType() == Type.DRINK)
				{
					drinkQuery=true;
					drink = toCheck.getName();
				}
				else if (toCheck != null && toCheck.getType() == Type.INGREDIENT)
				{
					ingredientQuery=true;
					if(!ingredients.contains(toCheck.getName()))
						ingredients.add(toCheck.getName());
				}
				query.removeReceiver(bartender);
			}
				
		}
		
		DrinkRequestType type = DrinkRequestType.FROM_NAME;
		String result = "";
		Drink d = new Drink();
		DrinkRequest dRequest = new DrinkRequest();
		LinkedList<Drink> drinks = null;
		if (drinkQuery && !ingredientQuery)
		{
			result = "Spytałeś o skład " + drink;
			
			d.setName(drink);
			dRequest.setAskFor(d);
			type = DrinkRequestType.FROM_NAME;
			dRequest.setType(type);
		}
		else if (!drinkQuery && ingredientQuery)
		{
			String ing = "";
	        ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
			for(String ingredient : ingredients)
			{
				ing += ingredient + ", ";
				Ingredient i = new Ingredient();
				i.setName(ingredient);
				ings.add(i);
			}
				
			if(ing.length() > 1)
				ing = ing.substring(0, ing.length() - 2);
			
			result = "Spytałeś co można zrobić z " + ing;
						
			d.setIngredients(ings);
			dRequest.setAskFor(d);
			type = DrinkRequestType.FROM_INGREDIENTS;
			dRequest.setType(type);
		}
		else if (drinkQuery && ingredientQuery)
		{
			String ing = "";
	        ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
			for(String ingredient : ingredients)
			{
				ing += ingredient + ", ";
				Ingredient i = new Ingredient();
				i.setName(ingredient);
				ings.add(i);
			}
			
			if(ing.length() > 1)
				ing = ing.substring(0, ing.length() - 2);
			
			result = "Spytałeś czego brakuje Ci do " + drink + " jeśli posiadasz " + ing;
			
			d.setName(drink);
			d.setIngredients(ings);
			dRequest.setAskFor(d);
			type = DrinkRequestType.FROM_NAME_AND_INGREDIENTS;
			dRequest.setType(type);
		}
		else
		{
			result =" Nie znam odpowiedzi na to pytanie";
		}
		gui.setResponse(result);
		
		String response = "";
		if(drinkQuery || ingredientQuery)
		{
			drinks = HandleRequest(dRequest);
			if (drinks == null)
			{
				response = ("Żaden barman nie posiada wiedzy na ten temat");
			}
			else
			{
			 if (drinks.size() > 5)
			 {
				 response+= "Znaleziono aż "+ drinks.size()+ " pasujących drinków\n";
				 response+= "Proszę wybrać konkretnego drinka z listy\n";
				 for(Drink drin : drinks)
					{
					 	response+=("Drink: " + drin.getName()+"\n");
					}
			 }
			 else
			 {
			for(Drink drin : drinks)
			{
				response+=  "Drink: " + drin.getName() + "\n";
				String ing = "";
				for(Ingredient ingredient : drin.getIngredients())
				{
					ing += ingredient.getName() + ", ";
				}
				if(ing.length() > 1)
					ing = ing.substring(0, ing.length() - 2);
				
				if (type == DrinkRequestType.FROM_NAME_AND_INGREDIENTS)
				{
					response+= "Brakujące składniki: " + ing + "\n";
				}
				else
				{
					response+= "Składniki: " + ing + "\n";
				}
			
				Recipe r = drin.getRecipe();
				if(r != null)
					response += "Przepis: " + r.getContent() + "\n";
			}
			}
			}
		}
		
		gui.setResponse(response);
		gui.setEditable(true);
	}
	
	private LinkedList<Drink> HandleRequest(DrinkRequest request)
	{
		ACLMessage query = new ACLMessage(ACLMessage.REQUEST);
		query.setOntology(DrinkOntology.NAME);
		query.setLanguage(((WaiterAgent)myAgent).getCodecName());
		LinkedList<Drink> results = new LinkedList<Drink>();
		AgentAction act = null;
		DrinkResponse response = null;
		for(AID bartender: bartenders)
		{
			act = request;
			Action actOperator = new Action(bartender, act);
			try {
				myAgent.getContentManager().fillContent(query,actOperator);
			   }
			   catch (Exception ex) { ex.printStackTrace(); }
			query.addReceiver(bartender);
			myAgent.send(query);
			ACLMessage msg = myAgent.blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
		
						try {
					ContentElement ce =  myAgent.getContentManager().extractContent(msg);
					if (ce instanceof Action)
					{
						response = (DrinkResponse)((Action) ce).getAction(); 
					}
		
				} catch (UngroundedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CodecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OntologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				query.removeReceiver(bartender);
				
				if(response.getType() != DrinkResponseType.UNKNOWN)
				{
					results.addAll(response.getResults());
				}
					
		}
		
		if(results.size()>0)
		{
			return results;
		}
		return null;
	}

}
