package shibe.croberson.modularteleporters.network;

import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	//message registry should go here
	public static void init(){
		//INSTANCE.registerMessage(ExampleMessage.class, ExampleMessage.class, 0, Side.CLIENT);
		
		
	}
}
