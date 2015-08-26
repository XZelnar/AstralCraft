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
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import static net.minecraftforge.common.ForgeDirection.*;

public class YC_BlockSymbolInput extends BlockContainer implements BlockProxy
{
    private Random random = new Random();

    protected YC_BlockSymbolInput(int par1)
    {
        super(par1, YC_Mod.m_blockCircuit);
        //func_94332_a(YC_ClientProxy.textureMap);
        //setCreativeTab(CreativeTabs.tabBlock);
        setHardness(2);
        setResistance(5);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("stone");
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
    	return 1;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
    	return 0;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return true;
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	//TODO Create explosion
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "";
    }

    @Override
    public TileEntity createNewTileEntity(World par1World)
    {
    	return new YC_TileEntityBlockSymbolInput();
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
    	return new YC_TileEntityBlockSymbolInput();
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_symbolInputRenderID;
    }
    
    public TileEntity GetTileEntity(World w)
    {
    	return new YC_TileEntityBlockSymbolInput();
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y,
    		int z, EntityPlayer player, int par6, float par7,
    		float par8, float par9) {

    	
    	//YC_TileEntitySymbolInput te = (YC_TileEntitySymbolInput) world.getBlockTileEntity(x, y, z);
    	//te.WestFace = 11;
    	//te.EastFace = 11;
    	//te.SouthFace = 11;
    	//te.NorthFace = 11;
    	//return true;
    	YC_TileEntityBlockSymbolInput w = (YC_TileEntityBlockSymbolInput)world.getBlockTileEntity(x, y, z);
		player.openGui(YC_Mod.instance, YC_Mod.c_symbolInputRenderID, world, x, y, z);
		return true;
    }
    
    
    
}
