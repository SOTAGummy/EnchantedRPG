package items.skill

import Core
import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTest: ItemSkill("code_test", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val recipe1 = arrayOf(
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack(Items.APPLE, 1),
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY
		)

		println(Core.testRecipe.canCraft(recipe1))
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val recipe1 = arrayOf(
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack(Items.APPLE, 1),
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY,
				ItemStack.EMPTY
		)

		println(Core.testRecipe.canCraft(recipe1))
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		if (world.isRemote){
			clientFunction(world, player, hand)
		} else {
			serverFunction(world, player, hand)
		}
		return super.onItemRightClick(world, player, hand)
	}
}