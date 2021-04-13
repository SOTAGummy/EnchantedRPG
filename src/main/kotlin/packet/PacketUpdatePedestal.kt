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

class PacketUpdatePedestal(): IMessage{
	private var pos: BlockPos? = null
	private var stack: ItemStack = ItemStack.EMPTY
	private var lastChangeTime: Long = 0

	constructor(pos: BlockPos, stack: ItemStack, lastChangeTime: Long): this(){
		this.pos = pos
		this.stack = stack
		this.lastChangeTime = lastChangeTime
	}

	constructor(te: TileEntityPedestal): this(){
		val itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
		this.pos = te.pos
		this.stack = itemHandler?.getStackInSlot(0)!!
		this.lastChangeTime = te.getLastChangeTime()
	}

	override fun fromBytes(buf: ByteBuf) {
		this.pos = BlockPos.fromLong(buf.readLong())
		this.stack = ByteBufUtils.readItemStack(buf)
		this.lastChangeTime = buf.readLong()
	}

	override fun toBytes(buf: ByteBuf) {
		this.pos?.toLong()?.let { buf.writeLong(it) }
		ByteBufUtils.writeItemStack(buf, stack)
		buf.writeLong(lastChangeTime)
	}

	class Handler: IMessageHandler<PacketUpdatePedestal, IMessage>{
		override fun onMessage(message: PacketUpdatePedestal?, ctx: MessageContext?): IMessage? {
			Minecraft.getMinecraft().addScheduledTask(){
				val te = Minecraft.getMinecraft().world.getTileEntity(message?.pos) as TileEntityPedestal
				te.inventory.setStackInSlot(0, message?.stack!!)
				te.setLastChangeTime(message.lastChangeTime)
			}
			return null
		}
	}
}