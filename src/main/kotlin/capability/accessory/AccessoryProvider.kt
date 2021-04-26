package capability.accessory

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class AccessoryProvider(container: AccessoryItemContainer): ICapabilitySerializable<NBTTagCompound?> {
	companion object {
		@CapabilityInject(IAccessory::class)
		val ACCESSORY: Capability<IAccessory?>? = null
	}

	private val instance = ACCESSORY!!.defaultInstance
	private var container: AccessoryItemContainer? = null

	init {
		this.container = container
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability == ACCESSORY
	}

	override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
		return if (capability === ACCESSORY) this.container as T else null
	}

	override fun serializeNBT(): NBTTagCompound {
		repeat(8){
			this.container?.setStackInSlot(it, ACCESSORY?.defaultInstance?.getStackInSlot(it)!!)
		}
		return this.container?.serializeNBT()!!
	}

	override fun deserializeNBT(nbt: NBTTagCompound?) {
		this.container?.deserializeNBT(nbt)
	}
}