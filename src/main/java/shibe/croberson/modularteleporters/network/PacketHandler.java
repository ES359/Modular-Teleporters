package shibe.croberson.modularteleporters.network;

import shibe.croberson.modularteleporters.network.message.multiblock.TeleporterUpdateMessage;
import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	//message registry should go here
	public static void init(){
		//INSTANCE.registerMessage(ExampleMessage.class, ExampleMessage.class, 0, Side.CLIENT);
		
		//SERVER >> CLIENT
		INSTANCE.registerMessage(TeleporterUpdateMessage.Handler.class, TeleporterUpdateMessage.class, 0, Side.CLIENT);
		
		//CLIENT >> SERVER
		
		
	}
}
