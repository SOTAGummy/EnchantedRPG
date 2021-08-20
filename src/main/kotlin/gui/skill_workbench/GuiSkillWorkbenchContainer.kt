package gui.skill_workbench

import Core
import blocks.TileEntitySkillWorkbench
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.Container
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.FMLCommonHandler
import packet.PacketHandler
import packet.PacketRequestUpdateSkillWorkbench


class GuiSkillWorkbenchContainer(container: Container, val te: TileEntitySkillWorkbench): GuiContainer(container){
	private val texture = ResourceLocation(Core.ID, "textures/gui/skill_workbench_gui.png")

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1f, 1f, 1f, 1f)
		mc.textureManager.bindTexture(texture)
		val x = (width - xSize) / 2
		val y = (height - ySize) / 2
		drawTexturedModalRect(x, y, 0, 0, xSize, ySize)
		GlStateManager.color(0F, 0F, 0F, 0F)
	}

	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		drawDefaultBackground()
		super.drawScreen(mouseX, mouseY, partialTicks)
		renderHoveredToolTip(mouseX, mouseY)
	}

	override fun initGui() {
		super.initGui()
		this.buttonList.add(GuiButton(1, this.guiLeft / 2, this.guiTop / 2, 60, 20, "set"))
	}

	override fun actionPerformed(button: GuiButton) {
		when (button.id){
			1 -> {
				val world = FMLCommonHandler.instance().minecraftServerInstance.getWorld(te.world.provider.dimension)
				world.addScheduledTask(){
					(world.getTileEntity(te.pos) as TileEntitySkillWorkbench).inventory.setStackInSlot(0, ItemStack.EMPTY)
				}
			}
			else -> {}
		}
		super.actionPerformed(button)
	}
}