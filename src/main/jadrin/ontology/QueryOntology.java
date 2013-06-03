package main.jadrin.ontology;

import java.util.logging.Level;

import jade.content.onto.Ontology;
import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import jade.util.Logger;


public class QueryOntology extends BeanOntology {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final String NAME = "query-ontology";

    private static final Ontology instance = new QueryOntology();

    private QueryOntology() {
        super(NAME);
        try {
        //	add(PartOfIngredient.class);
       // 	add(PartOfDrink.class);
            add(Ingredient.class);
            add(Drink.class);
            add(Unknown.class);
        } catch (BeanOntologyException ex) {
            Logger.getLogger(DrinkOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Ontology getInstance() {
        return instance;
    }
}
