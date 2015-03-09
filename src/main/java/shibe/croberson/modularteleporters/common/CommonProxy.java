package shibe.croberson.modularteleporters.common;

import shibe.croberson.beefcore.core.multiblock.MultiblockServerTickHandler;
import cpw.mods.fml.common.FMLCommonHandler;

public class CommonProxy {
	
	public void preinit() {
		
	}
	
	public void init() {
		  FMLCommonHandler.instance().bus().register(new MultiblockServerTickHandler());
		  
		  
	}
	
	
	
}
