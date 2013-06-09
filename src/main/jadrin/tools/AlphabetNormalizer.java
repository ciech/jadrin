package main.jadrin.tools;

import java.text.Normalizer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class AlphabetNormalizer {

  private static final Charset CHARSET = Charset.forName("UTF-8");
		
  public static String unAccent(String s) {
  		CharsetDecoder decoder = CHARSET.newDecoder();
		CharsetEncoder encoder = CHARSET.newEncoder();
		try {
		    ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(s));		
		    CharBuffer cbuf = decoder.decode(bbuf);
		    s = cbuf.toString();
		} catch (CharacterCodingException e) {
		}
  	  
  	s = s.replace('ł', 'l').replace('Ł', 'L');
  	s = s.replace((char) 322, (char) 'l').replace((char) 321, (char) 'L');
  	String result =   Normalizer
	           .normalize(s, Normalizer.Form.NFD)
	           .replaceAll("[^\\p{ASCII}]", "");
    return result;
  }
  	
}
