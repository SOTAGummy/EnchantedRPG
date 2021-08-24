package source

import net.minecraft.entity.player.EntityPlayer

class CriticalDamageSource(player: EntityPlayer): CustomDamageSource("critical", player){}