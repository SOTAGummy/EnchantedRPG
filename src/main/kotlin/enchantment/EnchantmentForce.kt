package enchantment

import com.google.common.collect.Multimap
import items.baseItem.ItemAccessory
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.item.ItemStack

object EnchantmentForce: AccessoryEnchantment("force", Rarity.VERY_RARE){
	override fun getAttributes(stack: ItemStack, level: Int): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributes(stack, level)
		val item = stack.item as ItemAccessory
		multimap.put(Core.CRITICAL_DAMAGE.name, AttributeModifier(item.getUUID(item.equipmentSlot), "force", level * 5.0, 0))
		return multimap
	}
}