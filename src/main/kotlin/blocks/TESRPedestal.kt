package blocks

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.item.ItemStack
import net.minecraftforge.client.ForgeHooksClient
import org.lwjgl.opengl.GL11
import kotlin.math.cos
import kotlin.math.sin

class TESRPedestal: TileEntitySpecialRenderer<TileEntityPedestal>() {
	override fun render(te: TileEntityPedestal, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
		val items = arrayListOf<ItemStack>()
		repeat(8){
			val stack = te.inventory.getStackInSlot(it)
			if (!stack.isEmpty){
				items.add(stack)
			}
		}

		var craftingTime: Long = 0

		if (te.isCrafting() && craftingTime == (0.toLong())){
			craftingTime = te.world.totalWorldTime
		}

		if (items.size == 1) {
			val stack = items[0]
			GlStateManager.enableRescaleNormal()
			GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f)
			GlStateManager.enableBlend()
			RenderHelper.enableStandardItemLighting()
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
			GlStateManager.pushMatrix()
			val offset = sin(((te.world.totalWorldTime - te.getLastChangeTime() + partialTicks) / 8).toDouble()) / 6.0
			GlStateManager.translate(x + 0.5, y + 1.2 + offset, z + 0.5)
			GlStateManager.rotate((te.world.totalWorldTime + partialTicks) * 4, 0f, 1f, 0f)
			var model = Minecraft.getMinecraft().renderItem.getItemModelWithOverrides(stack, te.world, null)
			model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false)
			Minecraft.getMinecraft().textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
			Minecraft.getMinecraft().renderItem.renderItem(stack, model)
			GlStateManager.popMatrix()
			GlStateManager.disableRescaleNormal()
			GlStateManager.disableBlend()
		} else {
			for (i in 0 until items.size){
				val stack = items[i]
				var phaseX: Double
				var phaseZ: Double
				if (craftingTime.compareTo(0) == 0){
					phaseX = sin(2 * Math.PI * (i.toDouble() / items.size))
					phaseZ = cos(2 * Math.PI * (i.toDouble() / items.size))
				} else {
					val correct = (te.getLastChangeTime() - craftingTime).toDouble()
					phaseX = sin((2 * Math.PI * (i.toDouble() / items.size)) + correct * Math.PI / 10)
					phaseZ = cos((2 * Math.PI * (i.toDouble() / items.size)) + correct * Math.PI / 10)
				}
				GlStateManager.enableRescaleNormal()
				GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f)
				GlStateManager.enableBlend()
				RenderHelper.enableStandardItemLighting()
				GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0)
				GlStateManager.pushMatrix()
				val offset = sin(((te.world.totalWorldTime - te.getLastChangeTime() + partialTicks) / 8).toDouble()) / 6.0
				GlStateManager.translate(x + 0.5 + phaseX, y + 1.2 + offset, z + 0.5 + phaseZ)
				GlStateManager.rotate((te.world.totalWorldTime + partialTicks) * 4, 0f, 1f, 0f)
				var model = Minecraft.getMinecraft().renderItem.getItemModelWithOverrides(stack, te.world, null)
				model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false)
				Minecraft.getMinecraft().textureManager.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE)
				Minecraft.getMinecraft().renderItem.renderItem(stack, model)
				GlStateManager.popMatrix()
				GlStateManager.disableRescaleNormal()
				GlStateManager.disableBlend()
			}
		}
	}
}