package net.minecraft.src;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;

public class YC_BlockRainDetector extends Block
{
	static Icon side, top;
    protected YC_BlockRainDetector(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        setUnlocalizedName("Rain detector"); 
        this.setCreativeTab(CreativeTabs.tabRedstone);
        setBlockBounds(0, 0, 0, 1, 0.25f, 1);
        setTickRandomly(true);
        setHardness(1);
        setResistance(5);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	side = par1IconRegister.func_94245_a("YC_Stone");
    	top = par1IconRegister.func_94245_a("YC_Rain detector");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Rain detector";
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean getBlocksMovement(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4) {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
        if (par1 == 1)
        {
            return top;
        }
        else
        {
            return side;
        }
    }

    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == 1)
        {
            return top;
        }
        else
        {
            return side;
        }
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	return par1iBlockAccess.getBlockMetadata(par2, par3, par4)==1 ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess par1World, int par2, int par3,
    		int par4, int par5) {
    	return 0;
    	//return par1World.getBlockMetadata(par2, par3, par4)==1;
    }
    
    @Override
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    	par1World.setBlockMetadataWithNotify(par2, par3, par4, par1World.isRaining()&&par1World.canBlockSeeTheSky(par2, par3, par4)?1:2, 3);
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        
        int md = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, par1World.isRaining()&&par1World.canBlockSeeTheSky(par2, par3, par4)?1:2, 3);
        notifyWireNeighborsOfNeighborChange(par1World,par2,par3,par4);
    }
    
    private void notifyWireNeighborsOfNeighborChange(World par1World, int par2, int par3, int par4)
    {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
    }

    @Override
    public int tickRate(World par1World)
    {
        return 20;
    }
    
    
    
    
}
