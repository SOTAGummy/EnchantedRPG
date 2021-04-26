package capability.accessory

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class AccessoryStorage: Capability.IStorage<IAccessory> {
	override fun readNBT(capability: Capability<IAccessory>?, instance: IAccessory?, side: EnumFacing?, nbt: NBTBase) {

	}

	override fun writeNBT(capability: Capability<IAccessory>?, instance: IAccessory?, side: EnumFacing?): NBTTagCompound? {
		return null
	}
}