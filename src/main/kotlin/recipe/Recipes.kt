package recipe

import Core
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object Recipes {
	init {
		PedestalRecipe(Core.uncommonToken, Core.commonToken, Core.commonToken, Core.commonToken, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.rareToken, Core.uncommonToken, Core.uncommonToken, Core.uncommonToken, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.epicToken, Core.rareToken, Core.rareToken, Core.rareToken, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.legendToken, Core.epicToken, Core.epicToken, Core.epicToken, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.mythicToken, Core.legendToken, Core.legendToken, Core.legendToken, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)

		registerSkillRecipes("heal")
		registerSkillRecipes("leap")
		registerSkillRecipes("arrow_rain")
		registerSkillRecipes("rage")
		registerSkillRecipes("berserk")
		registerSkillRecipes("black_hole")
		registerSkillRecipes("cure")
		registerSkillRecipes("blow")
		registerSkillRecipes("lightning")
		registerSkillRecipes("explosion")
		registerSkillRecipes("fire_ball")
		registerSkillRecipes("shock_wave")
		registerSkillRecipes("vampirism")

		PedestalRecipe(Core.dragonBreathSpecial, Item.getItemFromBlock(Blocks.DRAGON_EGG), Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
		PedestalRecipe(Core.amethyst, Core.enchantedDust, Items.DIAMOND, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR, Items.AIR)
	}

	private fun registerSkillRecipes(name: String){
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