package gui.accessory

import capability.accessory.AccessoryProvider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiAccessoryHandler : IGuiHandler {
	companion object {
		const val AccessoryGui = 1
	}

	override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == AccessoryGui) {
			val inv = AccessoryItemContainer()
			repeat(4) {
				if (player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getItem(it) != ItemStack.EMPTY) {
					inv.setStackInSlot(it, player.getCapability(AccessoryProvider.ACCESSORY, null)?.getItem(it)!!)
				}
			}
			return GuiAccessoryContainer(player, inv)
		}
		return null
	}

	override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World?, x: Int, y: Int, z: Int): Any? {
		if (ID == AccessoryGui) {
			val inv = AccessoryItemContainer()
			repeat(4) {
				if (player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getItem(it) != ItemStack.EMPTY) {
					inv.setStackInSlot(it, player.getCapability(AccessoryProvider.ACCESSORY, null)?.getItem(it)!!)
				}
			}
			return AccessoryContainer(player, inv)
		}
		return null
	}
}