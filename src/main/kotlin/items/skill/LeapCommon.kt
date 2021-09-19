package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import kotlin.math.cos
import kotlin.math.sin

object LeapCommon: ItemSkill("leap", 25, IItemRarity.COMMON, 1){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vx = -sin(Math.toRadians(player.rotationYaw.toDouble())) * cos(
			Math.toRadians(player.rotationPitch.toDouble())
		)
		val vz = cos(Math.toRadians(player.rotationYaw.toDouble())) * cos(
			Math.toRadians(player.rotationPitch.toDouble())
		)
		val vy = -sin(Math.toRadians(player.rotationPitch.toDouble()))
		player.addVelocity(vx, vy, vz)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vx = -MathHelper.sin(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vz = MathHelper.cos(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
			Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vy = -MathHelper.sin(Math.toRadians(player.rotationPitch.toDouble()).toFloat())
		player.addVelocity(vx.toDouble(), vy.toDouble(), vz.toDouble())
	}
}