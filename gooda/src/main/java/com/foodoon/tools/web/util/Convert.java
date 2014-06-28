package com.foodoon.tools.web.util;

public class Convert {
	
	public static int toInt(Object obj){
		if(obj == null){
			return 0;
		}
		try{
		    return Integer.parseInt(obj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}

}
