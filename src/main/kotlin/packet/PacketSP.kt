package packet

import capability.sp.SPProvider
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketSP(): IMessage{
	private var sp = 0
	private var playerId = 0

	constructor(sp: Int, player: EntityPlayer): this(){
		this.sp = sp
		this.playerId = player.entityId
	}

	override fun fromBytes(buf: ByteBuf) {
		this.sp = buf.readInt()
		this.playerId = buf.readInt()
	}

	override fun toBytes(buf: ByteBuf) {
		buf.writeInt(sp)
		buf.writeInt(playerId)
	}

	class Handler: IMessageHandler<PacketSP, IMessage>{
		override fun onMessage(message: PacketSP?, ctx: MessageContext?): IMessage? {
			val mainThread = Minecraft.getMinecraft()
			mainThread.addScheduledTask(){
				val player = mainThread.world.getEntityByID(message?.playerId!!)
				player?.getCapability(SPProvider.SP!!, null)?.setSP(message.sp)
			}

			return null
		}
	}
}