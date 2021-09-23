package potion

import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.DamageSource
import source.CriticalDamageSource
import java.awt.Color

class PotionPursuit: CustomPotion("pursuit", false, Color.RED.rgb){
	override fun onAttack(source: DamageSource, attacked: EntityLivingBase, amount: Float, amplifier: Int) {
		attacked.attackEntityFrom(CriticalDamageSource(source.trueSource!!), (amount * 0.1 * amplifier).toFloat())
	}
}