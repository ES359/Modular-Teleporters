package common.item;

import common.creativeTab.MTCreativeTab;

import net.minecraft.item.Item;

public class ItemBase extends Item{
	
	public ItemBase(int stackSize) {
		super();
		this.maxStackSize = stackSize;
		this.setCreativeTab(MTCreativeTab.MODULAR_TELEPORTER_TAB);
		
	}
	
}
