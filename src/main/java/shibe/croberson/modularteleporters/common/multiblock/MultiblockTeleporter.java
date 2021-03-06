package shibe.croberson.modularteleporters.common.multiblock;

import io.netty.buffer.ByteBuf;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import shibe.croberson.beefcore.core.common.CoordTriplet;
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
import shibe.croberson.modularteleporters.reference.Reference;
import shibe.croberson.modularteleporters.utils.MtMathHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
//Most of this is taken from bigreactors
public class MultiblockTeleporter extends RectangularMultiblockControllerBase implements IMultipleFluidHandler, IActivateable{
	
	//UI updates
	private Set<EntityPlayer> updatePlayers;
	private int ticksSinceLastUpdate;
	private static final int tickBetweenUpdates = 3;
	
	//fluid tank
	public static int tankSize = 16000; //tank size stays final for now, but will need to adjust due to size
	public static final int FLUID_NONE = -1;
	public static final int MAX_PERMITTED_FLOW = 2000;
	private FluidTank _tank;
	
	//rotarycraft implementation
	/** torque is measured in Newton-meters **/
	public int torque;
	/** omega is rpm I guess **/
	public int omega;
	
	//parts
	private Set<IMultiblockPart> attachedControllers;
	private Set<ITickableMultiblockPart> attachedTickables;
	private Set<TileEntityTeleporterGlass> attachedGlass;
	private Set<TileEntityTeleporterFluidPort> attachedFluidPorts;
	private Set<TileEntityTeleporterRotorBearing> attachedRotorBearings;
	private Set<CoordTriplet> foundAir;
	//game data
	/**maximum amount of energy the teleporter is capable of storing **/
	public static int maximumPotentialEnergy;
	
	private boolean active;
	private float efficiency = 0.5F;
	private int maxIntakeRate;
	private int luck;
	private int potentialEnergy;
	private int defaultCoolDown;
	private int coolDown;
	private int accuracy;
	
	private int fluidConsumedLastTick;
	private int energyConsumedLastTick;
	
	private int destX, destY, destZ;
	
	
	public MultiblockTeleporter(World world) {
		super(world);
		
		updatePlayers = new HashSet<EntityPlayer>();
		maxIntakeRate = MAX_PERMITTED_FLOW;
		_tank = new FluidTank(tankSize);
		ticksSinceLastUpdate = 0;
		
		attachedTickables = new HashSet<ITickableMultiblockPart>();
		attachedGlass = new HashSet<TileEntityTeleporterGlass>();
		attachedFluidPorts = new HashSet<TileEntityTeleporterFluidPort>();
		attachedRotorBearings = new HashSet<TileEntityTeleporterRotorBearing>();
		foundAir = new HashSet<CoordTriplet>();
		tankSize = foundAir.size() * 8;
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
	
	public void setEfficiency(float efficiency) {
		this.efficiency = efficiency;
	}
	
	public float getEfficiency() {
		return efficiency;
	}
	
	public void setMaxIntakeRate(int rate) {
		this.maxIntakeRate = rate;
	}
	
	public int getMaxIntakeRate(int rate) {
		return maxIntakeRate;
	}
	/** used for rotarycraft to return rotational speed **/
	public int getOmega() {
		return omega;
	}
	/** used for rotarycraft to set rotational speed **/
	public void setOmega(int omega) {
		this.omega = omega;
	}
	/**used for rotarycraft to get torque **/
	public int getTorque() {
		return torque;
	}
	
	public void setTorque(int torque) {
		this.torque = torque;
	}
	
	public void setPotentialEnergy(int energies) {
		if (energies > getPotentialEnergy()) {
			return;
		}
		this.potentialEnergy = energies;
	}
	
	public int getPotentialEnergy() {
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
		potentialEnergy = 0; //kill energy
	}
	
	@Override
	protected void isMachineWhole() throws MultiblockValidationException{
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
		if (attachedControllers != null) {
			attachedControllers.clear();
		}
		if (attachedRotorBearings != null) {
			attachedRotorBearings.clear();
		}
		if (attachedTickables != null) {
			attachedTickables.clear();
		}
		
		
	}
	
	@Override
	protected void isBlockGoodForInterior(World world, int x, int y, int z) throws MultiblockValidationException {

		// Air is ok
		if(world.isAirBlock(x, y, z)) { 
			
			return;
		}

		// Everything else, gtfo
		throw new MultiblockValidationException(String.format("%d, %d, %d is invalid for a teleporter interior, metal blocks and empty space are allowed.", x, y, z));
	}
	
	@Override
	protected boolean updateServer() {
		fluidConsumedLastTick = 0;
		energyConsumedLastTick = 0;
		
		if(getActive()) { // where active is enabled, add an if statement to check if it has enough power or fluid or something
			
			if(coolDown <= 0) {
				
				for(int i = 0; i < updatePlayers.size(); i++) { //quick for loop, can't decide what entity to teleport, so just everyone that views the GUI for now.
					if (canTeleport((Entity) updatePlayers.toArray()[i], worldObj, destX, destY, destZ, accuracy)){
						teleport((Entity) updatePlayers.toArray()[i], this.worldObj, destX, destY, destZ);
					}
				}
				setActive(false);
				_tank.drain(getFluidAmountRequiredForTeleport(worldObj, _tank.getFluid().getFluid(), destX, destY, destZ), true);
				potentialEnergy -= this.getPowerRequiredForTeleport(worldObj, destX, destY, destZ);
				
				
			} else {
				coolDown--;
			}
		}
		
		
		
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
	
	protected void markReferenceCoordDirty() {
		if(worldObj == null || worldObj.isRemote) { return; }

		CoordTriplet referenceCoord = getReferenceCoord();
		if(referenceCoord == null) { return; }

		
		TileEntity saveTe = worldObj.getTileEntity(referenceCoord.x, referenceCoord.y, referenceCoord.z);
		worldObj.markTileEntityChunkModified(referenceCoord.x, referenceCoord.y, referenceCoord.z, saveTe);
		worldObj.markBlockForUpdate(referenceCoord.x, referenceCoord.y, referenceCoord.z);
	}
	
	// Network Serialization
	/**
	* Used when dispatching update packets from the server.
	* @param buf ByteBuf into which the turbine's full status should be written
	*/
	public void cerealize(ByteBuf buf) {
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
		if(doFill) {
			setEfficiency((Float) Reference.teleporterFluidEfficiencies.get(fluid.getFluid().getName())); //efficiency is based on the fluid inside the teleporter
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
		return Reference.teleporterFluidEfficiencies.containsKey(fluid.getName());
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
	/**
	 * Returns the power required to complete the teleport at that distance and accuracy
	 * @param world
	 * @param destX
	 * @param destY
	 * @param destZ
	 * @param accuracy
	 * @return
	 */
	public int getPowerRequiredForTeleport(World world, int destX, int destY, int destZ) {
		int teleporterX = getReferenceCoord().x;
		int teleporterY = getReferenceCoord().y;
		int teleporterZ = getReferenceCoord().z;
		int distance = (int) Math.sqrt((Math.pow((teleporterX - destX) ,2 ) + Math.pow(((teleporterY - destY)), 2) + Math.pow((teleporterZ - destZ), 2)));
		return (int)((Math.pow(distance, 2)) / (efficiency * 10));
		
	}
	
	public int getFluidAmountRequiredForTeleport(World world, Fluid fluid, int destX, int destY, int destZ) {
		int teleporterX = getReferenceCoord().x;
		int teleporterY = getReferenceCoord().y;
		int teleporterZ = getReferenceCoord().z;
		int distance = (int) Math.sqrt((Math.pow((teleporterX - destX) ,2 ) + Math.pow(((teleporterY - destY)), 2) + Math.pow((teleporterZ - destZ), 2)));
		return (int) ((Integer) Reference.teleporterFluidEfficiencies.get(fluid.getName()) * distance / accuracy);
	}
	/**
	 * Checks if the space provided is 2 high
	 * @param entity
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param accuracy
	 * @return
	 */
	public boolean isSafeLocationForSteve(World world, int x, int y, int z) {
		return world.isAirBlock(x, y, z) && world.isAirBlock(x, y +1 , z);
	}
	
	
	/**
	 * Checks if the teleporter is able to complete the teleport
	 * @param entity
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param accuracy
	 * @return
	 */
	public boolean canTeleport(Entity entity, World world, int x, int y, int z, int accuracy) {
		return entity != null && isSafeLocationForSteve(world, x, y, z) && getPowerRequiredForTeleport(world, x, y, z) < getPotentialEnergy();
	}
	
	/**
	 * This method teleports a player to the location specified at the cost of power.
	 * @param entity
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param accuracy
	 * @param luck determines if the player will get lucky* and get a perfectly accurate teleport 	*see http://goo.gl/nhzzXX
	 * @return
	 */
	public boolean teleport(Entity entity, World world, int x, int y, int z) {
		Random random = new Random();
		if (entity == null ){ 
			return false;
		}
		
		int randX, randY, randZ;
		randX = random.nextInt(accuracy);
		randY = random.nextInt(accuracy);
		randZ = random.nextInt(accuracy);
		
		
		int destX = x + (MtMathHelper.randomifySign(randX, true, luck)); 
		int destY = y + (MtMathHelper.randomifySign(randY, true, luck));
		int destZ = z + (MtMathHelper.randomifySign(randZ, true, luck));
		
		if (!canTeleport(entity, world, destX, destY, destZ, accuracy)) { //THIS NEEDS FAIL-SAFE
			for(int x2 = -randX/2 ; x2 < randX/2; x2++) {		//	<-\
				for (int y2 = -randY/2; y2 < randY/2; y2++) {	//	<--|== loop thru all possible blocks the teleport could have made
					for(int z2 = -randZ/2; z2 < randY/2; z2++) {//	<-/
						if (world.isAirBlock(x + x2, y + y2, z + z2) && (world.isAirBlock(x + x2, y + y2 + 1, z + z2))) { //check if the block and the one above it is an airblock
							entity.setPosition(destX, destY, destZ);
							return true;
						} else {
							System.out.println("not a safe destination");
						}
					}
				}
			}
		}
		entity.setPosition(destX, destY, destZ);
		return true;
		
	}
	

}
