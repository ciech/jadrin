package main.jadrin.ontology;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.StringTokenizer;

import jade.content.Concept;


public class Drink implements Concept, Serializable{
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

	//	try {
	//		File temp = File.createTempFile("tempfile", ".tmp");
	//		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
	//	    drink.writeFacts(bw);
	//	    bw.close();
	//	} catch (IOException e1) {
	//		e1.printStackTrace();
	//	} 
	
	public void writeIngredients(java.io.BufferedWriter out) throws IOException{
		String nameStr = serializeName();
		String ingredientsStr = serializeIngredients();
		out.write("ingredients(");
		out.write(nameStr);
		out.write(",");
		out.write(ingredientsStr);
		out.write(").");
		out.newLine();
	}
	
	public void writeRecipe(java.io.BufferedWriter out) throws IOException{
		String nameStr = serializeName();
		String recipeStr = serializeRecipe();
		out.write("recipe(");
		out.write(nameStr);
		out.write(",");
		out.write(recipeStr);
		out.write(").");
		out.newLine();
	}
	
	
	public void writeFacts(java.io.BufferedWriter out) throws IOException{
		
		
		writeIngredients(out);
		writeRecipe(out);

	
	}

	private String serializeName() {
		return serializeSimpleString(this.name).toLowerCase();
	}

	private String serializeSimpleString(String string){
		StringTokenizer tokenizer = new StringTokenizer(string);
		StringBuilder builder = new StringBuilder();
		builder.append("[");

		while(tokenizer.hasMoreTokens()){
			builder.append("['");
			builder.append(tokenizer.nextToken());
			builder.append("']");

			if(tokenizer.hasMoreTokens())
				builder.append(",");
		}

		builder.append("]");
		return builder.toString().toLowerCase();
	}

	private String serializeRecipe() {
		return "['" + this.recipe.getContent() + "']".toLowerCase();
	}

	private String serializeIngredients() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		Iterator<Ingredient> iterator =  this.ingredients.iterator();
		while(iterator.hasNext()){
			builder.append(serializeSimpleString(iterator.next().getName()));
			if(iterator.hasNext()){
				builder.append(",");
			}
		}

		builder.append("]");
		return builder.toString().toLowerCase();
	}


}