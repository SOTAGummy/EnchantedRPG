package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import source.EarthenDamage
import source.LightningDamage
import kotlin.math.sqrt
import kotlin.random.Random

object BlackHoleCommon: ItemSkill("black_hole_common", 70, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val ray = player.rayTrace(15.0, 0F)?.blockPos!!
		GlobalScope.launch {
			repeat(10){
				(world as WorldServer).addScheduledTask(){
					val entityList = world.getLivingEntitiesInArea(ray, 10)
					repeat(entityList.size){
						val posX = entityList[it].posX
						val posY = entityList[it].posY
						val posZ = entityList[it].posZ
						entityList[it].setVelocity((ray.x - posX) / 2, (ray.y - posY) / 2, (ray.z - posZ) / 2)
						entityList[it].attackEntityFrom(EarthenDamage(player), 1F)

					}
				}
				Minecraft.getMinecraft().addScheduledTask(){
					repeat(100){
						Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.PORTAL, ray.x.toDouble(), ray.y.toDouble(), ray.z.toDouble(), Random.nextDouble(-3.0, 3.0), Random.nextDouble(-3.0, 3.0), Random.nextDouble(-3.0, 3.0))
					}
				}
				delay(100)
			}
		}
	}
}