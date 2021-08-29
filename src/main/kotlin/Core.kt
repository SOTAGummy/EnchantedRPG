import attribute.AttributeUtils
import blocks.*
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryStorage
import capability.accessory.IAccessory
import capability.sp.ISP
import capability.sp.SP
import capability.sp.SPStorage
import creativeTab.EnchantedRPGAccessoryTab
import creativeTab.EnchantedRPGEnchantmentTab
import creativeTab.EnchantedRPGItemsTab
import creativeTab.EnchantedRPGSkillsTab
import enchantment.*
import event.Events
import gui.GuiHandler
import items.EnchantedDust
import items.accessory.DiamondAmulet
import items.accessory.DiamondGlove
import items.accessory.DiamondNecklace
import items.accessory.DiamondRing
import items.baseItem.ItemAccessory
import items.container.*
import items.skill.*
import items.token.*
import net.minecraft.block.Block
import net.minecraft.block.BlockPlanks
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.enchantment.Enchantment
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.potion.Potion
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.EnumHelperClient
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
import packet.PacketHandler
import potion.*
import proxy.CommonProxy
import recipe.Recipes
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
		val pedestal = Pedestal
		val skill_workbench = SkillWorkbench

		//SkillContainer
		val skill_book = SkillBook
		val wooden_wand = WoodenWand
		val stone_wand = StoneWand
		val iron_wand = IronWand
		val diamond_wand = DiamondWand
		val emerald_wand = EmeraldWand
		val obsidian_wand = ObsidianWand

		//Skill
		val code_test_master = CodeTestMaster
		val toggle_mode_master = ToggleModeMaster
		val full_fill_master = FullFillMaster
		val heal_common = HealCommon
		val heal_uncommon = HealUncommon
		val heal_rare = HealRare
		val heal_epic = HealEpic
		val heal_legend = HealLegend
		val heal_mythic = HealMythic
		val leap_common = LeapCommon
		val leap_uncommon = LeapUncommon
		val leap_rare = LeapRare
		val leap_epic = LeapEpic
		val leap_legend = LeapLegend
		val leap_mythic = LeapMythic
		val arrow_rain_common = ArrowRainCommon
		val arrow_rain_uncommon = ArrowRainUncommon
		val arrow_rain_rare = ArrowRainRare
		val arrow_rain_epic = ArrowRainEpic
		val arrow_rain_legend = ArrowRainLegend
		val arrow_rain_mythic = ArrowRainMythic
		val rage_common = RageCommon
		val rage_uncommon = RageUncommon
		val rage_rare = RageRare
		val rage_epic = RageEpic
		val rage_legend = RageLegend
		val rage_mythic = RageMythic
		val berserk_common = BerserkCommon
		val berserk_uncommon = BerserkUncommon
		val berserk_rare = BerserkRare
		val berserk_epic = BerserkEpic
		val berserk_legend = BerserkLegend
		val berserk_mythic = BerserkMythic
		val black_hole_common = BlackHoleCommon
		val black_hole_uncommon = BlackHoleUncommon
		val black_hole_rare = BlackHoleRare
		val black_hole_epic = BlackHoleEpic
		val black_hole_legend = BlackHoleLegend
		val black_hole_mythic = BlackHoleMythic
		val cure_common = CureCommon
		val cure_uncommon = CureUncommon
		val cure_rare = CureRare
		val cure_epic = CureEpic
		val cure_legend = CureLegend
		val cure_mythic = CureMythic
		val blow_common = BlowCommon
		val blow_uncommon = BlowUncommon
		val blow_rare = BlowRare
		val blow_epic = BlowEpic
		val blow_legend = BlowLegend
		val blow_mythic = BlowMythic
		val lightning_common = LightningCommon
		val lightning_uncommon = LightningUncommon
		val lightning_rare = LightningRare
		val lightning_epic = LightningEpic
		val lightning_legend = LightningLegend
		val lightning_mythic = LightningMythic
		val explosion_common = ExplosionCommon
		val explosion_uncommon = ExplosionUncommon
		val explosion_rare = ExplosionRare
		val explosion_epic = ExplosionEpic
		val explosion_legend = ExplosionLegend
		val explosion_mythic = ExplosionMythic
		val fire_ball_common = FireBallCommon
		val fire_ball_uncommon = FireBallUncommon
		val fire_ball_rare = FireBallRare
		val fire_ball_epic = FireBallEpic
		val fire_ball_legend = FireBallLegend
		val fire_ball_mythic = FireBallMythic
		val dragon_breath_special = DragonBreathSpecial

		//Token
		val common_token = CommonToken
		val uncommon_token = UncommonToken
		val rare_token = RareToken
		val epic_token = EpicToken
		val legend_token = LegendToken
		val mythic_token = MythicToken

		//Accessory
		val diamond_necklace = DiamondNecklace
		val diamond_amulet = DiamondAmulet
		val diamond_glove = DiamondGlove
		val diamond_ring = DiamondRing

		//Attribute
		val MAX_SP = AttributeUtils.addAttribute("maxSp", 100.0, 0.0, Double.MAX_VALUE)
		val SP_SAVING_RATE = AttributeUtils.addAttribute("spSavingRate", 0.0, -2147483647.0, 100.0)
		val SP_RECOVER_RATE = AttributeUtils.addAttribute("spRecoverRate", 1.0, 1.0, Double.MAX_VALUE)
		val CRITICAL_RATE = AttributeUtils.addAttribute("criticalRate", 0.0, 0.0, 100.0)
		val CRITICAL_DAMAGE = AttributeUtils.addAttribute("criticalDamage", 0.0, 0.0, Double.MAX_VALUE)

		//Sound
		val CRAFT_SOUND = SoundHandler.registerSound("craft_sound")
		val HEAL_SOUND = SoundHandler.registerSound("heal")
		val RAGE_SOUND = SoundHandler.registerSound("rage")

		//Enchantment
		val toughness = EnchantmentToughness
		val vitality = EnchantmentVitality
		val blessing = EnchantmentBlessing
		val hardness = EnchantmentHardness
		val insight = EnchantmentInsight
		val rapid = EnchantmentRapid
		val force = EnchantmentForce
		val wise = EnchantmentWise

		//PotionEffect
		val burning = PotionBurning()
		val electric_shock = PotionElectricShock()
		val flooded = PotionFlooded()
		val frozen = PotionFrozen()
		val muddy = PotionMuddy()
		val paralysis = PotionParalysis()
		val no_gravity = PotionNoGravity()
		val sp_boost = PotionSPBoost()
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
			GameRegistry.registerTileEntity(TileEntitySkillWorkbench::class.java, ResourceLocation(ID, "skill_workbench"))
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPedestal::class.java, TESRPedestal())
		}

		NetworkRegistry.INSTANCE.registerGuiHandler(this, GuiHandler())
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
		Recipes
	}

	@SubscribeEvent
	fun registerItems(event: RegistryEvent.Register<Item>){
		repeat(Storage.Items.size){
			event.registry.register(Storage.Items[it])
		}
		event.registry.register(ItemBlock(pedestal).setRegistryName(ResourceLocation(ID, "pedestal")))
		event.registry.register(ItemBlock(skill_workbench).setRegistryName(ResourceLocation(ID, "skill_workbench")))
	}

	@SubscribeEvent
	fun registerBlocks(event: RegistryEvent.Register<Block>){
		event.registry.register(pedestal)
		event.registry.register(skill_workbench)
	}

	@SubscribeEvent
	fun registerEnchantments(event: RegistryEvent.Register<Enchantment>) {
		repeat(Storage.Enchantments.size) {
			event.registry.register(Storage.Enchantments[it])
		}
	}

	@SubscribeEvent
	fun registerPotionEffects(event: RegistryEvent.Register<Potion>){
		repeat(Storage.Potions.size){
			event.registry.register(Storage.Potions[it])
		}
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	fun registerModels(event: ModelRegistryEvent?) {
		for (model in Storage.Items) {
			ModelLoader.setCustomModelResourceLocation(model, 0, ModelResourceLocation(ResourceLocation(ID, model.unlocalizedName.split(".")[1]), "inventory"))
		}
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(pedestal), 0, ModelResourceLocation(ResourceLocation(ID, "pedestal"), "inventory"))
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(skill_workbench), 0, ModelResourceLocation(ResourceLocation(ID, "skill_workbench"), "inventory"))
	}
}