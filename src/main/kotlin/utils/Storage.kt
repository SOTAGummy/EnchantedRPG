package utils

import enchantment.AccessoryEnchantment
import items.baseItem.RootItem
import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.util.SoundEvent

object Storage {
	val Items = arrayListOf<RootItem>()
	val Sounds = arrayListOf<SoundEvent>()
	val Attributes = arrayListOf<IAttribute>()
	val Enchantments = arrayListOf<AccessoryEnchantment>()
}