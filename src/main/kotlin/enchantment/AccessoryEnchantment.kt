package enchantment

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage

abstract class AccessoryEnchantment(name: String, rarity: Rarity): Enchantment(rarity, Core.accessoryType, arrayOf(Core.NECKLACE, Core.AMULET, Core.GLOVE, Core.RING)){
	init {
		Storage.Enchantments.add(this)
		registryName = ResourceLocation(Core.ID, name)
		this.name = name
	}

	open fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		return HashMultimap.create()
	}

	override fun canApply(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory
	}

	override fun canApplyAtEnchantingTable(stack: ItemStack): Boolean {
		return stack.item is ItemAccessory || stack.item == Items.BOOK
	}

	override fun getMinLevel(): Int {
		return 1
	}

	override fun isTreasureEnchantment(): Boolean {
		return false
	}

	override fun getMaxLevel(): Int {
		return 5
	}

	override fun getMaxEnchantability(enchantmentLevel: Int): Int {
		return 30
	}

	override fun getMinEnchantability(enchantmentLevel: Int): Int {
		return 30
	}
}