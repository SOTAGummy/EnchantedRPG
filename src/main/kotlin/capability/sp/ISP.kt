package capability.sp

import net.minecraft.entity.player.EntityPlayer

interface ISP {
	fun getSP(): Int
	fun setSP(value: Int)
	fun addSP(player: EntityPlayer, value: Int)
	fun useSP(value: Int): Boolean
}