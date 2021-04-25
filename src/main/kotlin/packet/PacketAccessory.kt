package packet

import capability.accessory.AccessoryProvider
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketAccessory(): IMessage{
	private var playerId: Int = 0
	private var slotId: Byte = 0
	private var stack: ItemStack = ItemStack.EMPTY

	constructor(player: EntityPlayer, slotId: Byte, stack: ItemStack): this(){
		this.playerId = player.entityId
		this.slotId = slotId
		this.stack = stack
	}

	override fun fromBytes(buf: ByteBuf) {
		playerId = buf.readInt()
		slotId = buf.readByte()
		stack = ByteBufUtils.readItemStack(buf)
	}

	override fun toBytes(buf: ByteBuf) {
		buf.writeInt(playerId)
		buf.writeByte(slotId.toInt())
		ByteBufUtils.writeItemStack(buf, stack)
	}

	class Handler: IMessageHandler<PacketAccessory, IMessage>{
		override fun onMessage(message: PacketAccessory?, ctx: MessageContext?): IMessage? {
			val mainThread = Minecraft.getMinecraft()
			mainThread.addScheduledTask(){
				val player = Minecraft.getMinecraft().world.getEntityByID(message?.playerId!!)
				player?.getCapability(AccessoryProvider.ACCESSORY!!, null)?.setItem(message.slotId.toInt(), message.stack)
			}
			return null
		}
	}
}