package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class YC_BiomeGenAstral extends BiomeGenBase
{
    public YC_BiomeGenAstral(int par1)
    {
        super(par1);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        setBiomeName("Astral");
        setDisableRain();
    }
    
    @Override
    public boolean canSpawnLightningBolt() {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor() {
    	return 0x00ff00;
    }
    
    @Override
    public int getBiomeGrassColor() { return 0xffffff; };
    
    @Override
    public boolean getEnableSnow() {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float par1) {
    	return 0xffffff;
    }
    
    @Override
    public List<?> getSpawnableList(EnumCreatureType par1EnumCreatureType) {
    	return new ArrayList<Object>();
    }
    
    @Override
    public void decorate(World par1World, Random par2Random, int par3, int par4) {
    	
    }
    
    
    
    
}
