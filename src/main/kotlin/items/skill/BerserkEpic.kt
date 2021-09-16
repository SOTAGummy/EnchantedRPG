package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object BerserkEpic: ItemSkill("berserk", 70, IItemRarity.EPIC, 3){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.attackEntityFrom(DamageSource.causePlayerDamage(player), player.health / 2)
		player.addPotionEffect(PotionEffect(Potion.getPotionById(1)!!, 400, 1))
		player.addPotionEffect(PotionEffect(Potion.getPotionById(5)!!, 400, 2))
		player.addPotionEffect(PotionEffect(Potion.getPotionById(12)!!, 400, 0))
	}
}