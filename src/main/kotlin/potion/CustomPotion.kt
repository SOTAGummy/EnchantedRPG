package potion

import net.minecraft.client.Minecraft
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.potion.Potion
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import utils.Storage

abstract class CustomPotion(name: String, isBad: Boolean, color: Int): Potion(isBad, color){
	init {
		this.registryName = ResourceLocation(Core.ID, name)
		this.setIconIndex(0, 0)
		this.setPotionName(name)
		Storage.Potions.add(this)
	}

	override fun getStatusIconIndex(): Int {
		Minecraft.getMinecraft().textureManager.bindTexture(ResourceLocation(Core.ID, "textures/potion/${name}.png"))
		return super.getStatusIconIndex()
	}

	override fun isReady(duration: Int, amplifier: Int): Boolean {
		return true
	}

	open fun onAttacked(source: DamageSource, attacked: EntityLivingBase, amount: Float, amplifier: Int){

	}

	open fun onAttack(source: DamageSource, attacked: EntityLivingBase, amount: Float, amplifier: Int){

	}
}