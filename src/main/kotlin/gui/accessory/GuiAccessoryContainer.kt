package gui.accessory

import Core
import capability.accessory.AccessoryProvider
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.client.gui.inventory.GuiInventory
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation
import packet.PacketAccessory
import packet.PacketHandler


class GuiAccessoryContainer(player: EntityPlayer, customInv: AccessoryItemContainer): GuiContainer(AccessoryContainer(player, customInv)) {
	init {
		this.xSize = 192
		this.ySize = 166
	}

	override fun drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) {
		GlStateManager.color(1F, 1F, 1F, 1F)
		mc.textureManager.bindTexture(ResourceLocation("${Core.ID}:textures/gui/accessory.png"))
		drawTexturedModalRect(guiLeft - 16, guiTop, 0, 0, xSize, ySize)
		GuiInventory.drawEntityOnScreen(
			guiLeft + 51,
			guiTop + 75,
			30,
			((guiLeft + 51) - mouseX).toFloat(),
			((guiTop + 25) - mouseY).toFloat(),
			mc.player
		)
		GlStateManager.color(0F, 0F, 0F, 0F)
	}

	override fun drawGuiContainerForegroundLayer(p_146979_1_: Int, p_146979_2_: Int) {
		fontRenderer.drawString(I18n.format("container.crafting", arrayOfNulls<Any>(0)), 115, 8, 4210752)
	}

	override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
		drawDefaultBackground()
		super.drawScreen(mouseX, mouseY, partialTicks)
		renderHoveredToolTip(mouseX, mouseY)
	}
}