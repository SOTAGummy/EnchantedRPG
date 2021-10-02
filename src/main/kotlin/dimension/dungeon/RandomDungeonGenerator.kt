package dimension.dungeon

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing.*
import net.minecraft.world.World
import utils.BlockRect

object RandomDungeonGenerator {
	fun generateRandomDungeon(dungeon: Dungeon, rect: BlockRect, world: World){
		val rectList = arrayListOf<BlockRect>()
		val list = arrayListOf<Block>(Blocks.DIAMOND_BLOCK, Blocks.EMERALD_BLOCK, Blocks.COAL_BLOCK, Blocks.IRON_BLOCK, Blocks.REDSTONE_BLOCK, Blocks.LAPIS_BLOCK)
		var calcRect = rect.sortRect()
		for (i in 0 until dungeon.getMaxRoom()) {
			if (i == dungeon.getMaxRoom() - 1) {
				rectList.add(calcRect)
				break
			}
			if ((calcRect.xSize < (dungeon.getMaxPart().size.x + 4) * 2) || (calcRect.zSize < (dungeon.getMaxPart().size.z + 4) * 2)) {
				rectList.add(calcRect)
				break
			}
			val rects = calcRect.splitRect(dungeon.getMaxPart().size.x + 4, dungeon.getMaxPart().size.z + 4)
			calcRect = if (rects.first.getArea() <= rects.second.getArea()) {
				rectList.add(rects.first)
				rects.second
			} else {
				rectList.add(rects.second)
				rects.first
			}
		}
		for (i in 0 until rectList.size) {
			rectList[i].fill(world, list[i])
			rectList[i].createRandomRoom(world, dungeon)
			for (j in rectList[i].roadFaces) {
				when (j) {
					NORTH -> {

					}
					SOUTH -> {

					}
					EAST -> {

					}
					WEST -> {

					}
					else -> {}
				}
			}
		}
	}
}