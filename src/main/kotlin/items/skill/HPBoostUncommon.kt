package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object HPBoostUncommon: ItemSkill("hp_boost", 120, IItemRarity.UNCOMMON, 30){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.absorptionAmount = 0F
		player.absorptionAmount = player.maxHealth * 0.4F
	}
}