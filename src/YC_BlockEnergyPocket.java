package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockEnergyPocket extends Block
{
    public YC_BlockEnergyPocket(int par1)
    {
        super(par1, Material.rock);
        setHardness(1f);
        setResistance(5);
        setStepSound(soundStoneFootstep);
        setLightValue(0.75f);
        setBlockBounds(0.2f,0.2f,0.2f,0.8f,0.8f,0.8f);
        
        setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("blockIron");
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Energy Pocket";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_energyPocketRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return true;
	}

	
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return false;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer,
			World par2World, int par3, int par4, int par5) {
			return par1EntityPlayer.getCurrentPlayerStrVsBlock(this,false, par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 30F;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 0;
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new YC_TileEntityBlockEnergyPocket();
	}
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
    		int par2, int par3, int par4) {
    	return null;
    }
	
	
}
