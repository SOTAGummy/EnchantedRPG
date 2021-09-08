package items

import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

object TestArea: Item(){
	init {
		this.registryName = ResourceLocation(Core.ID, "test_area")
		this.unlocalizedName = "test_area"
		this.creativeTab = Core.itemsTab
	}
}