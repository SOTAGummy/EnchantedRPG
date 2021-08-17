package gui.skill_workbench

import blocks.TileEntitySkillWorkbench
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.inventory.IInventory
import net.minecraft.util.ResourceLocation

class GuiSkillWorkbenchContainer(inv: IInventory, te: TileEntitySkillWorkbench): GuiContainer(SkillWorkbenchContainer(inv, te)){
	init {
		this.xSize = 175
		this.ySize = 165
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1F, 1F, 1F,1F)
		this.mc.textureManager.bindTexture(ResourceLocation(Core.ID,"textures/gui/skill_workbench_gui.png"))
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize,this.ySize)
		GlStateManager.color(0F, 0F, 0F, 0F)
	}

	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		drawDefaultBackground()
		super.drawScreen(mouseX, mouseY, partialTicks)
		renderHoveredToolTip(mouseX, mouseY)
	}

	override fun initGui() {
		super.initGui()
		this.addButton(GuiButton(1, 165, 125, 54, 12, "set"))
		this.addButton(GuiButton(2, 240, 125, 54, 12, "remove"))
	}
}