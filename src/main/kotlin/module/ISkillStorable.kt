package module

import items.baseItem.ItemSkill
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer

interface ISkillStorable{
	fun getSkillCapacity(): Int

	fun getItemSkill(stack: ItemStack, index: Int): ItemSkill?{
		return if (stack.tagCompound != null && stack.tagCompound?.getTag("1") != null){
			ItemStack(stack.tagCompound?.getTag("$index") as NBTTagCompound).item as ItemSkill
		}else {
			null
		}
	}

	fun addItemSkill(stack: ItemStack, skill: ItemStack){
		if (stack.tagCompound == null){
			stack.tagCompound = NBTTagCompound()
		} else {
			if (stack.tagCompound!!.getTag("1") == null){
				stack.tagCompound?.setTag("1", skill.serializeNBT())
			} else {
				repeat(getSkillCapacity()){
					if (stack.tagCompound?.getTag("${it + 2}") == null){
						stack.tagCompound?.setTag("${it + 2}", skill.serializeNBT())
					}
				}
			}
		}
	}

	fun call(world: World, player: EntityPlayer, hand: EnumHand){
		if (world.isRemote){
			val clientThread = Minecraft.getMinecraft()
			var func = Runnable(){}
			repeat(getSkillCapacity() * 20){
				if (it % 20 == 0){
					val temp = Runnable(){
						clientThread.addScheduledTask(){
							func.run()
							getItemSkill(player.getHeldItem(hand), it / 20 + 1)?.serverFunction(world, player, hand)
						}
					}

					func = temp
				} else {
					val temp = Runnable(){
						clientThread.addScheduledTask(){
							func.run()
						}
					}

					func = temp
				}
			}
			clientThread.addScheduledTask(){
				func
			}
		} else {
			val serverThread = world as WorldServer
			var func = Runnable(){}
			repeat(getSkillCapacity() * 20){
				if (it % 20 == 0){
					val temp = Runnable(){
						serverThread.addScheduledTask(){
							func.run()
							getItemSkill(player.getHeldItem(hand), it / 20 + 1)?.serverFunction(world, player, hand)
						}
					}

					func = temp
				} else {
					val temp = Runnable(){
						serverThread.addScheduledTask(){
							func.run()
						}
					}

					func = temp
				}
			}
			serverThread.addScheduledTask(){
				func
			}
		}
	}
}