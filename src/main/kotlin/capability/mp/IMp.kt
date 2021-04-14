package capability.mp

import net.minecraft.entity.player.EntityPlayer

interface IMp {
	fun getMp(): Int
	fun setMp(value: Int)
	fun addMp(player: EntityPlayer, value: Int)
	fun useMp(value: Int): Boolean
}