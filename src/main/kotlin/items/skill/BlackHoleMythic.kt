package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import kotlin.math.sqrt

object BlackHoleMythic: ItemSkill("black_hole_mythic", 120, IItemRarity.MYTHIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = world.loadedEntityList
		val ray = player.rayTrace(15.0, 0F)?.blockPos!!
		GlobalScope.launch {
			repeat(6){
				(world as WorldServer).addScheduledTask(){
					repeat(entityList.size){
						val posX = entityList[it].posX
						val posY = entityList[it].posY
						val posZ = entityList[it].posZ
						if (sqrt(entityList[it].getDistanceSqToCenter(ray)) <= 5.0){
							entityList[it].addVelocity((ray.x - posX) / 2, (ray.y - posY) / 2, (ray.z - posZ) / 2)
						}
					}
				}
				delay(1000)
			}
		}
	}
}