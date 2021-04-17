package items.skill

import Core
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World

object CodeTest: ItemSkill("code_test", 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		world.spawnParticle(Core.TEST, player.posX, player.posY, player.posZ, 1.0, 1.0, 1.0)
		println("aaa")
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		println(EnumParticleTypes.values())
	}
}