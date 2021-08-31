package potion

import net.minecraft.entity.EntityLivingBase

class PotionNoGravity: CustomPotion("no_gravity",false, 1390139){
	override fun performEffect(entity: EntityLivingBase, amplifier: Int) {
		entity.setNoGravity(true)
		entity.addVelocity(0.0, 0.02, 0.0)
	}
}