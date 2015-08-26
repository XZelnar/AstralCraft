package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class YC_BlockFromAstralPortal extends Block
{
    protected YC_BlockFromAstralPortal(int par1)
    {
        super(par1, Material.portal);
        //func_94332_a(YC_ClientProxy.textureMap);//TODO
        setBlockUnbreakable();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("stone");
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_transparentBlockRenderID;
    }
    
    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public boolean isCollidable() {
    	return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
    		int par4, Entity par5Entity) {
    	if (par1World.isRemote) return;
    	if(par5Entity instanceof EntityLiving)
    	{
    		Random r = new Random();
    		MinecraftServer mcServer = MinecraftServer.getServer();
    		WorldServer w = mcServer.worldServerForDimension(0);
    		ChunkCoordinates c = w.getSpawnPoint();
    		par5Entity.posX = c.posX+r.nextInt(16)+0.5;
    		par5Entity.posZ = c.posZ+r.nextInt(16)+0.5;
    		{
    			for(int i = 255; i>0; i--)
    			{
    				if (w.getBlockId((int)par5Entity.posX, i, (int)par5Entity.posZ) != 0)
    				{
    					par5Entity.posY = i + 2f;
    					break;
    				}
    			}
    		}
    		par5Entity.motionY = 0;
    		par5Entity.fallDistance = 0;
    		if (par5Entity instanceof EntityPlayerMP)
    			mcServer.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP)par5Entity, 0, new YC_Teleporter());
    		else
    			mcServer.getConfigurationManager().transferEntityToWorld(par5Entity, 0, (WorldServer) par5Entity.worldObj, mcServer.worldServerForDimension(0), new YC_Teleporter());
    	}
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
            float f = 0.3625F;
            return AxisAlignedBB.getBoundingBox((float)i, j, (float)k, (float)(i + 1), (float)(j + 0.01), (float)(k + 1));
    }
    
}
