package com.cascade;

import java.util.ArrayList;

public class Queue {
	private static ArrayList<String> queue = new ArrayList<String>();
	
	public static void push(String value)
	{
		queue.add(value);
	}
	
	public static String pop()
	{
		if(queue.size()==0)
			return "null";
		String value = queue.get(0);
		queue.remove(0);
		return value;
	}
}
