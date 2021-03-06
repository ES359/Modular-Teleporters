package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.beefcore.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockCatalyst;

public class TileEntityCatalystPartBase extends RectangularMultiblockTileEntityBase{

	@Override
	public void isGoodForFrame() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException {
		
	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException {
		
	}

	@Override
	public void onMachineActivated() {
		
	}

	@Override
	public void onMachineDeactivated() {
		
	}

	@Override
	public MultiblockControllerBase createNewMultiblock() {
		return new MultiblockCatalyst(worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return null;
	}
	
	public MultiblockCatalyst getCatalyst() {
		return (MultiblockCatalyst)getMultiblockController();
		
	}
	
}
