package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.Items
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import kotlin.random.Random

object ArrowRainEpic: ItemSkill("arrow_rain", 45, IItemRarity.EPIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = arrayListOf<EntityArrow>()
		val pos = player.rayTrace(15.0, 0F)?.blockPos!!

		GlobalScope.launch {
			repeat(80){
				val itemArrow = Items.ARROW as ItemArrow
				val arrow = itemArrow.createArrow(world, ItemStack(itemArrow), player)
				val i = (0 .. 1).random()
				val randomCodeX = if (i == 0){
					1
				} else {
					-1
				}
				val j = (0 .. 1).random()
				val randomCodeZ = if (j == 0){
					1
				} else {
					-1
				}
				val randomX = Random.nextDouble(4.5) * randomCodeX
				val randomZ = Random.nextDouble(4.5) * randomCodeZ
				arrow.setPosition(pos.x + randomX, pos.y.toDouble() + 5.0, pos.z + randomZ)
				arrow.setVelocity(0.0, -1.0, 0.0)
				arrow.damage = 6.0
				arrow.pickupStatus = EntityArrow.PickupStatus.DISALLOWED
				entityList.add(arrow)
				val server = world as WorldServer
				server.addScheduledTask() {
					world.spawnEntity(arrow)
				}
				delay(12)

				GlobalScope.launch {
					delay(1000)
					world.addScheduledTask(){
						world.removeEntity(arrow)
					}
				}
			}
		}
	}
}