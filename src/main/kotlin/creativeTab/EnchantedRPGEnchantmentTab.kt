package creativeTab

import Core
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import utils.Storage


object EnchantedRPGEnchantmentTab: CreativeTabs("${Core.ID}.enchantments"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Items.ENCHANTED_BOOK)
	}

	override fun displayAllRelevantItems(list: NonNullList<ItemStack>) {
		for (i in Storage.Enchantments){
			for (j in i.minLevel .. i.maxLevel){
				val stack = ItemStack(Items.ENCHANTED_BOOK)
				EnchantmentHelper.setEnchantments(mutableMapOf(Pair(i, j)), stack)
				list.add(stack)
			}
		}
		super.displayAllRelevantItems(list)
	}
}