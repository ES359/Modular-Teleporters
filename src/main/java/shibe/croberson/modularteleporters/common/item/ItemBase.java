package shibe.croberson.modularteleporters.common.item;

import net.minecraft.item.Item;
import shibe.croberson.modularteleporters.common.creativeTab.MTCreativeTab;

public class ItemBase extends Item{
	
	public ItemBase(int stackSize) {
		super();
		this.maxStackSize = stackSize;
		this.setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
		
	}
	
}
