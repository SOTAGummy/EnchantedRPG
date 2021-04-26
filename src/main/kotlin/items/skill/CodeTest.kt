package items.skill

import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTest: ItemSkill("code_test", 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}
}