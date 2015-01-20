package shibe.croberson.modularteleporters.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPart;

public class BlockTeleporterPart extends BlockContainer implements ITileEntityProvider{
	
	public static final int TELEPORTER_CASING = 0;
	public static final int TELEPORTER_CONTROLLER = 1;
	public static final int TELEPORTER_FLUID_PORT = 2;

	
	protected BlockTeleporterPart(Material material) {
		super(material);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		IIcon icon = null;
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		
		
		
		return icon != null ? icon : getIcon(side, metadata);
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch(metadata) {
		case TELEPORTER_FLUID_PORT:
			return new TileEntityTeleporterFluidPort();
		
		default: 
			return new TileEntityTeleporterPart();
		
		}
		
		return null;
	}
	
	//checks what tileentity this block belongs to and routes it to the correct functionality
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()) {
			return false;
		}
		
		int metadata = world.getBlockMetadata(x, y, z);
		
		if(world.isRemote) {
			return true;
		}
		
		// If the player's hands are empty and they rightclick on a multiblock, they get a 
		// multiblock-debugging message if the machine is not assembled.
		if(player.getCurrentEquippedItem() == null) {
			TileEntity te = world.getTileEntity(x, y, z);
			if(te instanceof IMultiblockPart) {
				MultiblockControllerBase controller = ((IMultiblockPart)te).getMultiblockController();

				if(controller == null) {
					player.addChatMessage(new ChatComponentText(String.format("SERIOUS ERROR - server part @ %d, %d, %d has no controller!", x, y, z))); //TODO Localize
				}
				else {
					Exception e = controller.getLastValidationException();
					if(e != null) {
						player.addChatMessage(new ChatComponentText(e.getMessage() + " - controller " + Integer.toString(controller.hashCode()))); //Would it be worth it to localize one word?
						return true;
					}
				}
			}
		}
		
		if(metadata != TELEPORTER_CONTROLLER){ return false; }
		
		//check if machine is assembled
		TileEntity te = world.getTileEntity(x, y, z);
		if(!(te instanceof IMultiblockPart)){
			return false;
		}
		
		IMultiblockPart part = (IMultiblockPart)te;
		if(!part.isConnected() || !part.getMultiblockController().isAssembled()){
			return false;
		}
		//insert openGuiMethod call here
		return true;
	}
	
	
	
}
