package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.BlockProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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

public class YC_BlockChest extends BlockContainer implements BlockProxy
{
    private Random random = new Random();
    static Icon top, side, bottom, transparent;

    protected YC_BlockChest(int par1)
    {
        super(par1, Material.wood);
        setUnlocalizedName("Chest Part");
        setHardness(1);
        setResistance(3);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	top = par1IconRegister.func_94245_a("YC_ChestTop");
    	side = par1IconRegister.func_94245_a("YC_ChestSide");
    	bottom = par1IconRegister.func_94245_a("YC_ChestBottom");
    	transparent = par1IconRegister.func_94245_a("YC_transparent");
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_chestRenderID;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
    	return par1 == 0 ? bottom : par1 == 1 ? top : side;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	if (par5 == 1) return top;
    	if (par5 == 0) return bottom;
    	return side;
    	//int md = par1IBlockAccess.getBlockMetadata(par2, par3, par4); 
    	//return md >= 11 ? transparent : side;
    	/*
    	int md = par1IBlockAccess.getBlockMetadata(par2, par3, par4); 
    	if (md == 0 || md >= 11)
    	{
            return 13;
    	}
    	else
    	{
        	return 12;
    	}//*/
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Chest Part";
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	if (world.getBlockMetadata(x, y, z) == 0) return false;
	    YC_TileEntityBlockChest w = (YC_TileEntityBlockChest)world.getBlockTileEntity(x, y, z);
	    if (w == null) return false;
	    w = (YC_TileEntityBlockChest)world.getBlockTileEntity(w.mx, w.my, w.mz);
	    if (w == null) return false;
	    if (w.numUsingPlayers != 0) return true;
	    PacketDispatcher.sendPacketToPlayer(w.getDescriptionPacket(), (Player) player);
		player.openGui(YC_Mod.instance, YC_Mod.c_blockChestGuiID, world, x, y, z);
		
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new YC_TileEntityBlockChest();
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new YC_TileEntityBlockChest();
    }
    
    @Override
    public boolean isBlockNormalCube(World world, int x, int y, int z) {
    	return false;
    }
    
    
}
