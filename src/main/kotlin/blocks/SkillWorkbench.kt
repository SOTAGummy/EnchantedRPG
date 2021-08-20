package blocks

import gui.GuiHandler
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

object SkillWorkbench: BlockContainer(Material.ROCK){
	init {
		registryName = ResourceLocation(Core.ID, "skill_workbench")
		unlocalizedName = "skill_workbench"
		setCreativeTab(Core.itemsTab)
	}

	override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? {
		return TileEntitySkillWorkbench()
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val te = world.getTileEntity(pos) as TileEntitySkillWorkbench
		val itemHandler = te.inventory
		repeat(9){
			val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), itemHandler.getStackInSlot(it))
			world.spawnEntity(item)
		}
		super.breakBlock(world, pos, state)
	}

	override fun getRenderType(state: IBlockState): EnumBlockRenderType {
		return EnumBlockRenderType.MODEL
	}

	override fun onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if (!world.isRemote){
			val te = world.getTileEntity(pos) as TileEntitySkillWorkbench
			val itemHandler = te.inventory
			if (!player.isSneaking){
				player.openGui(Core.instance, GuiHandler.SkillWorkbenchGui, world, pos.x, pos.y, pos.z)
			}
		}
		return true
	}
}