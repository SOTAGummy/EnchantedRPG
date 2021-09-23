package items

import Core
import enum.IItemRarity
import items.baseItem.ItemFood
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

object CriticalFruit: ItemFood(0, 0F, false, "critical_fruit", IItemRarity.MYTHIC){
	init {
		this.setAlwaysEdible()
	}

	override fun hasEffect(stack: ItemStack): Boolean {
		return true
	}

	override fun onFoodEaten(stack: ItemStack, worldIn: World, player: EntityPlayer) {
		super.onFoodEaten(stack, worldIn, player)
		player.getEntityAttribute(Core.CRITICAL_RATE).baseValue += 0.5
		player.getEntityAttribute(Core.CRITICAL_DAMAGE).baseValue += 1
	}

	override fun getMaxItemUseDuration(stack: ItemStack): Int {
		return 10
	}
}