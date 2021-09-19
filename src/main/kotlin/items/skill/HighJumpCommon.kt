package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object HighJumpCommon: ItemSkill("high_jump", 5, IItemRarity.COMMON, 1){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, 0.5F, 1F)
		player.addVelocity(0.0, 1.0, 0.0)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.addVelocity(0.0, 1.0, 0.0)
	}
}