package utils

import capability.accessory.AccessoryProvider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand

object AccessoryAPI {
	fun getAccessory(player: EntityPlayer, equipmentSlot: EntityEquipmentSlot): ItemStack?{
		return if (equipmentSlot.slotType == Core.ACCESSORY){
			player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(equipmentSlot.index)
		} else {
			null
		}
	}

	fun setAccessory(player: EntityPlayer, equipmentSlot: EntityEquipmentSlot, stack: ItemStack){
		if (getAccessory(player, equipmentSlot) == null || getAccessory(player, equipmentSlot)?.isEmpty == false) return
		else {
			player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.setStackInSlot(equipmentSlot.index, stack)
			player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY)
		}
	}
}