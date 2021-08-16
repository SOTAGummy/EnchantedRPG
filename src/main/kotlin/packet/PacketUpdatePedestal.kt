package packet

import blocks.TileEntityPedestal
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

class PacketUpdatePedestal(): IMessage{
	private var pos: BlockPos? = null
	private var inventory = ItemStackHandler(8)
	private var lastChangeTime: Long = 0

	constructor(pos: BlockPos, inventory: ItemStackHandler, lastChangeTime: Long): this(){
		this.pos = pos
		this.inventory = inventory
		this.lastChangeTime = lastChangeTime
	}

	constructor(te: TileEntityPedestal): this(){
		val itemHandler = te.inventory
		this.pos = te.pos
		this.inventory = itemHandler
		this.lastChangeTime = te.getLastChangeTime()
	}

	override fun fromBytes(buf: ByteBuf) {
		this.pos = BlockPos.fromLong(buf.readLong())
		this.lastChangeTime = buf.readLong()
		repeat(8){
			this.inventory.setStackInSlot(it, ByteBufUtils.readItemStack(buf))
		}
	}

	override fun toBytes(buf: ByteBuf) {
		this.pos?.toLong()?.let { buf.writeLong(it) }
		buf.writeLong(lastChangeTime)
		repeat(8){
			ByteBufUtils.writeItemStack(buf, inventory.getStackInSlot(it))
		}
	}

	class Handler: IMessageHandler<PacketUpdatePedestal, IMessage>{
		override fun onMessage(message: PacketUpdatePedestal?, ctx: MessageContext?): IMessage? {
			Minecraft.getMinecraft().addScheduledTask(){
				val te = Minecraft.getMinecraft().world.getTileEntity(message?.pos) as TileEntityPedestal
				te.inventory = message?.inventory!!
				te.setLastChangeTime(message.lastChangeTime)
			}
			return null
		}
	}
}