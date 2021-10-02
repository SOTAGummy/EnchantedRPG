package items.skill

import Core
import capability.sp.SPProvider
import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object FullFillMaster: ItemSkill("full_fill", 0, IItemRarity.MASTER, 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.getCapability(SPProvider.SP!!, null)?.setSP(player.getEntityAttribute(Core.MAX_SP).attributeValue.toInt())
	}
}