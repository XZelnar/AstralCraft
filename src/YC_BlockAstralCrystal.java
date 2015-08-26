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
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockAstralCrystal extends Block
{
    public YC_BlockAstralCrystal(int par1)
    {
        super(par1, Material.rock);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setHardness(1f);
        setResistance(5);
        setStepSound(soundStoneFootstep);
        setTickRandomly(true);
        setLightValue(0.75f);
        setTickRandomly(true);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("stone");
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

            var8 = MathHelper.getRandomIntegerInRange(par1World.rand, 2, 4);

            this.dropXpOnBlockBreak(par1World, par2, par3, par4, var8);
        }
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Astral crystals";
    }
	
	//@Override
	//public String getTextureFile() {
	//	return "/YC/YC_textures.png";
	//}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_crystalRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		if(!par1World.isBlockOpaqueCube(par2, par3-1, par4)){
			EntityItem e = new EntityItem(par1World, par2+0.5f, par3+0.5f, par4+0.5f, 
					new ItemStack(YC_Mod.b_astralCrystals));
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
			return par1EntityPlayer.getCurrentPlayerStrVsBlock(this,false,par2World.getBlockMetadata(par3, par4, par5)) / blockHardness / 30F;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return blockID;
	}
	
	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return 1;
	}

	/*@Override
	public int tickRate() {
		return 20;
	}
	
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		//System.out.println(1);
		//System.out.println(FMLCommonHandler.instance().getEffectiveSide());
		par1World.markBlockForRenderUpdate(par2, par3, par4);
		par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate());
	}
	
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		updateTick(par1World, par2, par3, par4, null);
		return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
	}//*/
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
		par1World.markBlockRangeForRenderUpdate(par2-2, par3-2, par4-2, par2+2, par3+2, par4+2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3,
			int par4, Random par5Random) {
		updateTick(par1World, par2, par3, par4, null);
	}*/
	
	
}
