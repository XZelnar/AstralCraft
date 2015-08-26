package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockTreeCrystal extends Block
{
    public YC_BlockTreeCrystal(int par1)
    {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setHardness(1f);
        setResistance(5);
        setStepSound(soundStoneFootstep);
        setTickRandomly(true);
        setLightValue(0.75f);
        setTickRandomly(true);
        setBlockBounds(0.3f, 0.3f, 0.3f, 0.7f, 0.7f, 0.7f);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	this.field_94336_cN = par1IconRegister.func_94245_a("blockEmerald");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4) {
    	return 0xffffff;
    }
    
    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);

        if (this.idDropped(par5, par1World.rand, par7) != this.blockID)
        {
            int var8 = 0;

            var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 0, 1);

            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Crystal";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_treeCrystalRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		if(GetHolderBlockID(par1World, par2, par3, par4) != YC_Mod.b_treeLog.blockID){
			EntityItem e = new EntityItem(par1World, par2+0.5f, par3+0.5f, par4+0.5f, 
					new ItemStack(YC_Mod.i_crystalPowder));
			par1World.spawnEntityInWorld(e);
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 3);
		}
	}
	
	public int GetHolderBlockID(World w, int x, int y, int z)
	{
		int md = w.getBlockMetadata(x, y, z);
		if (md == 0)
			return w.getBlockId(x, y-1, z);
		if (md == 1)
			return w.getBlockId(x, y+1, z);
		if (md == 2)
			return w.getBlockId(x+1, y, z);
		if (md == 3)
			return w.getBlockId(x, y, z-1);
		if (md == 4)
			return w.getBlockId(x-1, y, z);
		if (md == 5)
			return w.getBlockId(x, y, z+1);
		return -1;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return false;
	}

	
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer,
			World par2World, int par3, int par4, int par5) {
			return par1EntityPlayer.getCurrentPlayerStrVsBlock(this,false, par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 30F;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return YC_Mod.i_crystalPowder.itemID;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		int md = par1World.getBlockMetadata(par2, par3, par4);
		switch (md)
		{
		case 0:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0, (double)par4 + 0.15, (double)par2 + 0.85, (double)par3 + 0.45, (double)par4 + 0.85);
		case 1:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.55, (double)par4 + 0.15, (double)par2 + 0.85, (double)par3 + 1, (double)par4 + 0.85);
		case 2:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.55, (double)par3 + 0.15, (double)par4 + 0.15, (double)par2 + 1, (double)par3 + 0.85, (double)par4 + 0.85);
		case 3:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.15, (double)par4 + 0, (double)par2 + 0.85, (double)par3 + 0.85, (double)par4 + 0.45);
		case 4:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0, (double)par3 + 0.15, (double)par4 + 0.15, (double)par2 + 0.45, (double)par3 + 0.85, (double)par4 + 0.85);
		default:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.15, (double)par4 + 0.55, (double)par2 + 0.85, (double)par3 + 0.85, (double)par4 + 1);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
			int par2, int par3, int par4) {
		int md = par1World.getBlockMetadata(par2, par3, par4);
		switch (md)
		{
		case 0:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0, (double)par4 + 0.15, (double)par2 + 0.85, (double)par3 + 0.45, (double)par4 + 0.85);
		case 1:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.55, (double)par4 + 0.15, (double)par2 + 0.85, (double)par3 + 1, (double)par4 + 0.85);
		case 2:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.55, (double)par3 + 0.15, (double)par4 + 0.15, (double)par2 + 1, (double)par3 + 0.85, (double)par4 + 0.85);
		case 3:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.15, (double)par4 + 0, (double)par2 + 0.85, (double)par3 + 0.85, (double)par4 + 0.45);
		case 4:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0, (double)par3 + 0.15, (double)par4 + 0.15, (double)par2 + 0.45, (double)par3 + 0.85, (double)par4 + 0.85);
		default:
			return AxisAlignedBB.getAABBPool().getAABB((double)par2 + 0.15, (double)par3 + 0.15, (double)par4 + 0.55, (double)par2 + 0.85, (double)par3 + 0.85, (double)par4 + 1);
		}
	}
	
	
}
