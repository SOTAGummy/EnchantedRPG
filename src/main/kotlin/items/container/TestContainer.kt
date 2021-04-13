package items.container

import Core
import items.rootItem.RootItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import module.ISkillStorable
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.world.WorldServer

object TestContainer: RootItem("test_container"), ISkillStorable{
	init {
		registryName = ResourceLocation(Core.ID, "test_container")
		unlocalizedName = "test_container"
		maxStackSize = 1
		creativeTab = Core.itemsTab
	}

	override fun getSkillCapacity(): Int {
		return 2
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		call(world, player, hand)
		if (!world.isRemote){
			val thread = world as WorldServer
			thread.addScheduledTask(){
				world.createExplosion(player, player.posX, player.posY, player.posZ, 1F, false)
				GlobalScope.launch {
					delay(1000)
					thread.addScheduledTask(){
						repeat(20){
							val arrow = EntityItem(world, player.posX, player.posY, player.posZ, ItemStack(Core.enchanted_dust))
							world.spawnEntity(arrow)
						}
					}
				}
			}
		}
		return super.onItemRightClick(world, player, hand)
	}
}