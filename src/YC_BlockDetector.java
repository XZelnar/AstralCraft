package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.BlockProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import static net.minecraftforge.common.ForgeDirection.*;

public class YC_BlockDetector extends BlockContainer implements BlockProxy
{
    private Random random = new Random();
    static Icon top, side;

    protected YC_BlockDetector(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        setUnlocalizedName("Block detector");
        setHardness(1);
        setResistance(5);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        setTickRandomly(true);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	top = par1IconRegister.func_94245_a("YC_Block detector");
    	side = par1IconRegister.func_94245_a("YC_Stone");
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
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
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
    	updateTick(par1World, par2, par3, par4, new Random());
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack is)
    {
    	int var7 = BlockPistonBase.determineOrientation(par1World, par2, par3, par4, (EntityPlayer)par5EntityLiving);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7*2, 3);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        YC_TileEntityBlockDetector var7 = (YC_TileEntityBlockDetector)par1World.getBlockTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
                    {
                        int var13 = this.random.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.random.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.random.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Block detector";
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
	    YC_TileEntityBlockDetector w = (YC_TileEntityBlockDetector)world.getBlockTileEntity(x, y, z);
		//YC_GUIBlockDetector g = new YC_GUIBlockDetector(ModLoader.getMinecraftInstance().thePlayer.inventory, w);
		player.openGui(YC_Mod.instance, YC_Mod.c_blockDetectorGuiID, world, x, y, z);
		
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    @Override
    public TileEntity createNewTileEntity(World par1World)
    {
        return new YC_TileEntityBlockDetector();
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new YC_TileEntityBlockDetector();
    }
    
    
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3,
    		int par4, int par5) {
    	CheckNeighbourBlocks(par1World, par2, par3, par4);
    }
    
    @Override
    public int tickRate(World w) {
    	return 5;
    }
    
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4,
    		Random par5Random) {
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
    	CheckNeighbourBlocks(par1World, par2, par3, par4);
    }
    
    public void CheckNeighbourBlocks(World w, int x, int y, int z)
    {
    	int bid = 0;
    	int bid2 = 0;
    	int bid3 = 0;
    	int md = w.getBlockMetadata(x, y, z);
    	YC_TileEntityBlockDetector te = (YC_TileEntityBlockDetector)w.getBlockTileEntity(x, y, z);
    	
    	if (md/2 == 0)//down
    	{
    		bid = w.getBlockId(x, y-1, z);
    		bid2 = w.getBlockId(x, y-2, z);
    		bid3 = w.getBlockId(x, y-3, z);
    	}
    	if (md/2 == 1)//up
    	{
    		bid = w.getBlockId(x, y+1, z);
    		bid2 = w.getBlockId(x, y+2, z);
    		bid3 = w.getBlockId(x, y+3, z);
    	}
    	if (md/2 == 2)//z-
    	{
    		bid = w.getBlockId(x, y, z-1);
    		bid2 = w.getBlockId(x, y, z-2);
    		bid3 = w.getBlockId(x, y, z-3);
    	}
    	if (md/2 == 3)//z+
    	{
    		bid = w.getBlockId(x, y, z+1);
    		bid2 = w.getBlockId(x, y, z+2);
    		bid3 = w.getBlockId(x, y, z+3);
    	}
    	if (md/2 == 4)//x-
    	{
    		bid = w.getBlockId(x-1, y, z);
    		bid2 = w.getBlockId(x-2, y, z);
    		bid3 = w.getBlockId(x-3, y, z);
    	}
    	if (md/2 == 5)//x+
    	{
    		bid = w.getBlockId(x+1, y, z);
    		bid2 = w.getBlockId(x+2, y, z);
    		bid3 = w.getBlockId(x+3, y, z);
    	}
    	
    	if (te.IsBlockInGrid(bid) || te.IsBlockInGrid(bid2) || te.IsBlockInGrid(bid3))//equals
    	{
    		if (md % 2 == 0)
    		{
    			md=(md/2)*2+1;
    			w.setBlockMetadataWithNotify(x, y, z, md, 3);
    			notifyWireNeighborsOfNeighborChange(w, x, y, z);
    		}
    	}
    	else
    	{
    		if (md % 2 == 1)
    		{
    			md = (md/2)*2;
        		w.setBlockMetadataWithNotify(x, y, z, md, 3);
    			notifyWireNeighborsOfNeighborChange(w, x, y, z);
    		}
    	}
    }
    
    @Override
    public boolean canProvidePower() {
    	return true;
    }
    
    @Override
    public int isProvidingStrongPower(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	return par1iBlockAccess.getBlockMetadata(par2, par3, par4)%2==1 ? 15 : 0;
    }
    
    @Override
    public int isProvidingWeakPower(IBlockAccess par1World, int par2, int par3,
    		int par4, int par5) {
    	return par1World.getBlockMetadata(par2, par3, par4)%2==1 ? 15 : 0;
    	//return false;
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
    
    
}
