package sound

import Core
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import utils.Storage

object SoundHandler {
	fun registerSound(name: String): SoundEvent {
		val location = ResourceLocation(Core.ID, name)
		val event = SoundEvent(location)
		event.setRegistryName(name)
		Storage.Sounds.add(event)
		return event
	}
}