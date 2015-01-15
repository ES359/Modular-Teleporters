package shibe.croberson.modularteleporters.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockModularTeleporters extends ItemBlockWithMetadata {

	public ItemBlockModularTeleporters(Block block) {
		super(block, block);

	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (this.hasSubtypes) {
			int metadata = stack.getItemDamage();
			return super.getUnlocalizedName(stack) + "." + Integer.toString(metadata);
		} else {
			return super.getUnlocalizedName(stack);
		}
	}

	@Override
	public String getUnlocalizedName() {
		if (this.hasSubtypes) {
			return super.getUnlocalizedName() + ".0";
		} else {
			return super.getUnlocalizedName();
		}

	}
}
