package items.skill

import enum.IItemRarity
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

object LightningMythic: ItemSkill("lightning_mythic", 105, IItemRarity.MYTHIC){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entityList = arrayListOf<EntityLiving>()
		val allEntities = world.loadedEntityList
		repeat(allEntities.size){
			if (allEntities[it] !is EntityPlayer && allEntities[it] is IMob && allEntities[it].getDistance(player) <= 10.0){
				entityList.add(allEntities[it] as EntityLiving)
			}
		}
		if (entityList.size != 0){
			val count = if (entityList.size < 20){
				entityList.size
			} else {
				20
			}
			GlobalScope.launch {
				repeat(count){
					val lightning = EntityLightningBolt(world, entityList[it].posX, entityList[it].posY, entityList[it].posZ, true)
					lightning.setLocationAndAngles(entityList[it].posX, entityList[it].posY, entityList[it].posZ, 0F, 0F)
					(world as WorldServer).addScheduledTask(){
						world.addWeatherEffect(lightning)
						if (entityList[it] !is EntityPlayerMP)
							entityList[it].attackEntityFrom(LightningDamage(player), 12F)
					}
					delay(50)
				}
			}
		}
	}
}