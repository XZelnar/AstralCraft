/*
*** MADE BY MITHION'S .SCHEMATIC TO JAVA CONVERTING TOOL v1.6 ***
*/

package net.minecraft.src;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

public class YC_wgPyramidDown extends WorldGenerator
{
	protected int[] GetValidSpawnBlocks() {
		return new int[] {
			Block.grass.blockID
		};
	}

	public boolean LocationIsValidSpawn(World world, int i, int j, int k){
		int distanceToAir = 0;
		int checkID = world.getBlockId(i, j, k);

		while (checkID != 0){
			distanceToAir++;
			checkID = world.getBlockId(i, j + distanceToAir, k);
		}

		if (distanceToAir > 3){
			return false;
		}
		j += distanceToAir - 1;

		int blockID = world.getBlockId(i, j, k);
		int blockIDAbove = world.getBlockId(i, j+1, k);
		int blockIDBelow = world.getBlockId(i, j-1, k);
		for (int x : GetValidSpawnBlocks()){
			if (blockIDAbove != 0){
				return false;
			}
			if (blockID == x){
				return true;
			}else if (blockID == Block.snow.blockID && blockIDBelow == x){
				return true;
			}
		}
		return false;
	}

	public YC_wgPyramidDown() { }

	public boolean generate(World world, Random rand, int i, int j, int k) {
		//check that each corner is one of the valid spawn blocks
		//if(!LocationIsValidSpawn(world, i, j, k) || !LocationIsValidSpawn(world, i + 8, j, k) || !LocationIsValidSpawn(world, i + 8, j, k + 28) || !LocationIsValidSpawn(world, i, j, k + 28))
		//{
		//	return false;
		//}

		world.setBlockAndMetadataWithNotify(i + 0, j + 1, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 1, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 1, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 1, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 1, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 3, k + 16, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 3, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 3, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 15, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 16, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 4, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 1, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 1, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 5, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 5, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 5, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 5, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 5, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 1, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 1, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 1, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 1, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 1, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 12, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 3, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 12, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 20, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 5, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 6, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 6, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 6, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 6, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 6, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		//world.setBlockAndMetadata(i + 3, j + 1, k + 16, -125, 7, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 11, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 12, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 13, Block.pistonStickyBase.blockID, 5, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 19, Block.pistonStickyBase.blockID, 5, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 12, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 3, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 14, Block.pistonStickyBase.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 15, Block.pistonStickyBase.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 16, Block.pistonStickyBase.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 17, Block.pistonStickyBase.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 18, Block.pistonStickyBase.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 4, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 5, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 5, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 5, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 5, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 5, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 0, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 4, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 5, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 6, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 7, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 8, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 9, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 10, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 11, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 12, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 13, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 15, 0, 0, 0);
		//world.setBlockAndMetadata(i + 4, j + 1, k + 16, -124, 4, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 20, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 21, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 22, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 23, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 24, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 1, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 3, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 14, Block.pistonExtension.blockID, 13, 0);//
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 15, Block.pistonExtension.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 16, Block.pistonExtension.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 17, Block.pistonExtension.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 18, Block.pistonExtension.blockID, 13, 0);//
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 4, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 5, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 6, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 6, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 6, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 7, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 7, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 7, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 8, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 8, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 8, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 0, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 2, Block.chest.blockID, 3, 0);
		GenChestContense(world, rand, i + 5, j + 1, k + 2);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 4, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 5, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 6, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 7, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 8, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 9, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 10, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 11, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 12, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 13, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 15, 0, 0, 0);
		//world.setBlockAndMetadata(i + 5, j + 1, k + 16, -124, 4, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 20, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 21, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 22, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 23, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 24, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 1, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 4, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 5, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 6, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 7, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 8, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 9, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 10, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 11, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 12, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 13, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 15, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 16, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 20, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 21, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 22, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 23, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 24, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 2, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 4, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 5, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 6, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 7, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 8, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 9, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 10, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 11, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 12, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 13, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 15, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 16, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 20, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 21, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 22, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 23, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 24, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 3, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 4, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 13, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 19, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 6, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 7, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 7, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 7, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 7, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 7, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 8, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 8, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 8, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 8, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 8, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 0, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 4, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 5, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 6, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 7, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 8, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 9, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 10, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 11, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 12, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 13, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 14, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 15, 0, 0, 0);
		//world.setBlockAndMetadata(i + 6, j + 1, k + 16, -124, 4, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 17, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 18, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 19, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 20, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 21, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 22, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 23, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 24, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 1, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 0, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 1, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 2, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 3, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 26, Block.glowStone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 3, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 4, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 14, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 16, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 18, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 5, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 6, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 6, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 6, k + 26, Block.glowStone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 6, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 6, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 7, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 7, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 7, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 7, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 7, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 8, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 8, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 8, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 8, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 8, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 0, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 4, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 5, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 6, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 7, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 8, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 9, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 10, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 11, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 12, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 13, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 14, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 15, Block.cobblestoneMossy.blockID, 0, 0);
		//world.setBlockAndMetadata(i + 7, j + 1, k + 16, -125, 5, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 17, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 18, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 19, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 20, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 21, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 22, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 23, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 1, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 2, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 1, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 2, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 3, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 3, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 4, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 4, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 4, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 4, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 4, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 5, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 5, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 5, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 5, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 5, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 6, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 6, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 6, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 6, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 6, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 7, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 7, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 7, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 7, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 7, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 8, k + 24, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 8, k + 25, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 8, k + 26, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 8, k + 27, 0, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 7, j + 8, k + 28, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 0, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 0, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 0, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 15, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 16, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 17, Block.cobblestone.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 1, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 2, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 2, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 2, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 3, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 3, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 3, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 4, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 4, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 4, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 5, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 5, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 5, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 6, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 6, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 6, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 7, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 7, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 7, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 8, k + 25, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 8, k + 26, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 8, j + 8, k + 27, Block.cobblestoneMossy.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 15, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 16, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 18, Block.torchRedstoneActive.blockID, 3, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 2, k + 19, Block.redstoneWire.blockID, 15, 0);
		world.setBlockAndMetadataWithNotify(i + 0, j + 3, k + 17, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 15, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 2, k + 16, Block.redstoneRepeaterIdle.blockID, 15, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 3, k + 19, Block.redstoneWire.blockID, 14, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 14, Block.redstoneWire.blockID, 9, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 15, Block.redstoneWire.blockID, 10, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 16, Block.redstoneWire.blockID, 11, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 17, Block.redstoneWire.blockID, 12, 0);
		world.setBlockAndMetadataWithNotify(i + 1, j + 4, k + 18, Block.redstoneWire.blockID, 13, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 15, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 16, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 2, k + 17, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 14, Block.redstoneRepeaterActive.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 15, Block.redstoneRepeaterActive.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 16, Block.redstoneRepeaterActive.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 17, Block.redstoneRepeaterActive.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 2, j + 4, k + 18, Block.redstoneRepeaterActive.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 14, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 15, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 17, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 3, j + 2, k + 18, Block.redstoneWire.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 4, j + 2, k + 2, Block.torchWood.blockID, 1, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 14, Block.lavaStill.blockID, 4, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 15, Block.lavaStill.blockID, 2, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 16, Block.lavaStill.blockID, 0, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 17, Block.lavaStill.blockID, 2, 0);
		world.setBlockAndMetadataWithNotify(i + 5, j + 5, k + 18, Block.lavaStill.blockID, 4, 0);
		world.setBlockAndMetadataWithNotify(i + 6, j + 2, k + 2, Block.torchWood.blockID, 2, 0);
		

		
		world.setBlockAndMetadataWithNotify(i+4, j+1, k+16, Block.tripWire.blockID, 4, 0);
		world.setBlockAndMetadataWithNotify(i+5, j+1, k+16, Block.tripWire.blockID, 4, 0);
		world.setBlockAndMetadataWithNotify(i+6, j+1, k+16, Block.tripWire.blockID, 4, 0);

		world.setBlockAndMetadataWithNotify(i+3, j+1, k+16, Block.tripWireSource.blockID, 7, 0);
		world.setBlockAndMetadataWithNotify(i+7, j+1, k+16, Block.tripWireSource.blockID, 5, 0);
		

		return true;
	}
	
	public void GenChestContense(World par1World, Random par2Random, int var12, int par4, int var14)
	{
		TileEntityChest var16 = (TileEntityChest)par1World.getBlockTileEntity(var12, par4, var14);

		if (var16 != null)
		{
            ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
            WeightedRandomChestContent.generateChestContents(par2Random, info.getItems(par2Random), var16, info.getCount(par2Random));
			
			short cur = 0;
			Random r = new Random();
			for(int i = 0; i<27; i++)
			{
				if (var16.getStackInSlot(i) == null)
				{
					if (cur == 0)
					{
						var16.setInventorySlotContents(i, new ItemStack(Item.diamond, r.nextInt(4)));
						cur++;
						continue;
					}
					if (cur == 1)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.b_astralCrystals, r.nextInt(20)));
						cur++;
						continue;
					}
					if (cur == 2)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_sChip, r.nextInt(20)));
						cur++;
						continue;
					}
					if (cur == 3)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_sCogwheel, r.nextInt(20)));
						cur++;
						continue;
					}
					if (cur == 4)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_sMatrix, r.nextInt(20)));
						cur++;
						continue;
					}
					if (cur == 5)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_sEngine, r.nextInt(20)));
						cur++;
						continue;
					}
					if (cur == 6)
					{
						var16.setInventorySlotContents(i, new ItemStack(Item.ingotIron, r.nextInt(12)));
						cur++;
						continue;
					}
					if (cur == 7)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_EssenceOfKnowledge, r.nextInt(7)));
						cur++;
						continue;
					}
					if (cur == 8)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_rsActivator, r.nextInt(2)));
						cur++;
						continue;
					}
					if (cur == 9)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.b_rWater, r.nextInt(3)));
						cur++;
						continue;
					}
					if (cur == 10)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.b_rWood, r.nextInt(3)));
						cur++;
						continue;
					}
					if (cur == 11)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.b_rLava, r.nextInt(3)));
						cur++;
						continue;
					}
					if (cur == 12)
					{
						var16.setInventorySlotContents(i, new ItemStack(YC_Mod.i_rEnderKnowledgePiece, r.nextInt(3)));
						cur++;
						continue;
					}
					if (cur == 13)
					{
						var16.setInventorySlotContents(i, new ItemStack(Item.ingotGold, r.nextInt(6)));
						break;
					}
				}
			}
		}
	}
	
}