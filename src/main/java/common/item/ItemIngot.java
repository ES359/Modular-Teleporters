package common.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemBase{
	
	public int StackSize = 64;
	
	public static String subTypes[] = {"osmium", "bismuth", "platinum", "lead"}; //include ingots here
		
	public ItemIngot() {
		super(64);
		setHasSubtypes(true);
		//setUnlocalizedName() // set this to something once I get my shit together.
	}
	
	@Override
	public String getUnlocalizedName() {
		return null; //I havent set up unlocalized names yet
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return null; //I havent set up unlocalized names yet
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < subTypes.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
}
