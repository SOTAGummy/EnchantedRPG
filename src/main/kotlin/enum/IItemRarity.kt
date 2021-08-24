package enum

import net.minecraft.util.text.TextFormatting

enum class IItemRarity(val colorChar: TextFormatting) {
	COMMON(TextFormatting.WHITE),
	UNCOMMON(TextFormatting.GREEN),
	RARE(TextFormatting.AQUA),
	EPIC(TextFormatting.LIGHT_PURPLE),
	LEGEND(TextFormatting.GOLD),
	MYTHIC(TextFormatting.YELLOW),
	SPECIAL(TextFormatting.DARK_BLUE),
	MASTER(TextFormatting.RED)
}