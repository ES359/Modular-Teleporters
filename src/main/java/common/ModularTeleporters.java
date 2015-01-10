package common;

import reference.Reference;
import common.block.MTBlocks;
import common.handler.MTGuiConfigHandler;
import common.handler.MTConfigHandler;
import common.item.MTItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = "client.MtGuiConfigFactory")//"I'm making a note here... we need to reformat the packages so they arent just "common.block.etc"
public class ModularTeleporters{
	
	@Instance(Reference.MODID)
	public static ModularTeleporters instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MTConfigHandler.init(event.getSuggestedConfigurationFile());
		MTConfigHandler.loadConfiguration();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MTItems.init();
		MTBlocks.init();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
}
