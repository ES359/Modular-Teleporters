package common.creativeTab;

import common.item.ItemIngot;
import common.item.MTItems;
import reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MTCreativeTab {
	public static final CreativeTabs MODULAR_TELEPORTER_TAB = new CreativeTabs(Reference.NAME) {
		@Override
		public Item getTabIconItem() {
			return MTItems.itemIngot;
		}
		
	};
}
