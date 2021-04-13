import blocks.BlockPedestal
import blocks.TESRPedestal
import blocks.TileEntityPedestal
import creativeTab.EnchantedRPGItemsTab
import items.EnchantedDust
import items.container.TestContainer
import items.skill.TestSkill
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketHandler
import proxy.CommonProxy
import utils.Storage

@Mod(modid = Core.ID, name = Core.NAME, version = Core.VERSION)
class Core {
	companion object{
		const val ID = "enchanted-rpg"
		const val NAME = "EnchantedRPG"
		const val VERSION = "1.0"

		lateinit var wrapper: SimpleNetworkWrapper

		@SidedProxy(clientSide = "proxy.ClientProxy", serverSide = "proxy.ServerProxy")
		lateinit var proxy: CommonProxy

		val itemsTab = EnchantedRPGItemsTab

		val enchanted_dust = EnchantedDust

		val pedestal = BlockPedestal

		val test = TestContainer
		val test_skill = TestSkill
	}

	@Mod.EventHandler
	fun construct(event: FMLConstructionEvent?) {
		MinecraftForge.EVENT_BUS.register(this)
	}

	@Mod.EventHandler
	fun preInitEvent(event: FMLPreInitializationEvent){
		PacketHandler()

		if (event.side.isClient){
			GameRegistry.registerTileEntity(TileEntityPedestal::class.java, ResourceLocation(ID, "pedestal"))
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal::class.java, TESRPedestal())
		}
	}

	@Mod.EventHandler
	fun initEvent(event: FMLInitializationEvent){

	}

	@Mod.EventHandler
	fun postInitEvent(event: FMLPostInitializationEvent){

	}

	@SubscribeEvent
	fun registerItems(event: RegistryEvent.Register<Item>){
		repeat(Storage.Items.size){
			event.registry.register(Storage.Items[it])
		}
		event.registry.register(ItemBlock(pedestal).setRegistryName(ResourceLocation(ID, "pedestal")))
	}

	@SubscribeEvent
	fun registerBlocks(event: RegistryEvent.Register<Block>){
		event.registry.register(pedestal)
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	fun registerModels(event: ModelRegistryEvent?) {
		for (model in Storage.Items) {
			ModelLoader.setCustomModelResourceLocation(model, 0, ModelResourceLocation(ResourceLocation(ID, model.unlocalizedName.split(".")[1]), "inventory"))
		}
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(pedestal), 0, ModelResourceLocation(ResourceLocation(ID, "pedestal"), "inventory"))
	}
}