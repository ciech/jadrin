package main.jadrin.tools;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;


public class Parser_drinkuj_pl {

	static public void parseDrinkPage(String pageAddr)
	{
		try {
			Document doc = Jsoup.connect(pageAddr).get();
			String titleFromAddr = pageAddr.replace("http://www.drinkuj.pl/drink","");
			titleFromAddr = titleFromAddr.replace(".html","");
			titleFromAddr = titleFromAddr.replace("_"," ");
			titleFromAddr = titleFromAddr.substring(1);
			System.out.println(titleFromAddr);
			Element main = doc.getElementById("main");
			Element right = main.child(2);

			Element recipeContainer = right.child(1);
			List<Element> potencialRecipe = recipeContainer .getElementsByClass("style1");
			Element recipe = potencialRecipe.get(0);
			List<Element> tags = recipe.getElementsByTag("br");
			for(Element tag : tags)
			{
				tag.remove();
			}
			String recipeStr = AlphabetNormalizer.unAccent(recipe.html().replace("&oacute;", "o"));
			System.out.println(recipeStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public void parseListPage()
	{
		try {
			Document doc = Jsoup.connect("http://www.drinkuj.pl/spis.php").get();
			
			Element main = doc.getElementById("main");
			Element element = main.child(0);
			List<Element> elements = element.getElementsByClass("header");
			for (Element elem : elements)
			{
				Element container = elem.nextElementSibling();
				List<Element> kols = container.children();
				for (Element kol : kols)
				{
					List<Element> links = kol.getElementsByTag("a");
					for (Element link : links)
					{
						parseDrinkPage(link.attr("href"));
					}
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args)
	{
		Parser_drinkuj_pl.parseListPage();
	}
}
