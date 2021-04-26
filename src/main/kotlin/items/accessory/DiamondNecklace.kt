package items.accessory

import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object DiamondNecklace: ItemAccessory("diamond_necklace", Core.NECKLACE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.NECKLACE){
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.name, AttributeModifier(getUUID(slot), "atk", 1.0, 0))
			multimap.put(Core.MAXSP.name, AttributeModifier(getUUID(slot), "maxmp", 50.0, 0))
		}
		return multimap
	}
}