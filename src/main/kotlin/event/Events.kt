package event

import capability.accessory.AccessoryProvider
import capability.mp.MpProvider
import gui.accessory.button.AccessoryButton
import gui.mp.MPIndicator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.event.entity.living.LivingEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import packet.PacketAccessory
import packet.PacketHandler
import utils.Storage
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
			event.addCapability(ResourceLocation(Core.ID, "mp"), MpProvider())
			event.addCapability(ResourceLocation(Core.ID, "accessory"), AccessoryProvider())
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
			event.buttonList.add(AccessoryButton(70, event.gui.mc.displayWidth / 2 - 314, event.gui.mc.displayHeight / 2 - 190, 12, 14, ""))
	}

	@SubscribeEvent
	fun onTickEvent(event: TickEvent.PlayerTickEvent){
		if (event.phase == TickEvent.Phase.END && event.player is EntityPlayerMP){
			val player = event.player as EntityPlayerMP
			repeat(4){
				PacketHandler.network.sendTo(PacketAccessory(player, it.toByte(), player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getItem(it)!!), player as EntityPlayerMP?)
			}
		}

		if (event.side == Side.SERVER){
			repeat(4){
				val cap = event.player.getCapability(AccessoryProvider.ACCESSORY!!, null)!!
				val current = cap.getItem(it)
				val old = cap.getItem(it + 4)
				if (!old.isItemEqual(current)){
					if (old.isEmpty && !current.isEmpty){
						event.player.attributeMap.applyAttributeModifiers(current.getAttributeModifiers(accessorySlots[it]))
					} else if (!old.isEmpty && current.isEmpty){
						event.player.attributeMap.removeAttributeModifiers(old.getAttributeModifiers(accessorySlots[it]))
					} else if (!old.isEmpty && !current.isEmpty){
						event.player.attributeMap.applyAttributeModifiers(current.getAttributeModifiers(accessorySlots[it]))
						event.player.attributeMap.removeAttributeModifiers(old.getAttributeModifiers(accessorySlots[it]))
					}
				}
				cap.setItem(it + 4, cap.getItem(it).copy())
			}

			if (event.player.health > event.player.maxHealth){
				event.player.health = event.player.maxHealth
			}
		}
	}
}