package main.jadrin.bartender;
import jade.core.Agent;

public class BartenderAgent extends Agent { 
      /**
       * 
       */
    private static final long serialVersionUID = 1L;

    
    protected void setup() 
    { 
        System.out.println("Bartender setup: "+getAID().getName());
    }
}
