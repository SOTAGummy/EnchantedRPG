package items.container

import enum.IItemRarity
import items.baseItem.RootItem
import module.ISkillStorable
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object SkillBook: RootItem("skill_book", IItemRarity.UNCOMMON), ISkillStorable{
	init {
		maxStackSize = 1
		creativeTab = Core.itemsTab
	}


	override fun getSkillCapacity(): Int {
		return 4
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		call(world, player, hand)
		return ActionResult(EnumActionResult.SUCCESS, player.heldItemMainhand)
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
		addToolTip(stack, tooltip)
	}
}