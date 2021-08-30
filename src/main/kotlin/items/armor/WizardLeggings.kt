package items.armor

import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemArmor
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object WizardLeggings: ItemArmor("wizard_leggings", Core.WIZARD, EntityEquipmentSlot.LEGS, IItemRarity.RARE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == EntityEquipmentSlot.LEGS){
			multimap.put(Core.MAX_SP.name, AttributeModifier(ARMOR_MODIFIERS[slot.index], "maxSp", 25.0, 0))
			multimap.put(Core.SP_RECOVER_RATE.name, AttributeModifier(ARMOR_MODIFIERS[slot.index], "spRecover", 1.0, 0))
		}
		return multimap
	}
}