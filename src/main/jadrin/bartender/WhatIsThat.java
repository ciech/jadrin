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

	WhatIsThat(BartenderAgent agent) {
		super(agent);
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchOntology(QueryOntology.NAME);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate mt3 = MessageTemplate.and(mt, mt2);
		BartenderAgent bartender = (BartenderAgent)myAgent;
		ACLMessage msg = myAgent.blockingReceive(mt3);
		if (msg != null) {
			String objectName = msg.getContent();
//			String result = bartender.whatIsThat(objectName);
			String result = null;
			String[] test = {"wodka"};
			bartender.getDrinksWithGivenIngredients(test);
			
			ACLMessage reply = msg.createReply();

			reply.setPerformative(ACLMessage.INFORM);
			reply.setContent(result);
			reply.setContent(result);
			myAgent.send(reply);
		}
		else {
			block();
		}
	}

}
