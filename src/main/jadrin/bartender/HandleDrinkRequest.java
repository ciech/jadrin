package main.jadrin.bartender;

import java.util.LinkedList;

import main.jadrin.ontology.CheckElement;
import main.jadrin.ontology.Drink;
import main.jadrin.ontology.DrinkOntology;
import main.jadrin.ontology.DrinkRequest;
import main.jadrin.ontology.DrinkResponse;
import main.jadrin.ontology.DrinkResponseType;
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

public class HandleDrinkRequest extends CyclicBehaviour {

	private static final long serialVersionUID = -1131798599467868104L;


	HandleDrinkRequest(BartenderAgent agent) {
		super(agent);
	}

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchOntology(DrinkOntology.NAME);
		MessageTemplate mt2 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		MessageTemplate mt3 = MessageTemplate.and(mt, mt2);
		
		ACLMessage msg = myAgent.blockingReceive(mt3);
		DrinkRequest request = null;
		if (msg != null) {
			try {
				ContentElement ce =  myAgent.getContentManager().extractContent(msg);
				if (ce instanceof Action)
				{
					 request = (DrinkRequest)((Action) ce).getAction(); 
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
					

			switch(request.getType())
			{
			case FROM_INGREDIENTS:
				break;
			case FROM_NAME:
				break;
			case FROM_NAME_AND_INGREDIENTS:
				break;
			default:
				break;
				
			}

			DrinkResponse response = new DrinkResponse();
			response.setType(DrinkResponseType.UNKNOWN);
			ACLMessage reply = msg.createReply();
			AgentAction act = response;
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
