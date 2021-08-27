package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
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
import recipe.PedestalRecipe
import recipe.Recipes

object CodeTestMaster: ItemSkill("code_test_master", 0, IItemRarity.MASTER){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		repeat(world.loadedEntityList.size){
			if (world.loadedEntityList[it] == player){
				println(123)
			}
		}
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		repeat(world.loadedEntityList.size){
			if (world.loadedEntityList[it] == player){
				println(123)
			}
		}
	}
}