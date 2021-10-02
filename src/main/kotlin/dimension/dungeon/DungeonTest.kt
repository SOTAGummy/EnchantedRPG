package dimension.dungeon

import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import utils.BlockRect

object DungeonTest: Dungeon("test", 5, 5){
	override fun getMaxRoom(): Int {
		return 5
	}

	override fun getBossRoom(): DungeonPart {
		return TestRoom
	}

	override fun getDungeonFloor(): Block {
		return Blocks.STONEBRICK
	}

	override fun getDungeonWall(): DungeonWall {
		return TestWall
	}

	override fun getEntranceRoom(): DungeonPart {
		return TestRoom
	}

	override fun getDungeonParts(): ArrayList<DungeonPart> {
		return arrayListOf(TestRoom)
	}

	override fun generateDungeon(world: World, pos: BlockPos) {
		RandomDungeonGenerator.generateRandomDungeon(this, BlockRect(pos, pos.add(this.xChunkSize * 16, 0, this.zChunkSize * 16)), world)
	}
}