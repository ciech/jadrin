package main.jadrin.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import main.jadrin.ontology.Drink;
import main.jadrin.ontology.Ingredient;
import main.jadrin.ontology.Recipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser_koktajlbar_pl implements PageParser{

	static public String PAGE_TO_PARSE = "http://www.koktajlbar.pl/cgi-bin/search2.cgi?Q=g4&P=1&RP=101";
	static public String PARSER_NAME = "koktajl_pl";
	
	static ArrayList<Drink> drinkCache = null;
	
	public String getParserName() {
		return PARSER_NAME;
	}
	
	static public Drink parseDrinkPage(String pageAddr)
	{
		try {
			
			Drink drink = new Drink();
			Document doc;
			doc = Jsoup.connect(pageAddr).get();
			
			Elements desc = doc.getElementsByClass("koktajl-box-name");
			Element descOne = desc.get(0);
			Elements titleContainer = descOne.getElementsByTag("h1");
			String titleTemp = titleContainer.get(0).html();
			int titleEnd = titleTemp.indexOf("<br");
			if (titleEnd < 3)
			{
				System.out.println("Uwaga: zły format strony: " + pageAddr);
				return null;
			}
			
			String title = titleTemp.substring(0, titleEnd);
			drink.setName(title);
			
			String text = descOne.html();
			text = text.substring(text.indexOf("</h1>")+"</h1>".length(),text.length());
			text = text.substring(0,text.indexOf("<u>"));
			text = text.replace("<br />","|");
			text = text.substring(1);
			text = text.substring(text.indexOf("|")+1, text.length());
			Document temp = Jsoup.parse(text);
			text = temp.text();
			text = AlphabetNormalizer.unAccent(text.replace("&oacute;", "o"));
			String[] ingredients = text.split("\\|");
			ArrayList<Ingredient> ingren = new ArrayList<Ingredient>();
			for(String ing : ingredients)
			{
				if (ing.length() > 1)
				{
					Ingredient i = new Ingredient();
					if (ing.indexOf("-") > 0)
					{
						i.setName(ing.substring(ing.indexOf("-")+1,ing.length()));
					}
					else
					{
						i.setName(ing);
					}
					ingren.add(i);
				}
			}
			drink.setIngredients(ingren);
			Elements r = doc.getElementsByClass("koktajl-box").get(0).getElementsByTag("tr");
			Element rec = r.get(1).getElementsByTag("td").get(0);
			Recipe recipe = new Recipe();
			String recStr = rec.ownText();
			recStr = AlphabetNormalizer.unAccent(recStr.replace("&oacute;", "o"));
			recipe.setContent(recStr);
			drink.setRecipe(recipe);
			return drink;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return null;
	}
	
	static public ArrayList<Drink> parseListPage()
	{
		ArrayList<Drink> drinks = new ArrayList<Drink>();
		try {
	
			Document doc = Jsoup.connect(PAGE_TO_PARSE).get();
			
			Elements results = doc.getElementsByClass("result-box");
		
			for (Element elem : results)
			{
				Elements links = elem.getElementsByTag("a");
				Element link = links.get(0);
				Drink drink = parseDrinkPage(link.attr("href"));
				if (drink!=null)
				{
					drinks.add(drink);
				}			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return drinks;
	}
	
	public static void main(String [] args)
	{
		System.out.println("Parsuję stronę " + PAGE_TO_PARSE  );
		System.out.println("Proszę czekać..");
		ArrayList<Drink> list = Parser_koktajlbar_pl.parseListPage();
		for (Drink drink : list)
		{
			System.out.println("Nazwa: " + drink.getName());
			if (drink.getIngredients() !=null)
			{
				System.out.println("Ilość składników: " + drink.getIngredients().size());	
			}
			if (drink.getRecipe() !=null)
			System.out.println("Opis: " + drink.getRecipe().getContent());
		}
	}

	@Override
	public ArrayList<Drink> parsePage() {
		if (drinkCache == null)
		{
			drinkCache = Parser_koktajlbar_pl.parseListPage();
		}
		return drinkCache;
	}
}
