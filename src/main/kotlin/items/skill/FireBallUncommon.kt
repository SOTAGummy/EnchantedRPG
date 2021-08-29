package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.Explosion
import net.minecraft.world.World
import source.FireDamage

object FireBallUncommon: ItemSkill("fire_ball_uncommon", 25, IItemRarity.UNCOMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val ray = player.rayTrace(15.0, 0F)?.blockPos!!
		val ball = object: EntitySmallFireball(world, player, ray.x.toDouble(), ray.y.toDouble(), ray.z.toDouble()){
			var time = 0

			override fun canExplosionDestroyBlock(explosionIn: Explosion, worldIn: World, pos: BlockPos, blockStateIn: IBlockState, p_174816_5_: Float): Boolean {
				return false
			}

			override fun onImpact(result: RayTraceResult) {
				if (!this.world.isRemote) {
					if (result.entityHit != null) {
						result.entityHit.attackEntityFrom(FireDamage(shootingEntity), 8F)
						applyEnchantments(shootingEntity, result.entityHit)
					}
					setDead()
				}
			}

			override fun onUpdate() {
				time++
				if (time == 40) setDead()
				super.onUpdate()
				val entityList = world.getLivingEntitiesInArea(player.position, 10)
				if (entityList.size != 0) {
					this.setVelocity((entityList[0].posX - this.posX) / 2, (entityList[0].posY - this.posY) / 2, (entityList[0].posZ - this.posZ) / 2)
				} else {
					setDead()
				}
			}
		}
		ball.posY += 1
		world.spawnEntity(ball)
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 10).isNotEmpty()
	}
}