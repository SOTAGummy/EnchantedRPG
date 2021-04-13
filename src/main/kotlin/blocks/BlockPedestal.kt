package blocks

import net.minecraft.block.BlockAnvil
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler

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
			val itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)
			if (!player.isSneaking){
				if (player.heldItemMainhand.isEmpty) player.setHeldItem(hand, itemHandler?.extractItem(0, 64, false)!!)
				else player.setHeldItem(hand, itemHandler?.insertItem(0, player.getHeldItem(hand), false)!!)
				tile.markDirty()
			}
		}
		return true
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val tile = world.getTileEntity(pos) as TileEntityPedestal
		val itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
		val stack = itemHandler?.getStackInSlot(0)!!

		if (!stack.isEmpty){
			val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
			world.spawnEntity(item)
		}

		super.breakBlock(world, pos, state)
	}
}