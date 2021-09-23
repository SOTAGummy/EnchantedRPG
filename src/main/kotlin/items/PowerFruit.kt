package items

import enum.IItemRarity
import items.baseItem.ItemFood
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object PowerFruit: ItemFood(0, 0F, false, "power_fruit", IItemRarity.MYTHIC){
	init {
		this.setAlwaysEdible()
	}

	override fun hasEffect(stack: ItemStack): Boolean {
		return true
	}

	override fun onFoodEaten(stack: ItemStack, worldIn: World, player: EntityPlayer) {
		super.onFoodEaten(stack, worldIn, player)
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue += 0.5
	}

	override fun getMaxItemUseDuration(stack: ItemStack): Int {
		return 10
	}
}