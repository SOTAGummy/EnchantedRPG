package potion

import Core
import net.minecraft.entity.ai.attributes.AttributeModifier
import java.awt.Color


class PotionCriticalDamage: CustomPotion("critical_damage", false, Color.PINK.rgb){
	init {
		this.registerPotionAttributeModifier(Core.CRITICAL_DAMAGE, "0c6beae8-8723-4e5d-8281-16f1cdda67ce", 0.0, 0)
	}

	override fun getAttributeModifierAmount(amplifier: Int, modifier: AttributeModifier): Double {
		return amplifier * 5.0
	}
}