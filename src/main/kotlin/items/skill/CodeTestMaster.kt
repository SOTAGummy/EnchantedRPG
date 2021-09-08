package items.skill

import entity.EntityArea
import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTestMaster: ItemSkill("code_test", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val test = EntityArea(world, player.posX, player.posY - 0.8, player.posZ, ItemStack(Core.testArea))
		world.spawnEntity(test)
	}
}