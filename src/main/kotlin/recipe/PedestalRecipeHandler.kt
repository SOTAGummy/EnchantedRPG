package recipe

import net.minecraft.item.Item
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

	fun registerSkillRecipes(name: String): Array<PedestalRecipe>{
		val array = arrayOf<PedestalRecipe>()
		array[0] = PedestalRecipe(ItemStack(Item.getByNameOrId("${name}_uncommon")!!), arrayOf(
				ItemStack(Item.getByNameOrId("${name}_common")!!),
				ItemStack(Item.getByNameOrId("${name}_common")!!),
				ItemStack(Item.getByNameOrId("${name}_common")!!),
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY
		))

		return array
	}
}