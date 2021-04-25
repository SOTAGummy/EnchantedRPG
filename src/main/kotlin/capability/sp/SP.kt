package capability.sp

import net.minecraft.entity.player.EntityPlayer

class SP: ISP {
	private var spValue: Int = 100

	override fun getSP(): Int {
		return spValue
	}

	override fun setSP(value: Int) {
		this.spValue = value
	}

	override fun addSP(player: EntityPlayer, value: Int) {
		if ((player.getCapability(SPProvider.MP!!, null)?.getSP()?.plus(value))!! <= player.getEntityAttribute(Core.MAXSP).attributeValue.toInt())
			this.spValue += value
	}

	override fun useSP(value: Int): Boolean {
		return if (this.spValue - value < 0) {
			false
		} else {
			this.spValue -= value
			true
		}
	}
}