package shibe.croberson.modularteleporters.utils;

import java.util.Random;

public class MtMathHelper {
	
	public static Random random = new Random();
	/** not entirely random, -1: 66%, 1 33% **/
	public static int getRandNegativePositive() { 
		int x = random.nextInt(2) - 2;
		return x == 0 ? x - 1 : x;
	}
	
	
}
