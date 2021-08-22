package items.skill

import capability.sp.SPProvider
import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object FullFillMaster: ItemSkill("full_fill_master", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.getCapability(SPProvider.SP!!, null)?.setSP(player.getEntityAttribute(Core.MAX_SP).attributeValue.toInt())
	}
}