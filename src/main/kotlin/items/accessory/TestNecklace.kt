package items.accessory

import items.baseItem.ItemAccessory
import net.minecraft.init.Blocks

object TestNecklace: ItemAccessory("test_necklace", Core.NECKLACE){
	init {
		Blocks.ACACIA_DOOR
	}
}