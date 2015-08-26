package net.minecraft.src;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_BlockLavaGenerator extends Block
{
    protected YC_BlockLavaGenerator(int par1)
    {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabMisc);
        setBlockBounds(0, 0, 0, 1, 0.5f, 1);
        setTickRandomly(true);
        setLightValue(1);
        setHardness(3);
        setResistance(7);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("obsidian");
    }

    @Override
    public boolean hasTileEntity(int metadata) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
    	return new YC_TileEntityBlockRenderUpdater();
    }
    
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
    		int par4, int par5) {
    	par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    	par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
    	par1World.markBlockForUpdate(par2, par3, par4);
    }
    
    @Override
    public int tickRate(World par1World) {
    	return 5;
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
    	par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
    	if (par1World.isRemote) 
    	{
        	par1World.markBlockForRenderUpdate(par2, par3, par4);
    		return;
    	}
    	
    	int md1 = par1World.getBlockMetadata(par2, par3, par4);
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2+0.1f, par3, par4+0.1f, par2+0.9f, par3+0.7f, par4+0.9f));
    	if (a != null && a.size()>0)
    	{
    		for(int i = 0; i<a.size(); i++)
    		{
    			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == YC_Mod.b_astralCrystals.blockID)
    			{
    				if (md1<14)
    				{
    					((EntityItem)a.get(i)).setDead();
    					par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, md1+2, 0);
    					md1+=2;
    				}
    			}
    		}
    	}
    	
    	if (md1 > 7)
    	{
    		a = par1World.getEntitiesWithinAABB(EntityLiving.class, 
    				AxisAlignedBB.getBoundingBox(par2+0.1f, par3, par4, par2+1.9f, par3+0.7f, par4+0.9f));
    		if (a != null && a.size()>0)
    		{
    			for(int i = 0; i<a.size(); i++)
    			{
    				((EntityLiving)a.get(i)).attackEntityFrom(DamageSource.anvil, 1);
    				if (md1 < 15 && par5Random.nextInt(100) == 1)
    				{
    					par1World.setBlockAndMetadataWithNotify(par2, par3, par4, blockID, md1+1, 0);
    					md1++;
    				}
    			}
    		}
    	}
    }
    
    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
    	return 15;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_lavaGeneratorRenderID;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3,
    		int par4, Random rand) {
    	int md = par1World.getBlockMetadata(par2, par3, par4);
    	if (rand.nextInt(16)>md-1) return;
    	
        double var7 = (double)((float)par2 + 0.5F);
        double var9 = (double)((float)par3 + 0.5F);
        double var11 = (double)((float)par4 + 0.5F);
        double var13 = (float)(rand.nextInt(1000)-500)/5000f;
        double var14 = (float)(rand.nextInt(100) +500)/100000f;
        double var15 = (float)(rand.nextInt(1000)-500)/5000f;
        
    	par1World.spawnParticle("lava", var7, var9, var11, var13, var14, var15);
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Lava generator";
    }

	
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer,
			World par2World, int par3, int par4, int par5) {
		return par1EntityPlayer.getCurrentPlayerStrVsBlock(this,false, par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 20F;
	}
    
    
    
}
