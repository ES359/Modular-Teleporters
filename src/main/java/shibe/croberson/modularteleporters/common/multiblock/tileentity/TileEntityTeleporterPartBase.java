package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import net.minecraft.entity.player.InventoryPlayer;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.beefcore.core.multiblock.rectangular.RectangularMultiblockTileEntityBase;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.IMultiblockGuiHandler;

public class TileEntityTeleporterPartBase extends RectangularMultiblockTileEntityBase implements IMultiblockGuiHandler{

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
		return new MultiblockTeleporter(worldObj);
	}

	@Override
	public Class<? extends MultiblockControllerBase> getMultiblockControllerType() {
		return MultiblockTeleporter.class;
	}
	
	public MultiblockTeleporter getTeleporter() {
		return (MultiblockTeleporter)getMultiblockController();
	}

	@Override
	public Object getContainer(InventoryPlayer inventoryPlayer) {
		return null;
	}

	@Override
	public Object getGuiElement(InventoryPlayer inventoryPlayer) {
		return null;
	}
	
}
