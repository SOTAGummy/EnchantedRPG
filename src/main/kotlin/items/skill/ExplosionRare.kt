package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object ExplosionRare: ItemSkill("explosion_rare", 55, IItemRarity.RARE){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entity = world.getLivingEntitiesInArea(player.position, 15)
		repeat(5){
			world.createExplosion(player, entity[it].posX, entity[it].posY, entity[it].posZ, 3F, false)
		}
	}
}