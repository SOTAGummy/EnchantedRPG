package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import source.WindDamage
import kotlin.math.sqrt

object BlowLegend: ItemSkill("blow", 80, IItemRarity.LEGEND){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = world.loadedEntityList
		val playerX = player.posX
		val playerZ = player.posZ
		repeat(entityList.size){
			if (sqrt(entityList[it].getDistanceSqToCenter(player.position)) <= 9.0) {
				val posX = entityList[it].posX
				val posZ = entityList[it].posZ
				entityList[it].addVelocity(posX - playerX, 0.8, posZ - playerZ)
				entityList[it].attackEntityFrom(WindDamage(player), 10F)
			}
		}
	}
}