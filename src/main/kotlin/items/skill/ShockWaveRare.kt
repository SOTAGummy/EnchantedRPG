package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import source.EarthenDamage
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

object ShockWaveRare: ItemSkill("shock_wave", 55, IItemRarity.RARE){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.addVelocity(0.0, 1.0, 0.0)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.addVelocity(0.0, 1.0, 0.0)
		GlobalScope.launch {
			delay(10)
			launch {
				while (!player.onGround){
					delay(50)
				}
			}.join()
			Minecraft.getMinecraft().addScheduledTask(){
				repeat(100){
					val randomRad = Random.nextDouble(2 * Math.PI)
					Minecraft.getMinecraft().world.spawnParticle(EnumParticleTypes.CRIT, player.posX, player.posY, player.posZ, 5 * cos(randomRad), 0.0, 5 * sin(randomRad))
				}
			}
			val entityList = world.getLivingEntitiesInArea(player.position, 5)
			(world as WorldServer).addScheduledTask(){
				for (i in entityList) {
					i.attackEntityFrom(EarthenDamage(player), 20F)
				}
			}
		}
	}
}