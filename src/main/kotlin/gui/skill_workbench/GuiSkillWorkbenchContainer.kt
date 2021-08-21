package gui.skill_workbench

import Core
import blocks.TileEntitySkillWorkbench
import extension.*
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.Container
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.FMLCommonHandler


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
		this.addButton(GuiButton(1, this.guiLeft / 2, this.guiTop / 2, 60, 20, "set"))
		this.addButton(GuiButton(2, this.guiLeft / 7, this.guiTop / 2, 60, 20, "remove"))
	}

	override fun actionPerformed(button: GuiButton) {
		when (button.id){
			1 -> {
				val world = FMLCommonHandler.instance().minecraftServerInstance.getWorld(te.world.provider.dimension)
				world.addScheduledTask(){
					val tile = world.getTileEntity(te.pos) as TileEntitySkillWorkbench
					val stack = tile.inventory.getStackInSlot(0).copy()
					var count = 0
					for (i in 1 .. 8){
						if (!tile.inventory.getStackInSlot(i).isEmpty){
							count++
						}
					}
					if (count != 0){
						repeat(count){
							if (stack.canAddItemSkill()){
								stack.addItemSkill(tile.inventory.extractItem(it + 1, 1, false))
							}
						}
						tile.inventory.setStackInSlot(0, stack)
						count = 0
						for(i in 0 .. 8){
							if (!tile.inventory.getStackInSlot(i + 1).isEmpty){
								count = i
								break
							}
						}
						repeat(8 - count){
							tile.inventory.insertItem(it + 1, tile.inventory.extractItem(it + count + 1, 1, false), false)
						}
					}
				}
			}
			2 -> {
				val world = FMLCommonHandler.instance().minecraftServerInstance.getWorld(te.world.provider.dimension)
				world.addScheduledTask(){
					val tile = world.getTileEntity(te.pos) as TileEntitySkillWorkbench
					if (!tile.inventory.getStackInSlot(0).isEmpty && tile.inventory.getStackInSlot(0).getSkillCount() != 0){
						repeat(tile.inventory.getStackInSlot(0).getSkillCount()){
							if (tile.inventory.getStackInSlot(8).isEmpty){
								val stack = tile.inventory.getStackInSlot(0).copy()
								tile.inventory.setStackInSlot(8, stack.removeSkill())
								tile.inventory.setStackInSlot(0, stack)
							}
						}
					}
				}
			}
			else -> {}
		}
		super.actionPerformed(button)
	}
}