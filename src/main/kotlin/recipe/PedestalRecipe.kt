package recipe

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import utils.Storage

class PedestalRecipe(private val output: ItemStack, private val input: Array<ItemStack>) {
	init {
		Storage.Recipes.add(this)
	}

	constructor(output: Item, in1: Item, in2: Item, in3: Item, in4: Item, in5: Item, in6: Item, in7: Item, in8: Item)
			: this(ItemStack(output), arrayOf(ItemStack(in1), ItemStack(in2), ItemStack(in3), ItemStack(in4), ItemStack(in5), ItemStack(in6), ItemStack(in7), ItemStack(in8)))

	fun getIngredients(): Array<ItemStack>{
		return input
	}

	fun getCraftResult(): ItemStack{
		return output
	}

	fun canCraft(ingredient: Array<ItemStack>): Boolean{
		var count = 0
		var array1 = input.clone()
		var array2 = ingredient.clone()
		for (i in array1){
			first@ for (j in array2){
				if (ItemStack.areItemStacksEqual(i, j)){
					count++
					array1 = array1.toList().minus(i).toTypedArray().clone()
					array2 = array2.toList().minus(j).toTypedArray().clone()
					break@first
				}
			}
		}
		return count == 8
	}
}