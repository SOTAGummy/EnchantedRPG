package enum

import items.baseItem.ItemSkill
import net.minecraft.util.text.TextFormatting

enum class IItemRarity(val colorChar: TextFormatting, val rgb: Int, val skills: ArrayList<ItemSkill>) {
	COMMON(TextFormatting.WHITE, 16777215, arrayListOf()),
	UNCOMMON(TextFormatting.GREEN, 5635925, arrayListOf()),
	RARE(TextFormatting.AQUA, 5636095, arrayListOf()),
	EPIC(TextFormatting.LIGHT_PURPLE, 16733695, arrayListOf()),
	LEGEND(TextFormatting.GOLD, 16755200, arrayListOf()),
	MYTHIC(TextFormatting.YELLOW, 16777045, arrayListOf()),
	MASTER(TextFormatting.RED, 16733525, arrayListOf())
}