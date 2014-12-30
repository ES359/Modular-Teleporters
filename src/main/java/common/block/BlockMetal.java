package common.block;

import java.util.List;

import common.creativeTab.MTCreativeTab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMetal extends Block{
	
	public static final int OSMIUM = 0;
	public static final int BISMUTH = 1;
	public static final int PLATINUM = 2;
	public static final int LEAD = 3;
	
	public static String subBlocks[]= {"Osmium", "Bismuth", "Platinum", "Lead"};
	
	@SideOnly(Side.CLIENT)
	private IIcon icons[] = new IIcon[subBlocks.length];
	
	public BlockMetal() {
		super(Material.iron);
		setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
		setBlockName("mtMetal");
		
	}
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(int i = 0; i < subBlocks.length; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) { //error rendering block
		meta = Math.max(0, Math.min(subBlocks.length, meta));
		return icons[meta];
		
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for(int i = 0; i < subBlocks.length; i++){
			icons[i] = register.registerIcon(Reference.resourcePrefix + "block" + subBlocks[i]);
		}
	}
	
	public ItemStack getItemStackForName(String name) { 
		for (int i = 0; i < subBlocks.length; i++) {
			if (subBlocks[i].equals(name)){
				return (new ItemStack(this, 1, i));
			}
		}
		return null;
	}
}
