package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.modularteleporters.common.block.BlockCatalystPart;

public class TileEntityCatalystPart extends TileEntityCatalystPartBase{
	
	public TileEntityCatalystPart() {
		super();
		}
		
	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		if(getBlockMetadata() != BlockCatalystPart.METADATA_CASING) {
			throw new MultiblockValidationException(String.format("%d, %d, %d - only catalyst casing may be used as part of the catalyst's frame", xCoord, yCoord, zCoord));
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
	
	

