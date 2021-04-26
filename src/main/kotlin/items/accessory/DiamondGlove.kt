package items.accessory

import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object DiamondGlove: ItemAccessory("diamond_glove", Core.GLOVE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.GLOVE){
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.name, AttributeModifier(getUUID(slot), "hp", 10.0, 0))
			multimap.put(Core.MAXSP.name, AttributeModifier(getUUID(slot), "maxmp", 50.0, 0))
		}
		return multimap
	}
}