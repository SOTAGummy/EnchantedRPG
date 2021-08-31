package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import extension.times
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import source.LightningDamage

object LightningCommon: ItemSkill("lightning", 30, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = world.getLivingEntitiesInArea(player.position, 10)
		entityList.times(3)
		GlobalScope.launch {
			repeat(3){
				val lightning = EntityLightningBolt(world, entityList[it].posX, entityList[it].posY, entityList[it].posZ, true)
				lightning.setLocationAndAngles(entityList[it].posX, entityList[it].posY, entityList[it].posZ, 0F, 0F)
				(world as WorldServer).addScheduledTask(){
					world.addWeatherEffect(lightning)
					if (entityList[it] !is EntityPlayerMP) entityList[it].attackEntityFrom(LightningDamage(player), 2F)
				}
				delay(333)
			}
		}
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 10).isNotEmpty()
	}
}