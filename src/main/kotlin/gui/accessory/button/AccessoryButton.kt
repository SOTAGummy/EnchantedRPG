package gui.accessory.button

import Core
import gui.accessory.GuiAccessoryHandler
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.util.ResourceLocation
import packet.PacketHandler
import packet.PacketOpenAccessoryGui

class AccessoryButton(id: Int, x: Int, y: Int, width: Int, height: Int, name: String) :
	GuiButton(id, x, y, width, height, name) {
	override fun drawButton(mc: Minecraft, mouseX: Int, mouseY: Int, partialTicks: Float) {
		val fontRenderer = mc.fontRenderer
		mc.textureManager.bindTexture(ResourceLocation("${Core.ID}:textures/gui/accessory_button.png"))
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
		hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height
		val i = getHoverState(hovered)
		GlStateManager.enableBlend()
		GlStateManager.tryBlendFuncSeparate(
			GlStateManager.SourceFactor.SRC_ALPHA,
			GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
			GlStateManager.SourceFactor.ONE,
			GlStateManager.DestFactor.ZERO
		)
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA)
		if (i == 1) this.drawTexturedModalRect(x, y, 0, 0, 12, 14)
		else if (i == 2) this.drawTexturedModalRect(x, y, 12, 0, 12, 14)
		mouseDragged(mc, mouseX, mouseY)
		var j = 14737632

		if (packedFGColour != 0) {
			j = packedFGColour
		} else if (!enabled) {
			j = 10526880
		} else if (hovered) {
			j = 16777120
		}

		drawCenteredString(
			fontRenderer,
			displayString, x + width / 2, y + (height - 8) / 2, j
		)
	}

	override fun mousePressed(mc: Minecraft, mouseX: Int, mouseY: Int): Boolean {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			mc.player.openGui(Core.instance, GuiAccessoryHandler.AccessoryGui, mc.world, 0, 0, 0)
			PacketHandler.network.sendToServer(PacketOpenAccessoryGui())
		}
		return super.mousePressed(mc, mouseX, mouseY)
	}
}