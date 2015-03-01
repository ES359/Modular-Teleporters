package shibe.croberson.modularteleporters.network.message.multiblock;

import io.netty.buffer.ByteBuf;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.network.message.base.TeleporterMessageClient;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class TeleporterUpdateMessage extends TeleporterMessageClient {
	protected ByteBuf data;
	
	public TeleporterUpdateMessage() { super(); data = null; }
	public TeleporterUpdateMessage(MultiblockTeleporter teleporter) {
		super(teleporter);
		data = null;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		teleporter.cerealize(buf);
	}
	
	@Override public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		data = buf.readBytes(buf.readableBytes());
	}
	
	public static class Handler extends TeleporterMessageClient.Handler<TeleporterUpdateMessage> {
		@Override
		protected IMessage handleMessage(TeleporterUpdateMessage message, MessageContext ctx, MultiblockTeleporter teleporter) {
			teleporter.decerealize(message.data);
			return null;
		}
	}
}