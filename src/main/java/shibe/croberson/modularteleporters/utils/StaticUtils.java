package shibe.croberson.modularteleporters.utils;

import net.minecraftforge.common.util.ForgeDirection;

/**basically a copy of http://tinyurl.com/p54xcrd**/
public class StaticUtils {
	
	public static final ForgeDirection neighborsBySide[][] = new ForgeDirection[][] {
		{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST},
		{ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST},
		{ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.EAST, ForgeDirection.WEST},
		{ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.WEST, ForgeDirection.EAST},
		{ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.NORTH, ForgeDirection.SOUTH},
		{ForgeDirection.UP, ForgeDirection.DOWN, ForgeDirection.SOUTH, ForgeDirection.NORTH}
	};
}
