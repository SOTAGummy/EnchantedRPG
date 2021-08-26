package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object BerserkMythic: ItemSkill("berserk_mythic", 80, IItemRarity.MYTHIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.attackEntityFrom(DamageSource.causePlayerDamage(player), player.health / 2)
		player.addPotionEffect(PotionEffect(Potion.getPotionById(1)!!, 600, 2))
		player.addPotionEffect(PotionEffect(Potion.getPotionById(5)!!, 600, 4))
		player.addPotionEffect(PotionEffect(Potion.getPotionById(12)!!, 600, 0))
	}
}