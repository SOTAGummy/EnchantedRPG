package gui.skillList

import Core
import extension.getItemSkill
import extension.getSkillCount
import module.ISkillStorable
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.ScaledResolution
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import java.awt.Color

class SkillListIndicator(mc: Minecraft): Gui(){
	companion object {
		@JvmStatic
		private val texture = ResourceLocation(Core.ID + ":textures/gui/skill_list.png")
	}

	init {
		val player = mc.player
		val scaled = ScaledResolution(mc)
		val height = scaled.scaledHeight
		mc.textureManager.bindTexture(texture)
		if (player.heldItemMainhand.item is ISkillStorable){
			val count = player.heldItemMainhand.getSkillCount()
			if (count != 0){
				this.drawTexturedModalRect(10, height - count * 10 - 10, 0, 0, 125, count * 10 + 1)
				repeat(count){
					if (player.heldItemMainhand.getItemSkill(it) != null){
						this.drawString(mc.fontRenderer, (count - it).toString(), 14, height - 9 - 10 * (it + 1), Color.BLACK.rgb)
						this.drawString(mc.fontRenderer, ItemStack(player.heldItemMainhand.getItemSkill(count - it - 1)!!).displayName, 23, height - 9 - 10 * (it + 1), player.heldItemMainhand.getItemSkill(count - it - 1)!!.rarity.rgb)
					}
				}
			}
		}
	}
}