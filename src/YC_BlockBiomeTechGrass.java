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

import net.minecraft.block.Block;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import static net.minecraftforge.common.ForgeDirection.*;

public class YC_BlockBiomeTechGrass extends Block implements BlockProxy
{
    private Random random = new Random();

    protected YC_BlockBiomeTechGrass(int par1)
    {
        super(par1, Material.rock);
        //func_94332_a(YC_ClientProxy.textureMap);
        setHardness(1);
        setResistance(5);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	YC_TileEntityTechGrassRenderer.sides = par1IconRegister.func_94245_a("YC_techGrass");
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_techGrassRenderID;
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Tech Grass";
    }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
    		EntityLiving par5EntityLiving, ItemStack is) {
    	if (!par1World.isRemote)
    	{
    		par1World.setBlockAndMetadataWithNotify(par2, par3, par4, YC_Mod.c_BlockBiomeGrassFirstID + (par2<0?7+par2%8:(par2%8)), (par4<0?7+par4%8:par4%8), 3);
    	}
    }
    
    //TODO
    //@Override
    //public Icon getBlockTextureFromSide(int par1) {
    //	return 64;
    //}
    
    //@Override
    //public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
    //	if (par1 == 1)
    //	{
    //		return (par2%8)*8+(blockID - YC_Mod.c_BlockBiomeGrassFirstID)%8;
    //	}
    //	else
    //	{
    //		return 64;
    //	}
    //}
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
    	return Block.dirt.blockID;
    }
    
    
    
    
    
}
