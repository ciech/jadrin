package main.jadrin.tools;

import java.util.Hashtable;

public class ParserFactory
{
  private static Hashtable<String, PageParser> parsers;

  static
  {
	  parsers = new Hashtable<String, PageParser>();
	  parsers.put(Parser_drinkuj_pl.PARSER_NAME, new Parser_drinkuj_pl());
	  parsers.put(Parser_koktajlbar_pl.PARSER_NAME, new Parser_koktajlbar_pl());
  }

  public static PageParser getParser(String name)
  {
    if (!parsers.containsKey(name))
      return null;
    return parsers.get(name);
  }
}