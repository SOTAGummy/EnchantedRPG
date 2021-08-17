package recipe

import net.minecraft.item.ItemStack
import utils.Storage

class PedestalRecipe(private val output: ItemStack, private val input: Array<ItemStack>) {
	init {
		Storage.Recipes.add(this)
	}

	fun getIngredients(): Array<ItemStack>{
		return input
	}

	fun getCraftResult(): ItemStack{
		return output
	}

	fun canCraft(ingredient: Array<ItemStack>): Boolean{
		var count = 0
		for (i in 0 .. 7){
			first@ for (j in 0 .. 7){
				if (ItemStack.areItemStacksEqual(ingredient[i], input[j])){
					count++
					break@first
				}
			}
		}
		return count == 8
	}
}