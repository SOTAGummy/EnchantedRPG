package blocks

import Core
import items.baseItem.ItemSkill
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import module.ISkillStorable
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.items.CapabilityItemHandler
import recipe.PedestalRecipeHandler

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
			println(tile.location)
			if (!player.isSneaking){
				if (player.heldItemMainhand.isEmpty){
					if (tile.location == "Center" && tile.inventory.getStackInSlot(0).isEmpty) {
						val tileN = world.getTileEntity(pos.add(-3, 0, 0)) as TileEntityPedestal
						val tileS = world.getTileEntity(pos.add(3, 0, 0)) as TileEntityPedestal
						val tileW = world.getTileEntity(pos.add(0, 0, -3)) as TileEntityPedestal
						val tileE = world.getTileEntity(pos.add(0, 0, 3)) as TileEntityPedestal
						val tileNE = world.getTileEntity(pos.add(-2, 0, 2)) as TileEntityPedestal
						val tileNW = world.getTileEntity(pos.add(-2, 0, -2)) as TileEntityPedestal
						val tileSE = world.getTileEntity(pos.add(2, 0, 2)) as TileEntityPedestal
						val tileSW = world.getTileEntity(pos.add(2, 0, -2)) as TileEntityPedestal
						val array = arrayListOf(
							tileN.inventory.getStackInSlot(0),
							tileS.inventory.getStackInSlot(0),
							tileW.inventory.getStackInSlot(0),
							tileE.inventory.getStackInSlot(0),
							tileNE.inventory.getStackInSlot(0),
							tileNW.inventory.getStackInSlot(0),
							tileSE.inventory.getStackInSlot(0),
							tileSW.inventory.getStackInSlot(0)
						)

						if (!PedestalRecipeHandler.getCraftResult(array).isEmpty){
							tileN.inventory.extractItem(0, 1, false)
							tileS.inventory.extractItem(0, 1, false)
							tileE.inventory.extractItem(0, 1, false)
							tileW.inventory.extractItem(0, 1, false)
							tileNE.inventory.extractItem(0, 1, false)
							tileNW.inventory.extractItem(0, 1, false)
							tileSE.inventory.extractItem(0, 1, false)
							tileSW.inventory.extractItem(0, 1, false)
							world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, tile.pos.x.toDouble(), tile.pos.y.toDouble() + 1, tile.pos.z.toDouble(), 0.0, 1.0, 0.0)
							val serverThread = world as WorldServer
							val clientThread = Minecraft.getMinecraft()
							GlobalScope.launch {
								delay(1000)
								serverThread.addScheduledTask(){
									if (!tile.inventory.getStackInSlot(0).isEmpty){
										val item = EntityItem(world, tile.pos.x.toDouble(), tile.pos.y.toDouble(), tile.pos.z.toDouble(), tile.inventory.getStackInSlot(0))
										world.spawnEntity(item)
										itemHandler?.extractItem(0, 1, false)
									}
									tile.inventory.setStackInSlot(0, PedestalRecipeHandler.getCraftResult(array))
								}
							}
						}
					}else {
						player.setHeldItem(hand, itemHandler?.extractItem(0, 1, false)!!)
					}
				} else if (player.heldItemMainhand.isItemEqual(tile.inventory.getStackInSlot(0)) && player.heldItemMainhand.count < player.heldItemMainhand.maxStackSize){
					itemHandler?.extractItem(0, 1, false)
					player.heldItemMainhand.count++
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

		when(tile.location){
			"N" -> { (world.getTileEntity(pos.add(-3, 0, 0)) as TileEntityPedestal).location = "" }
			"S" -> { (world.getTileEntity(pos.add(3, 0, 0)) as TileEntityPedestal).location = "" }
			"W" -> { (world.getTileEntity(pos.add(0, 0, -3)) as TileEntityPedestal).location = "" }
			"E" -> { (world.getTileEntity(pos.add(0, 0, 3)) as TileEntityPedestal).location = "" }
			"NE" -> { (world.getTileEntity(pos.add(-2, 0, 2)) as TileEntityPedestal).location = "" }
			"NW" -> { (world.getTileEntity(pos.add(-2, 0, -2)) as TileEntityPedestal).location = "" }
			"SE" -> { (world.getTileEntity(pos.add(2, 0, 2)) as TileEntityPedestal).location = "" }
			"SW" -> { (world.getTileEntity(pos.add(2, 0, -2)) as TileEntityPedestal).location = "" }
		}

		super.breakBlock(world, pos, state)
	}

	override fun onBlockAdded(world: World, pos: BlockPos, state: IBlockState) {
		var tile = world.getTileEntity(pos) as TileEntityPedestal
		val flagN = world.getBlockState(pos.add(3, 0, 0)).block == Core.pedestal
		val flagS = world.getBlockState(pos.add(-3, 0, 0)).block == Core.pedestal
		val flagW = world.getBlockState(pos.add(0, 0, 3)).block == Core.pedestal
		val flagE = world.getBlockState(pos.add(0, 0, -3)).block == Core.pedestal
		val flagNE = world.getBlockState(pos.add(2, 0, -2)).block == Core.pedestal
		val flagNW = world.getBlockState(pos.add(2, 0, 2)).block == Core.pedestal
		val flagSE = world.getBlockState(pos.add(-2, 0, -2)).block == Core.pedestal
		val flagSW = world.getBlockState(pos.add(-2, 0, 2)).block == Core.pedestal
		if (flagN && flagS && flagW && flagE && flagNE && flagNW && flagSE && flagSW){
			tile.location = "Center"
			tile = world.getTileEntity(pos.add(3, 0, 0)) as TileEntityPedestal
			tile.location = "N"
			tile = world.getTileEntity(pos.add(-3, 0, 0)) as TileEntityPedestal
			tile.location = "S"
			tile = world.getTileEntity(pos.add(0, 0, 3)) as TileEntityPedestal
			tile.location = "W"
			tile = world.getTileEntity(pos.add(0, 0, -3)) as TileEntityPedestal
			tile.location = "E"
			tile = world.getTileEntity(pos.add(2, 0, -2)) as TileEntityPedestal
			tile.location = "NE"
			tile = world.getTileEntity(pos.add(2, 0, 2)) as TileEntityPedestal
			tile.location = "NW"
			tile = world.getTileEntity(pos.add(-2, 0, -2)) as TileEntityPedestal
			tile.location = "SE"
			tile = world.getTileEntity(pos.add(-2, 0, 2)) as TileEntityPedestal
			tile.location = "SW"
		}
	}
}