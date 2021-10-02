package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object MineMaster: ItemSkill("mine", 0, IItemRarity.MASTER, 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val pos = player.rayTrace(10.0, 0F)!!.blockPos
		world.getBlockState(pos).block.breakBlock(world, pos, world.getBlockState(pos))
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val pos = player.rayTrace(10.0, 0F)!!.blockPos
		world.getBlockState(pos).block.breakBlock(world, pos, world.getBlockState(pos))
	}
}