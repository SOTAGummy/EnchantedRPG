package module

import items.baseItem.ItemSkill
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemHandlerHelper

interface ISkillStorable: IItemHandler{
	fun callSkills(world: World, player: EntityPlayer, handIn: EnumHand){
		if (world.isRemote) { //CLIENT
			val clientThread = Minecraft.getMinecraft()
		}else{ // SERVER
			if (world is WorldServer) {
				var func = Runnable(){}
				repeat(this.slots * 20){
					if (it % 20 == 0){
						val temp = Runnable {
							world.addScheduledTask(){
								func
								(this.getStackInSlot(it % 20) as ItemSkill).serverFunction(world, player, handIn)
							}
						}

						func = temp
					}else{
						val temp = Runnable {
							world.addScheduledTask(){
								func
							}
						}

						func = temp
					}
				}
			}
		}
	}

	fun addSkillInfo(tooltip: MutableList<String>){

	}

	override fun isItemValid(slot: Int, stack: ItemStack): Boolean {
		return stack.item is ItemSkill
	}

	override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
		TODO("Not yet implemented")
	}
}