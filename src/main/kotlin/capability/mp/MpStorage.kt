package capability.mp

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class MpStorage : Capability.IStorage<IMp> {
	override fun readNBT(capability: Capability<IMp>?, instance: IMp?, side: EnumFacing?, nbt: NBTBase?) {
		capability?.defaultInstance?.setMp((nbt as NBTTagCompound).getInteger("mp"))
	}

	override fun writeNBT(capability: Capability<IMp>?, instance: IMp?, side: EnumFacing?): NBTBase? {
		val nbt = NBTTagCompound()
		nbt.setInteger("mp", instance?.getMp()!!)
		return nbt
	}
}