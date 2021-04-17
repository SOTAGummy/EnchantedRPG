package items.baseItem

import Core
import items.rootItem.RootItem
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import java.io.File

abstract class ItemSkill(name: String, val cost: Int): RootItem(name){
	init {
		creativeTab = Core.itemsTab
		maxStackSize = 1
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
		val costFormat = I18n.format(cost.toString())
		tooltip.add("${TextComponentTranslation("text.skill_cost").formattedText}: ${TextFormatting.BOLD}$costFormat")
	}

	override fun hasEffect(stack: ItemStack): Boolean {
			return true
	}

	abstract fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand)

	abstract fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand)
}