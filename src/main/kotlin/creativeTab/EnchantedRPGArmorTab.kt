package creativeTab

import Core
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.NonNullList
import utils.Storage

object EnchantedRPGArmorTab: CreativeTabs("${Core.ID}.armor"){
	override fun getTabIconItem(): ItemStack {
		return ItemStack(Core.wizardHelmet)
	}

	override fun displayAllRelevantItems(list: NonNullList<ItemStack>) {
		for (i in Storage.Armor) {
			val stack = ItemStack(i)
			val nbt = NBTTagCompound()
			nbt.setBoolean("Unbreakable", true)
			stack.tagCompound = nbt
			list.add(stack)
		}
		super.displayAllRelevantItems(list)
	}
}