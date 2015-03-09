package shibe.croberson.modularteleporters.common;

import net.minecraftforge.common.MinecraftForge;
import shibe.croberson.beefcore.core.multiblock.MultiblockEventHandler;
import shibe.croberson.modularteleporters.common.block.MTBlocks;
import shibe.croberson.modularteleporters.common.fluid.MTFluids;
import shibe.croberson.modularteleporters.common.handler.MTConfigHandler;
import shibe.croberson.modularteleporters.common.item.MTItems;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystAccessPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystGlass;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityCatalystPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterGlass;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterRotorBearing;
import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = "client.MtGuiConfigFactory")
public class ModularTeleporters{
	
	@SidedProxy(clientSide = "shibe.croberson.modularteleporters.client.ClientProxy", serverSide = "shibe.croberson.modularteleporters.common.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance(Reference.MODID)
	public static ModularTeleporters instance;
	
	private MultiblockEventHandler multiblockEventHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		MTConfigHandler.init(event.getSuggestedConfigurationFile());
		MTConfigHandler.loadConfiguration();
		proxy.preinit();
		
		multiblockEventHandler = new MultiblockEventHandler();
		MinecraftForge.EVENT_BUS.register(multiblockEventHandler);
		MinecraftForge.EVENT_BUS.register(proxy);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		MTItems.init();
		MTBlocks.init();
		MTFluids.init();
		proxy.init();
		registerTileEntities();
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityTeleporterFluidPort.class, "MTTileEntityTeleporterFluidPort");
		GameRegistry.registerTileEntity(TileEntityTeleporterGlass.class, "MTTileEntityTeleporterGlass");
		GameRegistry.registerTileEntity(TileEntityTeleporterPart.class, "MTTileEntityTeleporterpart");
		GameRegistry.registerTileEntity(TileEntityTeleporterRotorBearing.class, "MTTileEntityTeleporterRotorBearing");
		
		GameRegistry.registerTileEntity(TileEntityCatalystFluidPort.class, "MTTileEntityCatalystFluidPort");
		GameRegistry.registerTileEntity(TileEntityCatalystAccessPort.class, "MTTileEntityCatalystAccessPort");
		GameRegistry.registerTileEntity(TileEntityCatalystGlass.class, "MTTileEntityCatalystGlass");
		GameRegistry.registerTileEntity(TileEntityCatalystPart.class, "MTTileEntityCatalystPart");
		
	}
}
