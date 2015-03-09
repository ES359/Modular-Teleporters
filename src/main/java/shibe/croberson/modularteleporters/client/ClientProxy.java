package shibe.croberson.modularteleporters.client;

import shibe.croberson.beefcore.core.multiblock.MultiblockClientTickHandler;
import shibe.croberson.modularteleporters.common.CommonProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy{//never really used proxies before
	
	@Override
	public void preinit() {
		
	}
	
	@Override
	public void init() {
		super.init();

		FMLCommonHandler.instance().bus().register(new MultiblockClientTickHandler());
	}
	
	
}
