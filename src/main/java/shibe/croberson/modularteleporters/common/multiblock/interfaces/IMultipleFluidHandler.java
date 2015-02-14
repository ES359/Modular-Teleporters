package shibe.croberson.modularteleporters.common.multiblock.interfaces;

import net.minecraftforge.fluids.FluidTankInfo;

/**
 * A subset of the Forge fluid interface, specifically made for GUI use.
 * @author Erogenous Beef
 *
 */
public interface IMultipleFluidHandler {
	public FluidTankInfo[] getTankInfo();
}