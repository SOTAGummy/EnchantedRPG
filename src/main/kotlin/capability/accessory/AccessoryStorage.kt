package capability.accessory

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability

class AccessoryStorage : Capability.IStorage<IAccessory> {
	override fun readNBT(capability: Capability<IAccessory>?, instance: IAccessory?, side: EnumFacing?, nbt: NBTBase?) {
		val tag = nbt as NBTTagCompound
		val array = arrayOf("necklace", "amulet", "glove", "gem")
		repeat(4) { i ->
			(tag.getTag(array[i]) as NBTTagCompound?)?.let { it -> capability?.defaultInstance?.setItem(i, ItemStack(it)) }
		}
	}

	override fun writeNBT(capability: Capability<IAccessory>?, instance: IAccessory?, side: EnumFacing?): NBTBase {
		val nbt = NBTTagCompound()
		val array = arrayOf("necklace", "amulet", "glove", "gem")
		repeat(4) {
			if (capability?.defaultInstance?.getItem(it) != ItemStack.EMPTY)
				nbt.setTag(array[it], capability?.defaultInstance?.getItem(it)?.writeToNBT(NBTTagCompound()))
		}
		return nbt
	}
}