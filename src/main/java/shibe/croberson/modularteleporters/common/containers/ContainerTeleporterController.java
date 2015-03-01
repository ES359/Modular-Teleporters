package shibe.croberson.modularteleporters.common.containers;

import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerTeleporterController extends Container{

	TileEntityTeleporterPart part;

	public ContainerTeleporterController(TileEntityTeleporterPart teleporterPart, EntityPlayer player) {
		part = teleporterPart;
		
		part.getTeleporter().beginUpdatingPlayer(player);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void putStackInSlot(int slot, ItemStack stack) {
		return;
	}
	
	@Override
    public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		
		if(part != null && part.getTeleporter() != null)
			part.getTeleporter().stopUpdatingPlayer(player);
	}
}
