package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import com.sun.naming.internal.ResourceManager;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;

public class TileEntityTeleporterFluidPort extends TileEntityTeleporterPartBase implements IFluidHandler {
	
	public enum FluidFlow {
		in, out
	}
	
	FluidFlow flowSetting;
	
	public TileEntityTeleporterFluidPort() {
		super();
		flowSetting = FluidFlow.in;
		
	}
	public FluidFlow getFlowDirection() {
		return flowSetting;
	}
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(!isConnected() || from != getOutwardsDir() || flowSetting != FluidFlow.in) { return 0; }
		
		return getTeleporter().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource == null || !isConnected() || from != getOutwardsDir()) { return null; }
		return getTeleporter().drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(!isConnected() || from != getOutwardsDir()) { return null; }
		return getTeleporter().drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(!isConnected() || from != getOutwardsDir()) { return false; }
		return getTeleporter().canFill(fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(!isConnected() || from != getOutwardsDir()) { return false; }
		return getTeleporter().canDrain(fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		MultiblockTeleporter teleporter = getTeleporter();
		return null;
	}
	
}
