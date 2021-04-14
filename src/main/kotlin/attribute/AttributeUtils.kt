package attribute

import net.minecraft.entity.ai.attributes.IAttribute
import net.minecraft.entity.ai.attributes.RangedAttribute
import utils.Storage

object AttributeUtils {
	fun addAttribute(name: String, baseValue: Double, minValue: Double, maxValue: Double): IAttribute {
		val attributes = RangedAttribute(null, "${Core.ID}.$name", baseValue, minValue, maxValue).setShouldWatch(true)
		Storage.Attributes.add(attributes)
		return attributes
	}
}