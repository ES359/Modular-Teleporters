package common.block;

import java.util.List;

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
	
	public BlockMetal() {
		super(Material.iron);
		
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
	public IIcon getIcon(int side, int meta) {
		return blockIcon;
		
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register){
		for(int i = 0; i < subBlocks.length; i++){
			register.registerIcon(p_94245_1_);
		}
	}
}
