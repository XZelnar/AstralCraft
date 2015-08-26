package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockSpaceOccupier extends Block
{
    protected YC_BlockSpaceOccupier(int par1)
    {
        super(par1, Material.rock);
        //func_94332_a(YC_ClientProxy.textureMap);
        setBlockUnbreakable();
        setLightOpacity(0);
        setLightValue(1f);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	this.field_94336_cN = par1IconRegister.func_94245_a("YC_transparent");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
    		int par2, int par3, int par4) {
    	return null;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_blockRenderID;
    }
    
    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
    	return true;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public boolean isCollidable() {
    	return true;
    }
    
    @Override
    protected boolean canSilkHarvest() {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess,
    		int par2, int par3, int par4, int par5) {
    	return false;
    }
    
}
