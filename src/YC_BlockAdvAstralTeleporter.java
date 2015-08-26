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
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_BlockAdvAstralTeleporter extends Block
{
    public YC_BlockAdvAstralTeleporter(int par1)
    {
        super(par1, Material.iron);
        this.setCreativeTab(CreativeTabs.tabMisc);
        setLightValue(0.5f);
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
    	return "AdvAstralTeleporter";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		//return 1;
		return YC_Mod.c_advAstTelRenderID;
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
		return new YC_TileEntityBlockAdvAstralTeleporter();
	}
	
	@Override
	@Deprecated
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.getBlockId(par2, par3+1, par4) == 0 && par1World.isBlockOpaqueCube(par2, par3-1, par4))
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			return true;
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		/*par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);
		par1World.spawnParticle("smoke", par2-0.5f+par5Random.nextFloat()*2, par3+0.5f, 
				par4-0.5f+par5Random.nextFloat()*2, 0, 0f, 0);*/
		//par1World.spawnParticle("smoke", par2,par3+1,par4,0,0,0);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		if (par1World.isRemote) return true;
		ItemStack is = par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem];
		YC_TileEntityBlockAdvAstralTeleporter te = (YC_TileEntityBlockAdvAstralTeleporter) par1World.getBlockTileEntity(par2, par3, par4);
		if (is != null && is.getItem() != null && is.itemID == YC_Mod.i_astralTeleporter.itemID)
		{
			if (te.CurPlayer.equals(""))
			{
				par5EntityPlayer.inventory.mainInventory[par5EntityPlayer.inventory.currentItem] = null;
				te.CurPlayer = par5EntityPlayer.username;
				return true;
			}
		}
		
		if (par5EntityPlayer.username.equals(te.CurPlayer))
		{
			te.CurPlayer = "";
			te.Drop(YC_Mod.i_astralTeleporter.itemID, 1, 0);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 3);
		}
		
		return false;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		return null;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3,
			int par4, int par5) {
		YC_TileEntityBlockAdvAstralTeleporter te = (YC_TileEntityBlockAdvAstralTeleporter) par1World.getBlockTileEntity(par2, par3, par4);
		if (te != null)
			te.particleSpawnOnTick = 255;
		if (te != null && !te.CurPlayer.equals(""))
		{
			te.Drop(YC_Mod.i_astralTeleporter.itemID, 1, 0);
			te.CurPlayer = "";
		}
		//te = null;
		AddParticleToRemoveList(par2, par3, par4);
		
		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}
	
	public void AddParticleToRemoveList(int par2, int par3, int par4)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT) return;
		boolean add = true;
    	for (int i = 0; i<YC_ClientProxy.AdvAstTelRemoveCoord.size(); i+=3)
    	{
    		if (    ((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i  )) == par2 || 
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+1)) == par3 ||
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+2)) == par4)
    		{
    			add = false;
    			break;
    		}
    	}
    	if (add)
    	{
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par2);
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par3);
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par4);
    	}
	}
	
	
}
