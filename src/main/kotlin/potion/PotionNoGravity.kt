package potion

import Core
import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.potion.Potion
import net.minecraft.util.ResourceLocation

class PotionNoGravity: Potion(false, 1390139){
	init {
		this.registryName = ResourceLocation(Core.ID, "noGravity")
		this.setIconIndex(0, 0)
		this.setPotionName("noGravity")
		this.setBeneficial()
	}

	override fun getStatusIconIndex(): Int {
		Minecraft.getMinecraft().textureManager.bindTexture(ResourceLocation(Core.ID, "textures/potion/no_gravity.png"))
		return super.getStatusIconIndex()
	}

	override fun performEffect(entityLivingBaseIn: EntityLivingBase, amplifier: Int) {
		entityLivingBaseIn.setNoGravity(true)
		super.performEffect(entityLivingBaseIn, amplifier)
	}

	override fun affectEntity(source: Entity?, indirectSource: Entity?, entityLivingBaseIn: EntityLivingBase, amplifier: Int, health: Double) {
		println(source)
		println(indirectSource)
		println(entityLivingBaseIn)
		super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health)
	}
}