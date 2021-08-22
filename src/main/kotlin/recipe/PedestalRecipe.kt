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
		val array1 = input.clone()
		val array2 = ingredient.clone()
		for (i in array1){
			first@ for (j in array2){
				if (ItemStack.areItemStacksEqual(i, j)){
					count++
					array1.toList().minus(i).toTypedArray()
					array2.toList().minus(j).toTypedArray()
					break@first
				}
			}
		}
		return count == 8
	}
}