package dimension.dungeon

import net.minecraft.util.EnumFacing
import net.minecraft.util.math.BlockPos

object TestRoom: DungeonPart("test_room", BlockPos(12, 8, 12)){
	override fun getRoadOffset(face: EnumFacing): Int {
		return 5
	}
}