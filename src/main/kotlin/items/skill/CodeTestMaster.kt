package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.monster.EntitySlime
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import potion.PotionBurning
import potion.PotionFrozen
import recipe.PedestalRecipe
import recipe.Recipes

object CodeTestMaster: ItemSkill("code_test_master", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}
}