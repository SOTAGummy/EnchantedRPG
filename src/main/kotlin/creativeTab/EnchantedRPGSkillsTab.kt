package creativeTab

import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object EnchantedRPGSkillsTab: CreativeTabs("${Core.ID}.skills"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Core.code_test_master)
	}
}