package extension

import net.minecraft.item.ItemStack

fun ItemStack.isEqualStack(stack: ItemStack): Boolean {
    return this.item == stack.item && this.count == stack.count && this.metadata == stack.metadata && this.itemDamage == stack.itemDamage && this.displayName == stack.displayName && this.enchantmentTagList.equals(stack.enchantmentTagList)
}