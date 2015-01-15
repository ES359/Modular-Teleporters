package shibe.croberson.modularteleporters.common.creativeTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import shibe.croberson.modularteleporters.common.item.MTItems;
import shibe.croberson.modularteleporters.reference.Reference;

public class MTCreativeTab {
	public static final CreativeTabs MODULAR_TELEPORTER_TAB = new CreativeTabs(Reference.NAME) {
		@Override
		public Item getTabIconItem() {
			return MTItems.itemIngot;
		}
		
	};
}
