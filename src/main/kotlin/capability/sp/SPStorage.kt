package capability.sp

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class SPStorage: Capability.IStorage<ISP> {
	override fun readNBT(capability: Capability<ISP>?, instance: ISP?, side: EnumFacing?, nbt: NBTBase?) {
		instance?.setSP((nbt as NBTTagCompound).getInteger("sp"))
	}

	override fun writeNBT(capability: Capability<ISP>?, instance: ISP?, side: EnumFacing?): NBTBase? {
		val nbt = NBTTagCompound()
		nbt.setInteger("sp", instance?.getSP()!!)
		return nbt
	}
}