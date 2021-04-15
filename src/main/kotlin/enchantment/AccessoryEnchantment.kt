package enchantment

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage

abstract class AccessoryEnchantment(name: String, rarity: Rarity): Enchantment(rarity, Core.accessoryType, arrayOf(Core.NECKLACE, Core.AMULET, Core.GLOVE, Core.RING)){
	init {
		Storage.Enchantments.add(this)
		registryName = ResourceLocation(Core.ID, name)
	}

	open fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		return HashMultimap.create()
	}

	override fun canApply(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory
	}

	override fun canApplyAtEnchantingTable(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory
	}

	override fun getMinLevel(): Int {
		return 1
	}

	override fun isTreasureEnchantment(): Boolean {
		return true
	}
}