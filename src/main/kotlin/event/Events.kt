package event

import Core
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryProvider
import capability.sp.SPProvider
import extension.getExp
import extension.getLevel
import extension.renderDamage
import gui.accessory.button.AccessoryButton
import gui.mp.SPIndicator
import gui.skillList.SkillListIndicator
import gui.status.RenderStatus
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.monster.EntityWitch
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.RenderLivingEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketAccessory
import packet.PacketHandler
import packet.PacketSP
import particle.ParticleDamage
import potion.CustomPotion
import source.CriticalDamageSource
import utils.Storage
import java.util.*
import kotlin.random.Random


class Events {
	private val accessorySlots = arrayOf(Core.NECKLACE, Core.AMULET, Core.GLOVE, Core.RING)

	@SubscribeEvent
	fun attachAttributeEvent(event: EntityEvent.EntityConstructing){
		if (event.entity is EntityPlayer) {
			val player = event.entity as EntityPlayer
			repeat(Storage.Attributes.size) {
				player.attributeMap.registerAttribute(Storage.Attributes[it])
			}
		}
	}

	@SubscribeEvent
	fun attachCapabilityEvent(event: AttachCapabilitiesEvent<Entity>){
		if (event.`object` is EntityPlayer) {
			event.addCapability(ResourceLocation(Core.ID, "sp"), SPProvider())
			event.addCapability(ResourceLocation(Core.ID, "accessory"), AccessoryProvider(AccessoryItemContainer()))
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	fun onRenderGui(event: RenderGameOverlayEvent.Post) {
		if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE && !Minecraft.getMinecraft().player.isCreative) {
			val mc = Minecraft.getMinecraft()
			var correction = 0
			if (event.type == RenderGameOverlayEvent.ElementType.AIR) correction = 8
			SPIndicator(mc, correction)
			SkillListIndicator(mc)
		}
		if (event.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
			RenderStatus(Minecraft.getMinecraft())
		}
	}

	@SubscribeEvent
	fun guiPostInit(event: GuiScreenEvent.InitGuiEvent.Post) {
		if (event.gui is GuiInventory)
			event.buttonList.add(AccessoryButton(70, 10, 10, 12, 14, ""))
	}

	private var count = 0

	@SubscribeEvent
	fun onTickEvent(event: TickEvent.PlayerTickEvent){
		if (event.phase == TickEvent.Phase.END && event.player is EntityPlayerMP){
			val player = event.player as EntityPlayerMP
			repeat(4){
				PacketHandler.network.sendTo(player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(it)?.let { it1 -> PacketAccessory(player, it.toByte(), it1) }, player as EntityPlayerMP?)
			}
		}

		if (event.side == Side.SERVER){
			count++

			if (count == 40){
				count = 0
				val value = event.player.getEntityAttribute(Core.SP_RECOVER_RATE).attributeValue.toInt()
				event.player.getCapability(SPProvider.SP!!, null)?.addSP(event.player, value)
				PacketHandler.network.sendTo(PacketSP(event.player.getCapability(SPProvider.SP, null)?.getSP()!!, event.player), event.player as EntityPlayerMP?)
			}

			repeat(4){
				val cap = event.player.getCapability(AccessoryProvider.ACCESSORY!!, null)!!
				val current = cap.getStackInSlot(it)
				val old = cap.getStackInSlot(it + 4)
				if (!ItemStack.areItemStacksEqual(old, current)){
					if (!current.isEmpty){
						event.player.attributeMap.applyAttributeModifiers(current.getAttributeModifiers(accessorySlots[it]))
					}
					if (!old.isEmpty){
						event.player.attributeMap.removeAttributeModifiers(old.getAttributeModifiers(accessorySlots[it]))
					}
				}
				cap.setStackInSlot(it + 4, cap.getStackInSlot(it).copy())
			}

			if (event.player.health > event.player.maxHealth){
				event.player.health = event.player.maxHealth
			}
		}
	}

	@SubscribeEvent
	fun onCloneEvent(event: PlayerEvent.Clone){
		val player = event.entityPlayer
		val oldPlayer = event.original
		val currentItemHandler = player.getCapability(AccessoryProvider.ACCESSORY!!, null)
		val oldItemHandler = oldPlayer.getCapability(AccessoryProvider.ACCESSORY, null)
		if (!event.isWasDeath){
			repeat(8){
				currentItemHandler?.setStackInSlot(it, oldItemHandler?.getStackInSlot(it)!!)
			}
		} else {
			repeat(Storage.Attributes.size){
				player.getEntityAttribute(Storage.Attributes[it]).baseValue = oldPlayer.getEntityAttribute(Storage.Attributes[it]).baseValue
			}
			player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = oldPlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue
			player.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).baseValue = oldPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).baseValue
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).baseValue = oldPlayer.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).baseValue
			player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = oldPlayer.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue
		}
	}

	@SubscribeEvent
	fun onDeathEvent(event: LivingDeathEvent){
		if (event.entityLiving is EntityPlayer){
			val player = event.entityLiving as EntityPlayer
			if (!player.world.isRemote){
				repeat(4){
					val item = EntityItem(player.world, player.posX, player.posY, player.posZ, player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(it)!!)
					player.world.spawnEntity(item)
				}
			}
		}
	}

	@SubscribeEvent
	fun onAttackEvent(event: LivingAttackEvent){
		val attacker = event.source.trueSource
		val attacked = event.entityLiving
		if (!event.entityLiving.world.isRemote){
			if (event.source.damageType == "player"){
				val player = attacker as EntityPlayer
				if (player.getEntityAttribute(Core.CRITICAL_RATE).attributeValue != 0.0){
					val chance = Random.nextDouble(100.0)
					if (chance < player.getEntityAttribute(Core.CRITICAL_RATE).attributeValue){
						Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, attacked.posX, attacked.posY + 1, attacked.posZ, 0.0, 1.0, 0.0)
						val damage = (player.getEntityAttribute(Core.CRITICAL_DAMAGE).attributeValue / 100F) * event.amount
						attacked.attackEntityFrom(CriticalDamageSource(player), damage.toFloat())
						attacked.renderDamage((event.amount + damage).toInt(), TextFormatting.YELLOW)
					} else {
						attacked.renderDamage(event.amount.toInt(), TextFormatting.WHITE)
					}
				} else {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.WHITE)
				}
			}
		}

		if (attacker is EntityPlayer){
			when(event.source.damageType){
				"lightning" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.LIGHT_PURPLE)
				}
				"earthen" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.GOLD)
				}
				"water" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.DARK_BLUE)
				}
				"fire" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.RED)
				}
				"wind" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.DARK_GREEN)
				}
				"ice" -> {
					attacked.renderDamage(event.amount.toInt(), TextFormatting.BLUE)
				}
			}
		}

		if (attacker is EntityLivingBase && attacker.activePotionEffects.isNotEmpty()){
			for (i in Storage.Potions){
				if (attacker.isPotionActive(i)){
					(i as CustomPotion).onAttack(event.source, attacked, event.amount, attacker.activePotionMap[i]!!.amplifier)
				}
			}
		}

		if (attacked is EntityLivingBase && attacked.activePotionMap.isNotEmpty()){
			for (i in Storage.Potions){
				if (attacked.isPotionActive(i)){
					(i as CustomPotion).onAttacked(event.source, attacked, event.amount, attacked.activePotionMap[i]!!.amplifier)
				}
			}
		}
	}

	@SubscribeEvent
	fun onDropEvent(event: LivingDropsEvent){
		val entity = event.entityLiving
		if (entity is EntityDragon){
			event.drops.add(EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemStack(Core.dragonBreathSpecial)))
		} else if (entity is EntityWitch){
			if (Random.nextInt(100) >= 90) event.drops.add(EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemStack(Core.wizardFragment)))
		}

		if (event.source.trueSource is EntityPlayer && event.entityLiving.isNonBoss && event.entityLiving is IMob){
			val living = event.entityLiving
			val commonWeight = 25 + event.lootingLevel
			val uncommonWeight = 10 + event.lootingLevel
			val rareWeight = 5 + event.lootingLevel
			var dropChance = Random.nextInt(0, 500)
			if (dropChance < rareWeight){
				event.drops.add(EntityItem(living.world, living.posX, living.posY, living.posZ, ItemStack(Core.rareToken)))
			}
			dropChance -= rareWeight
			if (dropChance in 1 until uncommonWeight){
				event.drops.add(EntityItem(living.world, living.posX, living.posY, living.posZ, ItemStack(Core.uncommonToken)))
			}
			dropChance -= uncommonWeight
			if (dropChance in 1 until commonWeight){
				event.drops.add(EntityItem(living.world, living.posX, living.posY, living.posZ, ItemStack(Core.commonToken)))
			}
		}
	}

	@SubscribeEvent
	fun onLoginEvent(event: net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent){
		GlobalScope.launch {
			println(1)
		}
	}

	@SubscribeEvent
	fun onExpOrbPickUpEvent(event: PlayerPickupXpEvent){
		val player = event.entityPlayer
		if (player.getLevel() == 999) return
		repeat(event.orb.xpValue){
			player.getEntityAttribute(Core.EXP).baseValue++
			if ((0..16).contains(player.getLevel())) {
				if (2 * player.getLevel() + 7 == player.getExp()) {
					player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1F, 1F)
					player.getEntityAttribute(Core.EXP).baseValue = 0.0
					player.getEntityAttribute(Core.LEVEL).baseValue++
					player.sendMessage(TextComponentTranslation("text.levelUp"))
					player.addItemStackToInventory(ItemStack(Core.primalSeed, 1))
				}
			} else if ((17..31).contains(player.getLevel())) {
				if (5 * player.getLevel() - 38 == player.getExp()) {
					player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1F, 1F)
					player.getEntityAttribute(Core.EXP).baseValue = 0.0
					player.getEntityAttribute(Core.LEVEL).baseValue++
					player.sendMessage(TextComponentTranslation("text.levelUp"))
					player.addItemStackToInventory(ItemStack(Core.primalSeed, 1))
				}
			} else {
				if (9 * player.getLevel() - 158 == player.getExp()) {
					player.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1F, 1F)
					player.getEntityAttribute(Core.EXP).baseValue = 0.0
					player.getEntityAttribute(Core.LEVEL).baseValue++
					player.sendMessage(TextComponentTranslation("text.levelUp"))
					player.addItemStackToInventory(ItemStack(Core.primalSeed, 1))
				}
			}
		}
	}
}