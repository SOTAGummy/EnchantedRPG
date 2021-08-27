package source

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource

abstract class CustomDamageSource(name: String, val player: EntityPlayer): DamageSource(name){
	override fun getTrueSource(): Entity {
		return player
	}
}