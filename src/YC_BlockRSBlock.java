package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockRSBlock extends Block
{
    protected YC_BlockRSBlock(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        setBlockBounds(0, 0, 0, 0, 0, 0);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	this.field_94336_cN = par1IconRegister.func_94245_a("YC_transparent");
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
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	int md = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
    	if (md != 1)
    		return 15;
    	return 0;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess par1iBlockAccess,
    		int par2, int par3, int par4, int par5) {
    	return 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        super.onBlockAdded(par1World, par2, par3, par4);
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
        
    	int md = par1World.getBlockMetadata(par2, par3, par4);
    	if (md == 1)
    	{
    		par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
    		par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 3);
    		return;
    	}
    	else
    	{
    		if (md == 0)
    		{
    			md = 15;
    		}
    		else
    		{
    			md--;
    		}
    		par1World.setBlockMetadataWithNotify(par2,par3,par4,md,3);
    	}
    	
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    }
    
    @Override
    public int tickRate(World par1World) {
    	return 1;
    }
    
}
