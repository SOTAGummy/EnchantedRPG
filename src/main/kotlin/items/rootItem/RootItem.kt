package items.rootItem

import net.minecraft.item.Item
import utils.Storage
import java.io.File

abstract class RootItem(name: String): Item(){
	init {
		Storage.Items.add(this)

		val file = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\models\\item\\$name.json")
		if (!file.exists()) {
			file.createNewFile()
			file.writeText("{\n" +
					"  \"parent\": \"item/generated\",\n" +
					"  \"textures\": {\n" +
					"\t\"layer0\": \"enchanted-rpg:items/$name\"\n" +
					"  }\n" +
					"}")
		}
	}
}