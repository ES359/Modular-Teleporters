package common.item;

import java.util.List;

import reference.Reference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemBase{
	
	public int StackSize = 64;
	
	public static String subTypes[] = {"osmium", "bismuth", "platinum", "lead"}; //include ingots here
	
	@SideOnly(Side.CLIENT)
	public IIcon icons[];
	
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
			register.registerIcon(Reference.resourcePrefix + Reference.itemIngotTexture + "_" + subTypes[i]);
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
