package gui.mp

import capability.sp.SPProvider
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.util.ResourceLocation
import java.awt.Color

class MPIndicator(mc: Minecraft, correction: Int): Gui() {
	companion object {
		@JvmStatic
		private val texture = ResourceLocation(Core.ID + ":textures/gui/mp_bar.png")
	}

	init {
		val player = mc.player
		val scaled = ScaledResolution(mc)
		val width = scaled.scaledWidth
		val sp = player.getCapability(SPProvider.SP!!, null)?.getSP()!!
		val maxSP = player.getEntityAttribute(Core.MAX_SP).attributeValue.toInt()
		val height = scaled.scaledHeight
		val hp = (mc.player.health + 0.999999).toInt().toString() + "/" + mc.player.maxHealth.toInt().toString()
		val currentMP = ((sp.toFloat() / maxSP.toFloat()) * 81).toInt()
		val mp = "$sp/$maxSP"

		mc.textureManager.bindTexture(texture)
		this.drawTexturedModalRect(width / 2 + 10, height - 48 + correction, 0, 0, 81, 8)
		this.drawTexturedModalRect(width / 2 + 11, height - 47 + correction, 0, 8, currentMP, 8)
		mc.ingameGUI.drawCenteredString(mc.fontRenderer, hp, width / 2 - 48, height - 40, Color.WHITE.rgb)
		mc.ingameGUI.drawCenteredString(mc.fontRenderer, mp, width / 2 + 50, height - 48, Color.WHITE.rgb)
	}
}