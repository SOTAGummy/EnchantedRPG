package potion

import Core
import net.minecraft.entity.ai.attributes.AttributeModifier
import java.awt.Color

class PotionSPBoost: CustomPotion("sp_boost", false, Color.GREEN.rgb){
	init {
		this.registerPotionAttributeModifier(Core.SP_RECOVER_RATE, "72f41737-a1d0-45e9-b48c-e73fd29eb646", 0.0, 0)
	}

	override fun getAttributeModifierAmount(amplifier: Int, modifier: AttributeModifier): Double {
		return amplifier * 2.0
	}
}