package items.baseItem

import Core
import enum.IItemRarity
import net.minecraft.client.resources.I18n
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import utils.Storage
import java.io.File
import java.util.*

abstract class ItemArmor(name: String, material: ArmorMaterial, slot: EntityEquipmentSlot, val rarity: IItemRarity): ItemArmor(material, 0, slot) {
	init {
		this.maxStackSize = 1
		this.unlocalizedName = name
		this.registryName = ResourceLocation(Core.ID, name)
		this.creativeTab = Core.itemsTab
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

	companion object {
		val ARMOR_MODIFIERS = arrayOf(
				UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
				UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
				UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
				UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
		)
	}

	override fun getItemStackDisplayName(stack: ItemStack): String {
		return "${rarity.colorChar}${I18n.format(super.getItemStackDisplayName(stack))}"
	}
}