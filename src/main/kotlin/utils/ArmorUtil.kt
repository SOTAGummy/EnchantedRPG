package utils

import Core
import net.minecraft.item.ItemArmor
import net.minecraft.util.SoundEvent
import net.minecraftforge.common.util.EnumHelper

object ArmorUtil {
	fun addArmorType(name: String, durability: Int, reduction: IntArray, enchantability: Int, sound: SoundEvent, toughness: Float): ItemArmor.ArmorMaterial {
		return EnumHelper.addArmorMaterial(name, "${Core.ID}:${name}_armor", durability, reduction, enchantability, sound, toughness)!!
	}
}