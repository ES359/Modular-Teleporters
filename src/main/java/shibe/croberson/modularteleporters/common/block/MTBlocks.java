package shibe.croberson.modularteleporters.common.block;

import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;
import shibe.croberson.modularteleporters.common.handler.MTConfigHandler;
import shibe.croberson.modularteleporters.common.item.ItemBlockModularTeleporters;
import cpw.mods.fml.common.registry.GameRegistry;

public class MTBlocks {
	
	public static BlockMetal blockMetal = new BlockMetal();
	public static BlockMultiblockGlass blockMultiblockGlass = new BlockMultiblockGlass(Material.glass);
	
	public static BlockTeleporterPart blockTeleporterPart = new BlockTeleporterPart(Material.iron);
	
	public static void init() {
		//GameRegistry
		GameRegistry.registerBlock(blockMetal, ItemBlockModularTeleporters.class, "blockMetal");
		GameRegistry.registerBlock(blockMultiblockGlass, ItemBlockModularTeleporters.class, "blockMultiblockGlass");
		GameRegistry.registerBlock(blockTeleporterPart, ItemBlockModularTeleporters.class, "blockTeleporterPart");
		System.out.println(blockTeleporterPart.getUnlocalizedName());
		//OreDictionary
		String name; 
		
		for(int i = 0; i < BlockTeleporterPart.subBlocks.length; i++) {
			name = BlockTeleporterPart.subBlocks[i];
			OreDictionary.registerOre("teleporter" + name, blockTeleporterPart.getItemStackForName(name));//I hope this works, beef didnt have it so I am confused if there is a reason why
		}
		
		for(int i = 0; i < BlockMetal.subBlocks.length; i++) {
			name = BlockMetal.subBlocks[i];
			OreDictionary.registerOre("block" + name, blockMetal.getItemStackForName(name));
		}
		
		OreDictionary.registerOre("glassTeleporter", blockMultiblockGlass.getItemStack("teleporter"));
		OreDictionary.registerOre("glassCatalyst", blockMultiblockGlass.getItemStack("catalyst"));
	}
	
	public static void registerProperties() { 
		blockMetal.setHardness(MTConfigHandler.MetalBlockBlastResistance);
		
	}
	
}
