package utils

import enchantment.AccessoryEnchantment
import items.baseItem.RootItem
import net.minecraft.entity.ai.attributes.IAttribute

object Storage {
	val Items = arrayListOf<RootItem>()
	val Attributes = arrayListOf<IAttribute>()
	val Enchantments = arrayListOf<AccessoryEnchantment>()
}