package gui.accessory.slot

import items.baseItem.ItemAccessory
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class NecklaceSlot(inventory: IItemHandler, index: Int, x: Int, y: Int) : SlotItemHandler(inventory, index, x, y) {
	override fun isItemValid(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory && (stack.item as ItemAccessory).equipmentSlot == Core.NECKLACE
	}
}