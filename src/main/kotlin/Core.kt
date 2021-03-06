import attribute.AttributeUtils
import blocks.BlockPedestal
import blocks.TESRPedestal
import blocks.TileEntityPedestal
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryStorage
import capability.accessory.IAccessory
import capability.sp.ISP
import capability.sp.SP
import capability.sp.SPStorage
import com.google.common.collect.Multimap
import creativeTab.EnchantedRPGAccessoryTab
import creativeTab.EnchantedRPGEnchantmentTab
import creativeTab.EnchantedRPGItemsTab
import creativeTab.EnchantedRPGSkillsTab
import event.Events
import gui.accessory.GuiAccessoryHandler
import items.EnchantedDust
import items.accessory.*
import items.baseItem.ItemAccessory
import items.container.SkillBook
import items.container.WoodenWand
import items.skill.*
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.item.ItemStack
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
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import net.minecraftforge.items.IItemHandler
import packet.PacketHandler
import proxy.CommonProxy
import sound.SoundHandler
import utils.EnumExtension
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

		//CreativeTab
		val itemsTab = EnchantedRPGItemsTab
		val skillsTab = EnchantedRPGSkillsTab
		val accessoriesTab = EnchantedRPGAccessoryTab
		val enchantmentsTab = EnchantedRPGEnchantmentTab

		//Item
		val enchanted_dust = EnchantedDust

		//Block
		val pedestal = BlockPedestal

		//SkillContainer
		val skill_book = SkillBook
		val skill_wand = WoodenWand

		//Skill
		val code_test = CodeTest
		val toggle_mode = ToggleMode
		val full_fill = FullFill
		val heal = Heal
		val heal_plus = HealPlus
		val heal_plus_plus = HealPlusPlus
		val leap = Leap
		val leap_plus = LeapPlus
		val leap_plus_plus = LeapPlusPlus
		val arrow_rain = ArrowRain
		val arrow_rain_plus = ArrowRainPlus
		val arrow_rain_plus_plus = ArrowRainPlusPlus

		//Accessory
		val diamond_necklace = DiamondNecklace
		val diamond_amulet = DiamondAmulet
		val diamond_glove = DiamondGlove
		val diamond_ring = DiamondRing

		//Attribute
		val MAX_SP = AttributeUtils.addAttribute("maxSp", 100.0, 0.0, Double.MAX_VALUE)
		val SP_SAVING_RATE = AttributeUtils.addAttribute("spSavingRate", 0.0, 0.0, 100.0)
		val SP_RECOVER_RATE = AttributeUtils.addAttribute("spRecoverRate", 2.0, 2.0, Double.MAX_VALUE)
		val CRITICAL_RATE = AttributeUtils.addAttribute("criticalRate", 0.0, 0.0, 100.0)
		val CRITICAL_DAMAGE = AttributeUtils.addAttribute("criticalDamage", 0.0, 0.0, Double.MAX_VALUE)

		//Sound
		val CRAFT_SOUND = SoundHandler.registerSound("craft_sound")
		val HEAL_SOUND = SoundHandler.registerSound("heal")
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
		}
		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiAccessoryHandler())
		CapabilityManager.INSTANCE.register(ISP::class.java, SPStorage()) { SP() }
		CapabilityManager.INSTANCE.register(IAccessory::class.java, AccessoryStorage()) { AccessoryItemContainer() }
	}

	@Mod.EventHandler
	fun initEvent(event: FMLInitializationEvent){
		repeat(Storage.Sounds.size){
			ForgeRegistries.SOUND_EVENTS.register(Storage.Sounds[it])
		}
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