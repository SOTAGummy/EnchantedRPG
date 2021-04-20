package gui.accessory

import Core
import capability.accessory.AccessoryProvider
import items.baseItem.ItemAccessory
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.items.ItemStackHandler

class AccessoryItemContainer: ItemStackHandler(4) {
	override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
		return if (stack.item is ItemAccessory) {
			val equipmentSlot = (stack.item as ItemAccessory).equipmentSlot
			when (slot) {
				0 -> equipmentSlot == Core.NECKLACE
				1 -> equipmentSlot == Core.AMULET
				2 -> equipmentSlot == Core.GLOVE
				3 -> equipmentSlot == Core.RING
				else -> false
			}
		} else false
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
		if (!isItemValid(slot, stack)) return stack
		return super.insertItem(slot, stack, simulate)
	}

	override fun setStackInSlot(slot: Int, stack: ItemStack) {
		if (isItemValid(slot, stack)) {
			super.setStackInSlot(slot, stack)
		}
	}

}