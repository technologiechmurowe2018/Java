package mvc.controller;

import java.util.TreeMap;

public class Token {
	public static volatile TreeMap<String,Long> tokens  = new TreeMap<String,Long>();
}
