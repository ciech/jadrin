package main.jadrin.tools;

import java.util.ArrayList;

import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;

public class Tokenizer {
	
	public static String[] getTokens(IStemmer s, String sentence, String[] permitted)
	{
		ArrayList<String> result = new ArrayList<String>();
		String[] temp = sentence.split("[ ~@#$^&*()+=\\[\\]{}|\\\\,.?:;,\"]");
		
		String[] temp2;
		for(int i =0; i < temp.length ; i++)
		 {
			if(temp[i].length() > 0)
			{
			 temp2 = stem(s, temp[i], permitted);
			 if(temp2.length > 0)
			    result.add(temp2[0]);
			}
		 }
		
		return result.toArray(new String[result.size()]);
	}
	
    public static String[] stem(IStemmer s, String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (WordData wd : s.lookup(word)) {
            result.add(asString(wd.getStem()));
            result.add(asString(wd.getTag()));
        }
        return result.toArray(new String[result.size()]);
    }
    
    public static String[] stem(IStemmer s, String word, String[] notPermitted) {
        ArrayList<String> result = new ArrayList<String>();
        Boolean isPermitted = false;
        for (WordData wd : s.lookup(word)) {
        	String[] tags = asString(wd.getTag()).split(":");
        	if(IsTagPermitted(tags, notPermitted))
        	{
        		isPermitted = true;
        		continue;
        	}
            result.add(asString(wd.getStem()));
            result.add(asString(wd.getTag()));
        }
        if(!isPermitted)
        	result.add(word);
        return result.toArray(new String[result.size()]);
    }
    
    private static Boolean IsTagPermitted(String[] tags, String[] notPermitted)
    {
    	if(tags.length > 0)
    	{
    		for(int i =0; i < notPermitted.length ; i++)
	   		{
    			if(notPermitted[i].equals(tags[0]))
    				return true;
	   		}
    	}
    	return false;
    }
    
    /* */
    public static String asString(CharSequence s) {
        if (s == null)
            return null;
        return s.toString();
    }
}
