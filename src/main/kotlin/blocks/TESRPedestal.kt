package blocks

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraftforge.client.ForgeHooksClient
import org.lwjgl.opengl.GL11
import kotlin.math.sin

class TESRPedestal: TileEntitySpecialRenderer<TileEntityPedestal>() {
	override fun render(te: TileEntityPedestal, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int, alpha: Float) {
		val stack = te.inventory.getStackInSlot(0)
		if (!stack.isEmpty) {
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
		}
	}
}