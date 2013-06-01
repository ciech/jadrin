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
    private static final long serialVersionUID = 1L;

    
    protected void setup() 
    { 
      	String serviceName = "Bartender";
      	
      	// Register the service
      	System.out.println("Agent "+getLocalName()+" registering service \""+serviceName+"\" of type \"drink-knowledge\"");
      	try {
      		DFAgentDescription dfd = new DFAgentDescription();
      		dfd.setName(getAID());
      		ServiceDescription sd = new ServiceDescription();
      		sd.setName(serviceName);
      		sd.setType("drink-knowledge");
      		sd.addOntologies("jadrin-ontology");
      		sd.addLanguages(FIPANames.ContentLanguage.FIPA_SL);
      		sd.addProperties(new Property("country", "Poland"));
      		dfd.addServices(sd);
      		
      		DFService.register(this, dfd);
      	}
      	catch (FIPAException fe) {
      		fe.printStackTrace();
      	}
    }
}
