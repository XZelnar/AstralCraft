package net.minecraft.src;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockLavaDetector extends Block
{
	static Icon top, side;
    protected YC_BlockLavaDetector(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        setUnlocalizedName("Lava detector");
        setHardness(1);
        setResistance(5);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	top = par1IconRegister.func_94245_a("YC_Lava detector");
    	side = par1IconRegister.func_94245_a("YC_iron");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Lava detector";
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return true;
    }
    
    @Override
    public boolean getBlocksMovement(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4) {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
    	return par1==1?top:side;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        if (par5 == par1IBlockAccess.getBlockMetadata(par2, par3, par4)/2)
        {
            return top;
        }
        else
        {
            return side;
        }
    }

    
    @Override
    public int tickRate(World par1World) {
    	return 5;
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    	update(par1World, par2, par3, par4);
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
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
    	update(par1World, par2, par3, par4);
    }
    
    public void update(World par1World, int par2, int par3, int par4)
    {
    	int mdold = par1World.getBlockMetadata(par2, par3, par4);
    	int md = 0;
        if (IsWaterOnSideMetadata(par1World, par2, par3, par4))
        {
        	md = par1World.getBlockMetadata(par2, par3, par4);
        	md = (md/2)*2+1;
        }
        else
        {
        	md = par1World.getBlockMetadata(par2, par3, par4);
        	md = (md/2)*2;
        }
        if (md != mdold)
        {
        	par1World.setBlockMetadataWithNotify(par2, par3, par4, md, 3);
        	par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        	par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
        	//Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(par2, par3, par4, par2, par3, par4);
        }
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack is)
    {
    	int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7*2, 3);

    }
    
    public boolean IsWaterOnSideMetadata(World world, int x, int y, int z)
    {
    	int md = world.getBlockMetadata(x, y, z);
    	
    	//md == y-
    	if (md/2==0)
    	{
    		int nbid1 = world.getBlockId(x, y-1, z);//neighbour block id
    		int nbid2 = world.getBlockId(x, y-2, z);//neighbour block id
    		int nbid3 = world.getBlockId(x, y-3, z);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	//md == y+
    	if (md/2==1)
    	{
    		int nbid1 = world.getBlockId(x, y+1, z);//neighbour block id
    		int nbid2 = world.getBlockId(x, y+2, z);//neighbour block id
    		int nbid3 = world.getBlockId(x, y+3, z);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	//md == z-
    	if (md/2==2)
    	{
    		int nbid1 = world.getBlockId(x, y, z-1);//neighbour block id
    		int nbid2 = world.getBlockId(x, y, z-2);//neighbour block id
    		int nbid3 = world.getBlockId(x, y, z-3);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	//md == z+
    	if (md/2==3)
    	{
    		int nbid1 = world.getBlockId(x, y, z+1);//neighbour block id
    		int nbid2 = world.getBlockId(x, y, z+2);//neighbour block id
    		int nbid3 = world.getBlockId(x, y, z+3);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	//md == x-
    	if (md/2==4)
    	{
    		int nbid1 = world.getBlockId(x-1, y, z);//neighbour block id
    		int nbid2 = world.getBlockId(x-2, y, z);//neighbour block id
    		int nbid3 = world.getBlockId(x-3, y, z);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	//md == x+
    	if (md/2==5)
    	{
    		int nbid1 = world.getBlockId(x+1, y, z);//neighbour block id
    		int nbid2 = world.getBlockId(x+2, y, z);//neighbour block id
    		int nbid3 = world.getBlockId(x+3, y, z);//neighbour block id
    		if (CheckBlockForLiquid(nbid1) || CheckBlockForLiquid(nbid2) || 
    				CheckBlockForLiquid(nbid3))
    			return true;
    	}
    	
    	
    	return false;
    }
    
    public boolean CheckBlockForLiquid(int nbid)
    {
    	return (nbid==Block.lavaMoving.blockID || nbid==Block.lavaStill.blockID);
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	return par1iBlockAccess.getBlockMetadata(par2, par3, par4)%2==1 ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess par1World, int par2, int par3,
    		int par4, int par5) {
    	return 0;
    }
    
    @Override
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    	updateTick(par1World, par2, par3, par4, new Random());
    }
    
    
    
    
}
