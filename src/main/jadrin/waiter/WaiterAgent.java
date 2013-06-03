package main.jadrin.waiter;

import main.jadrin.ontology.QueryOntology;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.Property;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaiterAgent extends Agent { 

    /**
	 * 
	 */
	private static final long serialVersionUID = 1378662441382554121L;
	private WaiterAgentGui gui;
  
    protected void setup() 
    { 
		gui = new WaiterAgentGui(this);
		gui.showGui();	
    }

    private AID[] getBartendersList()
    {
    	DFAgentDescription template = new DFAgentDescription();
		ServiceDescription templateSd = new ServiceDescription();
		templateSd.setType("drink-knowledge");
		templateSd.addProperties(new Property("country", "Poland"));
		template.addServices(templateSd);
		AID aids[] = null;
     	try {
     		DFAgentDescription[] result = DFService.search(this, template);
        	aids = new AID[result.length];
     		for(int i=0; i<result.length; ++i)
    	    {
    	    	aids[i] = result[i].getName();
    	    }
     		return aids;
    	}
    	catch (FIPAException ex) {
    		ex.printStackTrace();
    	}
     	return aids;
    }

	public void analizeQuestion(String text) {
		AID bartenders[] = getBartendersList();
		if (bartenders != null)
		{
			for (AID bartender : bartenders)
			{
				System.out.println("Barman: " + bartender);
			}
		}
		
		// select proper bartender.. or ask all of them ?
		
		
		addBehaviour(new BuildQueryBehaviour(this,text,bartenders,gui));
	}
}
