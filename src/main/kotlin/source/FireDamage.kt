package source

import net.minecraft.entity.player.EntityPlayer

class FireDamage(player: EntityPlayer): CustomDamageSource("fire", player){
	override fun isFireDamage(): Boolean {
		return true
	}
}