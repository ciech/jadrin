package main.jadrin.bartender;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import main.jadrin.ontology.Drink;
import main.jadrin.ontology.Ingredient;
import main.jadrin.ontology.Unknown;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.DoubleQuotesTerm;
import gnu.prolog.term.IntegerTerm;
import gnu.prolog.term.Term;
import gnu.prolog.term.VariableTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Evaluate;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;
import jade.content.Concept;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BartenderAgent extends Agent { 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8576658642439840374L;
	private static final String SERVICE_NAME = "Bartender";
	private Environment environment;
	private Interpreter interpreter;
	private void registerService() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setName(SERVICE_NAME);
		sd.setType("drink-knowledge");
		sd.addOntologies("jadrin-ontology");
		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
		sd.addProperties(new Property("country", "Poland"));
		dfd.addServices(sd);


		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException e) {
			System.err.println(getLocalName() +
					" registration with DF unsucceeded. Reason: " + e.getMessage());
			doDelete();
		}
	}

	protected void setup() 
	{ 
		//Prolog setup:
		this.environment =  new Environment();

		URL knowledgeFile = getClass().getResource("/main/jadrin/resources/knowledge.pro");
		URL rulesFile = getClass().getResource("/main/jadrin/resources/rules.pro");
		environment.ensureLoaded(AtomTerm.get(knowledgeFile.getFile()));
		environment.ensureLoaded(AtomTerm.get(rulesFile.getFile()));

		this.interpreter = environment.createInterpreter();
		environment.runInitialization(interpreter);
		//    	
		
		registerService();
		addBehaviour(new WhatIsThat(this)); // for NLP on waiter
		addBehaviour(new GetDrinkRecipe(this)); // A case from documentation
		addBehaviour(new GetDrinksWithGivenIngredients(this)); // B case from documentation
		addBehaviour(new GetMissingIngredientsAndRecipe(this)); // C case from documentation 	


	}

	public Concept whatIsThat(String name){

		Term[] args = { AtomTerm.get(name)};

		CompoundTerm goalTermIngredient = new CompoundTerm(AtomTerm.get("is_ingredient"), args);
		CompoundTerm goalTermDrink = new CompoundTerm(AtomTerm.get("is_drink"), args);
		int isIngredient = PrologCode.FAIL;
		int isDrink = PrologCode.FAIL;
		try {
			isIngredient = interpreter.runOnce(goalTermIngredient);
			isDrink = interpreter.runOnce(goalTermDrink);			
		} catch (PrologException e) {
			e.printStackTrace();
		}


		if(isIngredient == PrologCode.SUCCESS)
			return new Ingredient();

		if(isDrink == PrologCode.SUCCESS)
			return  new Drink();

		return new Unknown();  	
	}
	
	public List<Drink> getDrinksWithGivenIngredients(String[] ingredients){

		//		LinkedList<Term> terms = new LinkedList<Term>();
		LinkedList<Drink> drinkList = new LinkedList<Drink>();
		for (String ingredient : ingredients) {
		
			Term ingredientTerm = buildTermList(ingredient);
			
			VariableTerm drinkName = new VariableTerm("DrinkName");
			VariableTerm fullIngredientsList = new VariableTerm("FullIngredientsList");
			VariableTerm recipe = new VariableTerm("Recipe");

			Term[] args = { ingredientTerm , drinkName, fullIngredientsList,recipe };
	
			CompoundTerm goalTerm = new CompoundTerm(AtomTerm.get("what_can_i_do"), args);
			Interpreter.Goal goal= interpreter.prepareGoal(goalTerm);

			int rc;
			try {
				do{
					rc = interpreter.execute(goal);
					//rc = interpreter.runOnce(goalTerm);
					Term drinkDeref  = drinkName.dereference();
					Term fullIngredientsDeref = fullIngredientsList.dereference();
					Term recipeDeref = recipe.dereference();	
					drinkList.add(createDrink(drinkDeref,fullIngredientsDeref,recipeDeref ));
				}while(rc == PrologCode.SUCCESS || rc == PrologCode.SUCCESS_LAST);
			} catch (PrologException e) {
				e.printStackTrace();
			}	
		}
		return drinkList;
		
	}

	private Drink createDrink(String drinkName, Term fullIngredientsDeref,
			Term recipeDeref) {
		
		String ingredients[] = tokenizeIngredients(fullIngredientsDeref);
		String recipe = tokenizeTerm(recipeDeref).replace("\\x20\\", " ");
		return new Drink(drinkName, ingredients, recipe);
	}
	
	private Drink createDrink(Term drinkDeref, Term fullIngredientsDeref,
			Term recipeDeref) {
		
		String drinkName = tokenizeTerm(drinkDeref);
		return createDrink(drinkName, fullIngredientsDeref, recipeDeref);
	}

	private String[] tokenizeIngredients(Term fullIngredientsDeref) {
		String str = fullIngredientsDeref.toString().replace("'", "").replace(",", "").replace("[[", "").replace("]]", "").replace("][", " ");
		StringTokenizer tokenizer = new StringTokenizer(str.toString(), "[]");
		ArrayList<String> ingredients = new ArrayList<String> ();
		
		while(tokenizer.hasMoreTokens()){
			ingredients.add(tokenizer.nextToken());
		}
		
		return ingredients.toArray(new String[ingredients.size()]);
	}

	private String tokenizeTerm(Term drinkDeref) {
		StringTokenizer tokenizer = new StringTokenizer(drinkDeref.toString(), "['], ");
		StringBuffer stringBuffer = new StringBuffer();
		while(tokenizer.hasMoreTokens()){
			stringBuffer.append(tokenizer.nextToken());
			stringBuffer.append(" ");
		}
		
		return stringBuffer.toString();
	}	
	
	public Drink getDrinkRecipe(String drinkName){
		
		VariableTerm fullIngredientsList = new VariableTerm("FullIngredientsList");
		VariableTerm recipe = new VariableTerm("Recipe");
		Term drinkTerm = buildTermList(drinkName);
		Term[] args1 = { drinkTerm, fullIngredientsList};	
		CompoundTerm goalTermIngredients = new CompoundTerm(AtomTerm.get("ingredients"), args1);
		Term[] args2 = { drinkTerm, recipe};	
		CompoundTerm goalTermRecipe= new CompoundTerm(AtomTerm.get("recipe"), args2);
	
		try {
			interpreter.runOnce(goalTermIngredients);	
			interpreter.runOnce(goalTermRecipe);	
		} catch (PrologException e) {
			e.printStackTrace();
		}
		
		Term fullIngredientsDeref = fullIngredientsList.dereference();
		Term recipeDeref = recipe.dereference();
		return createDrink(drinkName, fullIngredientsDeref, recipeDeref);
	}

	/**
	 * 
	 * @param ingredients
	 * @return Drink object with ingredients containing only missing ones, proper name, recipe
	 */
	public Drink getMissingIngredientsAndRecipe(String[] ingredients , String drinkName){
		
		VariableTerm missingIngredients = new VariableTerm("MissingIngredients");
		VariableTerm recipe = new VariableTerm("Recipe");
		Term drinkTerm = buildTermList(drinkName);
//		Term drinkTerm
		
//		Term[] args1 = { drinkTerm, fullIngredientsList};	
//		CompoundTerm goalTermIngredients = new CompoundTerm(AtomTerm.get("ingredients"), args1);
//		Term[] args2 = { drinkTerm, recipe};	
//		CompoundTerm goalTermRecipe= new CompoundTerm(AtomTerm.get("recipe"), args2);
//	
//		try {
//			interpreter.runOnce(goalTermIngredients);	
//			interpreter.runOnce(goalTermRecipe);	
//		} catch (PrologException e) {
//			e.printStackTrace();
//		}
//		
//		Term fullIngredientsDeref = fullIngredientsList.dereference();
//		Term recipeDeref = recipe.dereference();
//	
		return null;
	}
	
	private Term buildTerm(String termStr) {
		List<Term> terms = new LinkedList<Term>();
		terms.add(AtomTerm.get(termStr) );
		return CompoundTerm.getList(terms); 
	}
		
	private Term buildTermList(String str) {
		StringTokenizer tokenizer = new StringTokenizer(str);
		List<Term> terms = new LinkedList<Term>();
		while (tokenizer.hasMoreElements()) {
			terms.add(buildTerm(tokenizer.nextToken()) );
		}
		return CompoundTerm.getList(terms); 
	}
	
}
