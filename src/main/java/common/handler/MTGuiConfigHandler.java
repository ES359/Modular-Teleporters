package common.handler;

import java.io.File;

import reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class MTGuiConfigHandler extends GuiConfig{
	
	public static Configuration configuration;
	
	public static int leadBlockBlastResistance;
	public static int osmiumBlockBlastResistance;
	public static int bismuthBlockBlastResistance;
	public static int platinumBlockBlastResistance;
	
	public MTGuiConfigHandler(GuiScreen parentScreen ) {
		super(parentScreen, new ConfigElement(configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(configuration.toString()));
	}
		
	public static void init(File config) {//call this in init method of ModularTeleporters
		if(configuration == null) {
			configuration = new Configuration(config);
		} else {
			loadConfiguration();
		}
		
	}
	
	public static void loadConfiguration() {
		//set config fields to configuration.get>Value<()
		leadBlockBlastResistance = configuration.getInt("Block of Lead blast resistance", Configuration.CATEGORY_GENERAL, 6000, 4, 10000, "Sets the blast resistance of the block of lead");
		osmiumBlockBlastResistance = configuration.getInt("Block of Osmium blast resistance", Configuration.CATEGORY_GENERAL, 43, 4, 10000, "Sets the blast resistance of the block of Osmium");
		bismuthBlockBlastResistance = configuration.getInt("Block of Bismuth blast resistance", Configuration.CATEGORY_GENERAL,((int) leadBlockBlastResistance * 0.86), 4, 10000, "Sets the blast resistance of the block of Bismuth");
	}
}
