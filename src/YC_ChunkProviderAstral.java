package net.minecraft.src;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

public class YC_ChunkProviderAstral implements IChunkProvider
{
    //private Random hellRNG;

    /** Is the world that the nether is getting generated. */
    private World worldObj;

    public YC_ChunkProviderAstral(World par1World, long par2)
    {
        this.worldObj = par1World;
        //this.hellRNG = new Random(par2);
    }

    /**
     * Generates the shape of the terrain in the nether.
     */
    public void generateAstralTerrain(int par1, int par2, byte[] par3ArrayOfByte)
    {
    	/*
    	if (par1%100 == 0 || par2%100 == 0)
    	{
    		for(int i = 0; i<par3ArrayOfByte.length; i++)
    		{
    			par3ArrayOfByte[i] = (byte) Block.bedrock.blockID;
    		}
    	}
    	else
    	{//*/
    		for(int i = 0; i<par3ArrayOfByte.length; i++)
    		{
    			par3ArrayOfByte[i] = (byte) 0;
    		}
    	//}
    }

    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    byte[] btp = null;
    @Override
    public Chunk provideChunk(int par1, int par2)
    {
        //this.hellRNG.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        byte[] var3 = new byte[32768];
        //this.generateAstralTerrain(par1, par2, var3);
        Chunk var4 = new Chunk(this.worldObj, var3, par1, par2);
        if (btp == null)
        {
        	btp = new byte[256];
        	for(int i = 0; i<256; i++)
        	{
        		btp[i] = (byte)YC_Mod.bg_astral.biomeID;
        	}
        }
        var4.setBiomeArray(btp);

        var4.resetRelightChecks();
        return var4;
    }

    public boolean chunkExists(int par1, int par2)
    {
    	//System.out.println(1);
        return true;
    }

    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
    	GenPortalBlocks(par2, par3);
    	GenIsland(par2,par3);
    }
    
    public static Random r = new Random();
    public void GenIsland(int par1, int par2)
    {
    	if (par1 > 0 && par2 > 0)
    	{
    		if (par1%50 == 0 && par1%100 != 0 && par2%50 == 0 && par2%100 != 0)
    			YC_WorldGenAstral.GenerateStartIsland(worldObj, par1*16 + 8, 80, par2*16 + 8);
    		else
    		{
    			if (par1 % 3 == 0 && par2 % 3 == 0 && 
    					par1%100 > 2 && par1 % 100 < 98 && par2 % 100 > 2 && par2 % 100 < 98)
    			{
    				int x = par1*16+r.nextInt(17)-8;
    				int y = r.nextInt(32)+64;
    				int z = par2*16+r.nextInt(17)-8;
    				int t = r.nextInt(2);
    				if (t == 0)
    				{
    					YC_wgAstralIsland1.generate(worldObj, r, x, y, z);
    				}
    				if (t == 1)
    				{
    					YC_wgAstralIsland2.generate(worldObj, r, x, y, z);
    				}
    			}
    		}
    		return;
    	}
    	if (par1 < 0 && par2 < 0)
    	{
    		if (par1%50 == 0 && par1%100 != 0 && par2%50 == 0 && par2%100 != 0)
    		{
        		new YC_wgLabyrinth().generate(worldObj, r, par1*16, 80, par2*16);
    		}
    	}
    }
    
    public void GenPortalBlocks(int par1, int par2)
    {
    	for (int i = 0; i<16; i++)
    	{
    		for(int j = 0; j<16; j++)
    		{
    			worldObj.setBlockAndMetadataWithNotify(par1*16+i, 0, par2*16+j, YC_Mod.b_fromAstralPortal.blockID, 0, 0);//TODO
    			worldObj.setBlockAndMetadataWithNotify(par1*16+i, 1, par2*16+j, YC_Mod.b_fromAstralPortal.blockID, 0, 0);
    		}
    	}
    }

    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    @Override
    public boolean canSave()
    {
        return true;
    }

    @Override
    public String makeString()
    {
        return "AstralRandomLevelSource";
    }

    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
    {
    	return null;
    }

    @Override
    public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5)
    {
        return null;
    }

    @Override
    public int getLoadedChunkCount()
    {
        return 0;
    }
    
    @Override
    public void recreateStructures(int var1, int var2) {
    }

	@Override
	public boolean unloadQueuedChunks() {
		return false;
	}
}
