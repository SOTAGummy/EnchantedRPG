package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object EnderKnockCommon: ItemSkill("ender_knock", 35, IItemRarity.COMMON, 5){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val x = player.posX
		val y = player.posY
		val z = player.posZ
		val entities = world.getLivingEntitiesInArea(player.position, 15)
		player.setPosition(entities[0].posX, entities[0].posY, entities[0].posZ)
		player.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1F, 1F)
		entities[0].setVelocity((entities[0].posX - player.posX) / 2, 1.0, (entities[0].posZ - player.posZ) / 2)
		player.setPosition(x, y, z)
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val x = player.posX
		val y = player.posY
		val z = player.posZ
		val entities = world.getLivingEntitiesInArea(player.position, 15)
		player.setPosition(entities[0].posX, entities[0].posY, entities[0].posZ)
		entities[0].attackEntityFrom(DamageSource.causePlayerDamage(player), 5F)
		entities[0].setVelocity((entities[0].posX - player.posX) / 2, 1.0, (entities[0].posZ - player.posZ) / 2)
		player.setPosition(x, y, z)
	}

	override fun canCall(world: World, player: EntityPlayer, handIn: EnumHand): Boolean {
		return world.getLivingEntitiesInArea(player.position, 15).isNotEmpty()
	}
}