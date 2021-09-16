package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World
import source.FireDamage
import kotlin.random.Random

object InfernoCommon: ItemSkill("inferno", 80, IItemRarity.COMMON, 5){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entities = world.getLivingEntitiesInArea(player.position, 5)
		player.playSound(Core.INFERNO_SOUND, 0.4F, 1F)
		repeat(entities.size){
			val randomX = Random.nextDouble(-0.5, 0.5)
			val randomZ = Random.nextDouble(-0.5, 0.5)
			world.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, entities[it].posX, entities[it].posY, entities[it].posZ, randomX, 1.0, randomZ)
		}
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entities = world.getLivingEntitiesInArea(player.position, 5)
		repeat(entities.size){
			entities[it].attackEntityFrom(FireDamage(player), 10F)
			entities[it].setFire(10)
		}
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 5).isNotEmpty()
	}
}