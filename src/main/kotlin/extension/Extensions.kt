package extension

import capability.sp.SPProvider
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import module.ISkillStorable
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import kotlin.math.abs
import kotlin.random.Random

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
		return if ((this.item as ISkillStorable).getSkillCapacity() <= 8){
			(this.item as ISkillStorable).getSkillCapacity()
		} else {
			8
		}
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
					if ((getItemSkill(it) != null && getItemSkill(it)!!.canCall(world, player, hand) && player.useSP(getItemSkill(it)?.cost!!)) || player.isCreative){
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
					if ((getItemSkill(it) != null && getItemSkill(it)!!.canCall(world, player, hand) && player.useSP(getItemSkill(it)?.cost!!)) || player.isCreative){
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
		array[this.getSkillCount() - 1] = 0
		this.tagCompound!!.setIntArray("skills", array)
		stack
	} else {
		ItemStack.EMPTY
	}
}

fun EntityPlayer.useSP(amount: Int): Boolean{
	val consume = amount * ((-1 * this.getEntityAttribute(Core.SP_SAVING_RATE).attributeValue + 100) / 100)
	return this.getCapability(SPProvider.SP!!, null)!!.useSP(consume.toInt())
}

fun EntityLivingBase.renderDamage(damage: Int, color: TextFormatting){
	val randX = Random.nextDouble(1.5)
	val randZ = Random.nextDouble(1.5)
	val renderDamage = object: EntityArmorStand(this.world, this.posX + randX, this.posY, this.posZ + randZ){
		var time = 0

		override fun onUpdate() {
			time++
			if (time == 20){
				this.setDead()
			}
		}
	}
	renderDamage.isInvisible = true
	renderDamage.customNameTag = "${TextFormatting.BOLD}${color}${I18n.format((damage.toString()))}${TextComponentTranslation("text.damage").formattedText}"
	renderDamage.alwaysRenderNameTag = true
	this.world.spawnEntity(renderDamage)
}


fun World.getLivingEntitiesInArea(pos: BlockPos, distance: Int): ArrayList<EntityLiving>{
	val array = arrayListOf<EntityLiving>()
	val entities = this.loadedEntityList
	repeat(entities.size){
		if (entities[it] is EntityLiving && entities[it].getDistance(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) <= distance && entities[it] !is EntityPlayer){
			array.add(entities[it] as EntityLiving)
		}
	}
	array.sortBy { it.getDistance(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()) }
	return array
}

fun <T : Any?> ArrayList<T>.times(time: Int): ArrayList<T>{
	repeat(time - 1){
		this.addAll(this)
	}
	return this
}