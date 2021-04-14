package capability.accessory

import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class AccessoryProvider : ICapabilitySerializable<NBTBase?> {
	companion object {
		@CapabilityInject(IAccessory::class)
		val ACCESSORY: Capability<IAccessory?>? = null
	}

	private val instance = ACCESSORY!!.defaultInstance

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability == ACCESSORY
	}

	override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
		return if (capability === ACCESSORY) ACCESSORY.cast(instance) else null
	}

	override fun serializeNBT(): NBTBase? {
		return ACCESSORY!!.storage.writeNBT(ACCESSORY, instance, null)
	}

	override fun deserializeNBT(nbt: NBTBase?) {
		ACCESSORY!!.storage.readNBT(ACCESSORY, instance, null, nbt)
	}
}