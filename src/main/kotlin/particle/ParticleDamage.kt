package particle

import net.minecraft.client.Minecraft
import net.minecraft.client.particle.Particle
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.OpenGlHelper
import net.minecraft.entity.Entity
import net.minecraft.util.DamageSource
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import org.lwjgl.opengl.GL11
import java.awt.Color
import kotlin.math.abs


@SideOnly(Side.CLIENT)
class ParticleDamage(val damage: Int, val source: DamageSource, world: World, parX: Double, parY: Double, parZ: Double, parMotionX: Double, parMotionY: Double, parMotionZ: Double) : Particle(world, parX, parY, parZ, parMotionX, parMotionY, parMotionZ) {
	private var text: String
	private var shouldOnTop = true
	private var grow = true
	private var scale = 1.0f

	companion object {
		private const val GRAVITY = 0.1f
		private const val SIZE = 3.0f
		private const val LIFESPAN = 12
	}

	init {
		particleTextureJitterX = 0.0f
		particleTextureJitterY = 0.0f
		particleGravity = GRAVITY
		particleScale = SIZE
		particleMaxAge = LIFESPAN
		text = damage.toString()
	}

	private constructor(source: DamageSource, world: World, posX: Double, posY: Double, posZ: Double) : this(0, source, world, posX, posY, posZ, 0.0, 0.0, 0.0)

	override fun renderParticle(renderer: BufferBuilder?, entity: Entity?, x: Float, y: Float, z: Float, dX: Float, dY: Float, dZ: Float) {
		val rotationYaw = -Minecraft.getMinecraft().player.rotationYaw
		val rotationPitch = Minecraft.getMinecraft().player.rotationPitch
		val locX = (this.prevPosX + (this.posX - this.prevPosX) * x - interpPosX).toFloat()
		val locY = (this.prevPosY + (this.posY - this.prevPosY) * y - interpPosY).toFloat()
		val locZ = (this.prevPosZ + (this.posZ - this.prevPosZ) * z - interpPosZ).toFloat()
		GL11.glPushMatrix()
		if (shouldOnTop) {
			GL11.glDepthFunc(519)
		} else {
			GL11.glDepthFunc(515)
		}
		GL11.glTranslatef(locX, locY, locZ)
		GL11.glRotatef(rotationYaw, 0.0f, 1.0f, 0.0f)
		GL11.glRotatef(rotationPitch, 1.0f, 0.0f, 0.0f)
		GL11.glScalef(-1.0f, -1.0f, 1.0f)
		GL11.glScaled(this.particleScale * 0.008, this.particleScale * 0.008, this.particleScale * 0.008)
		GL11.glScaled(scale.toDouble(), scale.toDouble(), scale.toDouble())
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 0.003662109f)
		GL11.glEnable(3553)
		GL11.glDisable(3042)
		GL11.glDepthMask(true)
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
		GL11.glEnable(3553)
		GL11.glEnable(2929)
		GL11.glDisable(2896)
		GL11.glBlendFunc(770, 771)
		GL11.glEnable(3042)
		GL11.glEnable(3008)
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
		val color: Int = if (source.isFireDamage) {
			16711680
		} else if (source.damageType == "water") {
			2052331
		} else if (source.damageType == "ice") {
			2154733
		} else if (source.damageType == "wind") {
			244866
		} else if (source.damageType == "lightning") {
			10042327
		} else if (source.damageType == "earthen") {
			11699456
		} else if (source.damageType == "critical") {
			16766976
		} else {
			Color.WHITE.rgb
		}
		val fontRenderer = Minecraft.getMinecraft().fontRenderer
		fontRenderer.drawStringWithShadow(text, (
				-MathHelper.floor(fontRenderer.getStringWidth(text) / 2.0f) + 1).toFloat(), (-MathHelper.floor(fontRenderer.FONT_HEIGHT / 2.0f) + 1).toFloat(), color)
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
		GL11.glDepthFunc(515)
		GL11.glPopMatrix()
		if (grow) {
			this.particleScale *= 1.08f
			if (this.particleScale > SIZE * 3.0) {
				grow = false
			}
		} else {
			this.particleScale *= 0.96f
		}
	}

	override fun getFXLayer(): Int {
		return 3
	}

}