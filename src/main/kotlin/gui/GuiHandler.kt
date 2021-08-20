package gui

import blocks.TileEntitySkillWorkbench
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryProvider
import gui.accessory.AccessoryContainer
import gui.accessory.GuiAccessoryContainer
import gui.skill_workbench.GuiSkillWorkbenchContainer
import gui.skill_workbench.SkillWorkbenchContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiHandler : IGuiHandler {
	companion object {
		const val AccessoryGui = 1
		const val SkillWorkbenchGui = 2
	}

	override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
		if (ID == AccessoryGui) {
			val inv = AccessoryItemContainer()
			repeat(4) {
				if (player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(it) != ItemStack.EMPTY) {
					inv.setStackInSlot(it, player.getCapability(AccessoryProvider.ACCESSORY, null)?.getStackInSlot(it)!!)
				}
			}
			return GuiAccessoryContainer(player, inv)
		} else if (ID == SkillWorkbenchGui){
			return GuiSkillWorkbenchContainer(getServerGuiElement(ID, player, world, x, y, z)!!, world.getTileEntity(BlockPos(x, y, z)) as TileEntitySkillWorkbench)
		}
		return null
	}

	override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {
		if (ID == AccessoryGui) {
			val inv = AccessoryItemContainer()
			repeat(4) {
				if (player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getStackInSlot(it) != ItemStack.EMPTY) {
					inv.setStackInSlot(it, player.getCapability(AccessoryProvider.ACCESSORY, null)?.getStackInSlot(it)!!)
				}
			}
			return AccessoryContainer(player, inv)
		} else if (ID == SkillWorkbenchGui) {
			return SkillWorkbenchContainer(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntitySkillWorkbench)
		}
		return null
	}
}