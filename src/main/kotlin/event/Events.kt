package event

import Core
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryProvider
import capability.sp.SPProvider
import extension.renderDamage
import gui.accessory.button.AccessoryButton
import gui.mp.MPIndicator
import gui.skillList.SkillListIndicator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityWitch
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.living.LivingAttackEvent
import net.minecraftforge.event.entity.living.LivingDeathEvent
import net.minecraftforge.event.entity.living.LivingDropsEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketAccessory
import packet.PacketHandler
import packet.PacketSP
import potion.CustomPotion
import source.CriticalDamageSource
import utils.Storage
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
			MPIndicator(mc, correction)
			SkillListIndicator(mc)
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
				PacketHandler.network.sendTo(PacketAccessory(player, it.toByte(), player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(it)!!), player as EntityPlayerMP?)
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
		val currentItemHandler = player.getCapability(AccessoryProvider.ACCESSORY!!, null)
		val oldItemHandler = event.original.getCapability(AccessoryProvider.ACCESSORY, null)
		if (!event.isWasDeath){
			repeat(8){
				currentItemHandler?.setStackInSlot(it, oldItemHandler?.getStackInSlot(it)!!)
			}
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
						val damage = player.getEntityAttribute(Core.CRITICAL_DAMAGE).attributeValue.toFloat() * event.amount
						attacked.attackEntityFrom(CriticalDamageSource(player), damage)
						attacked.renderDamage((event.amount + damage).toInt(), TextFormatting.DARK_PURPLE)
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
					attacked.renderDamage(event.amount.toInt(), TextFormatting.YELLOW)
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
}