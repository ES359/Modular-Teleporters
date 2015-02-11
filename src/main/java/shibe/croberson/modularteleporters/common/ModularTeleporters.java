package shibe.croberson.modularteleporters.common;

import shibe.croberson.modularteleporters.common.block.MTBlocks;
import shibe.croberson.modularteleporters.common.handler.MTConfigHandler;
import shibe.croberson.modularteleporters.common.item.MTItems;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystRotorBearing;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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
		registerTileEntities();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTeleporterFluidPort.class, "MTTileEntityTeleporterFluidPort");
		
		GameRegistry.registerTileEntity(TileEntityCatalystFluidPort.class, "MTTileEntityCatalystFluidPort");
		GameRegistry.registerTileEntity(TileEntityCatalystRotorBearing.class, "MTTileEntityCatalystRotorBearing");
		
		
	}
}
