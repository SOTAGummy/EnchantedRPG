package gui.accessory

import Core
import capability.accessory.AccessoryItemContainer
import capability.accessory.AccessoryProvider
import gui.accessory.slot.*
import items.baseItem.ItemAccessory
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.InventoryPlayer
import net.minecraft.inventory.*
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack


class AccessoryContainer(val player: EntityPlayer, private val customInventory: AccessoryItemContainer): Container() {
	val playerInv: InventoryPlayer = player.inventory
	val inventory = AccessoryItemContainer()
	private val craftMatrix = InventoryCrafting(this, 2, 2)
	private val craftResult = InventoryCraftResult()
	private val equipmentSlots = arrayOf(EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET)

	init {
		//CRAFTRESULTSLOT
		addSlotToContainer(SlotCrafting(playerInv.player, craftMatrix, craftResult, 0, 154, 28))

		//CRAFTMTRIXSLOT
		for (i in 0..1) {
			for (j in 0..1) {
				addSlotToContainer(Slot(craftMatrix, j + i * 2, 98 + j * 18, 18 + i * 18))
			}
		}

		//ARMORSLOT
		for (k in 0..3) {
			val slot: EntityEquipmentSlot = equipmentSlots[k]
			addSlotToContainer(object: Slot(playerInv, 36 + (3 - k), 8, 8 + k * 18) {
				override fun getSlotStackLimit(): Int {
					return 1
				}

				override fun isItemValid(stack: ItemStack): Boolean {
					return stack.item.isValidArmor(stack, slot, player)
				}

				override fun canTakeStack(playerIn: EntityPlayer): Boolean {
					val itemstack = this.stack
					return if (!itemstack.isEmpty && !playerIn.isCreative && EnchantmentHelper.hasBindingCurse(itemstack)) false else super.canTakeStack(
						playerIn
					)
				}

				override fun getSlotTexture(): String? {
					return ItemArmor.EMPTY_SLOT_NAMES[slot.index]
				}
			})
		}

		//INVENTORYSLOT
		for (i in 0..2) {
			for (j in 0..8) {
				addSlotToContainer(Slot(playerInv, i * 9 + j + 9, 8 + j * 18, 84 + i * 18))
			}
		}

		//HOTBARSLOT
		for (i in 0..8) {
			addSlotToContainer(Slot(playerInv, i, 8 + i * 18, 142))
		}

		//OFFHANDSLOT
		addSlotToContainer(object: Slot(playerInv, 40, 77, 62) {
			override fun isItemValid(stack: ItemStack): Boolean {
				return super.isItemValid(stack)
			}

			override fun getSlotTexture(): String {
				return "minecraft:items/empty_armor_slot_shield"
			}
		})

		this.onCraftMatrixChanged(craftMatrix)

		//ACCESSORYSLOT
		addSlotToContainer(NecklaceSlot(customInventory, 0, -10, 8))
		addSlotToContainer(AmuletSlot(customInventory, 1, -10, 26))
		addSlotToContainer(GloveSlot(customInventory, 2, -10, 44))
		addSlotToContainer(RingSlot(customInventory, 3, -10, 62))
	}

	override fun canInteractWith(playerIn: EntityPlayer): Boolean {
		return true
	}

	override fun onContainerClosed(player: EntityPlayer) {
		craftResult.clear()
		if (!player.world.isRemote) { // SERVER
			clearContainer(player, player.world, craftMatrix)
			repeat(4){
				player.getCapability(AccessoryProvider.ACCESSORY!!, null)?.setStackInSlot(it, customInventory.getStackInSlot(it))
			}
		}
		super.onContainerClosed(player)
	}

	override fun transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack {
		var itemstack = ItemStack.EMPTY
		val slot = inventorySlots[index]

		if (slot != null && slot.hasStack) {
			val itemstack1 = slot.stack
			itemstack = itemstack1.copy()
			val entityEquipmentSlot = EntityLiving.getSlotForItemStack(itemstack)

			//craftResult -> inv
			if (index == 0) {
				if (!this.mergeItemStack(itemstack1, 9, 45, true)) return ItemStack.EMPTY
				slot.onSlotChange(itemstack1, itemstack)
			}
			//craftMatrix -> inv
			else if (index in 1..4) {
				if (!this.mergeItemStack(itemstack1, 9, 45, false)) return ItemStack.EMPTY
			}
			//armor -> inv
			else if (index in 5..8) {
				if (!this.mergeItemStack(itemstack1, 9, 45, false)) return ItemStack.EMPTY
			}
			//inv -> armor
			else if (index in 9..44 && entityEquipmentSlot == EntityEquipmentSlot.HEAD && !inventorySlots[5].hasStack) {
				if (!this.mergeItemStack(itemstack1, 5, 6, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && entityEquipmentSlot == EntityEquipmentSlot.CHEST && !inventorySlots[6].hasStack) {
				if (!this.mergeItemStack(itemstack1, 6, 7, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && entityEquipmentSlot == EntityEquipmentSlot.LEGS && !inventorySlots[7].hasStack) {
				if (!this.mergeItemStack(itemstack1, 7, 8, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && entityEquipmentSlot == EntityEquipmentSlot.FEET && !inventorySlots[8].hasStack) {
				if (!this.mergeItemStack(itemstack1, 8, 9, false)) return ItemStack.EMPTY
			}
			//inv -> accessory
			else if (index in 9..44 && !inventorySlots[46].hasStack && inventorySlots[index].stack.item is ItemAccessory && (inventorySlots[index].stack.item as ItemAccessory).equipmentSlot == Core.NECKLACE) {
				if (!this.mergeItemStack(itemstack1, 46, 47, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && !inventorySlots[47].hasStack && inventorySlots[index].stack.item is ItemAccessory && (inventorySlots[index].stack.item as ItemAccessory).equipmentSlot == Core.AMULET) {
				if (!this.mergeItemStack(itemstack1, 47, 48, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && !inventorySlots[48].hasStack && inventorySlots[index].stack.item is ItemAccessory && (inventorySlots[index].stack.item as ItemAccessory).equipmentSlot == Core.GLOVE) {
				if (!this.mergeItemStack(itemstack1, 48, 49, false)) return ItemStack.EMPTY
			} else if (index in 9..44 && !inventorySlots[49].hasStack && inventorySlots[index].stack.item is ItemAccessory && (inventorySlots[index].stack.item as ItemAccessory).equipmentSlot == Core.RING) {
				if (!this.mergeItemStack(itemstack1, 49, 50, false)) return ItemStack.EMPTY
			}
			//inv -> offhand
			else if (index in 9..44 && entityEquipmentSlot == EntityEquipmentSlot.OFFHAND && !inventorySlots[44].hasStack) {
				if (!this.mergeItemStack(itemstack1, 45, 46, false)) return ItemStack.EMPTY
			}
			//offhand -> inv
			else if (index == 45) {
				if (!this.mergeItemStack(itemstack1, 9, 45, false)) return ItemStack.EMPTY
			}
			//accessory -> inv
			else if (index in 46..49) {
				if (!this.mergeItemStack(itemstack1, 9, 45, false)) return ItemStack.EMPTY
			}
			//inv -> hotbar
			else if (index in 9..34) {
				if (!this.mergeItemStack(itemstack1, 36, 45, false)) return ItemStack.EMPTY
			}
			//hotbar -> inv
			else if (index in 36..44) {
				if (!this.mergeItemStack(itemstack1, 9, 36, false)) return ItemStack.EMPTY
			}

			if (itemstack1.isEmpty) slot.putStack(ItemStack.EMPTY)
			else slot.onSlotChanged()

			if (itemstack1.count == itemstack.count) return ItemStack.EMPTY

			val itemstack2 = slot.onTake(playerIn, itemstack1)
			if (index == 0) playerIn.dropItem(itemstack2, false)
		}
		return itemstack
	}

	override fun canMergeSlot(stack: ItemStack, slot: Slot): Boolean {
		return slot.inventory !== craftResult && super.canMergeSlot(stack, slot)
	}
}