package main.jadrin.ontology;
import java.util.ArrayList;
import jade.content.Concept;


public class Drink implements Concept{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private Recipe recipe;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
    
    public Recipe getRecipe(){
        return recipe;
    }
    
    public void setRecipe(Recipe recipe){
        this.recipe=recipe;
    }
    
    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }
    
    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }
}