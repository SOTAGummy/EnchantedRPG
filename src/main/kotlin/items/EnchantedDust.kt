package items

import Core
import items.rootItem.RootItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

object EnchantedDust: RootItem("enchanted_dust"){
	init {
		maxStackSize = 64
		registryName = ResourceLocation(Core.ID, "enchanted_dust")
		unlocalizedName = "enchanted_dust"
		creativeTab = Core.itemsTab
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		return ActionResult(EnumActionResult.SUCCESS, player.getHeldItem(hand))
	}

	override fun hasEffect(stack: ItemStack): Boolean {
		return true
	}
}