package common.creativeTab;

import reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class MTCreativeTab {
	public static final CreativeTabs MODULAR_TELEPORTER_TAB = new CreativeTabs(Reference.NAME) {
		@Override
		public Item getTabIconItem(){
			return Items.lead;
		}
	};
}
