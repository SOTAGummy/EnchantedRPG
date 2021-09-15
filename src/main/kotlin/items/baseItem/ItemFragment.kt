package items.baseItem

import enum.IItemRarity
import net.minecraft.item.ItemStack
import utils.Storage
import java.io.File

abstract class ItemFragment(name: String, rarity: IItemRarity): RootItem("${name}_fragment", rarity){
	init {
		creativeTab = Core.itemsTab
		Storage.Items.add(this)

		val helmet = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_helmet.json")
		if (!helmet.exists()) {
			helmet.createNewFile()
			helmet.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \"XXX\",\n" +
					"    \"X X\",\n" +
					"    \"   \"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_helmet\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val chestplate =  File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_chestplate.json")
		if (!chestplate.exists()) {
			chestplate.createNewFile()
			chestplate.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \"X X\",\n" +
					"    \"XXX\",\n" +
					"    \"XXX\"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_chestplate\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val leggings = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_leggings.json")
		if (!leggings.exists()) {
			leggings.createNewFile()
			leggings.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \"XXX\",\n" +
					"    \"X X\",\n" +
					"    \"X X\"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_leggings\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val boots = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_boots.json")
		if (!boots.exists()) {
			boots.createNewFile()
			boots.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \"   \",\n" +
					"    \"X X\",\n" +
					"    \"X X\"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_boots\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val necklace = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_necklace.json")
		if (!necklace.exists()) {
			necklace.createNewFile()
			necklace.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \" Y \",\n" +
					"    \"Y Y\",\n" +
					"    \" X \"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    },\n" +
					"    \"Y\": {\n" +
					"      \"item\": \"minecraft:string\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_necklace\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val amulet = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_amulet.json")
		if (!amulet.exists()) {
			amulet.createNewFile()
			amulet.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \" X \",\n" +
					"    \"XXX\",\n" +
					"    \"XXX\"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_amulet\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val glove = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_glove.json")
		if (!glove.exists()) {
			glove.createNewFile()
			glove.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \"XXX\",\n" +
					"    \"X X\",\n" +
					"    \"XXX\"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_glove\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
		val ring = File("D:\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\recipes\\${name}_ring.json")
		if (!ring.exists()) {
			ring.createNewFile()
			ring.writeText("{\n" +
					"  \"type\": \"minecraft:crafting_shaped\",\n" +
					"  \"pattern\": [\n" +
					"    \" X \",\n" +
					"    \"X X\",\n" +
					"    \" X \"\n" +
					"  ],\n" +
					"  \"key\": {\n" +
					"    \"X\": {\n" +
					"      \"item\": \"enchanted-rpg:${name}_fragment\",\n" +
					"      \"data\": 0\n" +
					"    }\n" +
					"  },\n" +
					"  \"result\": {\n" +
					"    \"item\": \"enchanted-rpg:${name}_ring\",\n" +
					"    \"data\": 0,\n" +
					"    \"count\": 1\n" +
					"  }\n" +
					"}")
		}
	}
}
