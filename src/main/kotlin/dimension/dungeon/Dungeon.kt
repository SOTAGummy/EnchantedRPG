package dimension.dungeon

import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import utils.Storage

abstract class Dungeon(name: String, val xChunkSize: Int, val zChunkSize: Int) {
	init {
		Storage.Dungeons.add(this)
	}

	abstract fun getDungeonParts(): ArrayList<DungeonPart>

	abstract fun getDungeonWall(): DungeonWall

	abstract fun getDungeonFloor(): Block

	abstract fun getEntranceRoom(): DungeonPart

	abstract fun getBossRoom(): DungeonPart

	abstract fun generateDungeon(world: World, pos: BlockPos)

	abstract fun getMaxRoom(): Int

	open fun getMaxPart(): DungeonPart{
		var part: DungeonPart = getDungeonParts()[0]
		repeat(getDungeonParts().size){
			if (part.getSpace() < getDungeonParts()[it].getSpace()) part = getDungeonParts()[it]
		}
		return part
	}
}