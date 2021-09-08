package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object EnderKnockEpic: ItemSkill("ender_knock_epic", 65, IItemRarity.EPIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entities = world.getLivingEntitiesInArea(player.position, 15)
		GlobalScope.launch {
			repeat(entities.size.coerceAtMost(4)){
				player.setPosition(entities[it].posX, entities[it].posY, entities[it].posZ)
				player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1F, 1F)
				entities[it].setVelocity((entities[it].posX - player.posX) / 2, 1.0, (entities[it].posZ - player.posZ) / 2)
				delay(250)
			}
		}
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entities = world.getLivingEntitiesInArea(player.position, 15)
		GlobalScope.launch {
			repeat(entities.size.coerceAtMost(4)){
				player.setPosition(entities[it].posX, entities[it].posY, entities[it].posZ)
				entities[it].attackEntityFrom(DamageSource.causePlayerDamage(player), 8F)
				entities[it].setVelocity((entities[it].posX - player.posX) / 2, 1.0, (entities[it].posZ - player.posZ) / 2)
				delay(250)
			}
		}
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 15).isNotEmpty()
	}
}