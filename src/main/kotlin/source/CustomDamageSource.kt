package source

import net.minecraft.entity.Entity
import net.minecraft.util.DamageSource

abstract class CustomDamageSource(name: String, val entity: Entity): DamageSource(name){
	override fun getTrueSource(): Entity {
		return entity
	}
}