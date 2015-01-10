package common.handler;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MTConfigHandler {

	public static Configuration configuration;
	
	public static int MetalBlockBlastResistance;

	public static void init(File config) {//call this in init method of ModularTeleporters
		if(configuration == null) {
			configuration = new Configuration(config);
		} else {
			loadConfiguration();
		}
		
	}
	
	public static void loadConfiguration() {
		//set config fields to configuration.get>Value<()
		MetalBlockBlastResistance = configuration.getInt("Block of Lead blast resistance", Configuration.CATEGORY_GENERAL, 6000, 4, 10000, "Sets the blast resistance of the block of lead");
		
		if (configuration.hasChanged()){
			configuration.save();
		}
	}
	@SubscribeEvent
	public static void onConfigChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MODID)){
			loadConfiguration();
		}
	}
}
