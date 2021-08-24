package source

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource

open class CustomDamageSource(name: String, val player: EntityPlayer): DamageSource(name){
	override fun getTrueSource(): Entity {
		return player
	}
}