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
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			String whatIsThis = msg.getContent();
			System.out.println("Co to jest: "+whatIsThis+ " ?");
		    ACLMessage reply = msg.createReply();
		    
		    // check what means item here
		    
		    reply.setPerformative(ACLMessage.INFORM);
		    reply.setContent("Unknown");
		    myAgent.send(reply);
		}
		else {
			block();
		}
	}

}
