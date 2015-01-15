package shibe.croberson.modularteleporters.common.handler;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;

public class MTGuiConfigHandler extends GuiConfig{
	
	public MTGuiConfigHandler(GuiScreen parentScreen ) {
		super(parentScreen, new ConfigElement(MTConfigHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(MTConfigHandler.configuration.toString()));
	}
		
}
