package items.baseItem

import com.google.common.collect.Multimap
import items.rootItem.RootItem
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.ai.attributes.AttributeModifier
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import utils.AccessoryAPI
import utils.Storage
import java.util.*

abstract class ItemAccessory(name: String, val equipmentSlot: EntityEquipmentSlot): RootItem(name){
	init{
		maxStackSize = 1
		creativeTab = Core.accessoriesTab
	}

	companion object {
		val ACCESSORY_MODIFIER = arrayListOf(
			UUID.fromString("1ee3ca10-3da2-4d4a-b15c-b4c62dca1f8f"),
			UUID.fromString("a139bb38-cd04-481a-b4b8-f55da85be43b"),
			UUID.fromString("ca10532a-fdf9-4292-b7ba-5ef20e252946"),
			UUID.fromString("e28a417d-5e63-4b9e-918f-1df81a6ac257")
		)
	}

	fun getUUID(slot: EntityEquipmentSlot): UUID? {
		return ACCESSORY_MODIFIER[slot.index]
	}

	override fun onItemRightClick(world: World, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
		AccessoryAPI.setAccessory(player, equipmentSlot, player.heldItemMainhand)
		return super.onItemRightClick(world, player, hand)
	}

	override fun getAttributeModifiers(slot: EntityEquipmentSlot, stack: ItemStack): Multimap<String, AttributeModifier> {
		val multimap = super.getAttributeModifiers(slot, stack)

		if (slot == (stack.item as ItemAccessory).equipmentSlot) {
			if (stack.isItemEnchanted) {
				repeat(Storage.Enchantments.size) {
					val enchantment = Storage.Enchantments[it]
					val level = EnchantmentHelper.getEnchantmentLevel(Storage.Enchantments[it], stack)
					if (level > 0) {
						if (!enchantment.getAttributes(stack, level).isEmpty) {
							multimap.putAll(Storage.Enchantments[it].getAttributes(stack, level))
						}
					}
				}
			}
		}
		return multimap
	}


	fun onEquipped(stack: ItemStack, player: EntityPlayer, world: World){}
}