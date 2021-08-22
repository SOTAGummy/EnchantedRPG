package blocks

import Core
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
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
import recipe.PedestalRecipe
import recipe.PedestalRecipeHandler

object Pedestal: BlockContainer(Material.ROCK){
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
			val tile = world.getTileEntity(pos) as TileEntityPedestal
			val itemHandler = tile.inventory
			if (!player.isSneaking && !tile.isCrafting()){
				if (player.heldItemMainhand.isEmpty){
					if (!itemHandler.getStackInSlot(0).isEmpty){
						for(i in 0 .. 7){
							if (!itemHandler.getStackInSlot(7 - i).isEmpty){
								player.setHeldItem(hand, itemHandler.extractItem(7 - i, 1, false))
								break
							}
						}
					}
				} else {
					if (itemHandler.getStackInSlot(7).isEmpty) {
						for(i in 0 .. 7){
							if (itemHandler.getStackInSlot(i).isEmpty){
								val stack = player.heldItemMainhand.copy()
								stack.count = 1
								itemHandler.insertItem(i, stack, false)
								player.heldItemMainhand.count--
								break
							}
						}
					}
				}
				tile.markDirty()
			} else {
				val items = arrayOf(
						itemHandler.getStackInSlot(0),
						itemHandler.getStackInSlot(1),
						itemHandler.getStackInSlot(2),
						itemHandler.getStackInSlot(3),
						itemHandler.getStackInSlot(4),
						itemHandler.getStackInSlot(5),
						itemHandler.getStackInSlot(6),
						itemHandler.getStackInSlot(7)
				)

				if (!PedestalRecipeHandler.getCraftResult(items).isEmpty){
					tile.startCrafting()
					repeat(8){
						tile.inventory.setStackInSlot(it, ItemStack.EMPTY)
					}
					tile.inventory.setStackInSlot(0, PedestalRecipeHandler.getCraftResult(items))
					Minecraft.getMinecraft().addScheduledTask(){
						Minecraft.getMinecraft().player.playSound(Core.CRAFT_SOUND, 1.0F, 1.0F)
					}
					tile.endCrafting()
				}
			}
		return true
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val tile = world.getTileEntity(pos) as TileEntityPedestal
		val itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)

		repeat(8){
			val stack = itemHandler?.getStackInSlot(it)!!
			if (!stack.isEmpty) {
				val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
				world.spawnEntity(item)
			}
		}

		super.breakBlock(world, pos, state)
	}
}