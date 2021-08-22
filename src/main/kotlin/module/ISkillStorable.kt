package module

import items.baseItem.ItemSkill
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item.getItemById
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting

interface ISkillStorable{
	fun getSkillCapacity(): Int

	fun addToolTip(stack: ItemStack, tooltip: MutableList<String>){
		var cost = 0
		for (i in 0 until getSkillCapacity()) {
			if (stack.tagCompound != null && stack.tagCompound!!.getIntArray("skills").isNotEmpty() && stack.tagCompound!!.getIntArray("skills")[i] != 0 && !stack.isEmpty) {
				val displayName = ItemStack(getItemById(stack.tagCompound!!.getIntArray("skills")[i])).displayName
				val format = I18n.format(displayName)
				val item = (getItemById(stack.tagCompound!!.getIntArray("skills")[i])) as ItemSkill
				val count = (i + 1).toString()

				cost += item.cost
				tooltip.add("$count : ${TextFormatting.UNDERLINE}$format")
			}
		}
		if (stack.tagCompound != null && stack.tagCompound!!.getIntArray("skills")[0] != 0) {
			tooltip.add("")
			tooltip.add("${TextComponentTranslation("text.skill_cost").formattedText} : " + cost.toString() + "MP")
		}
	}
}