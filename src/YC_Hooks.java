package net.minecraft.src;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Post;
import net.minecraftforge.event.world.WorldEvent;

public class YC_Hooks {
	
	@ForgeSubscribe
	public void ItemUsed(FillBucketEvent e)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) return;
		if (e.target.typeOfHit == EnumMovingObjectType.TILE &&
				e.world.getBlockId(e.target.blockX, e.target.blockY, e.target.blockZ)
				 == YC_Mod.b_lavaMaker.blockID && 
				 e.current.getItem() != null && 
				 e.current.getItem().itemID == Item.bucketEmpty.itemID)
		{
			int md = e.world.getBlockMetadata(e.target.blockX, e.target.blockY, e.target.blockZ);
			if (md >= e.current.stackSize)
			{
				e.current.itemID = Item.bucketLava.itemID;
				e.world.setBlockMetadataWithNotify(e.target.blockX, e.target.blockY, e.target.blockZ, md-e.current.stackSize, 3);
				e.world.markBlockForUpdate(e.target.blockX, e.target.blockY, e.target.blockZ);
				e.world.setBlockAndMetadataWithNotify(e.target.blockX, e.target.blockY, e.target.blockZ, YC_Mod.b_lavaMaker.blockID, md-e.current.stackSize, 3);
				e.setResult(null);
				e.setCanceled(true);
			}
		}
	}

	@ForgeSubscribe
	public void OnWorldEvent(net.minecraftforge.event.world.WorldEvent e)
	{
		if (e instanceof net.minecraftforge.event.world.WorldEvent.Save)
		{
			YC_Variables.Save();
		}
		if (e instanceof net.minecraftforge.event.world.WorldEvent.Load)
		{
			YC_Variables.Load();
		}
	}

	@ForgeSubscribe
	public void OnPopulateEvent(net.minecraftforge.event.terraingen.PopulateChunkEvent e)
	{
		if (e instanceof Post)
			YC_Mod.worldGen.generate(e.rand, e.chunkX, e.chunkZ, e.world, null,null);
	}
	
	@ForgeSubscribe
	public void OnBonemealUse(net.minecraftforge.event.entity.player.BonemealEvent e)
	{
		//check for astral tree growth
		if (!e.world.isRemote && e.entityPlayer.dimension == YC_Mod.d_astralDimID)
		{
			if (e.world.getBlockId(e.X, e.Y, e.Z) == YC_Mod.b_astralCrystals.blockID && e.world.getBlockId(e.X, e.Y-1, e.Z) == Block.grass.blockID)//crystal in center and grass beneath them
				if (e.world.getBlockId(e.X+1, e.Y, e.Z) == Block.sapling.blockID && 
					e.world.getBlockId(e.X-1, e.Y, e.Z) == Block.sapling.blockID && 
					e.world.getBlockId(e.X, e.Y, e.Z+1) == Block.sapling.blockID && 
					e.world.getBlockId(e.X, e.Y, e.Z-1) == Block.sapling.blockID)//4 sapplings on sides
				{
					int iron = 0, gold = 0;
					int id = 0;
					//============================================2 blocks of iron and gold each diagonally===================================================
					id = e.world.getBlockId(e.X+1, e.Y, e.Z+1);
					if (id == Block.blockSteel.blockID) iron++;
					if (id == Block.blockGold.blockID) gold++;
					
					id = e.world.getBlockId(e.X+1, e.Y, e.Z-1);
					if (id == Block.blockSteel.blockID) iron++;
					if (id == Block.blockGold.blockID) gold++;
					
					id = e.world.getBlockId(e.X-1, e.Y, e.Z-1);
					if (id == Block.blockSteel.blockID) iron++;
					if (id == Block.blockGold.blockID) gold++;
					
					id = e.world.getBlockId(e.X-1, e.Y, e.Z+1);
					if (id == Block.blockSteel.blockID) iron++;
					if (id == Block.blockGold.blockID) gold++;
					if (iron == 2 && gold == 2)
					{
						if (HasCoalCount(e.world, e.X, e.Y, e.Z, 64))//stack of coal for it
						{
							DecrCoalCount(e.world, e.X, e.Y, e.Z, 64);
							YC_WorldGenAstral.GenerateTree(e.world, new Random(), e.X, e.Y-1, e.Z);
							e.world.setBlockAndMetadataWithNotify(e.X, e.Y, e.Z-1, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X+1, e.Y, e.Z-1, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X-1, e.Y, e.Z-1, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X+1, e.Y, e.Z, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X-1, e.Y, e.Z, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X+1, e.Y, e.Z+1, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X, e.Y, e.Z+1, 0, 0, 3);
							e.world.setBlockAndMetadataWithNotify(e.X-1, e.Y, e.Z+1, 0, 0, 3);
						}
					}
				}
		}
		//System.out.println(e.X+" ; " + e.Y + " ; " + e.Z + " ; " + FMLCommonHandler.instance().getEffectiveSide());
	}
	
	@ForgeSubscribe
	public void OnChunkEvent(net.minecraftforge.event.world.ChunkEvent e)
	{/*
		if (e instanceof net.minecraftforge.event.world.ChunkEvent.Load)
		{
			Chunk c = e.getChunk();
			int dx = c.getChunkCoordIntPair().getCenterXPos()-8;
			int dz = c.getChunkCoordIntPair().getCenterZPosition()-8;
			//e.world.scheduledUpdatesAreImmediate = true;
			
			for(int x = 0; x<16; x++)
			{
				for(int y = 0; y<256; y++)
				{
					for(int z = 0; z<16; z++)
					{
						if (c.getBlockID(x+8, y, z+8) == YC_Mod.b_astralCrystals.blockID)
							e.world.scheduleBlockUpdateFromLoad(dx+x, y, dz+z, YC_Mod.b_astralCrystals.blockID, 20);
					}
				}
			}
			e.world.scheduledUpdatesAreImmediate = false;
		}//*/
	}
	
	public boolean HasCoalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.5f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == Item.coal.itemID && !((EntityItem)a.get(i)).isDead)
			{
				countt+=((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).stackSize;
				if (countt>=count) return true;
			}
    	}
    	
    	return false;
	}
	
	public void DecrCoalCount(World par1World, int par2, int par3, int par4, int count)
	{
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.5f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
    		ItemStack is = ((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10);
			if (is.itemID == Item.coal.itemID && !((EntityItem)a.get(i)).isDead)
			{
				if (is.stackSize<=count)
				{
					count -= is.stackSize;
					((EntityItem)a.get(i)).setDead();
				}
				else
				{
					is.stackSize -= count;
				}
				((EntityItem)a.get(i)).setEntityItemStack(is);
				if (count == 0) return;
			}
    	}
	}
	
}
