package items.skill

import enum.IItemRarity
import items.baseItem.ItemSkill
import net.minecraft.entity.ai.EntityAIBase
import net.minecraft.entity.item.EntityFallingBlock
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTestMaster: ItemSkill("code_test", 0, IItemRarity.MASTER, 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {

	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		val ray = player.rayTrace(10.0, 0F)?.blockPos!!
		val state = world.getBlockState(ray)
		val entity = object: EntityFallingBlock(world, ray.x.toDouble() + 0.5, ray.y.toDouble() + 0.05, ray.z.toDouble() + 0.5, state){
			override fun onUpdate() {
				super.onUpdate()
				if (this.onGround) {
					this.setDead()
				}
			}
		}
		entity.addVelocity(0.0, 0.2, 0.0)
		world.spawnEntity(entity)
		world.setBlockState(ray, state)
	}
}