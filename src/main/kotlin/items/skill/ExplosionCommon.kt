package items.skill

import enum.IItemRarity
import extension.getLivingEntitiesInArea
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object ExplosionCommon: ItemSkill("explosion_common", 35, IItemRarity.COMMON){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val entity = world.getLivingEntitiesInArea(player.position, 15)[0]
		world.createExplosion(player, entity.posX, entity.posY, entity.posZ, 1F, false)
	}
}