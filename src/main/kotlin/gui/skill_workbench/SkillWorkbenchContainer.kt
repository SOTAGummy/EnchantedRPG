package gui.skill_workbench

import blocks.TileEntitySkillWorkbench
import items.baseItem.ItemSkill
import module.ISkillStorable
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack
import net.minecraftforge.items.SlotItemHandler


class SkillWorkbenchContainer(inv: InventoryPlayer, te: TileEntitySkillWorkbench): Container(){
	init {
		val inventory = te.inventory

		for (i in 0 .. 2) {
			for (j in 0 .. 8) {
				addSlotToContainer(Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
			}
		}

		for (k in 0 .. 8) {
			addSlotToContainer(Slot(inv, k, 8 + k * 18, 142))
		}

		addSlotToContainer(object: SlotItemHandler(inventory, 0, 81, 10){
			override fun isItemValid(stack: ItemStack): Boolean {
				return stack.item is ISkillStorable
			}

			override fun onSlotChanged() {
				te.markDirty()
			}
		})

		for (i in 0 .. 7){
			addSlotToContainer(object: SlotItemHandler(inventory, i + 1, 18 + 18 * i, 45){
				override fun isItemValid(stack: ItemStack): Boolean {
					return stack.item is ItemSkill
				}

				override fun onSlotChanged() {
					te.markDirty()
				}
			})
		}
	}

	override fun canInteractWith(playerIn: EntityPlayer): Boolean {
		return true
	}

	override fun transferStackInSlot(player: EntityPlayer, index: Int): ItemStack? {
		var itemstack = ItemStack.EMPTY
		val slot: Slot? = inventorySlots[index]
		if (slot != null && slot.hasStack) {
			val itemstack1: ItemStack = slot.stack
			itemstack = itemstack1.copy()
			val containerSlots = inventorySlots.size - player.inventory.mainInventory.size
			if (index < containerSlots) {
				if (!mergeItemStack(itemstack1, containerSlots, inventorySlots.size, true)) {
					return ItemStack.EMPTY
				}
			} else if (!mergeItemStack(itemstack1, 0, containerSlots, false)) {
				return ItemStack.EMPTY
			}
			if (itemstack1.count == 0) {
				slot.putStack(ItemStack.EMPTY)
			} else {
				slot.onSlotChanged()
			}
			if (itemstack1.count == itemstack.count) {
				return ItemStack.EMPTY
			}
			slot.onTake(player, itemstack1)
		}
		return itemstack
	}
}