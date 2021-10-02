package items.armor

import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemArmor
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object GhostLeggings: ItemArmor("ghost_leggings", Core.GHOST, EntityEquipmentSlot.LEGS, IItemRarity.EPIC) {
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == EntityEquipmentSlot.LEGS) {
			multimap.put(Core.MAX_SP.name, AttributeModifier(ARMOR_MODIFIERS[slot.index], "maxSp", 25.0, 0))
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.name, AttributeModifier(ARMOR_MODIFIERS[slot.index], "spRecover", 1.0, 1))
		}
		return multimap
	}
}