package main.jadrin.bartender;

import gnu.prolog.vm.Environment;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

public class GetDrinksWithGivenIngredients extends CyclicBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3413424606194196859L;

	public GetDrinksWithGivenIngredients(Agent agent) {
		super(agent);
		new Environment();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

}
