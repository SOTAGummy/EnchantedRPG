package utils

import enchantment.AccessoryEnchantment
import items.baseItem.RootItem
import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.potion.Potion
import net.minecraft.util.SoundEvent
import recipe.PedestalRecipe

object Storage {
	val Items = arrayListOf<RootItem>()
	val Sounds = arrayListOf<SoundEvent>()
	val Potions = arrayListOf<Potion>()
	val Recipes = arrayListOf<PedestalRecipe>()
	val Attributes = arrayListOf<IAttribute>()
	val Enchantments = arrayListOf<AccessoryEnchantment>()
}