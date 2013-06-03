package main.jadrin.bartender;
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
    }
}
