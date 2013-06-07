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
	private static final long serialVersionUID = 3117856331840314129L;

	/**
     * 
     */
    public static final String NAME = "query-ontology";

    private static final Ontology instance = new QueryOntology();

    private QueryOntology() {
        super(NAME);
        try {
        	add(CheckElement.class);
        } catch (BeanOntologyException ex) {
            Logger.getLogger(QueryOntology.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Ontology getInstance() {
        return instance;
    }
}
