package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class YC_WorldGenAstral {
//!!! Not an actual worldgen class. Just is used as a handy replacement. Functions are called only from YC_ChunkProviderAstral. Used to generate Astral dimension !!!//
	
	public static void GenerateStartIsland(World world, int x, int y, int z)
	{
		//Tech grass
		AddTechGrassBlock(world, x-1, y, z);
		AddTechGrassBlock(world, x-1, y, z-1);
		
		AddTechGrassBlock(world, x, y, z);
		AddTechGrassBlock(world, x, y, z-1);
		AddTechGrassBlock(world, x, y, z-2);
		AddTechGrassBlock(world, x, y, z+1);

		AddTechGrassBlock(world, x+1, y, z);
		AddTechGrassBlock(world, x+1, y, z-1);
		AddTechGrassBlock(world, x+1, y, z-2);
		AddTechGrassBlock(world, x+1, y, z-3);
		AddTechGrassBlock(world, x+1, y, z+1);
		AddTechGrassBlock(world, x+1, y, z+2);
		AddTechGrassBlock(world, x+1, y, z+3);

		AddTechGrassBlock(world, x+2, y, z);
		AddTechGrassBlock(world, x+2, y, z-1);
		AddTechGrassBlock(world, x+2, y, z-2);
		AddTechGrassBlock(world, x+2, y, z+1);
		AddTechGrassBlock(world, x+2, y, z+2);
		AddTechGrassBlock(world, x+2, y, z+3);
		AddTechGrassBlock(world, x+2, y, z+4);

		AddTechGrassBlock(world, x+3, y, z);
		AddTechGrassBlock(world, x+3, y, z-1);
		AddTechGrassBlock(world, x+3, y, z-2);
		AddTechGrassBlock(world, x+3, y, z+1);
		AddTechGrassBlock(world, x+3, y, z+2);
		AddTechGrassBlock(world, x+3, y, z+3);
		AddTechGrassBlock(world, x+3, y, z+4);
		AddTechGrassBlock(world, x+3, y, z+5);

		AddTechGrassBlock(world, x+4, y, z);
		AddTechGrassBlock(world, x+4, y, z-1);
		AddTechGrassBlock(world, x+4, y, z+1);
		AddTechGrassBlock(world, x+4, y, z+2);
		AddTechGrassBlock(world, x+4, y, z+3);
		AddTechGrassBlock(world, x+4, y, z+4);
		AddTechGrassBlock(world, x+4, y, z+5);
		AddTechGrassBlock(world, x+4, y, z+6);

		AddTechGrassBlock(world, x+5, y, z);
		AddTechGrassBlock(world, x+5, y, z-1);
		AddTechGrassBlock(world, x+5, y, z+1);
		AddTechGrassBlock(world, x+5, y, z+2);
		AddTechGrassBlock(world, x+5, y, z+3);
		AddTechGrassBlock(world, x+5, y, z+4);
		AddTechGrassBlock(world, x+5, y, z+5);

		AddTechGrassBlock(world, x+6, y, z);
		AddTechGrassBlock(world, x+6, y, z+1);
		AddTechGrassBlock(world, x+6, y, z+2);
		AddTechGrassBlock(world, x+6, y, z+3);
		AddTechGrassBlock(world, x+6, y, z+4);

		AddTechGrassBlock(world, x+7, y, z);
		AddTechGrassBlock(world, x+7, y, z+1);
		AddTechGrassBlock(world, x+7, y, z+2);
		AddTechGrassBlock(world, x+7, y, z+3);

		AddTechGrassBlock(world, x+8, y, z+1);
		AddTechGrassBlock(world, x+8, y, z+2);
		
		//grass for tree
		world.setBlockAndMetadataWithNotify(x+2, y+1, z+1, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+1, z+1, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+1, z+2, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+1, z+3, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+1, z+1, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+1, z+2, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+1, z+3, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+1, z+4, Block.grass.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+1, z+2, Block.grass.blockID, 0, 3);
		
		//tree
		GenTree1(world, null, x+4-3, y+2, z+2-3);
		
		//dirt beneath the island
		world.setBlockAndMetadataWithNotify(x+1, y-1, z-1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+1, y-1, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+1, y-1, z+1, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+2, y-1, z-1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y-1, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y-1, z+1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y-1, z+2, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+3, y-1, z-1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-1, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-1, z+1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-1, z+2, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-1, z+3, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+4, y-1, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y-1, z+1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y-1, z+2, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+5, y-1, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+5, y-1, z+1, Block.dirt.blockID, 0, 3);
		
		//

		world.setBlockAndMetadataWithNotify(x+1, y-2, z-1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+1, y-2, z, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+2, y-2, z-1, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y-2, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y-2, z+1, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+3, y-2, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-2, z+1, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+4, y-2, z+1, Block.dirt.blockID, 0, 3);
		
		//

		world.setBlockAndMetadataWithNotify(x+2, y-3, z, Block.dirt.blockID, 0, 3);

		world.setBlockAndMetadataWithNotify(x+3, y-3, z, Block.dirt.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y-3, z+1, Block.dirt.blockID, 0, 3);
		
		//

		world.setBlockAndMetadataWithNotify(x+2, y-4, z, Block.dirt.blockID, 0, 3);
	}
	
	/*
	 * Is needed to set proper block texture
	 */
	public static void AddTechGrassBlock(World par1World, int par2, int par3, int par4)
	{
    	par1World.setBlockAndMetadataWithNotify(par2, par3, par4, YC_Mod.b_biomeTechGrass[0].blockID + (par2<0?7+par2%8:(par2%8)), (par4<0?7+par4%8:par4%8), 3);
	}
	
	public static void GenerateTree(World world, Random rand, int x, int y, int z)
	{
		y=y+1; x=x-3; z=z-3;
		int t = rand.nextInt(1); 
		if (t == 0) GenTree1(world, rand, x, y, z);
	}
	
	public static void GenTree1(World world, Random rand, int x, int y, int z)
	{
		//LOGS
		world.setBlockAndMetadataWithNotify(x+3, y+0, z+3, YC_Mod.b_treeLogTE.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+3, y+1, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+3, y+2, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+1, y+3, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+3, z+2, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+3, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+3, z+4, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+1, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+2, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+4, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+5, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+3, z+2, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+3, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+3, z+4, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+3, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+2, y+4, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+4, z+2, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+4, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+4, z+4, YC_Mod.b_treeLog.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+4, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+3, y+5, z+3, YC_Mod.b_treeLog.blockID, 0, 3);
		
		//CRYSTALS

		world.setBlockAndMetadataWithNotify(x+2, y+4, z+2, YC_Mod.b_treeCrystal.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+4, z+2, YC_Mod.b_treeCrystal.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+4, z+4, YC_Mod.b_treeCrystal.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+4, z+4, YC_Mod.b_treeCrystal.blockID, 0, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+6, z+3, YC_Mod.b_treeCrystal.blockID, 0, 3);
		//
		world.setBlockAndMetadataWithNotify(x+1, y+2, z+3, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+2, z+2, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+2, z+3, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+2, z+4, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+2, z+1, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+2, z+2, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+2, z+4, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+2, z+5, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+2, z+2, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+2, z+3, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+2, z+4, YC_Mod.b_treeCrystal.blockID, 1, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+2, z+3, YC_Mod.b_treeCrystal.blockID, 1, 3);
		//
		world.setBlockAndMetadataWithNotify(x+0, y+3, z+3, YC_Mod.b_treeCrystal.blockID, 2, 3);
		world.setBlockAndMetadataWithNotify(x+1, y+3, z+2, YC_Mod.b_treeCrystal.blockID, 2, 3);
		world.setBlockAndMetadataWithNotify(x+1, y+3, z+4, YC_Mod.b_treeCrystal.blockID, 2, 3);
		world.setBlockAndMetadataWithNotify(x+1, y+4, z+3, YC_Mod.b_treeCrystal.blockID, 2, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+5, z+3, YC_Mod.b_treeCrystal.blockID, 2, 3);
		//
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+6, YC_Mod.b_treeCrystal.blockID, 3, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+3, z+5, YC_Mod.b_treeCrystal.blockID, 3, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+3, z+5, YC_Mod.b_treeCrystal.blockID, 3, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+4, z+5, YC_Mod.b_treeCrystal.blockID, 3, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+5, z+4, YC_Mod.b_treeCrystal.blockID, 3, 3);
		//
		world.setBlockAndMetadataWithNotify(x+6, y+3, z+3, YC_Mod.b_treeCrystal.blockID, 4, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+3, z+2, YC_Mod.b_treeCrystal.blockID, 4, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+3, z+4, YC_Mod.b_treeCrystal.blockID, 4, 3);
		world.setBlockAndMetadataWithNotify(x+5, y+4, z+3, YC_Mod.b_treeCrystal.blockID, 4, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+5, z+3, YC_Mod.b_treeCrystal.blockID, 4, 3);
		//
		world.setBlockAndMetadataWithNotify(x+3, y+3, z+0, YC_Mod.b_treeCrystal.blockID, 5, 3);
		world.setBlockAndMetadataWithNotify(x+2, y+3, z+1, YC_Mod.b_treeCrystal.blockID, 5, 3);
		world.setBlockAndMetadataWithNotify(x+4, y+3, z+1, YC_Mod.b_treeCrystal.blockID, 5, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+4, z+1, YC_Mod.b_treeCrystal.blockID, 5, 3);
		world.setBlockAndMetadataWithNotify(x+3, y+5, z+2, YC_Mod.b_treeCrystal.blockID, 5, 3);
	}
}
