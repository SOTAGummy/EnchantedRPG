package items.rootItem

import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import utils.Storage

open class RootItem(name: String): Item(){
	init {
		this.unlocalizedName = name
		this.registryName = ResourceLocation(Core.ID, name)
		Storage.Items.add(this)
	}
}