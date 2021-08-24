package enum

import items.baseItem.ItemSkill
import net.minecraft.util.text.TextFormatting

enum class IItemRarity(val colorChar: TextFormatting, val skills: ArrayList<ItemSkill>) {
	COMMON(TextFormatting.WHITE, arrayListOf()),
	UNCOMMON(TextFormatting.GREEN, arrayListOf()),
	RARE(TextFormatting.AQUA, arrayListOf()),
	EPIC(TextFormatting.LIGHT_PURPLE, arrayListOf()),
	LEGEND(TextFormatting.GOLD, arrayListOf()),
	MYTHIC(TextFormatting.YELLOW, arrayListOf()),
	SPECIAL(TextFormatting.DARK_BLUE, arrayListOf()),
	MASTER(TextFormatting.RED, arrayListOf())
}