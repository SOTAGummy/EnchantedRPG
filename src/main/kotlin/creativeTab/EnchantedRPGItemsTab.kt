package creativeTab

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object EnchantedRPGItemsTab: CreativeTabs("${Core.ID}.items"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Core.skillBook)
	}
}