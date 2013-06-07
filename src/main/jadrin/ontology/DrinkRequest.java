package main.jadrin.ontology;

import jade.content.AgentAction;

public class DrinkRequest implements AgentAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6180271888356439137L;
	private Drink askFor;
	private DrinkRequestType type;
	
	public Drink getAskFor() {
		return askFor;
	}
	public void setAskFor(Drink askFor) {
		this.askFor = askFor;
	}
	public DrinkRequestType getType() {
		return type;
	}
	public void setType(DrinkRequestType type) {
		this.type = type;
	}
	
	

}
