package potion

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.util.DamageSource

class PotionVampirism: CustomPotion("vampirism", false, 10040012){
	override fun onAttack(source: DamageSource, attacked: Entity, amount: Float, amplifier: Int) {
		(source.trueSource as EntityLiving).heal((amount * 0.1 * (amplifier + 1)).toFloat())
	}
}