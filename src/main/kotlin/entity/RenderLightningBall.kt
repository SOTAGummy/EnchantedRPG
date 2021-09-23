package entity

import net.minecraft.client.renderer.RenderItem
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.client.renderer.entity.RenderSnowball
import net.minecraft.entity.Entity
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation

class RenderLightningBall(renderManager: RenderManager, item: Item, itemRender: RenderItem): RenderSnowball<Entity>(renderManager, item, itemRender){
	override fun getEntityTexture(entity: Entity): ResourceLocation {
		return ResourceLocation(Core.ID, "textures/entity/lightning_ball.png")
	}
}