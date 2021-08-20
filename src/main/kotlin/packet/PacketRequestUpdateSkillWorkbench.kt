package packet

import blocks.TileEntitySkillWorkbench
import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.FMLCommonHandler
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class PacketRequestUpdateSkillWorkbench(): IMessage {
	private var pos: BlockPos? = null
	private var dimensionId = 0

	constructor(pos: BlockPos, dimensionId: Int): this(){
		this.pos = pos
		this.dimensionId = dimensionId
	}

	constructor(te: TileEntitySkillWorkbench): this(){
		this.pos = te.pos
		this.dimensionId = te.world.provider.dimension
	}

	override fun fromBytes(buf: ByteBuf) {
		pos = BlockPos.fromLong(buf.readLong());
		dimensionId = buf.readInt();
	}

	override fun toBytes(buf: ByteBuf) {
		pos?.toLong()?.let { buf.writeLong(it) };
		buf.writeInt(dimensionId);
	}

	class Handler: IMessageHandler<PacketRequestUpdateSkillWorkbench, IMessage> {
		override fun onMessage(message: PacketRequestUpdateSkillWorkbench?, ctx: MessageContext?): IMessage? {
			val world = FMLCommonHandler.instance().minecraftServerInstance.getWorld(message?.dimensionId!!)
			val te = world.getTileEntity(message.pos) as TileEntitySkillWorkbench?
			return if (te != null) PacketUpdateSkillWorkbench(te) else null
		}
	}
}