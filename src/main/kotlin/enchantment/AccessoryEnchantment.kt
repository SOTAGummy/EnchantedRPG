package enchantment

import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.init.Items
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage
import java.util.*

abstract class AccessoryEnchantment(name: String, rarity: Rarity): Enchantment(rarity, Core.accessoryType, arrayOf(Core.NECKLACE, Core.AMULET, Core.GLOVE, Core.RING)){
	init {
		Storage.Enchantments.add(this)
		registryName = ResourceLocation(Core.ID, name)
		this.name = name
	}

	companion object{
		private val ENCHANTMENT_MODIFIER = arrayOf(
				UUID.fromString("e82d32e7-e2e3-44e1-bc7a-650678089058"),
				UUID.fromString("6a5d6a90-9dd9-4c05-89ab-90b63144d2a1"),
				UUID.fromString("e3c7b68e-4cb7-4727-97f9-7165750dee48"),
				UUID.fromString("14b0dd2f-88a0-4355-9256-53d43ccbb26f")
		)
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
		return 1
	}

	override fun canApplyTogether(ench: Enchantment): Boolean {
		return ench !is AccessoryEnchantment
	}

	fun getUUID(slot: EntityEquipmentSlot): UUID {
		return ENCHANTMENT_MODIFIER[slot.index]
	}
}