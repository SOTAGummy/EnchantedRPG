package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World
import recipe.PedestalRecipeHandler

object CodeTest: ItemSkill("code_test", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val recipe = arrayListOf(
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack.EMPTY,
			ItemStack(Items.APPLE, 1)
		)
		PedestalRecipeHandler.addRecipe(ItemStack(Items.DIAMOND, 1), recipe)
	}
}