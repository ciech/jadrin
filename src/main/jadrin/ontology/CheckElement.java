package main.jadrin.ontology;

import jade.content.AgentAction;

public class CheckElement implements AgentAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839339267078088806L;
	
	private String name;
	private boolean isPartOf; 
	private Type type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPartOf() {
		return isPartOf;
	}
	public void setPartOf(boolean isPartOf) {
		this.isPartOf = isPartOf;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}


}
