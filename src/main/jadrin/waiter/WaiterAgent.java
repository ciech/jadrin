package main.jadrin.waiter;
import jade.core.Agent;

public class WaiterAgent extends Agent { 
      /**
       * 
       */
    private static final long serialVersionUID = 1L;

    
    protected void setup() 
    { 
        System.out.println("Waiter setup: "+getAID().getName());
    }
}
