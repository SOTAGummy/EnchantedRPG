package source

import net.minecraft.entity.Entity

class FireDamage(entity: Entity): CustomDamageSource("fire", entity){
	override fun isFireDamage(): Boolean {
		return true
	}
}