package recipe

import net.minecraft.item.ItemStack
import utils.Storage

object PedestalRecipeHandler {
	fun getCraftResult(ingredients: Array<ItemStack>): ItemStack{
		for (i in Storage.Recipes){
			if (i.canCraft(ingredients)){
				return i.getCraftResult()
			}
		}

		return ItemStack.EMPTY
	}
}