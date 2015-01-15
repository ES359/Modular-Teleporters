package shibe.croberson.modularteleporters.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import shibe.croberson.modularteleporters.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemBase{
	
	public int StackSize = 64;
	
	public static String subTypes[] = {"Osmium", "Bismuth", "Platinum", "Lead"}; //include ingots here
	
	@SideOnly(Side.CLIENT)
	public IIcon icons[];
	
	public ItemIngot() {
		super(64);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName() {
		return null; 
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = Math.min(subTypes.length-1, stack.getItemDamage());
		return "item.ingot" + subTypes[i];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < subTypes.length; i++) {
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return icons[MathHelper.clamp_int(meta, 0, subTypes.length - 1)]; 
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icons = new IIcon[subTypes.length];
		for (int i = 0; i < subTypes.length; i++) {
			icons[i] = register.registerIcon(Reference.resourcePrefix + "ingot" + subTypes[i]);
		}	
	}
	
	public ItemStack getItemStackForType(String type){
		for(int i = 0; i < subTypes.length; i++) {
			if(subTypes[i].equals(type)){
				return new ItemStack(this, 1, i);
			}
		}
		return null;
	}
}
