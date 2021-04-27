package capability.sp

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class SPProvider: ICapabilitySerializable<NBTBase?> {
	companion object {
		@CapabilityInject(ISP::class)
		val SP: Capability<ISP?>? = null
	}

	private val instance = SP!!.defaultInstance
	private var sp = 0

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability === SP
	}

	override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
		return if (capability === SP) SP.cast(instance) else null
	}

	override fun serializeNBT(): NBTTagCompound {
		val nbt = NBTTagCompound()
		nbt.setInteger("SP", this.sp)
		return nbt
	}

	override fun deserializeNBT(nbt: NBTBase?) {
		this.sp = (nbt as NBTTagCompound).getInteger("SP")
	}
}