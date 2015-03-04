package shibe.croberson.modularteleporters.common.fluid;

import shibe.croberson.modularteleporters.common.creativeTab.MTCreativeTab;
import shibe.croberson.modularteleporters.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMTFluid extends BlockFluidClassic{
	
	private IIcon iconFlowing;
	private IIcon iconStill;
	
	public BlockMTFluid(Fluid fluid, String unlocalizedName) {
		super(fluid, Material.water);
		setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
		setBlockName("fluid." + unlocalizedName + ".still");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegistry) {
		iconStill   = iconRegistry.registerIcon(Reference.resourcePrefix + getUnlocalizedName());
		iconFlowing = iconRegistry.registerIcon(Reference.resourcePrefix + getUnlocalizedName().replace(".still", ".flowing"));

		this.stack.getFluid().setIcons(iconStill, iconFlowing);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		return side <= 1 ? iconStill : iconFlowing;
	}
	
	
}

