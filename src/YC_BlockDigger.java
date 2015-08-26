package net.minecraft.src;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class YC_BlockDigger extends Block
{
    public YC_BlockDigger(int par1)
    {
        super(par1, Material.clay);
        this.setCreativeTab(CreativeTabs.tabMisc);
        setLightValue(1f);
        setHardness(1);
        setResistance(5);
        setTickRandomly(true);
        //setBlockBounds(0, 0, 0, 0, 0, 0);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("bedrock");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Digger";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_diggerRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int tickRate(World par1World) {
		return 1;
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new YC_TileEntityBlockDigger();
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlockId(par2-1, par3, par4-1) == 0 &&
				par1World.getBlockId(par2, par3, par4-1) == 0 &&
				par1World.getBlockId(par2+1, par3, par4-1) == 0 &&
				par1World.getBlockId(par2+1, par3, par4) == 0 &&
				par1World.getBlockId(par2+1, par3, par4+1) == 0 &&
				par1World.getBlockId(par2, par3, par4+1) == 0 &&
				par1World.getBlockId(par2-1, par3, par4+1) == 0 &&
				par1World.getBlockId(par2-1, par3, par4) == 0)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3,
			int par4, int par5) {
		par1World.setBlockTileEntity(par2, par3, par4, null);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4-1, 0, 0, 3);
    	par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4+1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4-1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4+1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4-1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4+1, 0, 0, 3);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2,
			int par3, int par4, Explosion e) {
		par1World.setBlockTileEntity(par2, par3, par4, null);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4-1, 0, 0, 3);
    	par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4+1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4-1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4+1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4-1, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4, 0, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4+1, 0, 0, 3);
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLiving par5EntityLiving, ItemStack is) {
		//par1World.setBlockMetadata(par2, par3, par4, 1);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2+1, par3, par4+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2, par3, par4+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		par1World.setBlockAndMetadataWithNotify(par2-1, par3, par4+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
	}
	
	
	

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		if (!par1World.isRemote)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			int md = par1World.getBlockMetadata(par2, par3, par4);
			if (md < 5)
				if (HasCrystalCount(par1World, par2, par3, par4, 1))
				{
					DecrCrystalCount(par1World, par2, par3, par4, 1);
					par1World.setBlockAndMetadataWithNotify(par2, par3, par4, this.blockID, md+1, 3);
				}
			/*else
			{
				EntityItem e = new EntityItem(par1World, par2, par3, par4, new ItemStack(YC_Mod.b_digger));
				par1World.spawnEntityInWorld(e);
				par1World.setBlock(par2+1, par3, par4-1, 0);
		    	par1World.setBlock(par2+1, par3, par4, 0);
				par1World.setBlock(par2+1, par3, par4+1, 0);
				par1World.setBlock(par2, par3, par4-1, 0);
				par1World.setBlock(par2, par3, par4+1, 0);
				par1World.setBlock(par2-1, par3, par4-1, 0);
				par1World.setBlock(par2-1, par3, par4, 0);
				par1World.setBlock(par2-1, par3, par4+1, 0);
				par1World.setBlock(par2, par3, par4, 0);
				TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
				if (te != null)
				{
					par1World.removeBlockTileEntity(par2, par3, par4);
					te.invalidate();
				}
			}*/
		}
	}
	
	public boolean HasCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				countt+=((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).stackSize;
				if (countt>=count) return true;
			}
    	}
    	
    	return false;
	}
	
	public void DecrCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
    		ItemStack is = ((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10);
			if (is.itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				if (is.stackSize<=count)
				{
					count -= is.stackSize;
					((EntityItem)a.get(i)).setDead();
				}
				else
				{
					is.stackSize -= count;
				}
				((EntityItem)a.get(i)).setEntityItemStack(is);
				if (count == 0) return;
			}
    	}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		//par1World.spawnParticle("smoke", par2,par3+1,par4,0,0,0);
	}
	
	
}
