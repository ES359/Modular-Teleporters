package common.block;

import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class MTBlocks {
	
	public static BlockMetal blockMetal = new BlockMetal();
	
	public static void init(){
		//GameRegisry
		GameRegistry.registerBlock(blockMetal, "blockMetal");
		//OreDictionary
		String name; 
		for (int i = 0; i < BlockMetal.subBlocks.length; i++) {
			name = BlockMetal.subBlocks[i];
			OreDictionary.registerOre("block" + name, blockMetal.getItemStackForName(name));
		}
		
	}
	
	
}
