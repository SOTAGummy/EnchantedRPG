package capability.accessory

import Core
import capability.accessory.AccessoryProvider
import items.baseItem.ItemAccessory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

class AccessoryItemContainer: ItemStackHandler(8), IAccessory{
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
		} else stack.isEmpty
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
		return if (!isItemValid(slot, stack)) stack
		else super.insertItem(slot, stack, simulate)
	}

	override fun getSlotLimit(slot: Int): Int {
		return 8
	}

	override fun setStackInSlot(slot: Int, stack: ItemStack) {
		if (isItemValid(slot, stack)){
			this.stacks[slot] = stack
		}
	}

	override fun getStackInSlot(slot: Int): ItemStack {
		return super.getStackInSlot(slot)
	}
}