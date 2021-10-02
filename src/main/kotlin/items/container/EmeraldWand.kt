package items.container

import Core
import com.google.common.collect.Multimap
import enum.IItemRarity
import extension.call
import items.baseItem.RootItem
import module.ISkillStorable
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object EmeraldWand: RootItem("emerald_wand", IItemRarity.MYTHIC), ISkillStorable {
	init {
		this.maxStackSize = 1
	}

	override fun getSkillCapacity(): Int {
		return 6
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		if (!player.isSneaking){
			player.heldItemMainhand.call(world, player, hand)
			player.cooldownTracker.setCooldown(this, getCooldownTime(player.heldItemMainhand) * 20)
		}

		return ActionResult(EnumActionResult.SUCCESS, player.heldItemMainhand)
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
		addToolTip(stack, tooltip)
	}

	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)
		if (slot == EntityEquipmentSlot.MAINHAND)
		multimap.put(Core.SP_SAVING_RATE.name, AttributeModifier(SKILL_CONTAINER_MODIFIER, "save", 20.0, 0))
		return multimap
	}
}