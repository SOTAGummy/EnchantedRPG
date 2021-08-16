package blocks

import gui.GuiHandler
import net.minecraft.block.Block
import net.minecraft.block.BlockContainer
import net.minecraft.block.BlockWorkbench
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.Explosion
import net.minecraft.world.World

object SkillWorkbench: BlockContainer(Material.ROCK){
	init {
		this.setCreativeTab(Core.itemsTab)
		registryName = ResourceLocation(Core.ID, "skill_workbench")
		unlocalizedName = "skill_workbench"
		blockHardness = 1F
		blockResistance = 100F
	}

	override fun createNewTileEntity(worldIn: World, meta: Int): TileEntity? {
		return TileEntitySkillWorkbench()
	}

	override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
		if (!worldIn.isRemote){
			if (!playerIn.isSneaking){
				playerIn.openGui(Core.instance, GuiHandler.SkillWorkbenchGui, worldIn, pos.x, pos.y, pos.z)
			}
		}

		return true
	}

	override fun breakBlock(world: World, pos: BlockPos, state: IBlockState) {
		val te = world.getTileEntity(pos) as TileEntitySkillWorkbench
		repeat(9){
			val item = EntityItem(world, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), te.inventory.getStackInSlot(it))
			world.spawnEntity(item)
		}
		super.breakBlock(world, pos, state)
	}
}