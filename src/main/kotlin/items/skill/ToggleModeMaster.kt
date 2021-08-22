package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.GameType
import net.minecraft.world.World

object ToggleModeMaster: ItemSkill("toggle_mode_master", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		if (player.isCreative) {
			player.setGameType(GameType.SURVIVAL)
		} else {
			player.setGameType(GameType.CREATIVE)
		}
	}
}