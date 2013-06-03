package main.jadrin.bartender;

import main.jadrin.ontology.QueryOntology;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WhatIsThat extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4871623810810380962L;
	
	WhatIsThat(Agent agent) {
		super(agent);
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchOntology(QueryOntology.NAME);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate mt3 = MessageTemplate.and(mt, mt2);
		ACLMessage msg = myAgent.blockingReceive(mt3);
		if (msg != null) {
			String whatIsThis = msg.getContent();
			String result;
			if (whatIsThis.equals("mojito"))
			{
				result = "drink";
			}
			else
			{
				result = "unknown";
			}
		    ACLMessage reply = msg.createReply();
		    
		    // check what means item here
		    
		    reply.setPerformative(ACLMessage.INFORM);
		    reply.setContent(result);
		    myAgent.send(reply);
		}
		else {
			block();
		}
	}

}
