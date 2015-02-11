package shibe.croberson.modularteleporters.common.block;

import net.minecraftforge.oredict.OreDictionary;
import shibe.croberson.modularteleporters.common.handler.MTConfigHandler;
import shibe.croberson.modularteleporters.common.item.ItemBlockModularTeleporters;
import cpw.mods.fml.common.registry.GameRegistry;

public class MTBlocks {
	
	public static BlockMetal blockMetal = new BlockMetal();
	public static BlockMultiblockGlass blockMultiblockGlass = new BlockMultiblockGlass();
	
	public static void init() {
		//GameRegistry
		GameRegistry.registerBlock(blockMetal, ItemBlockModularTeleporters.class, "blockMetal");
		GameRegistry.registerBlock(blockMultiblockGlass, ItemBlockModularTeleporters.class,"blockMultiblockGlass");
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
