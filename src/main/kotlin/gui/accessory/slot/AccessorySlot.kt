package gui.accessory.slot

import Core
import items.baseItem.ItemAccessory
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

open class AccessorySlot(inventory: IItemHandler, val index: Int, x: Int, y: Int): SlotItemHandler(inventory, index, x, y){
	private val accessorySlots = arrayOf(Core.NECKLACE, Core.AMULET, Core.GLOVE, Core.RING)

	override fun isItemValid(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory && (stack.item as ItemAccessory).equipmentSlot == accessorySlots[index]
	}
}