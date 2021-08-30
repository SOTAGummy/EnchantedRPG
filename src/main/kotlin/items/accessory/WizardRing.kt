package items.accessory

import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemAccessory
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object WizardRing: ItemAccessory("wizard_ring", Core.RING, IItemRarity.RARE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.RING){
			multimap.put(Core.MAX_SP.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "maxSp", 50.0, 0))
			multimap.put(Core.SP_RECOVER_RATE.name, AttributeModifier(ACCESSORY_MODIFIER[slot.index], "spSaving", 1.25, 0))
		}
		return multimap
	}
}