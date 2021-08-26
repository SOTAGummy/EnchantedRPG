package event

import Core
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryProvider
import capability.sp.SPProvider
import gui.accessory.button.AccessoryButton
import gui.mp.MPIndicator
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.Sound
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.boss.EntityDragon
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.*
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.BlockPos
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.util.Constants
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.living.*
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.world.ExplosionEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketAccessory
import packet.PacketHandler
import packet.PacketSP
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
		if (event.source.damageType == "player"){
			val player = event.source.trueSource as EntityPlayer
			val entity = event.entityLiving
			if (player.getEntityAttribute(Core.CRITICAL_RATE).attributeValue != 0.0){
				val chance = Random.nextDouble(100.0)
				if (chance < player.getEntityAttribute(Core.CRITICAL_RATE).attributeValue){
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, entity.posX, entity.posY + 1, entity.posZ, 0.0, 1.0, 0.0)
					val damage = player.getEntityAttribute(Core.CRITICAL_DAMAGE).attributeValue.toFloat() * event.amount
					entity.attackEntityFrom(CriticalDamageSource(player), damage)
				}
			}
		}

		if (event.source.damageType == "indirectMagic" && event.source.trueSource is EntityPlayer && event.entityLiving == event.source.trueSource){
			event.isCanceled = true
		}
	}

	@SubscribeEvent
	fun onDropEvent(event: LivingDropsEvent){
		if (event.entityLiving is EntityDragon){
			event.drops.add(EntityItem(event.entityLiving.world, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, ItemStack(Core.dragon_breath_special)))
		}

		if (event.source.trueSource is EntityPlayer && event.entityLiving.isNonBoss && event.entityLiving is IMob){
			val entity = event.entityLiving
			val commonWeight = 25 + event.lootingLevel
			val uncommonWeight = 10 + event.lootingLevel
			val rareWeight = 5 + event.lootingLevel
			var dropChance = Random.nextInt(0, 500)
			if (dropChance < rareWeight){
				event.drops.add(EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemStack(Core.rare_token)))
			}
			dropChance -= rareWeight
			if (dropChance in 1 until uncommonWeight){
				event.drops.add(EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemStack(Core.uncommon_token)))
			}
			dropChance -= uncommonWeight
			if (dropChance in 1 until commonWeight){
				event.drops.add(EntityItem(entity.world, entity.posX, entity.posY, entity.posZ, ItemStack(Core.common_token)))
			}
		}
	}
}