package shibe.croberson.modularteleporters.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.modularteleporters.common.creativeTab.MTCreativeTab;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterGlass;
import shibe.croberson.modularteleporters.reference.Reference;
import shibe.croberson.modularteleporters.utils.StaticUtils;

public class BlockMultiblockGlass extends BlockContainer{
	//remember to add gameregistry stuff for new metadata
	public static final int TELEPORTER_METADATA = 0;
	public static final int CATALYST_METADATA = 1;
	
	private static final String textureBaseName = "multiblockGlass";
	
	private static String subBlocks[] = {"teleporter", "catalyst" };
	private IIcon[][] icons = new IIcon[subBlocks.length][16];
	private IIcon transparentIcon;
	
	protected BlockMultiblockGlass() {
		super(Material.glass);
		
		setStepSound(soundTypeGlass);
		setHardness(2.0F);
		setBlockName("mtMultiblockGlass");
		setBlockTextureName(Reference.resourcePrefix + textureBaseName);
		setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityTeleporterGlass();
	}
	
	@Override
	public void registerBlockIcons(IIconRegister registry) {
		
		transparentIcon = registry.registerIcon(Reference.resourcePrefix + "tile." + textureBaseName + ".transparent");
		for(int metadata = 0; metadata < subBlocks.length; metadata++) {
			for(int i = 0; i < 16; i++) {
				icons[metadata][i] = registry.registerIcon(Reference.resourcePrefix + "tile." + textureBaseName + "." + subBlocks[metadata] + Integer.toString(i));
			}
		}
	}
	
	@Override
	 public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		// ctrl + c; ctrl + v;
		ForgeDirection[] dirsToCheck = StaticUtils.neighborsBySide[side];
		ForgeDirection dir;
		Block myBlock = blockAccess.getBlock(x, y, z);
		int myMetadata = blockAccess.getBlockMetadata(x, y, z);
		
		// First check if we have a block in front of us of the same type - if so, just be completely transparent on this side
		ForgeDirection out = ForgeDirection.getOrientation(side);
		if(blockAccess.getBlock(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myBlock && blockAccess.getBlockMetadata(x + out.offsetX, y + out.offsetY, z + out.offsetZ) == myMetadata) {
			return transparentIcon;
		}
		
		// Calculate icon index based on whether the blocks around this block match it
		// Icons use a naming pattern so that the bits correspond to:
		// 1 = Connected on top, 2 = connected on bottom, 4 = connected on left, 8 = connected on right
		int iconIdx = 0;
		for(int i = 0; i < dirsToCheck.length; i++) {
			dir = dirsToCheck[i];
			// Same blockID and metadata on this side?
			if(blockAccess.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myBlock && blockAccess.getBlockMetadata(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == myMetadata) {
				// Connected!
				iconIdx |= 1 << i;
			}
		}
		
		return icons[myMetadata][iconIdx];
		
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		return icons[metadata][0];
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (player.isSneaking()) {
			return false;
		}
		
		if(!world.isRemote && player.getCurrentEquippedItem() == null){
			TileEntity te = world.getTileEntity(x, y, z);
			
			if(te instanceof IMultiblockPart) {
				MultiblockControllerBase controller = ((IMultiblockPart) te).getMultiblockController();
				
				if(controller == null){ 	
					player.addChatMessage(new ChatComponentText(String.format("SERIOUS ERROR - server part @ %d, %d, %d has no controller!", x, y, z)));
				}else {
					Exception e = controller.getLastValidationException();
					if(e != null) {
						player.addChatMessage(new ChatComponentText(e.getMessage()));
						return true;
					}
				}
			}
		}
		return false;
		
	}
	
	public ItemStack getItemStack(String name) {
		int metadata = -1;
		for(int i = 0; i < subBlocks.length; i++) {
			if(subBlocks[i].equals(name)) {
				metadata = i;
				break;
			}
		}
		
		if(metadata < 0) {
			throw new IllegalArgumentException("Unable to find a block with the name " + name);
		}
		return new ItemStack(this, 1, metadata);
	}
	
}
