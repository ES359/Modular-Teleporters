package shibe.croberson.modularteleporters.client;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import shibe.croberson.modularteleporters.common.handler.MTGuiConfigHandler;
import cpw.mods.fml.client.IModGuiFactory;

public class MTGuiConfigFactory implements IModGuiFactory{

	@Override
	public void initialize(Minecraft minecraftInstance) {
		
	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass() {
		return MTGuiConfigHandler.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		
		return null;
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
		return null;
	}

}
