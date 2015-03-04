package shibe.croberson.modularteleporters.common.multiblock.tileentity;

import appeng.api.implementations.tiles.ICrankable;
import net.minecraftforge.common.util.ForgeDirection;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.common.multiblock.interfaces.ITickableMultiblockPart;
import Reika.RotaryCraft.API.Power.ShaftPowerReceiver;
import cpw.mods.fml.common.Optional;

@Optional.InterfaceList({
	@Optional.Interface(modid = "RotaryCraft", iface = "Reika.RotaryCraft.API.Power.ShaftPowerReceiver"),
	@Optional.Interface(modid = "appliedenergistics2", iface = "appeng.api.implementations.tiles.ICrankable")
})
public class TileEntityTeleporterRotorBearing extends TileEntityTeleporterPart implements ShaftPowerReceiver, ICrankable, ITickableMultiblockPart{ //will not have the same functions as bigreactors ones, this kind will connect to rotarycraft :D
	
	private MultiblockTeleporter teleporter;
	
	public TileEntityTeleporterRotorBearing() {
		teleporter = getTeleporter();
		
	}
	
	@Override
	public void onMultiblockServerTick() {
		
		
	}
	@Override
	public void setOmega(int omega) {
		if(teleporter == null || !teleporter.isAssembled()) {
			return;
		}
		teleporter.setOmega(omega);
		
	}

	@Override
	public void setTorque(int torque) {
		if(teleporter == null || !teleporter.isAssembled()) {
			return;
		}
		teleporter.setTorque(torque);
		
	}

	@Override
	public void setPower(long power) {
		if(teleporter == null || !teleporter.isAssembled() ||power > MultiblockTeleporter.MAX_POTENTIAL_ENERGIES) {
			return;
		}
		teleporter.setPotentialEnergy((int) power);
		
	}

	@Override
	public int getOmega() {
		return teleporter.getOmega();
	}

	@Override
	public int getTorque() {
		return teleporter.getTorque();
	}

	@Override
	public long getPower() {
		return (long) teleporter.getPotentialEnergy();
	}

	@Override
	public String getName() {
		
		return null;
	}

	@Override
	public int getIORenderAlpha() {
		
		return 0;
	}

	@Override
	public void setIORenderAlpha(int io) {
		
		
	}

	@Override
	public boolean canReadFrom(ForgeDirection dir) {
		
		return true;
	}

	@Override
	public boolean isReceiving() {
		return teleporter.getPotentialEnergy() < MultiblockTeleporter.MAX_POTENTIAL_ENERGIES;
	}

	@Override
	public int getMinTorque(int available) {
		return 1;
	}

	
	@Override
	public void noInputMachine() {
	
		
	}

	@Override
	public boolean canTurn() {
		return teleporter.getPotentialEnergy() < teleporter.MAX_POTENTIAL_ENERGIES;
	}
	
	/*
	 * average human of 2 meters (6.5 feet) has an average weight of 156 IBS
	 *	average person lift 1/3 of weight, 52 IBS
	 *	52 IBS = 232 newtons
	 *	AE2 hand crank = 1/2 meter
	 *	(232 newtons at 1/2 meter = 116 Newton-Meters)
	 *	AE2 hand crank at 1 rps (6.28 rad/s)
	 *	116 newton-metres *  6.28 rad/s= 728W or 728 joules/second
	 */
	@Override
	public void applyTurn() {
		teleporter.setPotentialEnergy(teleporter.getPotentialEnergy() + 728);
		teleporter.setTorque(teleporter.getTorque() + 116);
		teleporter.setOmega(teleporter.getOmega() + 6);
	}

	@Override
	public boolean canCrankAttach(ForgeDirection directionToCrank) {
		return true;
	} 
	
}
