package shibe.croberson.modularteleporters.network.message.base;

import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * A message to a client user which is grounded in world-spaaaaaace.
 * Generally, such messages will be sent FROM the server.
 * @author Erogenous Beef
 */
public abstract class WorldMessageClient extends WorldMessage { //dont be confused, WorldMessageClient goes to CLIENT, NOT coming from client
	protected WorldMessageClient() { super(); }
	protected WorldMessageClient(int x, int y, int z) {
		super(x, y, z);
	}

	public abstract static class Handler<M extends WorldMessageClient> extends WorldMessage.Handler<M> {
		@SideOnly(Side.CLIENT)
		@Override
		protected World getWorld(MessageContext ctx) {
			return FMLClientHandler.instance().getClient().theWorld;
		}
	}
}