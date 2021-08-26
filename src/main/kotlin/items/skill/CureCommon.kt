package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CureCommon: ItemSkill("cure_common", 10, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val potions = arrayListOf<Potion>()
		repeat(player.activePotionEffects.size){
			if (player.activePotionEffects.toList()[it].potion.isBadEffect){
				potions.add(player.activePotionEffects.toList()[it].potion)
			}
		}
		if (potions.size != 0){
			player.removePotionEffect(potions[0])
		}
	}
}