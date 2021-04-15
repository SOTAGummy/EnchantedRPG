package items.rootItem

import items.baseItem.ItemSkill
import net.minecraft.item.Item
import utils.Storage
import java.io.File

abstract class RootItem(name: String): Item(){
	init {
		Storage.Items.add(this)

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
			file.writeText("{\n" +
					"  \"parent\": \"item/generated\",\n" +
					"  \"textures\": {\n" +
					"\t\"layer0\": \"enchanted-rpg:items/skill_gem\"\n" +
					"  }\n" +
					"}")
		}
	}
}