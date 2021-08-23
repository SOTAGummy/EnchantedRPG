package enchantment

import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack

object EnchantmentBlessing: AccessoryEnchantment("blessing", Rarity.VERY_RARE){
	override fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributes(stack, level)
		val item = stack.item as ItemAccessory
		multimap.put(SharedMonsterAttributes.LUCK.name, AttributeModifier(item.getUUID(item.equipmentSlot), "armor", level.toDouble(), 0))
		return multimap
	}
}