package particle

import Core
import net.minecraft.client.particle.IParticleFactory
import net.minecraft.client.particle.Particle
import net.minecraft.world.World

class TestParticle(world: World, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double): Particle(world, x, y, z, xSpeed, ySpeed, zSpeed){
	init {
		this.setParticleTexture(Core.test_texture)

		motionX = xSpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806
		motionY = ySpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806
		motionZ = zSpeed + (Math.random() * 2.0 - 1.0) * 0.05000000074505806
		val f = rand.nextFloat() * 0.3f + 0.7f
		particleRed = f
		particleGreen = f
		particleBlue = f
		particleScale = rand.nextFloat() * rand.nextFloat() * 6.0f + 1.0f
		particleMaxAge = (16.0 / (rand.nextFloat().toDouble() * 0.8 + 0.2)).toInt() + 2
	}

	override fun getFXLayer(): Int {
		return 1
	}

	class Factory: IParticleFactory{
		override fun createParticle(particleID: Int, world: World, x: Double, y: Double, z: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double, vararg parameter: Int): Particle? {
			return TestParticle(world, x, y, z, xSpeed, ySpeed, zSpeed)
		}
	}
}