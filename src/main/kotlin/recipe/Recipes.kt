package recipe

import Core
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item

object Recipes {
	init {
		PedestalRecipe(Core.uncommon_token, Core.common_token, Core.common_token, Core.common_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rare_token, Core.uncommon_token, Core.uncommon_token, Core.uncommon_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.epic_token, Core.rare_token, Core.rare_token, Core.rare_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.legend_token, Core.epic_token, Core.epic_token, Core.epic_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.mythic_token, Core.legend_token, Core.legend_token, Core.legend_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		PedestalRecipe(Core.heal_uncommon, Core.heal_common, Core.heal_common, Core.heal_common, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.heal_rare, Core.heal_uncommon, Core.heal_uncommon, Core.heal_uncommon, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.heal_epic, Core.heal_rare, Core.heal_rare, Core.heal_rare, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.heal_legend, Core.heal_epic, Core.heal_epic, Core.heal_epic, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.heal_mythic, Core.heal_legend, Core.heal_legend, Core.heal_legend, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		PedestalRecipe(Core.leap_uncommon, Core.leap_common, Core.leap_common, Core.leap_common, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.leap_rare, Core.leap_uncommon, Core.leap_uncommon, Core.leap_uncommon, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.leap_epic, Core.leap_rare, Core.leap_rare, Core.leap_rare, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.leap_legend, Core.leap_epic, Core.leap_epic, Core.leap_epic, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.leap_mythic, Core.leap_legend, Core.leap_legend, Core.leap_legend, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		PedestalRecipe(Core.arrow_rain_uncommon, Core.arrow_rain_common, Core.arrow_rain_common, Core.arrow_rain_common, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.arrow_rain_rare, Core.arrow_rain_uncommon, Core.arrow_rain_uncommon, Core.arrow_rain_uncommon, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.arrow_rain_epic, Core.arrow_rain_rare, Core.arrow_rain_rare, Core.arrow_rain_rare, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.arrow_rain_legend, Core.arrow_rain_epic, Core.arrow_rain_epic, Core.arrow_rain_epic, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.arrow_rain_mythic, Core.arrow_rain_legend, Core.arrow_rain_legend, Core.arrow_rain_legend, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		PedestalRecipe(Core.rage_uncommon, Core.rage_common, Core.rage_common, Core.rage_common, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rage_rare, Core.rage_uncommon, Core.rage_uncommon, Core.rage_uncommon, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rage_epic, Core.rage_rare, Core.rage_rare, Core.rage_rare, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rage_legend, Core.rage_epic, Core.rage_epic, Core.rage_epic, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rage_mythic, Core.rage_legend, Core.rage_legend, Core.rage_legend, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		PedestalRecipe(Core.dragon_breath_special, Item.getItemFromBlock(Blocks.DRAGON_EGG), Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
	}
}