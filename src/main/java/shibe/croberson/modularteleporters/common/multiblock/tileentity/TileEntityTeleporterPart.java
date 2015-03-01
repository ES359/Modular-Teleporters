package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.modularteleporters.common.block.BlockTeleporterPart;
import shibe.croberson.modularteleporters.common.containers.ContainerTeleporterController;

public class TileEntityTeleporterPart extends TileEntityTeleporterPartBase{
	
	public TileEntityTeleporterPart() {
		super();
	}
	
	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		if(getBlockMetadata() != BlockTeleporterPart.METADATA_CASING) {
			throw new MultiblockValidationException(String.format("%d, %d, %d - only teleporter casing may be used as part of the teleporter's frame", xCoord, yCoord, zCoord));
		}
	}
	
	@Override
	public void isGoodForSides() { 
		
	}
	
	@Override
	public void isGoodForTop() {
		
	}
	
	@Override
	public void isGoodForBottom() {
		
	}
	
	@Override
	public Object getContainer(InventoryPlayer inventoryPlayer) {
		if(!this.isConnected()) {
			return null;
		}
		
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);		
		if(getBlockMetadata() != BlockTeleporterPart.METADATA_CONTROLLER) {
			return new ContainerTeleporterController(this, inventoryPlayer.player);
		}
		return null;
	}

	@Override
	public Object getGuiElement(InventoryPlayer inventoryPlayer) {
		if(!this.isConnected()) {
			return null;
		}
		
		int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);		
		if(getBlockMetadata() != BlockTeleporterPart.METADATA_CONTROLLER) {
			return new ContainerTeleporterController(this, inventoryPlayer.player);
		}
		return null;
	}
}
