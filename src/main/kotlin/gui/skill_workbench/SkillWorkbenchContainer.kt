package gui.skill_workbench

import blocks.TileEntitySkillWorkbench
import gui.skill_workbench.slot.SkillContainerSlot
import gui.skill_workbench.slot.SkillSlot
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack


class SkillWorkbenchContainer(inv: IInventory, te: TileEntitySkillWorkbench): Container(){
	init {
		val inventory = te.inventory
		for (i in 0 .. 2) {
			for (j in 0 .. 8) {
				addSlotToContainer(Slot(inv, i * 9 + j + 9, 8 + j * 18, 84 + i * 18))
			}
		}
		for (k in 0 .. 8) {
			addSlotToContainer(Slot(inv, k, 8 + k * 18, 142))
		}

		addSlotToContainer(SkillContainerSlot(inventory, 0, 81, 10))

		for (i in 0 .. 7){
			addSlotToContainer(SkillSlot(inventory, i + 1, 18 + i * 18, 45))
		}
	}

	override fun canInteractWith(playerIn: EntityPlayer): Boolean {
		return !playerIn.isSpectator
	}

	override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
		var itemstack = ItemStack.EMPTY
		val slot = inventorySlots[index]

		if (slot != null && slot.hasStack) {
			val itemstack1 = slot.stack
			itemstack = itemstack1.copy()

			if (index in 9..35) {
				if (!this.mergeItemStack(itemstack1, 36, 45, false)) return ItemStack.EMPTY
			}
			//hotbar -> inv
			else if (index in 0..8) {
				if (!this.mergeItemStack(itemstack1, 9, 36, false)) return ItemStack.EMPTY
			}

			if (itemstack1.isEmpty) slot.putStack(ItemStack.EMPTY)
			else slot.onSlotChanged()

			if (itemstack1.count == itemstack.count) return ItemStack.EMPTY

			val itemstack2 = slot.onTake(playerIn, itemstack1)
			if (index == 0) playerIn.dropItem(itemstack2, false)
		}

		return super.transferStackInSlot(playerIn, index)
	}
}