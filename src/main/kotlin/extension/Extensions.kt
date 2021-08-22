package extension

import capability.sp.SPProvider
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import module.ISkillStorable
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraft.world.WorldServer

fun ItemStack.getItemSkill(index: Int): ItemSkill?{
	if (this.tagCompound == null){
		this.tagCompound = NBTTagCompound()
	}
	return if (this.tagCompound != null && this.tagCompound?.getIntArray("skills")!!.isNotEmpty() && this.item is ISkillStorable){
		if (Item.getItemById(this.tagCompound?.getIntArray("skills")!![index]) is ItemSkill){
			Item.getItemById(this.tagCompound?.getIntArray("skills")!![index]) as ItemSkill
		} else {
			null
		}
	}else {
		null
	}
}

fun ItemStack.addItemSkill(skill: ItemStack){
	if (this.item is ISkillStorable){
		if (canAddItemSkill()){
			if (this.tagCompound == null){
				this.tagCompound = NBTTagCompound()
			}

			if (this.tagCompound != null) {
				if (this.tagCompound?.getIntArray("skills")?.isEmpty()!!){
					val array = IntArray(getSkillCapacity())
					array[0] = Item.getIdFromItem(skill.item)
					this.tagCompound?.setIntArray("skills", array)
				} else {
					for(i in 0 until getSkillCapacity()){
						if (this.tagCompound?.getIntArray("skills")!![i] == 0){
							val array = this.tagCompound?.getIntArray("skills")!!
							array[i] = Item.getIdFromItem(skill.item)
							this.tagCompound?.setIntArray("skills", array)
							break
						}
					}
				}
			}
		}
	}
}

fun ItemStack.canAddItemSkill(): Boolean{
	return if (this.item is ISkillStorable){
		getItemSkill(getSkillCapacity() -1) == null
	} else {
		false
	}
}

fun ItemStack.getSkillCapacity(): Int{
	return if (this.item is ISkillStorable){
		(this.item as ISkillStorable).getSkillCapacity()
	} else {
		0
	}
}

fun ItemStack.call(world: World, player: EntityPlayer, hand: EnumHand){
	if (this.tagCompound != null){
		if (world.isRemote){
			val clientThread = Minecraft.getMinecraft()
			GlobalScope.launch {
				repeat(getSkillCapacity()){
					if ((getItemSkill(it) != null && player.getCapability(SPProvider.SP!!, null)?.useSP(getItemSkill(it)?.cost!!) == true) || player.isCreative){
						clientThread.addScheduledTask(){
							getItemSkill(it)?.clientFunction(world, player, hand)
						}
						delay(1000)
					}
				}
			}
		} else {
			val serverThread = world as WorldServer
			GlobalScope.launch {
				repeat(getSkillCapacity()){
					if (getItemSkill(it) != null && player.getCapability(SPProvider.SP!!, null)?.useSP(getItemSkill(it)?.cost!!) == true){
						serverThread.addScheduledTask(){
							getItemSkill(it)?.serverFunction(world, player, hand)
						}
						delay(1000)
					}
				}
			}
		}
	}
}

fun ItemStack.getSkillCount(): Int{
	var count = 0
	if (this.item is ISkillStorable && this.tagCompound != null){
		repeat(this.getSkillCapacity()){
			if (this.tagCompound!!.getIntArray("skills")[it] != 0){
				count++
			}
		}
	}
	return count
}

fun ItemStack.removeSkill(): ItemStack{
	return if (this.getSkillCount() != 0){
		val stack = ItemStack(Item.getItemById(this.tagCompound!!.getIntArray("skills")[this.getSkillCount() - 1]))
		val array = this.tagCompound!!.getIntArray("skills").clone()
		array[0] = 0
		repeat(this.getSkillCapacity() - 1){
			array[it] = array[it + 1]
		}
		array[this.getSkillCapacity() - 1] = 0
		println(array)
		this.tagCompound!!.setIntArray("skills", array)
		stack
	} else {
		ItemStack.EMPTY
	}
}