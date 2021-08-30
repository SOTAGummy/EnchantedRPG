package items

import enum.IItemRarity
import items.baseItem.RootItem

object Amethyst: RootItem("amethyst", IItemRarity.RARE){
	init {
		maxStackSize = 64
		creativeTab = Core.itemsTab
	}
}