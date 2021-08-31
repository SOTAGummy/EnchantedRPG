package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object ExplosionLegend: ItemSkill("explosion", 75, IItemRarity.LEGEND){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entity = world.getLivingEntitiesInArea(player.position, 15)
		repeat(9){
			world.createExplosion(player, entity[it].posX, entity[it].posY, entity[it].posZ, 3F, false)
		}
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 15).isNotEmpty()
	}
}