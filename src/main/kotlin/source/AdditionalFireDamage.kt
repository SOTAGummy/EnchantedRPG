package source

import net.minecraft.entity.Entity

class AdditionalFireDamage(entity: Entity): CustomDamageSource("additional_fire", entity){
	override fun isFireDamage(): Boolean {
		return true
	}
}