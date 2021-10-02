package potion

import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource

class PotionVampirism: CustomPotion("vampirism", false, 10040012){
	override fun onAttack(source: DamageSource, attacked: EntityLivingBase, amount: Float, amplifier: Int) {
		val heal = (amount * 0.1 * (amplifier + 1)).toFloat()
		val entity = source.trueSource as EntityLivingBase
		if (entity.health + heal <= entity.maxHealth){
			entity.health += heal
		} else {
			entity.health = entity.maxHealth
		}
	}
}