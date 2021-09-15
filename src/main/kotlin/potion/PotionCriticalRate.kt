package potion

import net.minecraft.entity.ai.attributes.AttributeModifier
import java.awt.Color

class PotionCriticalRate: CustomPotion("critical_rate", false, Color.MAGENTA.rgb){
	init {
		this.registerPotionAttributeModifier(Core.CRITICAL_RATE, "bf233116-5351-4f11-b0e2-74eb30063542", 0.0, 0)
	}

	override fun getAttributeModifierAmount(amplifier: Int, modifier: AttributeModifier): Double {
		return amplifier * 5.0
	}
}