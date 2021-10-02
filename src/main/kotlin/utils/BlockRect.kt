package utils

import dimension.dungeon.Dungeon
import dimension.dungeon.DungeonPart
import net.minecraft.block.Block
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class BlockRect(val corner1: BlockPos, val corner2: BlockPos) {
	val xSize = abs(corner1.x - corner2.x)
	val ySize = abs(corner1.y - corner2.y)
	val zSize = abs(corner1.z - corner2.z)
	val minX = min(corner1.x, corner2.x)
	val maxX = max(corner1.x, corner2.x)
	val minZ = min(corner1.z, corner2.z)
	val maxZ = max(corner1.z, corner2.z)
	var roadFaces = arrayListOf<EnumFacing>()

	fun getArea(): Int{
		if (ySize == 0) {
			return xSize * zSize
		}
		return -1
	}

	fun sortRect(): BlockRect{
		return BlockRect(BlockPos(minX, corner1.y, minZ), BlockPos(maxX, corner2.y, maxZ))
	}

	fun fill(world: World, block: Block){
		for (i in 0 until xSize) {
			for (j in 0 until zSize) {
				world.setBlockState(this.sortRect().corner1.add(i, 0, j), block.defaultState)
			}
		}
	}

	fun splitRect(x: Int, z: Int): Pair<BlockRect, BlockRect>{
		return if (this.xSize >= this.zSize) {
			val rand = (min(x, xSize - x)..max(x, xSize - x)).random()
			val rect1 = BlockRect(corner1, corner1.add(rand, 0, zSize))
			rect1.roadFaces.addAll(this.roadFaces)
			rect1.roadFaces.add(EnumFacing.EAST)
			val rect2 = BlockRect(corner1.add(rand, 0, 0), corner2)
			rect2.roadFaces.addAll(this.roadFaces)
			rect2.roadFaces.add(EnumFacing.WEST)
			Pair(rect1, rect2)
		} else {
			val rand = (min(z, zSize - z)..max(z, zSize - z)).random()
			val rect1 = BlockRect(corner1, corner1.add(xSize, 0, rand))
			rect1.roadFaces.addAll(this.roadFaces)
			rect1.roadFaces.add(EnumFacing.NORTH)
			val rect2 = BlockRect(corner1.add(0, 0, rand), corner2)
			rect2.roadFaces.addAll(this.roadFaces)
			rect2.roadFaces.add(EnumFacing.SOUTH)
			Pair(rect1, rect2)
		}
	}

	fun createRandomRoom(world: World, dungeon: Dungeon){
		val roomList = arrayListOf<DungeonPart>()
		val rect = this.sortRect()
		for (i in dungeon.getDungeonParts()) {
			if (i.size.x * i.size.z <= this.getArea()) {
				roomList.add(i)
			}
		}
		val randomRoom = (0 until roomList.size).random()
		val randX = (min(2, rect.xSize - roomList[randomRoom].size.x - 2)..max(2, rect.xSize - roomList[randomRoom].size.x - 2)).random()
		val randZ = (min(2, rect.zSize - roomList[randomRoom].size.z - 2)..max(2, rect.zSize - roomList[randomRoom].size.z - 2)).random()
		roomList[randomRoom].generate(world, Random(), rect.corner1.add(randX, 0, randZ))
	}

	fun createEntranceRoom(world: World, dungeon: Dungeon){
		val rect = this.sortRect()
		val room = dungeon.getEntranceRoom()
		val randX = (min(2, rect.xSize - room.size.x - 2)..max(2, rect.xSize - room.size.x - 2)).random()
		val randZ = (min(2, rect.zSize - room.size.z - 2)..max(2, rect.zSize - room.size.z - 2)).random()
		room.generate(world, Random(), rect.corner1.add(randX, 0, randZ))
	}

	fun createWall(world: World, dungeon: Dungeon){
		val rect = this.sortRect()
		for (i in 0 until rect.xSize) {
			dungeon.getDungeonWall().generate(world, Random(), rect.corner1.add(i, 0, 0))
			dungeon.getDungeonWall().generate(world, Random(), rect.corner2.add(-i, 0, 0))
		}
		for (i in 0..rect.zSize) {
			dungeon.getDungeonWall().generate(world, Random(), rect.corner1.add(0, 0, i))
			dungeon.getDungeonWall().generate(world, Random(), rect.corner2.add(0, 0, -i))
		}
	}
}