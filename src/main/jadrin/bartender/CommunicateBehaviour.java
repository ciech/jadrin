package main.jadrin.bartender;

import main.jadrin.ontology.DrinkOntology;
import main.jadrin.ontology.QueryOntology;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class CommunicateBehaviour extends CyclicBehaviour {/**
	 * 
	 */
	private static final long serialVersionUID = -6901017220902936123L;




public CommunicateBehaviour(BartenderAgent bartenderAgent) {
	super(bartenderAgent);
}




public void action() {
	MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	BartenderAgent bartender = (BartenderAgent)myAgent;

	ACLMessage msg = myAgent.blockingReceive(mt2);
	if (msg != null) {		
		if (msg.getOntology() == QueryOntology.NAME)
		{
			HandleCheckElement b = new HandleCheckElement(bartender,msg);
			b.action();
		}
		
		else if (msg.getOntology() == DrinkOntology.NAME)
		{
			HandleDrinkRequest h = new HandleDrinkRequest(bartender,msg);
			h.action();
		}
		else
		{
			ACLMessage reply = msg.createReply();
			reply.setPerformative(ACLMessage.UNKNOWN);
			myAgent.send(reply);
		}
	}
	else
	{
		block();
	}
				
	
}


}

