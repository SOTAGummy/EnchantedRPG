package items

import enum.IItemRarity
import items.baseItem.RootItem

object WitchClose: RootItem("witch_close", IItemRarity.RARE){
	init {
		maxStackSize = 64
		creativeTab = Core.itemsTab
	}
}