package shibe.croberson.modularteleporters.common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import shibe.croberson.beefcore.core.common.CoordTriplet;
import shibe.croberson.beefcore.core.multiblock.IMultiblockPart;
import shibe.croberson.beefcore.core.multiblock.MultiblockControllerBase;
import shibe.croberson.beefcore.core.multiblock.rectangular.PartPosition;
import shibe.croberson.modularteleporters.common.creativeTab.MTCreativeTab;
import shibe.croberson.modularteleporters.common.multiblock.MultiblockTeleporter;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterFluidPort.FluidFlow;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPart;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterPartBase;
import shibe.croberson.modularteleporters.common.multiblock.tileentity.TileEntityTeleporterRotorBearing;
import shibe.croberson.modularteleporters.reference.Reference;
import shibe.croberson.modularteleporters.utils.StaticUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeleporterPart extends BlockContainer implements ITileEntityProvider{//see how to add this block with gameregistry
	
	public static final int METADATA_CASING = 0;
	public static final int METADATA_CONTROLLER = 1;
	public static final int METADATA_FLUID_PORT = 2;
	public static final int METADATA_ROTOR_BEARING = 3;
	
	public static final String[] subBlocks = {"casing", "controller", "fluidPort", "rotorBearing"};
	
	private static final int PORT_INLET = 0;
	private static final int PORT_OUTLET = 1;
	private static final int CONTROLLER_OFF = 0;
	private static final int CONTROLLER_ON = 1;
	
	private static final String[][] states = new String[][]{
		{"default", "face", "corner", "eastwest", "northsouth", "vertical" }, //casing
		{"off", "active"}, //controller
		{"inlet", "outlet"}, //fluid port
		{"default"} //rotor bearing
	};
	
	
	private IIcon[][] icons = new IIcon[subBlocks.length][];
	
	public BlockTeleporterPart(Material material) {
		super(material);
		setStepSound(soundTypeMetal);
		setHardness(2.0F);
		setBlockName("blockTeleporterPart");
		setBlockTextureName(Reference.resourcePrefix + "blockTeleporterPart");
		setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		String prefix = Reference.resourcePrefix + getUnlocalizedName() + ".";

		for(int metadata = 0; metadata < states.length; ++metadata) {
			String[] blockStates = states[metadata];
			icons[metadata] = new IIcon[blockStates.length];

			for(int state = 0; state < blockStates.length; state++) {
				icons[metadata][state] = registry.registerIcon(prefix + subBlocks[metadata] + "." + blockStates[state]);
			}
		}
		
		this.blockIcon = registry.registerIcon(Reference.resourcePrefix + getUnlocalizedName());
	}
	
	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		IIcon icon = null;
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		if (metadata == METADATA_ROTOR_BEARING) {
			return getIcon(side, metadata);
		}
		
		switch(metadata) {
		case METADATA_CASING:
			icon = getCasingIcon(blockAccess, x, y, z, side);
			break;
		case METADATA_CONTROLLER:
			icon = getControllerIcon(blockAccess, x, y, z, side);
			break;
		case METADATA_FLUID_PORT:
			icon = getFluidPortIcon(blockAccess, x, y, z, side);
			break;
		}
		//and end with this 
		return icon != null ? icon : getIcon(side, metadata);
	}
	
	@Override
	public IIcon getIcon(int side, int metadata) {
		
		if(side > 1 && (metadata >= 0 && metadata < icons.length)) {
				return icons[metadata][0];
		}
		
		return blockIcon;
	}
	
	
	public IIcon getFluidPortIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntity te = access.getTileEntity(x, y, z);
		int metadata = access.getBlockMetadata(x, y, z);
		
		//Are you of a TileEntityTeleporterPartBase class?
		if(te instanceof TileEntityTeleporterPartBase) {
			TileEntityTeleporterPartBase part = (TileEntityTeleporterPartBase)te; //Oh my god crober, you can't just ask parts if they're TileEntityTeleporterPartBase.
			MultiblockTeleporter teleporter = part.getTeleporter();
			if (te instanceof TileEntityTeleporterFluidPort) {
				if(teleporter == null || !teleporter.isAssembled() || part.getOutwardsDir().ordinal() == side) {
					if(((TileEntityTeleporterFluidPort)te).getFlowDirection() == FluidFlow.out) {
						return icons[METADATA_FLUID_PORT][PORT_OUTLET];
					} else {
						return icons[METADATA_FLUID_PORT][PORT_OUTLET];
					}
					
				}else if(teleporter.isAssembled() && part.getOutwardsDir().ordinal() != side) {
					return icons[METADATA_CASING][DEFAULT];
				}
			}
		}
		return getIcon(side, metadata); 
	}
	
	public IIcon getControllerIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntity te = access.getTileEntity(x, y, z);
		int metadata = access.getBlockMetadata(x, y, z);
		
		if (te instanceof TileEntityTeleporterPartBase) {
			TileEntityTeleporterPartBase part = (TileEntityTeleporterPartBase)te; 
			MultiblockTeleporter teleporter = part.getTeleporter();
			if(teleporter != null && teleporter.getActive()) {
				return icons[METADATA_CONTROLLER][CONTROLLER_ON];
			}else {
				return icons[METADATA_CONTROLLER][CONTROLLER_ON];
			}
			
		}
		return getIcon(side, metadata); 
	}
	
	private static final int DEFAULT = 0;
	private static final int FACE = 1;
	private static final int CORNER = 2;
	private static final int EASTWEST = 3;
	private static final int NORTHSOUTH = 4;
	private static final int VERTICAL = 5;
	
	private IIcon getCasingIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		TileEntity te = blockAccess.getTileEntity(x, y, z);
		if(te instanceof TileEntityTeleporterPart) {
			TileEntityTeleporterPart part = (TileEntityTeleporterPart)te;
			PartPosition position = part.getPartPosition();
			MultiblockTeleporter teleporter = part.getTeleporter();
			if(teleporter == null || !teleporter.isAssembled()) {
				return icons[METADATA_CASING][DEFAULT];
			}
			
			switch(position) {
			case BottomFace:
			case TopFace:
			case EastFace:
			case WestFace:
			case NorthFace:
			case SouthFace:
				return icons[METADATA_CASING][FACE];
			case FrameCorner:
				return icons[METADATA_CASING][CORNER];
			case Frame:
				return getCasingEdgeIcon(part, teleporter, side);
			case Interior:
			case Unknown:
			default:
				return icons[METADATA_CASING][DEFAULT];
			}
		}
		return icons[METADATA_CASING][DEFAULT];
	}
	
	private IIcon getCasingEdgeIcon(TileEntityTeleporterPart part, MultiblockTeleporter teleporter, int side) {
		if(teleporter == null || !teleporter.isAssembled()) { return icons[METADATA_CASING][DEFAULT]; }

		CoordTriplet minCoord = teleporter.getMinimumCoord();
		CoordTriplet maxCoord = teleporter.getMaximumCoord();

		boolean xExtreme, yExtreme, zExtreme;
		xExtreme = yExtreme = zExtreme = false;

		if(part.xCoord == minCoord.x || part.xCoord == maxCoord.x) { xExtreme = true; }
		if(part.yCoord == minCoord.y || part.yCoord == maxCoord.y) { yExtreme = true; }
		if(part.zCoord == minCoord.z || part.zCoord == maxCoord.z) { zExtreme = true; }
		
		int idx = DEFAULT;
		if(!xExtreme) {
			if(side < 4) { idx = EASTWEST; }
		}
		else if(!yExtreme) {
			if(side > 1) {
				idx = VERTICAL;
			}
		}
		else { // !zExtreme
			if(side < 2) {
				idx = NORTHSOUTH;
			}
			else if(side > 3) {
				idx = EASTWEST;
			}
		}
		return icons[METADATA_CASING][idx];
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		switch(metadata) {
		case METADATA_FLUID_PORT:
			return new TileEntityTeleporterFluidPort();
		case METADATA_ROTOR_BEARING:
			return new TileEntityTeleporterRotorBearing();
		default: 
			return new TileEntityTeleporterPart();
		
		}
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < subBlocks.length; i++){
			list.add(new ItemStack(item, 1, i));
		}
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
		
		if(metadata != METADATA_CONTROLLER){ return false; }
		
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
	//wasnt in Beef's multiblock parts, just trying it out
	public ItemStack getItemStackForName(String name) { 
		for (int i = 0; i < subBlocks.length; i++) {
			if (subBlocks[i].equals(name)){
				return (new ItemStack(this, 1, i));
			}
		}
		return null;
	}
	
	
}
