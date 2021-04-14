package event

import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.event.entity.EntityEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
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
}