package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class YC_WorldProviderAstral extends WorldProvider
{
    /**
     * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
     */
    @Override
    public String getDimensionName()
    {
        return "The Astral";
    }
    
    @Override
    protected void registerWorldChunkManager() {
        this.worldChunkMgr = new YC_WorldChunkManagerAstral(YC_Mod.bg_astral, 1.0F, 0.0F);
        this.isHellWorld = false;
        this.hasNoSky = true;
    }

    @Override
    protected void generateLightBrightnessTable()
    {
        for (int var2 = 0; var2 <= 15; ++var2)
        {
            this.lightBrightnessTable[var2] = 1f;
        }
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new YC_ChunkProviderAstral(this.worldObj, this.worldObj.getSeed());
    }

    @Override
    public boolean isSurfaceWorld()
    {
        return true;
    }

    @Override
    public boolean canCoordinateBeSpawn(int par1, int par2)
    {
        return true;
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        return 0F;
    }

    @Override
    public boolean canRespawnHere()
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean doesXZShowFog(int par1, int par2)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getCloudHeight() {
    	return 0;
    }
    
    @Override
    public String getDepartMessage() {
    	return "Returning to the reality...";
    }
    
    @Override
    public String getSaveFolder() {
    	return "AST";
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public double getVoidFogYFactor() {
    	return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean getWorldHasVoidParticles() {
    	return false;
    }
    
    @Override
    public void updateWeather() {
    	return;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Vec3 getFogColor(float par1, float par2) {
    	return Vec3.createVectorHelper(0.5f,0.5f,0.5f);
    	//return new Vec3(null, 0.5f,0.5f,0.5f);
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(int x, int z) {
    	return YC_Mod.bg_astral;
    }
    
    @Override
    public ChunkCoordinates getEntrancePortalLocation() {
    	return new ChunkCoordinates();
    }
    
    @Override
    public ChunkCoordinates getRandomizedSpawnPoint() {
    	return new ChunkCoordinates();
    }
    
    @Override
    public int getRespawnDimension(EntityPlayerMP player) {
    	return 0;
    }
    
    
}
