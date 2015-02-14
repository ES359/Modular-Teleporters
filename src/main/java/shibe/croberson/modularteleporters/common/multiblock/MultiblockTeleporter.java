package shibe.croberson.modularteleporters.common.multiblock;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.beefcore.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.IActivateable;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.ITickableMultiblockPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterGlass;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterRotorBearing;
//Most of this is taken from bigreactors
public class MultiblockTeleporter extends RectangularMultiblockControllerBase implements IFluidHandler, IActivateable{
	
	//UI updates
	private Set<EntityPlayer> updatePlayers;
	private int ticksSinceLastUpdate;
	private static final int tickBetweenUpdates = 3;
	
	//fluid tank
	public static final int TANK_SIZE = 16000; //tank size stays final for now, but will need to adjust due to size
	public static final int MAX_PERMITTED_FLOW = 2000;
	public static final int WORK_NECESSARY = 4000; //4000 units of work, genius
	private FluidTank _tank;
	
	//parts
	private Set<IMultiblockPart> attachedControllers;
	private Set<ITickableMultiblockPart> attachedTickables;
	private Set<TileEntityTeleporterGlass> attachedGlass;
	private Set<TileEntityTeleporterFluidPort> attachedFluidPorts;
	private Set<TileEntityTeleporterRotorBearing> attachedRotorBearings;
	
	//game data
	private boolean active;
	private int maxIntakeRate;
	private int teleportWorkRequired;
	
	public MultiblockTeleporter(World world) {
		super(world);
		
		updatePlayers = new HashSet<EntityPlayer>();
		maxIntakeRate = MAX_PERMITTED_FLOW;
		_tank = new FluidTank(TANK_SIZE);
		ticksSinceLastUpdate = 0;
		
		attachedTickables = new HashSet<ITickableMultiblockPart>();
		attachedGlass = new HashSet<TileEntityTeleporterGlass>();
		attachedFluidPorts = new HashSet<TileEntityTeleporterFluidPort>();
		attachedRotorBearings = new HashSet<TileEntityTeleporterRotorBearing>();
		active = false;
		
		
	}
	
	protected void sendIndividualUpdate(EntityPlayer player) {
		if(this.worldObj.isRemote) { return; }

        //PacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)player);
	}

	//protected IMessage getUpdatePacket() {
    //    return new TurbineUpdateMessage(this);
	//}
	@Override
	public boolean getActive() {
		return active;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
		
	}
	
	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		readFromNBT(data);
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if (newPart instanceof TileEntityTeleporterFluidPort) {
			this.attachedFluidPorts.add((TileEntityTeleporterFluidPort)newPart);
		} 
		if (newPart instanceof ITickableMultiblockPart) {
			this.attachedTickables.add((ITickableMultiblockPart)newPart);
		} 
		if (newPart instanceof TileEntityTeleporterRotorBearing){ 
			this.attachedRotorBearings.add((TileEntityTeleporterRotorBearing)newPart);
		}
		if (newPart instanceof TileEntityTeleporterGlass){ 
			this.attachedGlass.add((TileEntityTeleporterGlass)newPart);
		}
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if (oldPart instanceof TileEntityTeleporterFluidPort) {
			this.attachedFluidPorts.remove((TileEntityTeleporterFluidPort)oldPart);
		}
		if (oldPart instanceof ITickableMultiblockPart){
			this.attachedTickables.remove((ITickableMultiblockPart)oldPart);
		}
		if (oldPart instanceof TileEntityTeleporterRotorBearing){ 
			this.attachedRotorBearings.remove((TileEntityTeleporterRotorBearing)oldPart);
		}
		if (oldPart instanceof TileEntityTeleporterGlass){ 
			this.attachedGlass.remove((TileEntityTeleporterGlass)oldPart);
		}
	}

	@Override
	protected void onMachineAssembled() {
		
	}

	@Override
	protected void onMachineRestored() {
		
	}

	@Override
	protected void onMachinePaused() {
		
	}

	@Override
	protected void onMachineDisassembled() {
		
	}

	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() {
		return 0;
	}

	@Override
	protected int getMaximumXSize() {
		return 0;
	}

	@Override
	protected int getMaximumZSize() {
		return 0;
	}

	@Override
	protected int getMaximumYSize() {
		return 0;
	}

	@Override
	protected void onAssimilate(MultiblockControllerBase assimilated) {
		
	}

	@Override
	protected void onAssimilated(MultiblockControllerBase assimilator) {
		attachedControllers.clear();
		attachedRotorBearings.clear();
		attachedTickables.clear();
		
	}
	
	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) throws MultiblockValidationException {
		// We only allow air and functional parts in turbines.

		// Air is ok
		if(world.isAirBlock(x, y, z)) { return; }

		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x,y,z);

		// Everything else, gtfo
		throw new MultiblockValidationException(String.format("%d, %d, %d is invalid for a turbine interior. Only rotor parts, metal blocks and empty space are allowed.", x, y, z));
	}
	
	@Override
	protected boolean updateServer() {
		return false;
	}

	@Override
	protected void updateClient() {
		
		
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {

		
	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		 
		
	}

	@Override
	public void formatDescriptionPacket(NBTTagCompound data) {
		
		
	}

	@Override
	public void decodeDescriptionPacket(NBTTagCompound data) {
		
		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack fluid, boolean doFill) {
		if (!(canFill(from, fluid.getFluid()))) {
			return 0;
		}
		return _tank.fill(fluid, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack fluid, boolean doDrain) {
		if(canDrain(from, fluid.getFluid()) && fluid.getFluid() == _tank.getFluid().getFluid()){
			return _tank.drain(fluid.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if (canDrain(from, null)) {
			return _tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid.getName().equals("water");//use other fluids
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		FluidStack fluidStack = _tank.getFluid();
		if(fluidStack == null) {
			return false;
		}
		
		return fluidStack.getFluid().getID() == fluid.getID();
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidTankInfo[] info = {_tank.getInfo()};
		return info;
	}

	public FluidTankInfo getTankInfo(int tankIdx) {
		return _tank.getInfo();
	}
	

	

}
