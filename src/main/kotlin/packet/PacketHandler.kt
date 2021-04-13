package packet

import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.relauncher.Side

class PacketHandler {
	companion object {
		val network: SimpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Core.ID)
	}

	init {
		network.registerMessage(PacketUpdatePedestal.Handler(), PacketUpdatePedestal::class.java, 0, Side.CLIENT)
		network.registerMessage(PacketRequestUpdatePedestal.Handler(), PacketRequestUpdatePedestal::class.java, 1, Side.SERVER)
	}
}