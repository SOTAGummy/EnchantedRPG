package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityDragonFireball
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import kotlin.math.cos
import kotlin.math.sin

object DragonBreathMythic: ItemSkill("dragon_breath", 100, IItemRarity.MYTHIC, 6){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val vecX = -sin(Math.toRadians(player.rotationYaw.toDouble())) * cos(Math.toRadians(player.rotationPitch.toDouble()))
		val vecZ = cos(Math.toRadians(player.rotationYaw.toDouble())) * cos(Math.toRadians(player.rotationPitch.toDouble()))
		val vecY = -sin(Math.toRadians(player.rotationPitch.toDouble()))
		val entity = EntityDragonFireball(world, player.posX, player.posY + 1, player.posZ, vecX, vecY, vecZ)
		entity.shootingEntity = player
		entity.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch)
		world.spawnEntity(entity)
	}
}