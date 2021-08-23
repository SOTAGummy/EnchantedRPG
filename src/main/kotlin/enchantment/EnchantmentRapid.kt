package enchantment

import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack

object EnchantmentRapid: AccessoryEnchantment("rapid", Rarity.VERY_RARE){
	override fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributes(stack, level)
		val item = stack.item as ItemAccessory
		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.name, AttributeModifier(item.getUUID(item.equipmentSlot), "knockbackResistance", 0.2 * level, 0))
		return multimap
	}
}