package event

import capability.accessory.AccessoryProvider
import capability.mp.MpProvider
import gui.mp.MPIndicator
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import utils.Storage
class Events {
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
}