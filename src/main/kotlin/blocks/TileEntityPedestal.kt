package blocks

import net.minecraft.tileentity.TileEntity
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraft.util.EnumFacing
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.AxisAlignedBB
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.ItemStackHandler
import packet.PacketHandler
import packet.PacketRequestUpdatePedestal
import packet.PacketUpdatePedestal
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint


class TileEntityPedestal: TileEntity(){
	val inventory = object: ItemStackHandler(1){
		override fun onContentsChanged(slot: Int) {
			if (!world.isRemote){
				lastChangeTime = world.totalWorldTime
				PacketHandler.network.sendToAllAround(PacketUpdatePedestal(this@TileEntityPedestal), TargetPoint(world.provider.dimension, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 64.0))
			}
		}
	}
	private var lastChangeTime: Long = 0

	override fun writeToNBT(compound: NBTTagCompound): NBTTagCompound? {
		compound.setTag("inventory", inventory.serializeNBT())
		compound.setLong("lastChangeTime", lastChangeTime)
		return super.writeToNBT(compound)
	}

	override fun readFromNBT(compound: NBTTagCompound) {
		inventory.deserializeNBT(compound.getCompoundTag("inventory"))
		lastChangeTime = compound.getLong("lastChangeTime")
		super.readFromNBT(compound)
	}

	override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
		return capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
	}

	override fun <T> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
		return if (capability === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) inventory as T else super.getCapability(capability, facing)
	}

	override fun onLoad() {
		if (world.isRemote){
			PacketHandler.network.sendToServer(PacketRequestUpdatePedestal(this))
		}
	}

	override fun getRenderBoundingBox(): AxisAlignedBB {
		return AxisAlignedBB(getPos(), getPos().add(1, 2, 1))
	}

	fun getLastChangeTime(): Long {
		return lastChangeTime
	}

	fun setLastChangeTime(amount: Long){
		lastChangeTime = amount
	}
}