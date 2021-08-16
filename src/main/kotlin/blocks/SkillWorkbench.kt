package blocks

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.ResourceLocation

object SkillWorkbench: Block(Material.ROCK){
	init {
		this.setCreativeTab(Core.itemsTab)
		registryName = ResourceLocation(Core.ID, "skill_workbench")
		unlocalizedName = "skill_workbench"
		blockHardness = 1F
		blockResistance = 100F
	}


}