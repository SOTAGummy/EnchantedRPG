import blocks.BlockPedestal
import blocks.TileEntityPedestal
import creativeTab.EnchantedRPGItemsTab
import items.EnchantedDust
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.common.registry.GameRegistry
import utils.Storage
import net.minecraftforge.common.MinecraftForge

import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.client.event.ModelRegistryEvent

import net.minecraftforge.fml.relauncher.SideOnly

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.registries.IForgeRegistry

@Mod(modid = Core.ID, name = Core.NAME, version = Core.VERSION)
class Core {
	companion object{
		const val ID = "enchanted-rpg"
		const val NAME = "EnchantedRPG"
		const val VERSION = "1.0"

		val itemsTab = EnchantedRPGItemsTab

		val enchanted_dust = EnchantedDust

		val pedestal = BlockPedestal
	}

	@Mod.EventHandler
	fun construct(event: FMLConstructionEvent?) {
		MinecraftForge.EVENT_BUS.register(this)
	}

	@Mod.EventHandler
	fun preInitEvent(event: FMLPreInitializationEvent){


		if (event.side.isClient){
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(pedestal), 0, ModelResourceLocation(ID, "pedestal"))
			GameRegistry.registerTileEntity(TileEntityPedestal::class.java, ResourceLocation(ID, "pedestal"))
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

		event.registry.register(enchanted_dust)
		event.registry.register(ItemBlock(pedestal).setRegistryName(ResourceLocation(ID, "pedestal")))
	}

	@SubscribeEvent
	fun registerBlocks(event: RegistryEvent.Register<Block>){
		event.registry.register(pedestal)
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	fun registerModels(event: ModelRegistryEvent?) {
		ModelLoader.setCustomModelResourceLocation(enchanted_dust, 0, ModelResourceLocation(ResourceLocation(ID, "enchanted_dust"), "inventory"))
	}
}