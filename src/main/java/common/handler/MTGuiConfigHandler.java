package common.handler;

import java.io.File;

import reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MTGuiConfigHandler extends GuiConfig{
	
	public MTGuiConfigHandler(GuiScreen parentScreen ) {
		super(parentScreen, new ConfigElement(MTConfigHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MODID, false, false, GuiConfig.getAbridgedConfigPath(MTConfigHandler.configuration.toString()));
	}
		
}
