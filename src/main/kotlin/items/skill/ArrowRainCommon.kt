package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.init.Items
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import kotlin.random.Random

object ArrowRainCommon: ItemSkill("arrow_rain", 30, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = arrayListOf<EntityArrow>()
		val pos = player.rayTrace(15.0, 0F)?.blockPos!!

		GlobalScope.launch {
			repeat(20){
				val itemArrow = Items.ARROW as ItemArrow
				val arrow = itemArrow.createArrow(world, ItemStack(itemArrow), player)
				val randomX = Random.nextDouble(5.0)
				val randomZ = Random.nextDouble(5.0)
				arrow.setPosition(pos.x + randomX, pos.y.toDouble() + 5.0, pos.z + randomZ)
				arrow.setVelocity(0.0, -1.0, 0.0)
				arrow.damage = 2.0
				entityList.add(arrow)
				val server = world as WorldServer
				server.addScheduledTask() {
					world.spawnEntity(arrow)
				}
				delay(50)

			}
		}

		GlobalScope.launch {
			repeat(20){
				delay(75)
				val server = world as WorldServer
				server.addScheduledTask(){
					world.removeEntity(entityList[it])
				}
			}
		}
	}
}