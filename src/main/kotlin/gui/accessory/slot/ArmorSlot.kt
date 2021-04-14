package gui.accessory.slot

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class ArmorSlot(val player: EntityPlayer, inventory: IInventory, index: Int, x: Int, y: Int, val type: EntityEquipmentSlot) : Slot(inventory, index, x, y) {
	override fun isItemValid(stack: ItemStack): Boolean {
		val item: Item? = if (stack == null) null else stack.item
		return item != null && item.isValidArmor(stack, type, player)
	}

	override fun getSlotStackLimit(): Int {
		return 1
	}

	@SideOnly(Side.CLIENT)
	override fun getSlotTexture(): String {
		val armor = if (type == EntityEquipmentSlot.HEAD) {
			"helmet"
		} else if (type == EntityEquipmentSlot.CHEST) {
			"chestplate"
		} else if (type == EntityEquipmentSlot.LEGS) {
			"leggings"
		} else if (type == EntityEquipmentSlot.FEET) {
			"boots"
		} else {
			""
		}
		return "minecraft:items/empty_armor_slot_$armor"
	}

	override fun canTakeStack(playerIn: EntityPlayer): Boolean {
		val itemstack = this.stack
		return if (!itemstack.isEmpty && !playerIn.isCreative && EnchantmentHelper.hasBindingCurse(itemstack)) false else super.canTakeStack(
			playerIn
		)
	}
}