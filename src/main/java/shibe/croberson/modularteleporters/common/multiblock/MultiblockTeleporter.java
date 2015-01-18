package shibe.croberson.modularteleporters.common.multiblock;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.rectangular.RectangularMultiblockControllerBase;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.ITickableMultiblockPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
//Most of this is taken from bigreactors
public class MultiblockTeleporter extends RectangularMultiblockControllerBase {
	
	//UI updates
	private Set<EntityPlayer> updatePlayers;
	private int ticksSinceLastUpdate;
	private static final int tickBetweenUpdates = 3;
	
	//parts
	private Set<IMultiblockPart> attachedControllers;
	private Set<ITickableMultiblockPart> attachedTickables;
	private Set<TileEntityTeleporterFluidPort> attachedFluidPorts;
	
	protected MultiblockTeleporter(World world) {
		super(world);
		
		updatePlayers = new HashSet<EntityPlayer>();
		
		ticksSinceLastUpdate = 0;
		
		attachedTickables = new HashSet<ITickableMultiblockPart>();
		attachedFluidPorts = new HashSet<TileEntityTeleporterFluidPort>();
		
		
		
	}
	
	protected void sendIndividualUpdate(EntityPlayer player) {
		if(this.worldObj.isRemote) { return; }

        //PacketHandler.INSTANCE.sendTo(getUpdatePacket(), (EntityPlayerMP)player);
	}

	//protected IMessage getUpdatePacket() {
    //    return new TurbineUpdateMessage(this);
	//}
	
	
	
	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) {
		
	}

	@Override
	protected void onBlockAdded(IMultiblockPart newPart) {
		if (newPart instanceof TileEntityTeleporterFluidPort) {
			this.attachedFluidPorts.add((TileEntityTeleporterFluidPort)newPart);
		} 
		if (newPart instanceof ITickableMultiblockPart) {
			this.attachedTickables.add((ITickableMultiblockPart)newPart);
		} 
		
	}

	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) {
		if (oldPart instanceof TileEntityTeleporterFluidPort) {
			this.attachedFluidPorts.add((TileEntityTeleporterFluidPort)oldPart);
		}
		if (oldPart instanceof ITickableMultiblockPart){
			this.attachedTickables.add((ITickableMultiblockPart)oldPart);
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

}
