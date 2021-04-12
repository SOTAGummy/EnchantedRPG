package items

import items.rootItem.RootItem
import net.minecraft.util.ResourceLocation

object EnchantedDust: RootItem("enchanted_dust"){
	init {
		maxStackSize = 64
		registryName = ResourceLocation(Core.ID, "enchanted_dust")
		unlocalizedName = "enchanted_dust"
		creativeTab = Core.itemsTab
	}
}