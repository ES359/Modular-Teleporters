package common.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockOre extends Block{
	
	public static final int OSMIUM = 0;
	public static final int BISMUTHINITE = 1;
	public static final int PLATINUM = 2;
	public static final int LEAD = 3;
	
	public static String materials[] = {"osmium", "bismuthinite", "platinum", "lead"};
	public static String subBlocks[] = {"blockOsmiumOre", "blockBismuthOre", "blockPlatinumOre", "blockLeadOre"};
	
	public BlockOre() {
		super(Material.rock);
		
	}
	
	@Override
	public int damageDropped(int metadata){
		return metadata;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list ) {
		for(int i = 0; i < subBlocks.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	
}
