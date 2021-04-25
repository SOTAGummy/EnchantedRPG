package module

import capability.sp.SPProvider
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.Item.getItemById
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraft.world.WorldServer

interface ISkillStorable{
	fun getSkillCapacity(): Int

	fun getItemSkill(stack: ItemStack, index: Int): ItemSkill?{
		return if (stack.tagCompound != null && stack.tagCompound?.getIntArray("skills")!!.isNotEmpty()){
			if (getItemById(stack.tagCompound?.getIntArray("skills")!![index]) is ItemSkill){
				getItemById(stack.tagCompound?.getIntArray("skills")!![index]) as ItemSkill
			} else {
				null
			}
		}else {
			null
		}
	}

	fun addItemSkill(stack: ItemStack, skill: ItemStack){
		if (canAddItemSkill(stack)){
			if (stack.tagCompound == null){
				stack.tagCompound = NBTTagCompound()
			}

			if (stack.tagCompound != null) {
				if (stack.tagCompound?.getIntArray("skills")?.isEmpty()!!){
					val array = IntArray(getSkillCapacity())
					array[0] = Item.getIdFromItem(skill.item)
					stack.tagCompound?.setIntArray("skills", array)
				} else {
					for(i in 0 until getSkillCapacity()){
						if (stack.tagCompound?.getIntArray("skills")!![i] == 0){
							val array = stack.tagCompound?.getIntArray("skills")!!
							array[i] = Item.getIdFromItem(skill.item)
							stack.tagCompound?.setIntArray("skills", array)
							break
						}
					}
				}
			}
		}
	}

	fun call(world: World, player: EntityPlayer, hand: EnumHand){
		if (world.isRemote){
			val clientThread = Minecraft.getMinecraft()
			val stack = player.heldItemMainhand
			GlobalScope.launch {
				repeat(getSkillCapacity()){
					if (getItemSkill(stack, it) != null && player.getCapability(SPProvider.MP!!, null)?.useSP(getItemSkill(stack, it)?.cost!!) == true){
						clientThread.addScheduledTask(){
							getItemSkill(stack, it)?.clientFunction(world, player, hand)
						}
						delay(1000)
					}
				}
			}
		} else {
			val serverThread = world as WorldServer
			val stack = player.heldItemMainhand
			GlobalScope.launch {
				repeat(getSkillCapacity()){
					if (getItemSkill(stack, it) != null && player.getCapability(SPProvider.MP!!, null)?.useSP(getItemSkill(stack, it)?.cost!!) == true){
						serverThread.addScheduledTask(){
							getItemSkill(stack, it)?.serverFunction(world, player, hand)
						}
						delay(1000)
					}
				}
			}
		}
	}

	fun addToolTip(stack: ItemStack, tooltip: MutableList<String>){
		var cost = 0
		for (i in 0 until getSkillCapacity()) {
			if (stack.tagCompound != null && stack.tagCompound!!.getIntArray("skills")[i] != 0) {
				val displayName = ItemStack(getItemById(stack.tagCompound!!.getIntArray("skills")[i])).displayName
				val format = I18n.format(displayName)
				val item = (getItemById(stack.tagCompound!!.getIntArray("skills")[i])) as ItemSkill
				val count = (i + 1).toString()

				cost += item.cost
				tooltip.add("$count : ${TextFormatting.UNDERLINE}$format")
			}
		}
		if (stack.tagCompound != null) {
			tooltip.add("")
			tooltip.add("${TextComponentTranslation("text.skill_cost").formattedText} : " + cost.toString() + "MP")
		}
	}

	fun canAddItemSkill(stack: ItemStack): Boolean{
		return getItemSkill(stack, getSkillCapacity() -1) == null
	}
}