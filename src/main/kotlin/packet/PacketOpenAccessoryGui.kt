package packet

import gui.GuiHandler
import io.netty.buffer.ByteBuf
import net.minecraft.world.WorldServer
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketOpenAccessoryGui: IMessage, IMessageHandler<PacketOpenAccessoryGui, IMessage>{
	override fun fromBytes(buf: ByteBuf?) {}

	override fun toBytes(buf: ByteBuf?) {}

	override fun onMessage(message: PacketOpenAccessoryGui?, ctx: MessageContext?): IMessage? {
		val mainThread = ctx?.serverHandler?.player?.world as WorldServer
		mainThread.addScheduledTask {
			ctx.serverHandler.player.openContainer.onContainerClosed(ctx.serverHandler.player);
			ctx.serverHandler.player.openGui(Core.instance, GuiHandler.AccessoryGui, ctx.serverHandler.player.world, 0, 0, 0)
		}
		return null
	}
}