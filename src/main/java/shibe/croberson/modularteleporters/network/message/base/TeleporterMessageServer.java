package shibe.croberson.modularteleporters.network.message.base;

import net.minecraft.tileentity.TileEntity;
import shibe.croberson.beefcore.core.common.CoordTriplet;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPartBase;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TeleporterMessageServer extends WorldMessageServer {//carbon copy of TurbineMessageServer, don't let me take credit for this portion of the code
	protected MultiblockTeleporter teleporter;
	
	protected TeleporterMessageServer() { super(); teleporter = null; }
	protected TeleporterMessageServer(MultiblockTeleporter teleporter, CoordTriplet referenceCoord) {
		super(referenceCoord.x, referenceCoord.y, referenceCoord.z);
		this.teleporter = teleporter;
	}
	protected TeleporterMessageServer(MultiblockTeleporter teleporter) {
		this(teleporter, teleporter.getReferenceCoord());
	}
	
	public static abstract class Handler<M extends TeleporterMessageServer> extends WorldMessageServer.Handler<M> {
		protected abstract IMessage handleMessage(M message, MessageContext ctx, MultiblockTeleporter teleporter);

		@Override
		protected IMessage handleMessage(M message, MessageContext ctx, TileEntity te) {
			if(te instanceof TileEntityTeleporterPartBase) {
				MultiblockTeleporter reactor = ((TileEntityTeleporterPartBase)te).getTeleporter();
				if(reactor != null) {
					return handleMessage(message, ctx, reactor);
				}
				else {
					//BRLog.error("Received TeleporterMessageServer for a teleporter part @ %d, %d, %d which has no attached teleporter", te.xCoord, te.yCoord, te.zCoord);
				}
			}
			else {
				//BRLog.error("Received TeleporterMessageServer for a non-teleporter-part block @ %d, %d, %d", message.x, message.y, message.z);
			}
			return null;
		}
	}
}