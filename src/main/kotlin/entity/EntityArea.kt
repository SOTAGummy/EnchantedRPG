package entity

import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class EntityArea(world: World, x: Double, y: Double, z: Double, stack: ItemStack, val target: Entity): EntityItem(world, x, y, z, stack){
	init {
		this.motionY = 0.0
		this.motionX = 0.0
		this.motionZ = 0.0
		this.setSize(0.0000000000000000000001F, 0.0000000000000000000001F)
	}

	override fun onUpdate() {
		this.setAgeToCreativeDespawnTime()
		this.isImmuneToFire = true
		this.motionY = 0.0
		this.setNoGravity(true)
		this.setInfinitePickupDelay()
		super.onUpdate()
		this.setVelocity(0.0, 0.0, 0.0)
		this.setPosition(target.posX, target.posY - 1, target.posZ)
	}
}