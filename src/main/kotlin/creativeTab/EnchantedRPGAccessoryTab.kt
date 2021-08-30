package creativeTab

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object EnchantedRPGAccessoryTab: CreativeTabs("${Core.ID}.accessories"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Core.diamondRing)
	}
}