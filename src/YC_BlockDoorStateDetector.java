package net.minecraft.src;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class YC_BlockDoorStateDetector extends Block
{
	static Icon top, side;
    protected YC_BlockDoorStateDetector(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        setUnlocalizedName("Door state detector");
        setHardness(1);
        setResistance(5);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	top = par1IconRegister.func_94245_a("YC_Door state detector");
    	side = par1IconRegister.func_94245_a("YC_Stone");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Door state detector";
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
    public boolean isBlockSolid(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	return true;
    }
    
    @Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z,
    		ForgeDirection side) {
    	return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
    	return par1 == 1 ? top : side;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
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
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
    	if (par1World.isRemote) return;
    	int id = par1World.getBlockId(par2, par3+1, par4);
    	if (id != Block.doorSteel.blockID && id != Block.doorWood.blockID) return;
    	
    	int md = par1World.getBlockMetadata(par2, par3, par4);
    	int md1 = par1World.getBlockMetadata(par2, par3+1, par4);
    	
    	if ((md==0 && md1>=4) || (md==1 && md1<4))
    	{
    		par1World.setBlockMetadataWithNotify(par2, par3, par4, md==0?1:0, 3);
    		notifyWireNeighborsOfNeighborChange(par1World,par2,par3,par4);
    		
    		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
    	}
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
    	if (par1World.isRemote) return;
    	int id = par1World.getBlockId(par2, par3+1, par4);
    	if (id != Block.doorSteel.blockID && id != Block.doorWood.blockID) return;
    	
    	int md = par1World.getBlockMetadata(par2, par3, par4);
    	int md1 = par1World.getBlockMetadata(par2, par3+1, par4);
    	
    	if ((md==0 && md1>=4) || (md==1 && md1<4))
    	{
    		par1World.setBlockMetadataWithNotify(par2, par3, par4, md==0?1:0, 3);
    		notifyWireNeighborsOfNeighborChange(par1World,par2,par3,par4);
    		
    		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
    	}
    }
    
    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    	updateTick(par1World, par2, par3, par4, null);
    }
    
    @Override
    public int tickRate(World par1World) {
    	return 1;
    }
    
    private void notifyWireNeighborsOfNeighborChange(World par1World, int par2, int par3, int par4)
    {
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
		YC_TileEntityBlockDoorStateDetector te2 = (YC_TileEntityBlockDoorStateDetector) par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
		if (par5 != 0)
		{
    		if (par1iBlockAccess.getBlockId(par2, par3+1, par4)==Block.doorWood.blockID)
			return par1iBlockAccess.getBlockMetadata(par2, par3+1, par4)>=4 ? 15 : 0;
    		if (par1iBlockAccess.getBlockId(par2, par3+1, par4)==Block.doorSteel.blockID)
			return par1iBlockAccess.getBlockMetadata(par2, par3, par4)==1 ? 15 : 0;
		}
		else
		{
    		if (par1iBlockAccess.getBlockId(par2, par3+1, par4)==Block.doorWood.blockID)
    		{
    			//if(te2.isHandOpened) System.out.println();
    			return (te2.isHandOpened && (par1iBlockAccess.getBlockMetadata(par2, par3+1, par4) >= 4)) ? 15 : 0;
    		}
		}
		return 0;
    	//System.out.println(te2.CurState);
		/*
    	if (par5 == 0)
    	{
    		if (par1iBlockAccess.getBlockId(par2, par3+1, par4)==Block.doorWood.blockID)
    		{
    	    	if (te2.CurMD >= 4 && te2.CurState == 0) return 0;
    			int md1 = par1iBlockAccess.getBlockMetadata(par2, par3+1, par4);
    			YC_TileEntityBlockDoorStateDetector te = (YC_TileEntityBlockDoorStateDetector) par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
    			//System.out.println(te.isHandOpened);
    			if ((te.CurMD != md1 || te.isHandOpened) && md1 >= 4)
    			{
    				//if (md1 >= 4 && te.isHandOpened)
    				{
    					NotifyNeighbours(par1iBlockAccess, par2,par3,par4);
    				}
    				te.CurMD = md1;
    				te.isHandOpened = true;
    				return 15;
    			}
    			return 0;
    		}
    		else
    		{
    			return 0;
    		}
    	}
    	else
    	{
    		return ((YC_TileEntityBlockDoorStateDetector) par1iBlockAccess.getBlockTileEntity(par2, par3, par4)).CurState == 1 ? 15 : 0;
    	}//*/
    	//return par1iBlockAccess.getBlockMetadata(par2, par3, par4)==1 ? 15 : 0;
    }
    
    public void NotifyNeighbours(IBlockAccess t, int x, int y, int z)
    {
    	World w = t.getBlockTileEntity(x, y, z).worldObj;
    	w.notifyBlockOfNeighborChange(x+1, y, z, blockID);
    	w.notifyBlockOfNeighborChange(x-1, y, z, blockID);
    	w.notifyBlockOfNeighborChange(x, y, z+1, blockID);
    	w.notifyBlockOfNeighborChange(x, y, z-1, blockID);
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess par1iBlockAccess, int par2, int par3,
    		int par4, int par5) {
    	return 0;
    }
    
    @Override
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public boolean hasTileEntity(int metadata) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
    	return new YC_TileEntityBlockDoorStateDetector();
    }
    
    
    
    
}
