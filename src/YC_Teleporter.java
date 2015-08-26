package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet9Respawn;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;

public class YC_Teleporter extends Teleporter
{
    public YC_Teleporter() {
		super(MinecraftServer.getServer().worldServerForDimension(0));
	}

	/** A private Random() function in Teleporter */
    //private Random random = new Random();

    
    @Override
    public void placeInPortal(Entity par1Entity, double par2, double par4,
    		double par6, float par8) {
    	return;
    }
    
    @Override
    public boolean placeInExistingPortal(Entity par1Entity, double par2,
    		double par4, double par6, float par8) {
    	return true;
    }
    
    //Create portal
    @Override
    public boolean makePortal(Entity par1Entity) {
    	return true;
    }
    
    //legacy of ServerConfigurationManager
    public static void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2, Teleporter teleporter)
    {
    	MinecraftServer mcServer = MinecraftServer.getServer();
        int var3 = par1EntityPlayerMP.dimension;
        WorldServer var4 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer var5 = mcServer.worldServerForDimension(par1EntityPlayerMP.dimension);

        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, var5.getWorldInfo().getTerrainType(), var5.getHeight(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
        var4.removeEntity(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        transferEntityToWorld(par1EntityPlayerMP, var3, var4, var5, teleporter);
        mcServer.getConfigurationManager().func_72375_a(par1EntityPlayerMP, var4);
        par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.theItemInWorldManager.setWorld(var5);
        mcServer.getConfigurationManager().updateTimeAndWeatherForPlayer(par1EntityPlayerMP, var5);
        mcServer.getConfigurationManager().syncPlayerInventory(par1EntityPlayerMP);
        Iterator var6 = par1EntityPlayerMP.getActivePotionEffects().iterator();

        while (var6.hasNext())
        {
            PotionEffect var7 = (PotionEffect)var6.next();
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(par1EntityPlayerMP.entityId, var7));
        }

        GameRegistry.onPlayerChangedDimension(par1EntityPlayerMP);
    }
    
    public static void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer, Teleporter teleporter)
    {
        double d0 = par1Entity.posX;
        double d1 = par1Entity.posZ;
        double d3 = par1Entity.posX;
        double d4 = par1Entity.posY;
        double d5 = par1Entity.posZ;
        float f = par1Entity.rotationYaw;
        par3WorldServer.theProfiler.startSection("moving");

        if (par1Entity.dimension == 1)
        {
            ChunkCoordinates chunkcoordinates;

            if (par2 == 1)
            {
                chunkcoordinates = par4WorldServer.getSpawnPoint();
            }
            else
            {
                chunkcoordinates = par4WorldServer.getEntrancePortalLocation();
            }

            d0 = (double)chunkcoordinates.posX;
            par1Entity.posY = (double)chunkcoordinates.posY;
            d1 = (double)chunkcoordinates.posZ;
            par1Entity.setLocationAndAngles(d0, par1Entity.posY, d1, 90.0F, 0.0F);

            if (par1Entity.isEntityAlive())
            {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }

        par3WorldServer.theProfiler.endSection();

        if (par2 != 1)
        {
            par3WorldServer.theProfiler.startSection("placing");
            d0 = (double)MathHelper.clamp_int((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.clamp_int((int)d1, -29999872, 29999872);

            if (par1Entity.isEntityAlive())
            {
            	try {
                par4WorldServer.spawnEntityInWorld(par1Entity);
                par1Entity.setLocationAndAngles(d0, par1Entity.posY, d1, par1Entity.rotationYaw, par1Entity.rotationPitch);
                par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
                teleporter.placeInPortal(par1Entity, d3, d4, d5, f);
            	} catch (Exception e)
            	{
            		e.printStackTrace();
            		FMLLog.log(Level.SEVERE, "[AstralCraft] Minecraft whining about entity tracking again. Ignore it.");
            	}
            }

            par3WorldServer.theProfiler.endSection();
        }

        par1Entity.setWorld(par4WorldServer);
    }

    /*
    //legacy of ServerConfigurationManager
    public static void transferEntityToWorld(Entity par1Entity, int par2, WorldServer par3WorldServer, WorldServer par4WorldServer, Teleporter teleporter)
    {
        //WorldProvider pOld = par3WorldServer.provider;
        //WorldProvider pNew = par4WorldServer.provider;
        //double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
        //double moveFactor = 1;
        double var5 = par1Entity.posX;
        double var7 = par1Entity.posZ;
        double var11 = par1Entity.posX;
        double var13 = par1Entity.posY;
        double var15 = par1Entity.posZ;
        float var17 = par1Entity.rotationYaw;
        par3WorldServer.theProfiler.startSection("moving");

        if (par1Entity.dimension == 1)
        {
            ChunkCoordinates var18;

            if (par2 == 1)
            {
                var18 = par4WorldServer.getSpawnPoint();
            }
            else
            {
                var18 = par4WorldServer.getEntrancePortalLocation();
            }

            var5 = (double)var18.posX;
            par1Entity.posY = (double)var18.posY;
            var7 = (double)var18.posZ;
            par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, 90.0F, 0.0F);

            if (par1Entity.isEntityAlive())
            {
                par3WorldServer.updateEntityWithOptionalForce(par1Entity, false);
            }
        }

        par3WorldServer.theProfiler.endSection();

        if (par2 != 1)
        {
            par3WorldServer.theProfiler.startSection("placing");
            var5 = (double)MathHelper.clamp_int((int)var5, -29999872, 29999872);
            var7 = (double)MathHelper.clamp_int((int)var7, -29999872, 29999872);

            if (par1Entity.isEntityAlive())
            {
                par4WorldServer.spawnEntityInWorld(par1Entity);
                par1Entity.setLocationAndAngles(var5, par1Entity.posY, var7, par1Entity.rotationYaw, par1Entity.rotationPitch);
                par4WorldServer.updateEntityWithOptionalForce(par1Entity, false);
                teleporter.placeInPortal(par1Entity, var11, var13, var15, var17);
            }

            par3WorldServer.theProfiler.endSection();
        }

        par1Entity.setWorld(par4WorldServer);
    }//*/
}
