package gui.skill_workbench.slot

import items.baseItem.ItemSkill
import net.minecraft.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.SlotItemHandler

class SkillSlot(inv: IItemHandler, index: Int, x: Int, y: Int): SlotItemHandler(inv, index, x, y){
	override fun isItemValid(stack: ItemStack): Boolean {
		return stack.item is ItemSkill
	}
}