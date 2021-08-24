package items.baseItem

import enum.IItemRarity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import kotlin.random.Random

open class ItemToken(name: String, rarity: IItemRarity): RootItem(name, rarity){
	override fun onItemRightClick(world: World, player: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
		if (this.rarity.skills.isNotEmpty()) {
			val random = Random.nextInt(this.rarity.skills.size)
			player.addItemStackToInventory(ItemStack(this.rarity.skills[random], 1))
			if (!player.isCreative) player.getHeldItem(handIn).count -= 1
		}
		return ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn))
	}
}