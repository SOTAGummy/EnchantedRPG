package items.skill

import capability.accessory.AccessoryProvider
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import packet.PacketAccessory
import packet.PacketHandler

object CodeTest: ItemSkill("code_test", 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		println("CLIENT")
		println(player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getItem(3))
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_necklace).getAttributeModifiers(Core.NECKLACE))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_amulet).getAttributeModifiers(Core.AMULET))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_glove).getAttributeModifiers(Core.GLOVE))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_ring).getAttributeModifiers(Core.RING))

		println("SERVER")
		println(player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.getItem(3))
	}
}