package gui.status

import Core
import extension.getExp
import extension.getLevel
import extension.getMaxSP
import extension.getSP
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.resources.I18n
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextFormatting
import java.awt.Color

class RenderStatus(mc: Minecraft): Gui(){
	init {
		val playerTexture = (mc.player as AbstractClientPlayer).locationSkin
		val texture = ResourceLocation(Core.ID, "textures/gui/status.png")
		val player = mc.player
		val hp = ((player.health / player.maxHealth) * 110).toInt()
		val sp = ((player.getSP().toFloat() / player.getMaxSP().toFloat()) * 110).toInt()
		val exp = if ((1..16).contains(player.getLevel())) {
			((player.getExp().toDouble() / (2 * player.getLevel() + 7).toDouble()) * 110).toInt()
		} else if ((17..31).contains(player.getLevel())) {
			((player.getExp().toDouble() / (5 * player.getLevel() - 38).toDouble()) * 110).toInt()
		} else {
			((player.getExp().toDouble() / (9 * player.getLevel() - 158).toDouble()) * 110).toInt()
		}
		GlStateManager.pushMatrix()
		GlStateManager.color(1F, 1F, 1F)
		GlStateManager.scale(0.5, 0.5, 0.5)
		GlStateManager.enableAlpha()
		mc.textureManager.bindTexture(playerTexture)
		this.drawTexturedModalRect(10, 10, 32, 32, 32, 32)
		mc.textureManager.bindTexture(texture)
		this.drawTexturedModalRect(7, 7, 0, 0, 150, 38)
		this.drawTexturedModalRect(46, 15, 0, 44, exp, 3)
		this.drawTexturedModalRect(46, 27, 0, 38, hp, 3)
		this.drawTexturedModalRect(46, 39, 0, 41, sp, 3)
		this.drawString(mc.fontRenderer, "${TextFormatting.BOLD}${I18n.format(" Lv ${ player.getLevel() }    ${player.name}")}", 46, 4, Color.WHITE.rgb)
		this.drawString(mc.fontRenderer, "HP: ${player.health.toInt()} / ${player.maxHealth.toInt()}", 50, 18, 16716563)
		this.drawString(mc.fontRenderer, "SP: ${player.getSP()} / ${player.getMaxSP()}", 50, 30, 56576)
		GlStateManager.popMatrix()
	}
}