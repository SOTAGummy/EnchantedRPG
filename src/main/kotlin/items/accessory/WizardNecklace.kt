package items.accessory

import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object WizardNecklace: ItemAccessory("wizard_necklace", Core.NECKLACE, IItemRarity.RARE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.NECKLACE){
			multimap.put(Core.MAX_SP.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "maxSp", 75.0, 0))
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "hp", 5.0, 0))
		}
		return multimap
	}
}