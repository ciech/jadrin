package main.jadrin.bartender;
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
      	environment.ensureLoaded(AtomTerm.get("/jadrin/res/knowledge.pl"));
      	environment.ensureLoaded(AtomTerm.get("/jadrin/res/rules.pl"));
      	this.interpreter = environment.createInterpreter();
      	environment.runInitialization(interpreter);
      	//    	
    }
    
    public String whatIsThat(String name){
    	
//    	Term[] args = { AtomTerm.get(name)};
//    	try {
//    		//CompoundTerm goalTerm = CompoundTerm
////    		CompoundTerm goalTerm = CompoundTerm(AtomTerm.get("is_ingredient"), args);
//    	//	int rc = interpreter.execute(goalTerm);
////    		PrologCode.SUCCESS;0
////    		PrologCode.SUCCESS_LAST;1
////    		 PrologCode.FAIL; -1
////    		 PrologCode.HALT; -2
//    		int i = 0;
//    		i++;
//		} catch (PrologException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
    	return null;
    	
    }

}
