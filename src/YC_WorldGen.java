package net.minecraft.src;

import java.util.Random;

import org.lwjgl.util.vector.Vector2f;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenTrees;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;

public class YC_WorldGen implements IWorldGenerator {
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (!world.provider.isHellWorld && !(world.provider instanceof WorldProviderEnd)) {
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
		}
	}
	
	public static boolean WasPyramidGenerated = true;

	private void generateSurface(World world, Random random, int blockX,
			int blockZ) {
		//=========================================================RESEARCH STUFF==========================================================================
		//wood
		if (random.nextInt(50)<1)
		{
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			int y = world.getHeightValue(x, z);
			if (new WorldGenTrees(true).generate(world, random, x, y, z))
				world.setBlockAndMetadataWithNotify(x, y, z, YC_Mod.b_rWood.blockID, 0, 0);
		}
		
		//water
		if (random.nextInt(3)==1)
		{
			int x = blockX + random.nextInt(16);
			int z = blockZ + random.nextInt(16);
			int y = world.getHeightValue(x, z)-3;
			if (world.getBlockId(x, y, z)==Block.waterStill.blockID)
			{
				int id = world.getBlockId(x, y-1, z);
				if (id == Block.sand.blockID || id == Block.dirt.blockID)
				{
					id = world.getBlockId(x-1, y, z);
					if (id == Block.sand.blockID || id == Block.dirt.blockID)
					{
						world.setBlockAndMetadataWithNotify(x, y, z, YC_Mod.b_rWater.blockID, 0, 0);
					}
					id = world.getBlockId(x, y, z-1);
					if (id == Block.sand.blockID || id == Block.dirt.blockID)
					{
						world.setBlockAndMetadataWithNotify(x, y, z, YC_Mod.b_rWater.blockID, 0, 0);
					}
					id = world.getBlockId(x+1, y, z);
					if (id == Block.sand.blockID || id == Block.dirt.blockID)
					{
						world.setBlockAndMetadataWithNotify(x, y, z, YC_Mod.b_rWater.blockID, 0, 0);
					}
					id = world.getBlockId(x, y, z+1);
					if (id == Block.sand.blockID || id == Block.dirt.blockID)
					{
						world.setBlockAndMetadataWithNotify(x, y, z, YC_Mod.b_rWater.blockID, 0, 0);
					}
				}
			}
		}
		
		if (random.nextInt(15)<1)
		{
			if (new WorldGenLakes(Block.lavaStill.blockID).generate(world, random, blockX, 11, blockZ))
			{
			}
		}
		
		//lava
		boolean found = false;
		for (int x=blockX; x<blockX+16; x+=4)
		{
			for (int z=blockZ; z<blockZ+16; z+=4)
			{
				if (world.getBlockId(x, 10, z) == Block.lavaStill.blockID)// && random.nextInt(2)<1)
				{
					world.setBlockAndMetadataWithNotify(x, 10, z, YC_Mod.b_rLava.blockID, 0, 0);
					found = true;
					break;
				}
			}
			if (found) break;
		}
		
		
		
		
		//==============================================================================OTHER WORLDGEN==========================================================================
		
		
		
		//Random random = new Random(world.getSeed()*blockX / blockZ);
		//Pyramid
		if (WasPyramidGenerated)
		{
			if (YC_Options.GenerateStructures && random.nextInt(128) == 1) {
				GenPyramid(blockX, blockZ, random, world);
			}
		}
		else
		{
			if (YC_Options.GenerateStructures && random.nextInt(3) == 1) {
				GenPyramidHarder(blockX, blockZ, random, world);
			}
		}
		
		//teleporter block
		//if (random.nextInt(2) == 1)
		GenMysteryBlock(blockX, blockZ, random, world);
		
		//Ore
		if (YC_Options.GenerateSymbols)
		{
			for(int i = 0; i<60; i++)
			{
				int Xcoord = blockX + random.nextInt(16);
				int Ycoord = random.nextInt(60)+1;
				int Zcoord = blockZ + random.nextInt(16);
				//if (world.getBlockId(Xcoord, Ycoord-1, Zcoord) == Block.stone.blockID)
					GenerateOreVein(world, random, Xcoord, Ycoord, Zcoord, 3, YC_Mod.b_ore.blockID);
			}
		}
		
		//Crystal
		if (YC_Options.GenerateCrystals)
		{
			for(int i = 0; i<30; i++)
			{
				int Xcoord = blockX + random.nextInt(16);
				int Ycoord = random.nextInt(60)+1;
				int Zcoord = blockZ + random.nextInt(16);
				//if (world.getBlockId(Xcoord, Ycoord-1, Zcoord) == Block.stone.blockID)
					GenerateOreVein(world, random, Xcoord, Ycoord, Zcoord, 1, YC_Mod.b_astralCrystals.blockID);
			}
		}
		
		//Energy Pockets
		if (YC_Options.GenerateEnergyPockets)
		{
			for(int i = 0; i<3; i++)
			{
				int Xcoord = blockX + random.nextInt(16);
				int Ycoord = random.nextInt(27)+4;
				int Zcoord = blockZ + random.nextInt(16);
				if (world.getBlockId(Xcoord-1, Ycoord, Zcoord) != 0 && 
					world.getBlockId(Xcoord+1, Ycoord, Zcoord) != 0 && 
					world.getBlockId(Xcoord, Ycoord-1, Zcoord) != 0 && 
					world.getBlockId(Xcoord, Ycoord+1, Zcoord) != 0 && 
					world.getBlockId(Xcoord, Ycoord, Zcoord-1) != 0 && 
					world.getBlockId(Xcoord, Ycoord, Zcoord+1) != 0 &&
					!world.blockHasTileEntity(Xcoord, Ycoord, Zcoord))
						world.setBlockAndMetadataWithNotify(Xcoord,Ycoord,Zcoord,YC_Mod.b_energyPocket.blockID, 0, 0);
			}
		}
		
		
	}
	
	public void GenMysteryBlock(int blockX, int blockZ, Random random, World world)
	{
		int Xcoord1 = blockX + random.nextInt(16);
		int Zcoord1 = blockZ + random.nextInt(16);
		int Ycoord1 = random.nextInt(15) + 10;
		boolean doGen = false;
		int[] v;
		
		for(int i = 0; i < 2; i++)
		{
			Xcoord1 = blockX + random.nextInt(16);
			Zcoord1 = blockZ + random.nextInt(16);
			Ycoord1 = random.nextInt(15) + 10;
			v = GetSurrounding(Xcoord1, Ycoord1, Zcoord1, world);
			//System.out.println(v.x + " ; " + v.y);
			if (v[0] == 6)
			{
				while(Ycoord1 > 0)
				{
					Ycoord1--;
					v = GetSurrounding(Xcoord1, Ycoord1, Zcoord1, world);
					if (v[0] < 6 && v[1] != 0) 
					{
						doGen = true;
						break;
					}
					if (v[0] == 0)
					{
						doGen = false;
						break;
					}
				}
				if (doGen)
				{
					world.setBlockAndMetadataWithNotify(Xcoord1, Ycoord1, Zcoord1, YC_Mod.b_symbolInput.blockID, 0, 0);
					return;
				}
			}
			if (v[0] != 0 && v[1] != 0)
			{
				world.setBlockAndMetadataWithNotify(Xcoord1, Ycoord1, Zcoord1, YC_Mod.b_symbolInput.blockID, 0, 0);
				return;
			}
		}
	}
	
	//x = air; y = opaque cubes
	public int[] GetSurrounding(int x, int y, int z, World w)
	{
		int[] v = new int[2];
		//=====================================
		
		if (w.isAirBlock(x-1, y, z))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x-1, y, z))
				v[1]++;
		//=====================================
		
		if (w.isAirBlock(x+1, y, z))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x+1, y, z))
				v[1]++;
		//=====================================
		
		if (w.isAirBlock(x, y-1, z))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x, y-1, z))
				v[1]++;
		//=====================================
		
		if (w.isAirBlock(x, y+1, z))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x, y+1, z))
				v[1]++;
		//=====================================
		
		if (w.isAirBlock(x, y, z-1))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x, y, z-1))
				v[1]++;
		//=====================================
		
		if (w.isAirBlock(x, y, z+1))
			v[0]++;
		else
			if (w.isBlockOpaqueCube(x, y, z+1))
				v[1]++;
		//=====================================
		
		return v;
	}
	
	public void GenPyramid(int blockX, int blockZ, Random random, World world)
	{
		int Xcoord1 = blockX + random.nextInt(16);
		//int Ycoord1 = random.nextInt(60+10);
		int Zcoord1 = blockZ + random.nextInt(16);
		int Ycoord1 = world.getHeightValue(Xcoord1, Zcoord1);
		if (Ycoord1<0) Ycoord1=0;

		for (int y = Ycoord1; y>0; y--)
		{
			int id = world.getBlockId(Xcoord1, y, Zcoord1);
			if (id == Block.grass.blockID || 
					(id == Block.snow.blockID && world.getBlockId(Xcoord1, y-1, Zcoord1) == Block.grass.blockID))
			{
				if (!(new YC_wgPyramid()).generate(world, random, Xcoord1-9, y, Zcoord1-10))
					WasPyramidGenerated = false;
				else 
				{
					WasPyramidGenerated = true;
				}
				return;
			}
			if (id != 0 && id != Block.leaves.blockID && id != Block.tallGrass.blockID && 
					id != Block.plantYellow.blockID && id != Block.plantRed.blockID)
			{
				return;
			}
		}
	}
	
	public void GenPyramidHarder(int blockX, int blockZ, Random random, World world)
	{
		for(int i = 0; i<16; i+=4)
			for(int j = 0; j<16; j+=4)
			{
				int Xcoord1 = blockX + random.nextInt(16)+i;
				//int Ycoord1 = random.nextInt(60+10);
				int Zcoord1 = blockZ + random.nextInt(16)+j;
				int Ycoord1 = world.getHeightValue(Xcoord1, Zcoord1);
				if (Ycoord1<0) Ycoord1=0;

				for (int y = Ycoord1; y>0; y--)
				{
					int id = world.getBlockId(Xcoord1, y, Zcoord1);
					if (id == Block.grass.blockID || 
							(id == Block.snow.blockID && world.getBlockId(Xcoord1, y-1, Zcoord1) == Block.grass.blockID))
					{
						if (!(new YC_wgPyramid()).generate(world, random, Xcoord1-9, y, Zcoord1-10))
							WasPyramidGenerated = false;
						else 
						{
							WasPyramidGenerated = true;
						}
						return;
					}
					if (id != 0 && id != Block.leaves.blockID && id != Block.tallGrass.blockID && 
							id != Block.plantYellow.blockID && id != Block.plantRed.blockID)
					{
						return;
					}
				}
			}
	}
	
	
	public void GenerateOreVein(World world, Random random, int x, int y, int z, int max, int blockID)
	{
        //Chunk var5 = world.getChunkFromChunkCoords(x >> 4, z >> 4);
		//return;
		int c = random.nextInt(max)+1;
		if (c>=1 && world.getBlockId(x, y-1, z) == Block.stone.blockID) world.setBlockAndMetadataWithNotify((x), y, (z), blockID, 0, 0);
		if (c>=2 && world.getBlockId(x+1, y-1, z) == Block.stone.blockID) world.setBlockAndMetadataWithNotify((x+1), y, (z), blockID, 0, 0);
		if (c>=3 && world.getBlockId(x, y-1, z+1) == Block.stone.blockID) world.setBlockAndMetadataWithNotify((x), y, (z+1), blockID, 0, 0);
		if (c>=4 && world.getBlockId(x+1, y-1, z+1) == Block.stone.blockID) world.setBlockAndMetadataWithNotify((x+1), y, (z+1), blockID, 0, 0);
		/*if (c>=1) var5.setBlockID((x) & 15, y, (z) & 15, blockID);
		if (c>=2) var5.setBlockID((x+1) & 15, y, (z) & 15, blockID);
		if (c>=3) var5.setBlockID((x) & 15, y, (z+1) & 15, blockID);
		if (c>=4) var5.setBlockID((x+1) & 15, y, (z+1) & 15, blockID);*/
		//if (c>=5) world.setBlock(x-1,y,z,blockID);
		//if (c>=6) world.setBlock(x,y,z-1,blockID);
		//if (c>=7) world.setBlock(x-1,y,z-1,blockID);
		//if (c>=8) world.setBlock(x+1,y,z-1,blockID);
	}
	
	
	
}