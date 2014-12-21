package common.item;

import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MTItems {
	
	public static ItemIngot itemIngot = new ItemIngot();
	
	public static void init(){
		//GameRegistry
		GameRegistry.registerItem(itemIngot, "itemIngot");
		
		//OreDictionary
		String itemName;
		for(int i = 0; i < ItemIngot.subTypes.length; i++) {
			itemName = ItemIngot.subTypes[i];
			OreDictionary.registerOre("ingot" + itemName, itemIngot.getItemStackForType(itemName));
			
		}
	}
}
