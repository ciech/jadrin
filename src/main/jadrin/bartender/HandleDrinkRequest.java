package main.jadrin.bartender;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import main.jadrin.ontology.CheckElement;
import main.jadrin.ontology.Drink;
import main.jadrin.ontology.DrinkOntology;
import main.jadrin.ontology.DrinkRequest;
import main.jadrin.ontology.DrinkResponse;
import main.jadrin.ontology.DrinkResponseType;
import main.jadrin.ontology.Ingredient;
import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HandleDrinkRequest extends OneShotBehaviour {

	private static final long serialVersionUID = -1131798599467868104L;
	private ACLMessage msg;


	HandleDrinkRequest(BartenderAgent agent, ACLMessage msg) {
		super(agent);
		this.msg = msg;
	}

	@Override
	public void action() {
		try {
			ContentElement ce = myAgent.getContentManager().extractContent(msg);
			DrinkRequest request = (DrinkRequest)((Action) ce).getAction(); 
			BartenderAgent bartender = (BartenderAgent) myAgent;
			DrinkResponse response = new DrinkResponse();
			response.setType(DrinkResponseType.UNKNOWN);
			Drink drink;
			switch(request.getType())
			{
			case FROM_INGREDIENTS:
				LinkedList<Drink> drinks = bartender.getDrinksWithGivenIngredients(request.getAskFor().getIngredients());	
				fillResponse(drinks, response);
				break;
			case FROM_NAME:
				drink = bartender.getDrinkRecipe(request.getAskFor().getName());	
				fillResponse(drink, response);
				break;
			case FROM_NAME_AND_INGREDIENTS:
				drink = bartender.getMissingIngredientsAndRecipe(request.getAskFor().getIngredients(), request.getAskFor().getName());
				fillResponse(drink, response);
				break;
			default:
				break;

			}


			ACLMessage reply = msg.createReply();
			AgentAction act = response;
			Action actOperator = new Action(msg.getSender(), act);
			try {
				myAgent.getContentManager().fillContent(reply,actOperator);
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
		} catch (CodecException | OntologyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private void fillResponse(Drink drink, DrinkResponse response) {
		if(drink != null){
			response.setType(DrinkResponseType.SINGLE_MATCH);
			LinkedList<Drink> list = new LinkedList<Drink>();
			list.add(drink);
			response.setResults(list);
		}else
			response.setType(DrinkResponseType.UNKNOWN);	
	}

	private void fillResponse(LinkedList<Drink> drinks, DrinkResponse response) {
		switch (drinks.size()) {
		case 0:
			response.setType(DrinkResponseType.UNKNOWN);
			break;
		case 1:
			response.setType(DrinkResponseType.SINGLE_MATCH);
			response.setResults(drinks);
			break;
		default:
			response.setType(DrinkResponseType.MULTI_MATCH);
			response.setResults(drinks);
			break;
		}

	}
}
