package items.baseItem

import enum.IItemRarity
import net.minecraft.client.resources.I18n
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage
import java.io.File
import java.util.*

abstract class RootItem(name: String, val rarity: IItemRarity): Item(){
	init {
		Storage.Items.add(this)
		registryName = ResourceLocation(Core.ID, name)
		unlocalizedName = name
		creativeTab = Core.itemsTab

		val file = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\models\\item\\$name.json")
		if (!file.exists() && this !is ItemSkill) {
			file.createNewFile()
			file.writeText("{\n" +
					"  \"parent\": \"item/generated\",\n" +
					"  \"textures\": {\n" +
					"\t\"layer0\": \"enchanted-rpg:items/$name\"\n" +
					"  }\n" +
					"}")
		} else if (!file.exists() && this is ItemSkill){
			file.createNewFile()
			val item = this as ItemSkill
			file.writeText("{\n" +
					"  \"parent\": \"item/generated\",\n" +
					"  \"textures\": {\n" +
					"\t\"layer0\": \"enchanted-rpg:items/skill_gem_${item.rarity.name.toLowerCase()}\"\n" +
					"  }\n" +
					"}")
		}

	}

	companion object{
		val SKILL_CONTAINER_MODIFIER: UUID = UUID.fromString("a5abf643-c02d-48a3-880d-445a034179cc")
	}

	override fun getItemStackDisplayName(stack: ItemStack): String {
		return "${rarity.colorChar}${I18n.format(super.getItemStackDisplayName(stack))}"
	}
}