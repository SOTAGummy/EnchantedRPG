package proxy

import blocks.TESRPedestal
import blocks.TileEntityPedestal
import net.minecraftforge.fml.client.registry.ClientRegistry

class ClientProxy: CommonProxy(){
	override fun registerRender() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal::class.java, TESRPedestal())
	}
}