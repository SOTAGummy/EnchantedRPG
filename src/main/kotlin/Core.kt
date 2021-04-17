import attribute.AttributeUtils
import blocks.BlockPedestal
import blocks.TESRPedestal
import blocks.TileEntityPedestal
import capability.accessory.Accessory
import capability.accessory.AccessoryStorage
import capability.accessory.IAccessory
import capability.mp.IMp
import capability.mp.Mp
import capability.mp.MpStorage
import creativeTab.EnchantedRPGItemsTab
import event.Events
import gui.accessory.GuiAccessoryHandler
import items.EnchantedDust
import items.accessory.TestAmulet
import items.accessory.TestGlove
import items.accessory.TestNecklace
import items.accessory.TestRing
import items.baseItem.ItemAccessory
import items.container.SkillBook
import items.skill.CodeTest
import items.skill.ToggleMode
import utils.EnumExtension
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.particle.ParticleManager
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLConstructionEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketHandler
import particle.TestParticle
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

		@Mod.Instance
		lateinit var instance: Core

		//EquipmentSlot
		val ACCESSORY = EnumExtension.addSlotType("ACCESSORY", 2)
		val NECKLACE = EnumExtension.addEquipmentSlot("NECKLACE", 6, ACCESSORY, 0, 1, "necklace")
		val AMULET = EnumExtension.addEquipmentSlot("AMULET", 7, ACCESSORY, 1, 2, "amulet")
		val GLOVE = EnumExtension.addEquipmentSlot("GLOVE", 8, ACCESSORY, 2, 3, "glove")
		val RING = EnumExtension.addEquipmentSlot("RING", 9, ACCESSORY, 3, 4, "ring")
		val accessoryType = EnumHelper.addEnchantmentType("ACCESSORY") { item: Item? -> item is ItemAccessory }!!

		val TEST = EnumExtension.addParticleType("test", EnumParticleTypes.values().size, 49, false)

		//CreativeTab
		val itemsTab = EnchantedRPGItemsTab

		//Item
		val enchanted_dust = EnchantedDust

		//Block
		val pedestal = BlockPedestal

		//SkillContainer
		val skill_book = SkillBook

		//Skill
		val toggle_mode = ToggleMode
		val code_test = CodeTest

		//Accessory
		val test_necklace = TestNecklace
		val test_amulet = TestAmulet
		val test_glove = TestGlove
		val test_ring = TestRing

		//Attribute
		val MAXMP = AttributeUtils.addAttribute("maxmp", 100.0, 0.0, Double.MAX_VALUE)
		val EXP = AttributeUtils.addAttribute("exp", 0.0, 0.0, Double.MAX_VALUE)
		val LEVEL = AttributeUtils.addAttribute("level", 1.0, 1.0, Double.MAX_VALUE)
		val SAVINGRATE = AttributeUtils.addAttribute("savingrate", 0.0, 0.0, 100.0)
		val MPRECOVERRATE = AttributeUtils.addAttribute("mprecoverrate", 2.0, 2.0, Double.MAX_VALUE)

		//Texture
		lateinit var test_texture: TextureAtlasSprite
	}

	@Mod.EventHandler
	fun construct(event: FMLConstructionEvent?) {
		MinecraftForge.EVENT_BUS.register(this)
		MinecraftForge.EVENT_BUS.register(Events())
	}

	@Mod.EventHandler
	fun preInitEvent(event: FMLPreInitializationEvent){
		PacketHandler()

		if (event.side.isClient){
			GameRegistry.registerTileEntity(TileEntityPedestal::class.java, ResourceLocation(ID, "pedestal"))
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal::class.java, TESRPedestal())
			ParticleManager(Minecraft.getMinecraft().world, Minecraft.getMinecraft().renderEngine).registerParticle(Core.TEST.particleID, TestParticle.Factory())
		}
		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiAccessoryHandler())
		CapabilityManager.INSTANCE.register(IMp::class.java, MpStorage()) { Mp() }
		CapabilityManager.INSTANCE.register(IAccessory::class.java, AccessoryStorage()) { Accessory() }
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