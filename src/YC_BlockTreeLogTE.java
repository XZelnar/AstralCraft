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
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class YC_BlockTreeLogTE extends Block
{
    public YC_BlockTreeLogTE(int par1)
    {
        super(par1, Material.wood);
        setHardness(2f);
        setResistance(2);
        setStepSound(soundWoodFootstep);
        setUnlocalizedName("YC_Log"); 
    } 
    
    @Override
    public String getUnlocalizedName() {
    	return "Log";
    }
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new YC_TileEntityBlockTreeLog();
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return YC_Mod.b_treeLog.blockID;
	}
	
	
}
