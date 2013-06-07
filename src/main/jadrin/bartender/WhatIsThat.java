package main.jadrin.bartender;

import java.util.LinkedList;

import main.jadrin.ontology.CheckElement;
import main.jadrin.ontology.Drink;
import main.jadrin.ontology.QueryOntology;
import jade.content.AgentAction;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
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
		CheckElement toCheck = null;
		if (msg != null) {
			try {
				ContentElement ce =  myAgent.getContentManager().extractContent(msg);
				if (ce instanceof Action)
				{
					 toCheck = (CheckElement)((Action) ce).getAction(); 
				}
	
			} catch (UngroundedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
			CheckElement result = bartender.whatIsThat(toCheck);	
//			String [] s ={"wodka"} ; 
//			LinkedList<Drink> d0 =  bartender.getDrinksWithGivenIngredients(s);
//			Drink d1 =  bartender.getDrinkRecipe("Mojito");
//			String [] s1 ={"rum"} ;
//			Drink d2 =  bartender.getMissingIngredientsAndRecipe(s1,"Mojito");
			
			ACLMessage reply = msg.createReply();
			AgentAction act = result;
			Action actOperator = new Action(msg.getSender(), act);
			try {
				myAgent.getContentManager().fillContent(reply,actOperator);
			} catch (CodecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OntologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
		}
		else {
			block();
		}
	}

}
