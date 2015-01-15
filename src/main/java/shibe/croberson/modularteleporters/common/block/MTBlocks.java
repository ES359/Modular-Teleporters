package shibe.croberson.modularteleporters.common.block;

import net.minecraftforge.oredict.OreDictionary;
import shibe.croberson.modularteleporters.common.handler.MTConfigHandler;
import shibe.croberson.modularteleporters.common.item.ItemBlockModularTeleporters;
import cpw.mods.fml.common.registry.GameRegistry;

public class MTBlocks {
	
	public static BlockMetal blockMetal = new BlockMetal();
	
	public static void init() {
		//GameRegisry
		GameRegistry.registerBlock(blockMetal, ItemBlockModularTeleporters.class, "blockMetal");
		//OreDictionary
		String name; 
		for (int i = 0; i < BlockMetal.subBlocks.length; i++) {
			name = BlockMetal.subBlocks[i];
			OreDictionary.registerOre("block" + name, blockMetal.getItemStackForName(name));
		}
		
	}
	
	public static void registerProperties() { 
		blockMetal.setHardness(MTConfigHandler.MetalBlockBlastResistance);
		
	}
	
}
