import attribute.AttributeUtils
import blocks.*
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryStorage
import capability.accessory.IAccessory
import capability.sp.ISP
import capability.sp.SP
import capability.sp.SPStorage
import creativeTab.*
import enchantment.*
import event.Events
import gui.GuiHandler
import items.*
import items.accessory.*
import items.armor.*
import items.baseItem.ItemAccessory
import items.baseItem.ItemArmor
import items.container.*
import items.fragment.EnhancedWizardFragment
import items.fragment.WizardFragment
import items.skill.*
import items.token.*
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.enchantment.Enchantment
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.potion.Potion
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
import net.minecraftforge.fml.common.gameevent.PlayerEvent
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
import utils.ArmorUtil
import utils.EnumExtension
import utils.Storage
import java.awt.geom.Arc2D

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

		//ArmorType
		val WIZARD = ArmorUtil.addArmorType("wizard", 3000, intArrayOf(4, 4, 4, 4), 15, SoundEvents.BLOCK_CLOTH_PLACE, 0F)
		val ENHANCED_WIZARD = ArmorUtil.addArmorType("enhanced_wizard", 3000, intArrayOf(6, 8, 8, 6), 30, SoundEvents.BLOCK_CLOTH_PLACE, 2F)
		val STRONG = ArmorUtil.addArmorType("strong", 3000, intArrayOf(4, 5, 5, 4), 15, SoundEvents.BLOCK_ANVIL_PLACE, 0F)

		//CreativeTab
		val itemsTab = EnchantedRPGItemsTab
		val skillsTab = EnchantedRPGSkillsTab
		val accessoriesTab = EnchantedRPGAccessoryTab
		val enchantmentsTab = EnchantedRPGEnchantmentTab
		val armorTab = EnchantedRPGArmorTab

		//Item
		val enchantedDust = EnchantedDust
		val primalSeed = PrimalSeed

		//Block
		val pedestal = Pedestal
		val skillWorkbench = SkillWorkbench

		//Fragment
		val wizardFragment = WizardFragment
		val enhancedWizardFragment = EnhancedWizardFragment

		//SkillContainer
		val skillBook = SkillBook
		val woodenWand = WoodenWand
		val stoneWand = StoneWand
		val ironWand = IronWand
		val diamondWand = DiamondWand
		val emeraldWand = EmeraldWand
		val obsidianWand = ObsidianWand

		//Skill
		val codeTestMaster = CodeTestMaster
		val toggleModeMaster = ToggleModeMaster
		val fullFillMaster = FullFillMaster
		val healCommon = HealCommon
		val healUncommon = HealUncommon
		val healRare = HealRare
		val healEpic = HealEpic
		val healLegend = HealLegend
		val leapCommon = LeapCommon
		val leapUncommon = LeapUncommon
		val leapRare = LeapRare
		val leapEpic = LeapEpic
		val leapLegend = LeapLegend
		val arrowRainCommon = ArrowRainCommon
		val arrowRainUncommon = ArrowRainUncommon
		val arrowRainRare = ArrowRainRare
		val arrowRainEpic = ArrowRainEpic
		val arrowRainLegend = ArrowRainLegend
		val rageCommon = RageCommon
		val rageUncommon = RageUncommon
		val rageRare = RageRare
		val rageEpic = RageEpic
		val rageLegend = RageLegend
		val berserkCommon = BerserkCommon
		val berserkUncommon = BerserkUncommon
		val berserkRare = BerserkRare
		val berserkEpic = BerserkEpic
		val berserkLegend = BerserkLegend
		val blackHoleCommon = BlackHoleCommon
		val blackHoleUncommon = BlackHoleUncommon
		val blackHoleRare = BlackHoleRare
		val blackHoleEpic = BlackHoleEpic
		val blackHoleLegend = BlackHoleLegend
		val cureCommon = CureCommon
		val cureUncommon = CureUncommon
		val cureRare = CureRare
		val cureEpic = CureEpic
		val cureLegend = CureLegend
		val blowCommon = BlowCommon
		val blowUncommon = BlowUncommon
		val blowRare = BlowRare
		val blowEpic = BlowEpic
		val blowLegend = BlowLegend
		val lightningCommon = LightningCommon
		val lightningUncommon = LightningUncommon
		val lightningRare = LightningRare
		val lightningEpic = LightningEpic
		val lightningLegend = LightningLegend
		val explosionCommon = ExplosionCommon
		val explosionUncommon = ExplosionUncommon
		val explosionRare = ExplosionRare
		val explosionEpic = ExplosionEpic
		val explosionLegend = ExplosionLegend
		val shockWaveCommon = ShockWaveCommon
		val shockWaveUncommon = ShockWaveUncommon
		val shockWaveRare = ShockWaveRare
		val shockWaveEpic = ShockWaveEpic
		val shockWaveLegend = ShockWaveLegend
		val vampirismCommon = VampirismCommon
		val vampirismUncommon = VampirismUncommon
		val vampirismRare = VampirismRare
		val vampirismEpic = VampirismEpic
		val vampirismLegend = VampirismLegend
		val enderKnockCommon = EnderKnockCommon
		val enderKnockUncommon = EnderKnockUncommon
		val enderKnockRare = EnderKnockRare
		val enderKnockEpic = EnderKnockEpic
		val enderKnockLegend = EnderKnockLegend
		val highJumpCommon = HighJumpCommon
		val highJumpUncommon = HighJumpUncommon
		val highJumpRare = HighJumpRare
		val highJumpEpic = HighJumpEpic
		val highJumpLegend = HighJumpLegend
		val hpBoostCommon = HPBoostCommon
		val hpBoostUncommon = HPBoostUncommon
		val hpBoostRare = HPBoostRare
		val hpBoostEpic = HPBoostEpic
		val hpBoostLegend = HPBoostLegend
		val infernoCommon = InfernoCommon
		val dragonBreathSpecial = DragonBreathMythic

		//Token
		val commonToken = CommonToken
		val uncommonToken = UncommonToken
		val rareToken = RareToken
		val epicToken = EpicToken
		val legendToken = LegendToken

		//Food
		val lifeFruit = LifeFruit
		val spFruit = SPFruit
		val powerFruit = PowerFruit
		val guardFruit = GuardFruit
		val criticalFruit = CriticalFruit
		val speedFruit = SpeedFruit

		//Armor
		val wizardHelmet = WizardHelmet
		val wizardChestplate = WizardChestplate
		val wizardLeggings = WizardLeggings
		val wizardBoots = WizardBoots
		val enhancedWizardHelmet = EnhancedWizardHelmet
		val enhancedWizardChestplate = EnhancedWizardChestplate
		val enhancedWizardLeggings = EnhancedWizardLeggings
		val enhancedWizardBoots = EnhancedWizardBoots

		//Accessory
		val diamondNecklace = DiamondNecklace
		val diamondAmulet = DiamondAmulet
		val diamondGlove = DiamondGlove
		val diamondRing = DiamondRing
		val wizardNecklace = WizardNecklace
		val wizardAmulet = WizardAmulet
		val wizardGlove = WizardGlove
		val wizardRing = WizardRing
		val enhancedWizardNecklace = EnhancedWizardNecklace
		val enhancedWizardAmulet = EnhancedWizardAmulet
		val enhancedWizardGlove = EnhancedWizardGlove
		val enhancedWizardRing = EnhancedWizardRing

		//Attribute
		val MAX_SP = AttributeUtils.addAttribute("maxSp", 100.0, 0.0, Double.MAX_VALUE)
		val SP_SAVING_RATE = AttributeUtils.addAttribute("spSavingRate", 0.0, -2147483647.0, 100.0)
		val SP_RECOVER_RATE = AttributeUtils.addAttribute("spRecoverRate", 1.0, 1.0, Double.MAX_VALUE)
		val CRITICAL_RATE = AttributeUtils.addAttribute("criticalRate", 0.0, 0.0, Double.MAX_VALUE)
		val CRITICAL_DAMAGE = AttributeUtils.addAttribute("criticalDamage", 0.0, 0.0, Double.MAX_VALUE)
		val EXP = AttributeUtils.addAttribute("exp", 0.0, 0.0, Double.MAX_VALUE)
		val LEVEL = AttributeUtils.addAttribute("level", 1.0, 1.0, 999.0)

		//Sound
		val CRAFT_SOUND = SoundHandler.registerSound("craft_sound")
		val HEAL_SOUND = SoundHandler.registerSound("heal")
		val RAGE_SOUND = SoundHandler.registerSound("rage")
		val INFERNO_SOUND = SoundHandler.registerSound("inferno")

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
		val spBoost = PotionSPBoost()
		val vampirism = PotionVampirism()
		val pursuit = PotionPursuit()
		val criticalRate = PotionCriticalRate()
		val criticalDamage = PotionCriticalDamage()

		val testArea = TestArea
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
		event.registry.register(ItemBlock(skillWorkbench).setRegistryName(ResourceLocation(ID, "skill_workbench")))
		event.registry.register(testArea)
	}

	@SubscribeEvent
	fun registerBlocks(event: RegistryEvent.Register<Block>){
		event.registry.register(pedestal)
		event.registry.register(skillWorkbench)
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
		ModelLoader.setCustomModelResourceLocation(testArea, 0, ModelResourceLocation(ResourceLocation(ID, "test_area"), "inventory"))
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(pedestal), 0, ModelResourceLocation(ResourceLocation(ID, "pedestal"), "inventory"))
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(skillWorkbench), 0, ModelResourceLocation(ResourceLocation(ID, "skill_workbench"), "inventory"))
	}

	@SubscribeEvent
	fun onCraftEvent(event: PlayerEvent.ItemCraftedEvent){
		if (event.crafting.item is ItemArmor) {
			val nbt = if (event.crafting.tagCompound != null) {
				event.crafting.tagCompound
			} else {
				NBTTagCompound()
			}
			nbt!!.setBoolean("Unbreakable", true)
			event.crafting.tagCompound = nbt
		}
	}
}