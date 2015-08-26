package net.minecraft.src;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class YC_RBlockWater extends Block
{

    public YC_RBlockWater(int par1)
    {
        super(par1, Material.clay);
        setResistance(0.5f);
        setHardness(0.5f);
        setUnlocalizedName("researchWater");
    }
    
    
}
