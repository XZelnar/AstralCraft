package net.minecraft.src;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class YC_RBlockLava extends Block
{

    public YC_RBlockLava(int par1)
    {
        super(par1, Material.rock);
        setResistance(0.5f);
        setHardness(0.5f);
        setUnlocalizedName("YC_researchLava");
        setCreativeTab(CreativeTabs.tabBlock);
        setBlockBounds(0, 0, 0, 1, 0.8875f, 1);
    }
    
    //@Override
    //public boolean renderAsNormalBlock() {
    //	return false;
    //}
    
    //@Override
    //public int getRenderType() {
    //	return YC_Mod.c_blockRenderID;
    //}
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    
}
