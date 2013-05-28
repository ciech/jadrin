package main.jadrin.ontology;
import java.util.logging.Level;

import jade.content.onto.Ontology;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.util.Logger;


public class DrinkOntology extends BeanOntology {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String NAME = "drink-ontology";
    private static final Ontology instance = new DrinkOntology();
    private DrinkOntology() {
        super(NAME);
        try {
            add(Recipe.class);
            add(Ingredient.class);
            add(Drink.class);
        } catch (BeanOntologyException ex) {
            Logger.getLogger(DrinkOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Ontology getInstance() {
        return instance;
    }
}
