package recipe

import Core
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object Recipes {
	init {
		PedestalRecipe(Core.uncommon_token, Core.common_token, Core.common_token, Core.common_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rare_token, Core.uncommon_token, Core.uncommon_token, Core.uncommon_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.epic_token, Core.rare_token, Core.rare_token, Core.rare_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.legend_token, Core.epic_token, Core.epic_token, Core.epic_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.mythic_token, Core.legend_token, Core.legend_token, Core.legend_token, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		registerSkillRecipes("heal")
		registerSkillRecipes("leap")
		registerSkillRecipes("arrow_rain")
		registerSkillRecipes("rage")
		registerSkillRecipes("berserk")
		registerSkillRecipes("black_hole")
		registerSkillRecipes("cure")
		registerSkillRecipes("blow")
		registerSkillRecipes("lightning")

		PedestalRecipe(Core.dragon_breath_special, Item.getItemFromBlock(Blocks.DRAGON_EGG), Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
	}

	fun registerSkillRecipes(name: String){
		PedestalRecipe(Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_uncommon"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_common"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_common"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_common"))!!,
				Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR
		)


		PedestalRecipe(Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_rare"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_uncommon"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_uncommon"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_uncommon"))!!,
				Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR
		)

		PedestalRecipe(Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_epic"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_rare"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_rare"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_rare"))!!,
				Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR
		)

		PedestalRecipe(Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_legend"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_epic"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_epic"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_epic"))!!,
				Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR
		)

		PedestalRecipe(Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_mythic"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_legend"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_legend"))!!,
				Item.REGISTRY.getObject(ResourceLocation(Core.ID, "${name}_legend"))!!,
				Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR
		)
	}
}