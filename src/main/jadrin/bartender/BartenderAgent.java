package main.jadrin.bartender;
import java.net.URL;

import gnu.prolog.term.AtomTerm;
import gnu.prolog.term.CompoundTerm;
import gnu.prolog.term.DoubleQuotesTerm;
import gnu.prolog.term.IntegerTerm;
import gnu.prolog.term.Term;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Evaluate;
import gnu.prolog.vm.Interpreter;
import gnu.prolog.vm.Interpreter.Goal;
import gnu.prolog.vm.PrologCode;
import gnu.prolog.vm.PrologException;
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
      	registerService();
      	addBehaviour(new WhatIsThat(this)); // for NLP on waiter
      	addBehaviour(new GetDrinkRecipe(this)); // A case from documentation
      	addBehaviour(new GetDrinksWithGivenIngredients(this)); // B case from documentation
      	addBehaviour(new GetMissingIngredientsAndRecipe(this)); // C case from documentation 	
      	
      	//Prolog setup:
      	this.environment =  new Environment();
      	
      	URL knowledgeFile = getClass().getResource("/main/jadrin/resources/knowledge.pro");
      	URL rulesFile = getClass().getResource("/main/jadrin/resources/rules.pro");
      	environment.ensureLoaded(AtomTerm.get(knowledgeFile.getFile()));
      	environment.ensureLoaded(AtomTerm.get(rulesFile.getFile()));
      	
      	this.interpreter = environment.createInterpreter();
      	environment.runInitialization(interpreter);
      	//    	
    }
    
    public String whatIsThat(String name){
  	
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
			return "ingredient";

		if(isDrink == PrologCode.SUCCESS)
			return "drink";
		
    	return "uknown";  	
    }
    
   public String[] getDrinksWithGivenIngredients(String[] ingredients){
	  
	   
	   
	   String [] ret = new String[1];
	   ret[0] = "uknown";  	
	   return ret;
   }
   
   public String[] getDrinkRecipe(String[] ingredients){
	  
	   
	   
	   String [] ret = new String[1];
	   ret[0] = "uknown";  	
	   return ret;
   }
   
   public String[] getMissingIngredientsAndRecipe(String[] ingredients){
	  
	   
	   
	   String [] ret = new String[1];
	   ret[0] = "uknown";  	
	   return ret;
   }
   
}
