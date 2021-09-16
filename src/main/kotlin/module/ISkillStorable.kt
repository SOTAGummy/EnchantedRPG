package module

import extension.getItemSkill
import extension.getSkillCount
import items.baseItem.ItemSkill
import net.minecraft.client.resources.I18n
import net.minecraft.init.Items
import net.minecraft.item.Item.getItemById
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import java.util.*

interface ISkillStorable{
	fun getSkillCapacity(): Int

	fun addToolTip(stack: ItemStack, tooltip: MutableList<String>){
		var cost = 0
		for (i in 0 until getSkillCapacity()) {
			if (stack.tagCompound != null && stack.tagCompound!!.getIntArray("skills").isNotEmpty() && stack.tagCompound!!.getIntArray("skills")[i] != 0 && !stack.isEmpty) {
				val displayName = ItemStack(stack.tagCompound?.getIntArray("skills")?.get(i)?.let { getItemById(it) }).displayName
				val format = I18n.format(displayName)
				val item = if (getItemById(stack.tagCompound!!.getIntArray("skills")[i]) != null) {
						(getItemById(stack.tagCompound!!.getIntArray("skills")[i])) as ItemSkill
				} else {
					return
				}
				val count = (i + 1).toString()

				cost += item.cost
				tooltip.add("$count : ${TextFormatting.UNDERLINE}$format")
			}
		}
		if (stack.tagCompound != null && stack.tagCompound!!.getIntArray("skills")[0] != 0) {
			tooltip.add("")
			tooltip.add("${TextComponentTranslation("text.skill_cost").formattedText}: " + cost.toString() + "MP")
			tooltip.add("${TextComponentTranslation("text.cooldown").formattedText}: ${getCooldownTime(stack)}${TextComponentTranslation("text.second").formattedText}")
		}
	}

	fun getCooldownTime(stack: ItemStack): Int{
		if (stack.getSkillCount() != 0) {
			var time = 0
			repeat(stack.getSkillCount()){
				time += stack.getItemSkill(it)?.cooldown!!
			}
			return time
		}
		return 0
	}
}