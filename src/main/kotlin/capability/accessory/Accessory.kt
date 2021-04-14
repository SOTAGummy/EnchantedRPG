package capability.accessory

import items.baseItem.ItemAccessory
import net.minecraft.item.ItemStack

class Accessory : IAccessory {
	private val items = arrayOf(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY)

	override fun getItem(slot: Int): ItemStack{
		return items[slot]
	}

	override fun setItem(slot: Int, stack: ItemStack) {
		if (stack.item is ItemAccessory) {
			val item = stack.item as ItemAccessory
			if (item.equipmentSlot.index == slot) {
				items[slot] = stack
			}
		}
	}
}