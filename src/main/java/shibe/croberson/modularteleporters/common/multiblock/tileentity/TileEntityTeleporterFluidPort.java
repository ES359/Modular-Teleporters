package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityTeleporterFluidPort extends TileEntityTeleporterPartBase implements IFluidHandler{
	
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
		 
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		 
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		 
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		 
		return false;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		 
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		
		return null;
	}
	
}
