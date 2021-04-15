package utils

import enchantment.AccessoryEnchantment
import items.rootItem.RootItem
import net.minecraft.entity.ai.attributes.IAttribute

object Storage {
	val Items = arrayListOf<RootItem>()
	val Attributes = arrayListOf<IAttribute>()
	val Enchantments = arrayListOf<AccessoryEnchantment>()
}