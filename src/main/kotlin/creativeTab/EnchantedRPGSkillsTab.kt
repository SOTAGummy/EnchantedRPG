package creativeTab

import Core
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object EnchantedRPGSkillsTab: CreativeTabs("${Core.ID}.skills"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Core.codeTestMaster)
	}
}