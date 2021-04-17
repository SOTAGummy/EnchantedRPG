package utils

import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.util.EnumParticleTypes
import sun.misc.Unsafe
import sun.reflect.ConstructorAccessor
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object EnumExtension {
	fun addEquipmentSlot(name: String, ordinal: Int, type: EntityEquipmentSlot.Type, index: Int, slotIndex: Int, nameln: String): EntityEquipmentSlot {
		val method: Method = Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor")
		method.isAccessible = true

		val cls: Constructor<out EntityEquipmentSlot> = EntityEquipmentSlot::class.java.getDeclaredConstructor(String::class.java, Int::class.java, EntityEquipmentSlot.Type::class.java, Int::class.java, Int::class.java, String::class.java)
		val ca: ConstructorAccessor = method.invoke(cls) as ConstructorAccessor

		val result: EntityEquipmentSlot = ca.newInstance(arrayOf(name, ordinal, type, index, slotIndex, nameln)) as EntityEquipmentSlot
		addValueToEnum(result)
		return result
	}

	fun addSlotType(name: String, ordinal: Int): EntityEquipmentSlot.Type {
		val method: Method = Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor")
		method.isAccessible = true

		val cls: Constructor<out EntityEquipmentSlot.Type> = EntityEquipmentSlot.Type::class.java.getDeclaredConstructor(String::class.java, Int::class.java)
		val ca: ConstructorAccessor = method.invoke(cls) as ConstructorAccessor

		val result: EntityEquipmentSlot.Type = ca.newInstance(arrayOf(name, ordinal)) as EntityEquipmentSlot.Type
		addValueToEnum(result)
		return result
	}

	fun addParticleType(name: String, ordinal: Int, id: Int, ignoreRange: Boolean): EnumParticleTypes{
		val method: Method = Constructor::class.java.getDeclaredMethod("acquireConstructorAccessor")
		method.isAccessible = true

		val cls: Constructor<EnumParticleTypes> = EnumParticleTypes::class.java.getDeclaredConstructor(String::class.java, Int::class.java, String::class.java, Int::class.java, Boolean::class.java)
		val ca: ConstructorAccessor = method.invoke(cls) as ConstructorAccessor

		val result: EnumParticleTypes = ca.newInstance(arrayOf(name.toUpperCase(), ordinal, name, id, ignoreRange)) as EnumParticleTypes
		addValueToEnum(result)
		return result
	}

	private fun <T : Enum<*>> addValueToEnum(value: T) {
		val field: Field = value::class.java.getDeclaredField("\$VALUES")
		field.isAccessible = true
		@SuppressWarnings("unchecked")
		val values: Array<T> = field.get(null) as Array<T>
		val newValues = values.copyOf(values.size + 1)
		newValues[values.size] = value

		val uf: Field = Unsafe::class.java.getDeclaredField("theUnsafe")
		uf.isAccessible = true
		val unsafe: Unsafe = uf.get(null) as Unsafe
		unsafe.putObjectVolatile(unsafe.staticFieldBase(field), unsafe.staticFieldOffset(field), newValues)
	}
}