package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.modularteleporters.common.block.BlockTeleporterPart;

public class TileEntityTeleporterPart extends TileEntityTeleporterPartBase{
	
	public TileEntityTeleporterPart() {
		super();
	}
	
	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		if(getBlockMetadata() != BlockTeleporterPart.TELEPORTER_CASING) {
			throw new MultiblockValidationException(String.format("%d, %d, %d - only teleporter housing may be used as part of the teleporter's frame", xCoord, yCoord, zCoord));
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
}
