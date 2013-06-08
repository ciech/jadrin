package main.jadrin.tools;

import java.text.Normalizer;
import java.util.regex.Pattern;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class AlphabetNormalizer {

  private static final Charset CHARSET = Charset.forName("UTF-8");
		
  public static String unAccent(String s) {
  	//forcing encoding to UTF-8, because values can be in other encoding types, so normalization can go wrong
		CharsetDecoder decoder = CHARSET.newDecoder();
		CharsetEncoder encoder = CHARSET.newEncoder();
		try {
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(s));		
		    CharBuffer cbuf = decoder.decode(bbuf);
		    s = cbuf.toString();
		} catch (CharacterCodingException e) {
		}
  	  
  	//nasty trick, for some reason Java Normalizer.Form.NFD does not normalize polish wird "l" letter
  	s = s.replace('ł', 'l').replace('Ł', 'L');	//for changing wird "l" in UTF-8
  	s = s.replace((char) 322, (char) 'l').replace((char) 321, (char) 'L');	//for changing wird "l" in HTML encoding
  	String result =   Normalizer
	           .normalize(s, Normalizer.Form.NFD)
	           .replaceAll("[^\\p{ASCII}]", "");
    return result;
  }
  	
}
