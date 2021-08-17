package packet

import blocks.TileEntitySkillWorkbench
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.items.ItemStackHandler

class PacketUpdateSkillWorkbench(): IMessage{
	private var pos: BlockPos? = null
	private var inventory = ItemStackHandler(9)

	constructor(te: TileEntitySkillWorkbench): this(){
		val itemHandler = te.inventory
		this.pos = te.pos
		this.inventory = itemHandler
	}

	override fun fromBytes(buf: ByteBuf) {
		this.pos = BlockPos.fromLong(buf.readLong())
		this.inventory.deserializeNBT(ByteBufUtils.readTag(buf))
	}

	override fun toBytes(buf: ByteBuf) {
		this.pos?.toLong()?.let { buf.writeLong(it) }
		ByteBufUtils.writeTag(buf, this.inventory.serializeNBT())
	}

	class Handler: IMessageHandler<PacketUpdateSkillWorkbench, IMessage> {
		override fun onMessage(message: PacketUpdateSkillWorkbench?, ctx: MessageContext?): IMessage? {
			Minecraft.getMinecraft().addScheduledTask(){
				val te = Minecraft.getMinecraft().world.getTileEntity(message?.pos) as TileEntitySkillWorkbench
				te.inventory = message?.inventory!!
				println(te.inventory.getStackInSlot(0))
			}
			return null
		}
	}
}