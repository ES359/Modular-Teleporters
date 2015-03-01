package shibe.croberson.modularteleporters.common.multiblock;

import io.netty.buffer.ByteBuf;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.MultiblockValidationException;
import shibe.croberson.beefcore.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.IActivateable;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.IMultipleFluidHandler;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.ITickableMultiblockPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterGlass;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterRotorBearing;
import shibe.croberson.modularteleporters.network.PacketHandler;
import shibe.croberson.modularteleporters.network.message.multiblock.TeleporterUpdateMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
//Most of this is taken from bigreactors
public class MultiblockTeleporter extends RectangularMultiblockControllerBase implements IMultipleFluidHandler, IActivateable{
	
	//UI updates
	private Set<EntityPlayer> updatePlayers;
	private int ticksSinceLastUpdate;
	private static final int tickBetweenUpdates = 3;
	
	//fluid tank
	public static final int TANK_SIZE = 16000; //tank size stays final for now, but will need to adjust due to size
	public static final int FLUID_NONE = -1;
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
	private static final int MAX_POTENTIAL_ENERGIES = 1 * 10^6; //measured in joules, max potential energies is 1 megajoule, scientific notation because I am lazy, This shouldn't be final and should adjust due to size of teleporter
	
	private boolean active;
	private float efficiency = 0.5F;
	private int maxIntakeRate;
	private int potentialEnergy;
	
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

        PacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)player);
	}
	
	protected void sendTickUpdate() {
		if(this.updatePlayers.size() <= 0) { return; }

		for(EntityPlayer player : updatePlayers) {
            PacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)player);
		}
	}
	
	protected IMessage getUpdatePacket() {
        return new TeleporterUpdateMessage(this);
	}
	
	//getters + setters
	@Override
	public boolean getActive() {
		return active;
	}
	
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}
	
	public int getEfficiency(int efficiency) {
		return efficiency;
	}
	
	public void setMaxIntakeRate(int rate) {
		this.maxIntakeRate = rate;
	}
	
	public int getMaxIntakeRate(int rate) {
		return maxIntakeRate;
	}
	
	public void setPotentialEnergy(int energies) {
		this.potentialEnergy = energies;
	}
	
	public float getPotentialEnergy() {
		return this.potentialEnergy;
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
	protected void isMachineWhole() throws MultiblockValidationException{
		if(attachedRotorBearings.size() != 1) {
			throw new MultiblockValidationException("Teleporters require exactly one Rotor bearing, Nimrod");
		}
		super.isMachineWhole();
		//basically look for blocks and stuff
		
		
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
	// Network Serialization
	/**
	* Used when dispatching update packets from the server.
	* @param buf ByteBuf into which the turbine's full status should be written
	*/
	public void cerealize(ByteBuf buf) {//dont ask me what this method does, I just copied it from beef and I have yet to figure it out
		// Capture compacted fluid data first
				int fluidID, fluidAmt; //teleporters only use one tank and only input. Catalysts? different story
				{
					FluidStack fluid;
					fluid = _tank.getFluid();
					
					if(fluid == null || fluid.amount <= 0) {
						fluidID = FLUID_NONE;
						fluidAmt = 0;
					}
					else {
						fluidID = fluid.getFluid().getID();
						fluidAmt = fluid.amount;
					}
					
				}

				// User settings
				buf.writeBoolean(active);
				buf.writeInt(maxIntakeRate);

				// Basic stats
				buf.writeInt(potentialEnergy);

				// Reportage statistics
				//buf.writeFloat(energyGeneratedLastTick);
				//buf.writeInt(fluidConsumedLastTick);
				//buf.writeFloat(rotorEfficiencyLastTick);
				
				// Fluid data
				buf.writeInt(fluidID);
				buf.writeInt(fluidAmt);
		
		
	}
	/**
	 * Used when a status packet arrives on the client.
	 * @param buf ByteBuf containing serialized turbine data
	 */
	public void decerealize(ByteBuf buf) {
		setActive(buf.readBoolean());
		setMaxIntakeRate(buf.readInt());
		setPotentialEnergy(buf.readInt());
		
		// Fluid data
		int fluidID = buf.readInt();
		int fluidAmt = buf.readInt();
		
		if(fluidID == FLUID_NONE || fluidAmt <= 0) {
			_tank.setFluid(null);
		}
		else {
			Fluid fluid = FluidRegistry.getFluid(fluidID);
			if(fluid == null) {
				//BRLog.warning("[CLIENT] Multiblock Turbine received an unknown fluid of type %d, setting input tank to empty", fluidID);
				_tank.setFluid(null);
			}
			else {
				_tank.setFluid(new FluidStack(fluid, fluidAmt));
			}
		}
	}
	
	public int fill(FluidStack fluid, boolean doFill) {
		if (!(canFill(fluid.getFluid()))) {
			return 0;
		}
		return _tank.fill(fluid, doFill);
	}

	
	public FluidStack drain(FluidStack fluid, boolean doDrain) {
		if(canDrain(fluid.getFluid()) && fluid.getFluid() == _tank.getFluid().getFluid()){
			return _tank.drain(fluid.amount, doDrain);
		}
		return null;
	}

	
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (canDrain(null)) {
			return _tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	
	public boolean canFill(Fluid fluid) {
		return fluid.getName().equals("water");//use other fluids
	}


	public boolean canDrain(Fluid fluid) {
		FluidStack fluidStack = _tank.getFluid();
		if(fluidStack == null) {
			return false;
		}
		
		return fluidStack.getFluid().getID() == fluid.getID();
	}

	public FluidTankInfo getTankInfo(int tankIdx) {
		return _tank.getInfo();
	}

	@Override
	public FluidTankInfo[] getTankInfo() {
		FluidTankInfo[] info = {_tank.getInfo()};
		return info;
	}
	

	

}
