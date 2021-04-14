package items.baseItem

import Core
import items.rootItem.RootItem
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.util.text.TextFormatting
import net.minecraft.world.World
import java.io.File

abstract class ItemSkill(name: String, val cost: Int): RootItem(name){
	init {
		registryName = ResourceLocation(Core.ID, name)
		unlocalizedName = name
		creativeTab = Core.itemsTab
		maxStackSize = 1

		val file = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\models\\item\\$name.json")
		if (!file.exists()) {
			file.createNewFile()
			file.writeText("{\n" +
					"  \"parent\": \"item/generated\",\n" +
					"  \"textures\": {\n" +
					"\t\"layer0\": \"enchanted-rpg:items/skill_gem\"\n" +
					"  }\n" +
					"}")
		}
	}

	override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
		val costFormat = I18n.format(cost.toString())
		tooltip.add("${TextComponentTranslation("text.skill_cost").formattedText}: ${TextFormatting.BOLD}$costFormat")
	}

	override fun hasEffect(stack: ItemStack): Boolean {
			return true
	}

	abstract fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand)

	abstract fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand)
}