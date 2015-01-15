package shibe.croberson.modularteleporters.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import shibe.croberson.modularteleporters.common.ModularTeleporters;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler{

	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(ModularTeleporters.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { //return Containers
		switch(ID) {
		case 1: 
			
		
		
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { //return GUIs
		switch(ID) {
		case 1:
		
		}
		return null;
	}
}