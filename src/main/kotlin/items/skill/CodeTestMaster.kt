package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTestMaster: ItemSkill("code_test", 0, IItemRarity.MASTER, 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val ray = player.rayTrace(10.0, 0F)?.blockPos!!
		world.getBlockState(ray).block.breakBlock(world, ray, world.getBlockState(ray))
		world.setBlockState(ray, Blocks.AIR.defaultState)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val ray = player.rayTrace(10.0, 0F)?.blockPos!!
		world.getBlockState(ray).block.dropBlockAsItem(world, ray, world.getBlockState(ray), 0)
		world.getBlockState(ray).block.breakBlock(world, ray, world.getBlockState(ray))
		world.setBlockState(ray, Blocks.AIR.defaultState)
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getBlockState(player.rayTrace(10.0, 0F)?.blockPos).block != Blocks.AIR

	}
}