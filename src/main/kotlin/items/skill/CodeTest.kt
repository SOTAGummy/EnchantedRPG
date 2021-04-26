package items.skill

import Core
import items.baseItem.ItemSkill
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object CodeTest: ItemSkill("code_test", 0){
	override fun clientFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
	}

	override fun serverFunction(world: World, player: EntityPlayer, handIn: EnumHand) {
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_necklace).getAttributeModifiers(Core.NECKLACE))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_amulet).getAttributeModifiers(Core.AMULET))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_glove).getAttributeModifiers(Core.GLOVE))
		player.attributeMap.removeAttributeModifiers(ItemStack(Core.test_ring).getAttributeModifiers(Core.RING))

	}
}