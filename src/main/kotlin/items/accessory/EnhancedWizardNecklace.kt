package items.accessory

import Core
import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object EnhancedWizardNecklace: ItemAccessory("enhanced_wizard_necklace", Core.NECKLACE, IItemRarity.EPIC){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.NECKLACE){
			multimap.put(Core.MAX_SP.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "maxSp", 100.0, 0))
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "hp", 5.0, 0))
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "atk", 0.5, 0))
		}
		return multimap
	}
}