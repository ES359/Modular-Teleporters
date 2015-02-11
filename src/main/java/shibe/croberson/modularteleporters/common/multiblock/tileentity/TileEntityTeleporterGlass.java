package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;

public class TileEntityTeleporterGlass extends TileEntityTeleporterPartBase{
	@Override
	public void isGoodForFrame()  throws MultiblockValidationException {
		throw new MultiblockValidationException(String.format("%d, %d, %d - Teleporter glass may only be used on the exterior faces, not as part of a reactor's frame or interior", xCoord, yCoord, zCoord));
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
		throw new MultiblockValidationException(String.format("%d, %d, %d - Teleporter glass may only be used on the exterior faces, not as part of a reactor's frame or interior", xCoord, yCoord, zCoord));
	}

	@Override
	public void onMachineActivated() {
	}

	@Override
	public void onMachineDeactivated() {
	}
	
}
