package recipe

import net.minecraft.item.ItemStack

object PedestalRecipeHandler {
	private var recipes: ArrayList<Pair<ItemStack, ArrayList<ItemStack>>> = arrayListOf()

	fun addRecipe(output: ItemStack, input: ArrayList<ItemStack>){
		recipes.add(Pair(output, input))
	}

	fun getCraftResult(array: ArrayList<ItemStack>): ItemStack{
		repeat(recipes.size){
			return if (containsItemStack(recipes[it].second, array)){
				recipes[it].first
			} else {
				ItemStack.EMPTY
			}
		}

		return ItemStack.EMPTY
	}

	private fun containsItemStack(array1: ArrayList<ItemStack>, array2: ArrayList<ItemStack>): Boolean{
		var flag0 = false
		var flag1 = false
		var flag2 = false
		var flag3 = false
		var flag4 = false
		var flag5 = false
		var flag6 = false
		var flag7 = false

		if (array1[0].isItemEqual(array2[0]) ||
			array1[0].isItemEqual(array2[1]) ||
			array1[0].isItemEqual(array2[2]) ||
			array1[0].isItemEqual(array2[3]) ||
			array1[0].isItemEqual(array2[4]) ||
			array1[0].isItemEqual(array2[5]) ||
			array1[0].isItemEqual(array2[6]) ||
			array1[0].isItemEqual(array2[7]) ||
			(array1[0].isEmpty && array2[0].isEmpty) ||
			(array1[0].isEmpty && array2[1].isEmpty) ||
			(array1[0].isEmpty && array2[2].isEmpty) ||
			(array1[0].isEmpty && array2[3].isEmpty) ||
			(array1[0].isEmpty && array2[4].isEmpty) ||
			(array1[0].isEmpty && array2[5].isEmpty) ||
			(array1[0].isEmpty && array2[6].isEmpty) ||
			(array1[0].isEmpty && array2[7].isEmpty)
		){
			flag0 = true
		}

		if (array1[1].isItemEqual(array2[0]) ||
			array1[1].isItemEqual(array2[1]) ||
			array1[1].isItemEqual(array2[2]) ||
			array1[1].isItemEqual(array2[3]) ||
			array1[1].isItemEqual(array2[4]) ||
			array1[1].isItemEqual(array2[5]) ||
			array1[1].isItemEqual(array2[6]) ||
			array1[1].isItemEqual(array2[7]) ||
			(array1[1].isEmpty && array2[0].isEmpty) ||
			(array1[1].isEmpty && array2[1].isEmpty) ||
			(array1[1].isEmpty && array2[2].isEmpty) ||
			(array1[1].isEmpty && array2[3].isEmpty) ||
			(array1[1].isEmpty && array2[4].isEmpty) ||
			(array1[1].isEmpty && array2[5].isEmpty) ||
			(array1[1].isEmpty && array2[6].isEmpty) ||
			(array1[1].isEmpty && array2[7].isEmpty)
		){
			flag1 = true
		}

		if (array1[2].isItemEqual(array2[0]) ||
			array1[2].isItemEqual(array2[1]) ||
			array1[2].isItemEqual(array2[2]) ||
			array1[2].isItemEqual(array2[3]) ||
			array1[2].isItemEqual(array2[4]) ||
			array1[2].isItemEqual(array2[5]) ||
			array1[2].isItemEqual(array2[6]) ||
			array1[2].isItemEqual(array2[7]) ||
			(array1[2].isEmpty && array2[0].isEmpty) ||
			(array1[2].isEmpty && array2[1].isEmpty) ||
			(array1[2].isEmpty && array2[2].isEmpty) ||
			(array1[2].isEmpty && array2[3].isEmpty) ||
			(array1[2].isEmpty && array2[4].isEmpty) ||
			(array1[2].isEmpty && array2[5].isEmpty) ||
			(array1[2].isEmpty && array2[6].isEmpty) ||
			(array1[2].isEmpty && array2[7].isEmpty)
		){
			flag2 = true
		}

		if (array1[3].isItemEqual(array2[0]) ||
			array1[3].isItemEqual(array2[1]) ||
			array1[3].isItemEqual(array2[2]) ||
			array1[3].isItemEqual(array2[3]) ||
			array1[3].isItemEqual(array2[4]) ||
			array1[3].isItemEqual(array2[5]) ||
			array1[3].isItemEqual(array2[6]) ||
			array1[3].isItemEqual(array2[7]) ||
			(array1[3].isEmpty && array2[0].isEmpty) ||
			(array1[3].isEmpty && array2[1].isEmpty) ||
			(array1[3].isEmpty && array2[2].isEmpty) ||
			(array1[3].isEmpty && array2[3].isEmpty) ||
			(array1[3].isEmpty && array2[4].isEmpty) ||
			(array1[3].isEmpty && array2[5].isEmpty) ||
			(array1[3].isEmpty && array2[6].isEmpty) ||
			(array1[3].isEmpty && array2[7].isEmpty)
		){
			flag3 = true
		}

		if (array1[4].isItemEqual(array2[0]) ||
			array1[4].isItemEqual(array2[1]) ||
			array1[4].isItemEqual(array2[2]) ||
			array1[4].isItemEqual(array2[3]) ||
			array1[4].isItemEqual(array2[4]) ||
			array1[4].isItemEqual(array2[5]) ||
			array1[4].isItemEqual(array2[6]) ||
			array1[4].isItemEqual(array2[7]) ||
			(array1[4].isEmpty && array2[0].isEmpty) ||
			(array1[4].isEmpty && array2[1].isEmpty) ||
			(array1[4].isEmpty && array2[2].isEmpty) ||
			(array1[4].isEmpty && array2[3].isEmpty) ||
			(array1[4].isEmpty && array2[4].isEmpty) ||
			(array1[4].isEmpty && array2[5].isEmpty) ||
			(array1[4].isEmpty && array2[6].isEmpty) ||
			(array1[4].isEmpty && array2[7].isEmpty)
		){
			flag4 = true
		}

		if (array1[5].isItemEqual(array2[0]) ||
			array1[5].isItemEqual(array2[1]) ||
			array1[5].isItemEqual(array2[2]) ||
			array1[5].isItemEqual(array2[3]) ||
			array1[5].isItemEqual(array2[4]) ||
			array1[5].isItemEqual(array2[5]) ||
			array1[5].isItemEqual(array2[6]) ||
			array1[5].isItemEqual(array2[7]) ||
			(array1[5].isEmpty && array2[0].isEmpty) ||
			(array1[5].isEmpty && array2[1].isEmpty) ||
			(array1[5].isEmpty && array2[2].isEmpty) ||
			(array1[5].isEmpty && array2[3].isEmpty) ||
			(array1[5].isEmpty && array2[4].isEmpty) ||
			(array1[5].isEmpty && array2[5].isEmpty) ||
			(array1[5].isEmpty && array2[6].isEmpty) ||
			(array1[5].isEmpty && array2[7].isEmpty)
		){
			flag5 = true
		}

		if (array1[6].isItemEqual(array2[0]) ||
			array1[6].isItemEqual(array2[1]) ||
			array1[6].isItemEqual(array2[2]) ||
			array1[6].isItemEqual(array2[3]) ||
			array1[6].isItemEqual(array2[4]) ||
			array1[6].isItemEqual(array2[5]) ||
			array1[6].isItemEqual(array2[6]) ||
			array1[6].isItemEqual(array2[7]) ||
			(array1[6].isEmpty && array2[0].isEmpty) ||
			(array1[6].isEmpty && array2[1].isEmpty) ||
			(array1[6].isEmpty && array2[2].isEmpty) ||
			(array1[6].isEmpty && array2[3].isEmpty) ||
			(array1[6].isEmpty && array2[4].isEmpty) ||
			(array1[6].isEmpty && array2[5].isEmpty) ||
			(array1[6].isEmpty && array2[6].isEmpty) ||
			(array1[6].isEmpty && array2[7].isEmpty)
		){
			flag6 = true
		}

		if (array1[7].isItemEqual(array2[0]) ||
			array1[7].isItemEqual(array2[1]) ||
			array1[7].isItemEqual(array2[2]) ||
			array1[7].isItemEqual(array2[3]) ||
			array1[7].isItemEqual(array2[4]) ||
			array1[7].isItemEqual(array2[5]) ||
			array1[7].isItemEqual(array2[6]) ||
			array1[7].isItemEqual(array2[7]) ||
			(array1[7].isEmpty && array2[0].isEmpty) ||
			(array1[7].isEmpty && array2[1].isEmpty) ||
			(array1[7].isEmpty && array2[2].isEmpty) ||
			(array1[7].isEmpty && array2[3].isEmpty) ||
			(array1[7].isEmpty && array2[4].isEmpty) ||
			(array1[7].isEmpty && array2[5].isEmpty) ||
			(array1[7].isEmpty && array2[6].isEmpty) ||
			(array1[7].isEmpty && array2[7].isEmpty)
		){
			flag7 = true
		}

		return flag0 && flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7
	}
}