package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTestMaster: ItemSkill("code_test_master", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		println(Blocks.CRAFTING_TABLE)
	}
}