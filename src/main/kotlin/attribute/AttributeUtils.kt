package attribute

import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.entity.ai.attributes.RangedAttribute
import utils.Storage

object AttributeUtils {
	init {
		val health = SharedMonsterAttributes.MAX_HEALTH::class.java.getDeclaredField("maximumValue")
		health.isAccessible = true
		health.set(SharedMonsterAttributes.MAX_HEALTH, Double.MAX_VALUE)
		val armor = SharedMonsterAttributes.ARMOR::class.java.getDeclaredField("maximumValue")
		armor.isAccessible = true
		armor.set(SharedMonsterAttributes.ARMOR, Double.MAX_VALUE)
		val toughness = SharedMonsterAttributes.ARMOR_TOUGHNESS::class.java.getDeclaredField("maximumValue")
		toughness.isAccessible = true
		toughness.set(SharedMonsterAttributes.ARMOR_TOUGHNESS, Double.MAX_VALUE)
		val attack = SharedMonsterAttributes.ATTACK_DAMAGE::class.java.getDeclaredField("maximumValue")
		attack.isAccessible = true
		attack.set(SharedMonsterAttributes.ATTACK_DAMAGE, Double.MAX_VALUE)
		val speed = SharedMonsterAttributes.ATTACK_SPEED::class.java.getDeclaredField("maximumValue")
		speed.isAccessible = true
		speed.set(SharedMonsterAttributes.ATTACK_SPEED, Double.MAX_VALUE)
	}

	fun addAttribute(name: String, baseValue: Double, minValue: Double, maxValue: Double): IAttribute {
		val attributes = RangedAttribute(null, "${Core.ID}.$name", baseValue, minValue, maxValue).setShouldWatch(true)
		Storage.Attributes.add(attributes)
		return attributes
	}
}