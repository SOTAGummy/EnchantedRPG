package items.skill

import Core
import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object HealCommon: ItemSkill("heal", 5, IItemRarity.COMMON, 2){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.playSound(Core.HEAL_SOUND, 0.5F, 1F)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		if (player.health + (player.maxHealth * 0.1) < player.maxHealth){
			player.health += player.maxHealth * 0.1F
		} else {
			player.health = player.maxHealth
		}
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return player.maxHealth >= player.health
	}
}