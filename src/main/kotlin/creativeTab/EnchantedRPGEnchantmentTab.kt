package creativeTab

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
		repeat(Storage.Enchantments.size){
			val stack = ItemStack(Items.ENCHANTED_BOOK)
			EnchantmentHelper.setEnchantments(mutableMapOf(Pair(Storage.Enchantments[it], Storage.Enchantments[it].maxLevel)), stack)
			list.add(stack)
		}
		super.displayAllRelevantItems(list)
	}
}