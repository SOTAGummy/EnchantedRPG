package enchantment

import Core
import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack

object EnchantmentInsight: AccessoryEnchantment("insight", Rarity.VERY_RARE){
	override fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributes(stack, level)
		val item = stack.item as ItemAccessory
		multimap.put(Core.CRITICAL_RATE.name, AttributeModifier(getUUID(item.equipmentSlot), "critical_rate", level * 2.5, 0))
		return multimap
	}
}