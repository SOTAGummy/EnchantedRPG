package dimension

import Core
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.math.BlockPos
import net.minecraft.world.DimensionType
import net.minecraft.world.WorldProvider
import net.minecraft.world.gen.IChunkGenerator

class WorldProviderTest: WorldProvider(){
	override fun getDimensionType(): DimensionType {
		return Core.testDimension
	}

	override fun canMineBlock(player: EntityPlayer, pos: BlockPos): Boolean {
		return false
	}

	override fun getDimension(): Int {
		return dimensionType.id
	}

	override fun createChunkGenerator(): IChunkGenerator {
		return ChunkGeneratorTest(this.world)
	}
}