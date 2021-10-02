package dimension.dungeon

import Core
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldProviderSurface
import net.minecraft.world.WorldServer
import net.minecraft.world.gen.feature.WorldGenerator
import net.minecraft.world.gen.structure.template.PlacementSettings
import java.util.*

abstract class DungeonWall(val name: String): WorldGenerator(){
	override fun generate(world: World, rand: Random, pos: BlockPos): Boolean {
		if (world.provider is WorldProviderSurface) {
			val server = (world as WorldServer).minecraftServer
			val templateManager = world.structureTemplateManager
			val template = templateManager.getTemplate(server, ResourceLocation(Core.ID, name))
			val settings = PlacementSettings()
			template.addBlocksToWorld(world, pos, settings)
		}
		return false
	}
}