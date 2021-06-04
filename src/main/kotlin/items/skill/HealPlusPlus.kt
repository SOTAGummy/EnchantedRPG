package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object HealPlusPlus: ItemSkill("heal_plus_plus", 20, IItemRarity.RARE){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.playSound(Core.HEAL_SOUND, 0.5F, 1F)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		if (player.health + 10 < player.maxHealth){
			player.health += 10
		} else {
			player.health = player.maxHealth
		}
	}
}