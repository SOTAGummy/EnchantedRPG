package entity

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntitySnowball
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.World
import source.LightningDamage

class EntityLightningBall(world: World): EntitySnowball(world){
	constructor(world: World, source: EntityLivingBase): this(world){
		this.thrower = source
	}

	constructor(world: World, x: Double, y: Double, z: Double): this(world){
		this.posX = x
		this.posY = y
		this.posZ = z
	}

	override fun onImpact(result: RayTraceResult) {
		if (result.entityHit != null) {
			result.entityHit.attackEntityFrom(LightningDamage(this.thrower), 1F)
		}

		if (!world.isRemote) {
			world.setEntityState(this, 3.toByte())
			setDead()
		}
	}

	override fun handleStatusUpdate(id: Byte) {
		if (id.toInt() == 3) {
			for (i in 0..7) {
				world.spawnParticle(EnumParticleTypes.CRIT, posX, posY, posZ, 0.0, 0.0, 0.0)
			}
		}
	}
}