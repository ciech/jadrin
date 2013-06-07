package main.jadrin.ontology;
import java.util.ArrayList;
import java.util.Arrays;

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

//	public Drink(String name, ArrayList<Ingredient> ingredients, String recipe) {
//		super();
//		this.name = name;
//		this.ingredients = ingredients;
//		this.recipe = recipe;
//	}
	
	public Drink(String name, String[] ingredients, String recipe) {
		super();
		this.name = name;
//		this.ingredients = new ArrayList<Ingredient>(Arrays.asList(ingredients));
		this.recipe = new Recipe();
		this.recipe.setContent(recipe);
		this.ingredients = new ArrayList<Ingredient>(ingredients.length);
		for (int i=0; i< ingredients.length; i++){
			Ingredient ingredient = new  Ingredient();
			ingredient.setName(ingredients[i]);
			this.ingredients.add(ingredient);
		}
	}
	
	public Drink(){
		super();
	}

}