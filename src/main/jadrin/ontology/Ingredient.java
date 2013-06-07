package main.jadrin.ontology;
import jade.content.Concept;

public class Ingredient implements Concept {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer amount; // in ml

    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    

    public Integer getAmount(){
        return amount;
    }
    
    public void setAmount(Integer amout){
        this.amount = amout;
    }
    
}

