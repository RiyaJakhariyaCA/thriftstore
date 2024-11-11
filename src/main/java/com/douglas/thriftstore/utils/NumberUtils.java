package com.douglas.thriftstore.utils;

import java.util.Random;

public class NumberUtils {
	
	public static int  getStreamOfRandomInts() {
	   Random rnd = new Random();
	   return  100000 + rnd.nextInt(900000) ;
	}

}
