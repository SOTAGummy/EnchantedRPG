package enchantment

import Core
import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack

object EnchantmentWise: AccessoryEnchantment("wise", Rarity.VERY_RARE){
	override fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributes(stack, level)
		val item = stack.item as ItemAccessory
		multimap.put(Core.MAX_SP.name, AttributeModifier(getUUID(item.equipmentSlot), "sp", level * 10.0, 0))
		return multimap
	}
}