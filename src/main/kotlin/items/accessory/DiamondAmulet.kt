package items.accessory

import com.google.common.collect.Multimap
import enum.IItemRarity
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack

object DiamondAmulet: ItemAccessory("diamond_amulet", Core.AMULET, IItemRarity.RARE){
	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == Core.AMULET){
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.name, AttributeModifier(getUUID(slot), "hp", 10.0, 1))
			multimap.put(Core.MAX_SP.name, AttributeModifier(getUUID(slot), "maxmp", 50.0, 0))
		}
		return multimap
	}
}