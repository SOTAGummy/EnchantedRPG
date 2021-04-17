package blocks

import Core
import items.baseItem.ItemSkill
import module.ISkillStorable
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import java.util.*

object BlockPedestal: BlockContainer(Material.ROCK){
	init {
		this.setCreativeTab(Core.itemsTab)
		registryName = ResourceLocation(Core.ID, "pedestal")
		unlocalizedName = "pedestal"
		blockHardness = 1F
		blockResistance = 100F
		fullBlock = false
	}

	override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity {
		return TileEntityPedestal()
	}

	override fun getRenderType(state: IBlockState): EnumBlockRenderType {
		return EnumBlockRenderType.MODEL
	}

	override fun isOpaqueCube(state: IBlockState): Boolean {
		return false
	}

	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if (!world.isRemote){
			val tile = world.getTileEntity(pos) as TileEntityPedestal
			val flagN = world.getBlockState(pos.add(3, 0, 0)).block == Core.pedestal
			val flagS = world.getBlockState(pos.add(-3, 0, 0)).block == Core.pedestal
			val flagW = world.getBlockState(pos.add(0, 0, 3)).block == Core.pedestal
			val flagE = world.getBlockState(pos.add(0, 0, -3)).block == Core.pedestal
			val flagNE = world.getBlockState(pos.add(2, 0, -2)).block == Core.pedestal
			val flagNW = world.getBlockState(pos.add(2, 0, 2)).block == Core.pedestal
			val flagSE = world.getBlockState(pos.add(-2, 0, -2)).block == Core.pedestal
			val flagSW = world.getBlockState(pos.add(-2, 0, 2)).block == Core.pedestal
			if (flagN && flagS && flagW && flagE && flagNE && flagNW && flagSE && flagSW){
				tile.isCenter = true
			}

			println(tile.isCenter)

			val itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)
			if (!player.isSneaking){
				if (player.heldItemMainhand.isEmpty){
					player.setHeldItem(hand, itemHandler?.extractItem(0, 64, false)!!)
				} else {
					if (player.heldItemMainhand.item is ISkillStorable && tile.inventory.getStackInSlot(0).item is ItemSkill){
						val stack = player.heldItemMainhand
						val operator = stack.item as ISkillStorable
						if (operator.canAddItemSkill(stack)){
							operator.addItemSkill(stack, tile.inventory.getStackInSlot(0))
							tile.inventory.setStackInSlot(0, ItemStack.EMPTY)
							player.setHeldItem(hand, stack)
						}
					} else {
						player.setHeldItem(hand, itemHandler?.insertItem(0, player.getHeldItem(hand), false)!!)
					}
				}
				tile.markDirty()
			}
		}
		return true
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val tile = world.getTileEntity(pos) as TileEntityPedestal
		val itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
		val stack = itemHandler?.getStackInSlot(0)!!

		if (!stack.isEmpty) {
			val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
			world.spawnEntity(item)
		}

		super.breakBlock(world, pos, state)
	}

	override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
		val tile = world.getTileEntity(pos) as TileEntityPedestal
		val flagN = world.getBlockState(pos.add(3, 0, 0)).block == Core.pedestal
		val flagS = world.getBlockState(pos.add(-3, 0, 0)).block == Core.pedestal
		val flagW = world.getBlockState(pos.add(0, 0, 3)).block == Core.pedestal
		val flagE = world.getBlockState(pos.add(0, 0, -3)).block == Core.pedestal
		val flagNE = world.getBlockState(pos.add(2, 0, -2)).block == Core.pedestal
		val flagNW = world.getBlockState(pos.add(2, 0, 2)).block == Core.pedestal
		val flagSE = world.getBlockState(pos.add(-2, 0, -2)).block == Core.pedestal
		val flagSW = world.getBlockState(pos.add(-2, 0, 2)).block == Core.pedestal
		if (flagN && flagS && flagW && flagE && flagNE && flagNW && flagSE && flagSW){
			tile.isCenter = true
		}
	}
}