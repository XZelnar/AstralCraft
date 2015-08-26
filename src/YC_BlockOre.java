package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockOre extends Block
{
    public YC_BlockOre(int par1)
    {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setHardness(0.5f);
        setResistance(5);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Anscient remains block");
        setLightValue(0.375f);
        setBlockBounds(0f, 0f, 0f, 1f, 0.5f, 1f);
        setTickRandomly(true);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("stone");
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        switch(par2Random.nextInt(4))
        {
        	case 0: return YC_Mod.i_sCogwheel.itemID;
        	case 1: return YC_Mod.i_sMatrix.itemID;
        	case 2: return YC_Mod.i_sEngine.itemID;
        	default: return YC_Mod.i_sChip.itemID;
        }
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
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

            var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 4);

            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_oreRenderID;
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		if(!par1World.isBlockOpaqueCube(par2, par3-1, par4)){
			EntityItem e = new EntityItem(par1World, par2+0.5f, par3+0.5f, par4+0.5f, 
					new ItemStack(Item.itemsList[idDropped(0, new Random(), 0)]));
			par1World.spawnEntityInWorld(e);
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, 0, 0, 3);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if(!par1World.isBlockOpaqueCube(par2, par3-1, par4)){
			return false;
		}
		return true;
	}
	
	

	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		if (player == null) return true;
		if (player.getCurrentPlayerStrVsBlock(this,false,meta)>=4)//more or equal than stone pickaxe
			return true;
		return false;
	}
	
	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer par1EntityPlayer,
			World par2World, int par3, int par4, int par5) {
		return par1EntityPlayer.getCurrentPlayerStrVsBlock(this,false,par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 20F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getBlockBrightness(IBlockAccess par1iBlockAccess, int par2,
			int par3, int par4) {
		return 15;
	}
	/*
	@Override
	public int tickRate() {
		return 20;
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new YC_TileEntityWorldGenRerenderer();
	}//*/
	
	/*@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		//par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
		updateTick(par1World, par2, par3, par4, null);
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
		par1World.markBlocksDirtyVertical(par2, par3, par4, 0);
		par1World.markBlockRangeForRenderUpdate(par2-8, par3-64, par4-8, par2+8, par3+32, par4+8);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		updateTick(par1World, par2, par3, par4, null);
	}*/
	
	
}
