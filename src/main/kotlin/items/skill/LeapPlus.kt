package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

object LeapPlus: ItemSkill("leap_plus", 35, IItemRarity.EPIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vx = -MathHelper.sin(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vz = MathHelper.cos(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vy = -MathHelper.sin(Math.toRadians(player.rotationPitch.toDouble()).toFloat())
		player.addVelocity(vx.toDouble() * 1.5, vy.toDouble() * 1.5, vz.toDouble() * 1.5)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vx = -MathHelper.sin(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vz = MathHelper.cos(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vy = -MathHelper.sin(Math.toRadians(player.rotationPitch.toDouble()).toFloat())
		player.addVelocity(vx.toDouble() * 1.5, vy.toDouble() * 1.5, vz.toDouble() * 1.5)
	}
}