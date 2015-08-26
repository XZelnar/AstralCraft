package net.minecraft.src;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_BlockAstralBeacon extends Block
{
    public YC_BlockAstralBeacon(int par1)
    {
        super(par1, Material.iron);
        //this.setCreativeTab(CreativeTabs.tabMisc);
        setLightValue(1f);
        setHardness(1);
        setResistance(5);
        setTickRandomly(true);
        setBlockBounds(0, 0, 0, 1, 0.2f, 1);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("stone");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Astral Beacon";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_astBeaconRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
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
		return new YC_TileEntityBlockAstralBeacon();
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.canBlockSeeTheSky(par2, par3, par4) && par1World.isBlockOpaqueCube(par2, par3-1, par4))
			return true;
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		if (par1World.isRemote) return true;
		ItemStack is = par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem];
		YC_TileEntityBlockAstralBeacon te = (YC_TileEntityBlockAstralBeacon) par1World.getBlockTileEntity(par2, par3, par4);
		if (is != null && is.getItem() != null && is.itemID == YC_Mod.i_astralTeleporter.itemID)
		{
			if (te.CurPlayer.equals(""))
			{
				par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem] = null;
				te.CurPlayer = par5EntityPlayer.username;
				ClearBlocksAbove(par1World,par2,par3,par4);
				SpawnGuardian(par1World, par2, par4);
				return true;
			}
		}
		
		if (par5EntityPlayer.username.equals(te.CurPlayer))
		{
			te.CurPlayer = "";
			te.Drop(YC_Mod.i_astralTeleporter.itemID, 1, 0);
			return true;
		}
		
		return false;
	}
	
	public void ClearBlocksAbove(World w, int x, int y, int z)
	{
		for(int i = y+1; i < 256; i++)
		{
			for (int j = x-2; j <= x+2; j++)
			{
				for (int k = z-2; k <= z+2; k++)
				{
					if (w.getBlockId(j, i, k)!=0)
					{
						ClearBlock(w, j, i, k);
					}
				}
			}
		}
	}
	
	public void ClearBlock(World w, int x, int y, int z)
	{
		Block b = Block.blocksList[w.getBlockId(x, y, z)];
		int md = w.getBlockMetadata(x, y, z);
		Random r = new Random();
		int d = b.idDropped(md, r, 0);
		int c = b.quantityDropped(md, 0, r);
		int dam = b.damageDropped(md);
		Drop(w,x,y,z,d,c,dam);
		w.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
	}
    
    public void Drop(World worldObj, int xCoord, int yCoord, int zCoord, int id, int c, int damage)
    {
    	ItemStack is = null;
		if (Item.itemsList[id]==null)//Block
		{
			is = new ItemStack(Block.blocksList[id],c);
		}
		else//Item
		{
			is = new ItemStack(Item.itemsList[id],c, damage);
		}
    	EntityItem e = new EntityItem(worldObj, xCoord+0.5f, yCoord+1, zCoord+0.5f, is);
    	e.delayBeforeCanPickup = 20;
		e.motionY = 0.2f;
		worldObj.spawnEntityInWorld(e);
    }
	
    public void SpawnGuardian(World w, int x, int z)
    {
    	YC_EntityBeaconGuardian e = new YC_EntityBeaconGuardian(w);
    	e.posX = x;
    	e.posY = 256;
    	e.posZ = z;
    	e.prevPosX = x;
    	e.prevPosY = 256;
    	e.prevPosZ = z;
    	e.lastTickPosX = x;
    	e.lastTickPosY = 256;
    	e.lastTickPosZ = z;
    	e.isDead = false;
    	e.setLocationAndAngles(x+0.5, 256, z+0.5, 0, 0);
    	e.initCreature();
    	w.spawnEntityInWorld(e);
    }
    
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}
	
	
	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2,
			int par3, int par4, Explosion par5Explosion) {
		onBlockDestroyed(par1World, par2, par3, par4);
		super.onBlockDestroyedByExplosion(par1World, par2, par3, par4, par5Explosion);
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3,
			int par4, int par5) {
		onBlockDestroyed(par1World, par2, par3, par4);
		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}
	
	public static void onBlockDestroyed(World w, int x, int y, int z)
	{
		if (!w.isRemote);
			w.createExplosion(null, x+0.5d, y+0.5d, z+0.5d, 10, true);
	}
	
}
