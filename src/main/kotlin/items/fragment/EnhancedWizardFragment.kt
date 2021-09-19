package items.fragment

import enum.IItemRarity
import items.baseItem.ItemFragment
import net.minecraft.item.ItemStack

object EnhancedWizardFragment: ItemFragment("enhanced_wizard", IItemRarity.EPIC){
	override fun hasEffect(stack: ItemStack): Boolean {
		return true
	}
}