package dimension

import net.minecraft.entity.EnumCreatureType
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.monster.EntitySpider
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.biome.Biome
import net.minecraft.world.chunk.Chunk
import net.minecraft.world.gen.IChunkGenerator

class ChunkGeneratorTest(val world: World): IChunkGenerator{
	override fun generateChunk(x: Int, z: Int): Chunk {
		val chunk = Chunk(world, x, z)
		for (i in 0 .. 15) {
			for (j in 0 .. 15) {
				for (k in 0 .. 255) {
					chunk.setBlockState(BlockPos(i, k, j), Blocks.BEDROCK.defaultState)
				}
			}
		}
		return chunk
	}

	override fun generateStructures(chunkIn: Chunk, x: Int, z: Int): Boolean {
		return true
	}

	override fun getNearestStructurePos(worldIn: World, structureName: String, position: BlockPos, findUnexplored: Boolean): BlockPos? {
		return null
	}

	override fun getPossibleCreatures(creatureType: EnumCreatureType, pos: BlockPos): MutableList<Biome.SpawnListEntry> {
		val list = mutableListOf<Biome.SpawnListEntry>()
		list.add(Biome.SpawnListEntry(EntityZombie::class.java, 5, 3, 7))
		list.add(Biome.SpawnListEntry(EntitySkeleton::class.java, 5, 3, 7))
		list.add(Biome.SpawnListEntry(EntitySpider::class.java, 5, 3, 7))
		return list
	}

	override fun isInsideStructure(worldIn: World, structureName: String, pos: BlockPos): Boolean {
		return false
	}

	override fun populate(x: Int, z: Int) {

	}

	override fun recreateStructures(chunkIn: Chunk, x: Int, z: Int) {

	}
}