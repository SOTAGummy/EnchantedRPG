package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityDragonFireball
import net.minecraft.util.EnumHand
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

object DragonBreathSpecial: ItemSkill("dragon_breath_special", 100, IItemRarity.SPECIAL){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vecX = -MathHelper.sin(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
				Math.toRadians(player.rotationPitch.toDouble()).toFloat())
		val vecZ = MathHelper.cos(Math.toRadians(player.rotationYaw.toDouble()).toFloat()) * MathHelper.cos(
				Math.toRadians(player.rotationPitch.toDouble()).toFloat()
		)
		val vecY = -MathHelper.sin(Math.toRadians(player.rotationPitch.toDouble()).toFloat())
		val entity = EntityDragonFireball(world, player, vecX.toDouble(), vecY.toDouble(), vecZ.toDouble())
		world.spawnEntity(entity)
	}
}