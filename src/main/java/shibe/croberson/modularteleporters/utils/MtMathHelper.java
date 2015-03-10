package shibe.croberson.modularteleporters.utils;

import java.util.Random;

public class MtMathHelper {
	private static Random random = new Random();
	/**
	 * 
	 * @param x
	 * @param shouldReturnSpecialZero Should this function return a zero if the value is exactly the middle of param 3, else, return positive
	 * @param bounds the chance the integer has of returning 0, example: 1000 would have a chance of returning a zero 1/1000% of the time
	 * @return
	 */
	public static int randomifySign(int x, boolean shouldReturnSpecialZero, int bounds) { 
		int rand = random.nextInt(bounds);
		if(shouldReturnSpecialZero) {
			
			if(x < bounds/2) 
			{
				return -x;
			} 
			else if(x > bounds/2)
			{
				return x;
			}
			else
			{
				return 0;
			}
			
		} else {
			return x < bounds/2 ? -x : x;
		}
		
	}
	
	
}
