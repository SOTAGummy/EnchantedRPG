import blocks.BlockPedestal
import com.sun.applet2.preloader.event.InitEvent
import creativeTab.EnchantedRPGItemsTab
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import utils.Storage

@Mod(modid = Core.ID, name = Core.NAME, version = Core.VERSION)
class Core {
	companion object{
		const val ID = "enchanted-rpg"
		const val NAME = "EnchantedRPG"
		const val VERSION = "1.0"

		val itemsTab = EnchantedRPGItemsTab

		val pedestal = BlockPedestal
	}

	@Mod.EventHandler
	fun preInitEvent(event: FMLPreInitializationEvent){

	}

	@Mod.EventHandler
	fun initEvent(event: FMLInitializationEvent){

	}

	@Mod.EventHandler
	fun postInitEvent(event: FMLPostInitializationEvent){

	}

	//@Mod.EventHandler
	fun registerItems(event: RegistryEvent.Register<Item>){
		repeat(Storage.Items.size){
			event.registry.register(Storage.Items[it])
		}
	}

	@Mod.EventHandler
	fun registerBlocks(event: RegistryEvent.Register<Block>){
		event.registry.register(pedestal)
	}
}