package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object RageUncommon: ItemSkill("rage_uncommon", 16, IItemRarity.UNCOMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.playSound(Core.RAGE_SOUND, 0.5F, 1F)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.addPotionEffect(PotionEffect(Potion.getPotionById(5)!!, 1200, 0))
	}
}