package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object Heal: ItemSkill("heal", 5, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.playSound(Core.HEAL_SOUND, 0.5F, 1F)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		if (player.health + 2 < player.maxHealth){
			player.health += 2
		}
	}
}