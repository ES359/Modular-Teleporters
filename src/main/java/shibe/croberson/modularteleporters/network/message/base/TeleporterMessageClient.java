package shibe.croberson.modularteleporters.network.message.base;

import net.minecraft.tileentity.TileEntity;
import shibe.croberson.beefcore.core.common.CoordTriplet;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPartBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TeleporterMessageClient extends WorldMessageClient { //carbon copy of TurbineMessageClient, don't let me take credit for this portion of the code
	protected MultiblockTeleporter teleporter;
	
	protected TeleporterMessageClient() { super(); teleporter = null; }
	protected TeleporterMessageClient(MultiblockTeleporter teleporter, CoordTriplet referenceCoord) {
		super(referenceCoord.x, referenceCoord.y, referenceCoord.z);
		this.teleporter = teleporter;
	}
	protected TeleporterMessageClient(MultiblockTeleporter teleporter) {
		this(teleporter, teleporter.getReferenceCoord());
	}
	
	public static abstract class Handler<M extends TeleporterMessageClient> extends WorldMessageClient.Handler<M> {
		protected abstract IMessage handleMessage(M message, MessageContext ctx, MultiblockTeleporter teleporter);

		@Override
		protected IMessage handleMessage(M message, MessageContext ctx, TileEntity te) {
			if(te instanceof TileEntityTeleporterPartBase) {
				MultiblockTeleporter reactor = ((TileEntityTeleporterPartBase)te).getTeleporter();
				if(reactor != null) {
					return handleMessage(message, ctx, reactor);
				}
				else {
					//BRLog.error("Received teleporterMessageClient for a teleporter part @ %d, %d, %d which has no attached teleporter", te.xCoord, te.yCoord, te.zCoord);
				}
			}
			else {
				//BRLog.error("Received teleporterMessageClient for a non-teleporter-part block @ %d, %d, %d", message.x, message.y, message.z);
			}
			return null;
		}
	}
}