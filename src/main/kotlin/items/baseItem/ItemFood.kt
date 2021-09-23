package items.baseItem

import enum.IItemRarity
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemFood
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage
import java.io.File

open class ItemFood(amount: Int, saturation: Float, isWolfFood: Boolean, val name: String, val rarity: IItemRarity): ItemFood(amount, saturation, isWolfFood){
	init {
		Storage.Items.add(this)
		registryName = ResourceLocation(Core.ID, name)
		unlocalizedName = name
		creativeTab = Core.itemsTab

		val file = File("C:\\Users\\gummy\\OneDrive\\デスクトップ\\mod\\EnchantedRPG\\src\\main\\resources\\assets\\enchanted-rpg\\models\\item\\$name.json")
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

	override fun getItemStackDisplayName(stack: ItemStack): String {
		return "${rarity.colorChar}${I18n.format(super.getItemStackDisplayName(stack))}"
	}
}