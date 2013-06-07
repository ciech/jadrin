package main.jadrin.ontology;

import java.util.LinkedList;

import jade.content.AgentAction;

public class DrinkResponse implements AgentAction{
	
	private static final long serialVersionUID = 4456545349864367654L;
	private Drink askedFor;
	private DrinkResponseType type;
	private LinkedList<Drink> results;
	
	public Drink getAskFor() {
		return askedFor;
	}
	public void setAskFor(Drink askFor) {
		this.askedFor = askFor;
	}
	public DrinkResponseType getType() {
		return type;
	}
	public void setType(DrinkResponseType type) {
		this.type = type;
	}
	
	public LinkedList<Drink> getResults() {
		return results;
	}
	public void setResults(LinkedList<Drink> results) {
		this.results = results;
	}
	
	

}
