package items.rootItem

import net.minecraft.item.Item
import utils.Storage

abstract class RootItem(name: String): Item(){
	init {
		Storage.Items.add(this)
	}
}