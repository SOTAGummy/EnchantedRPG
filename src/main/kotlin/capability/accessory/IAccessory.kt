package capability.accessory

import Core
import items.baseItem.ItemAccessory
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandlerModifiable

interface IAccessory: IItemHandlerModifiable{
	override fun getSlotLimit(slot: Int): Int {
		return 1
	}

	override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
		return if (stack.item is ItemAccessory) {
			val equipmentSlot = (stack.item as ItemAccessory).equipmentSlot
			when (slot) {
				0 -> equipmentSlot == Core.NECKLACE
				1 -> equipmentSlot == Core.AMULET
				2 -> equipmentSlot == Core.GLOVE
				3 -> equipmentSlot == Core.RING
				4 -> equipmentSlot == Core.NECKLACE
				5 -> equipmentSlot == Core.AMULET
				6 -> equipmentSlot == Core.GLOVE
				7 -> equipmentSlot == Core.RING
				else -> false
			}
		} else false
	}
}