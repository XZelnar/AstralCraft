package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class YC_ItemRSActivator extends Item
{
	protected YC_ItemRSActivator(int par1)
    {
        super(par1);
        setUnlocalizedName("YC_Redstone activator");
        setCreativeTab(CreativeTabs.tabRedstone);
        setMaxStackSize(1);
    } 

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		return !DoThingie(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
	}
	
	//stack, player, world, x,y,z, ? ,In-block x, In-block y, In-block z (last 3 is actual point of hit)
    public boolean DoThingie(ItemStack par1ItemStack,
    		EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
    		int par6, int par7, float par8, float par9, float par10) {
    	int bid = 0;
    	
    	//Block adding method
    	bid = par3World.getBlockId(par4+1, par5, par6);
    	if (bid == YC_Mod.b_rsBlock.blockID)
    	{par3World.setBlockMetadataWithNotify(par4+1, par5, par6, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4+1, par5, par6, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	bid = par3World.getBlockId(par4-1, par5, par6);
    	if (bid == YC_Mod.b_rsBlock.blockID)
		{par3World.setBlockMetadataWithNotify(par4-1, par5, par6, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4-1, par5, par6, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	bid = par3World.getBlockId(par4, par5+1, par6);
    	if (bid == YC_Mod.b_rsBlock.blockID)
		{par3World.setBlockMetadataWithNotify(par4, par5+1, par6, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4, par5+1, par6, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	bid = par3World.getBlockId(par4, par5-1, par6);
    	if (bid == YC_Mod.b_rsBlock.blockID)
		{par3World.setBlockMetadataWithNotify(par4, par5-1, par6, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4, par5-1, par6, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	bid = par3World.getBlockId(par4, par5, par6+1);
    	if (bid == YC_Mod.b_rsBlock.blockID)
		{par3World.setBlockMetadataWithNotify(par4, par5, par6+1, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4, par5, par6+1, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	bid = par3World.getBlockId(par4, par5, par6-1);
    	if (bid == YC_Mod.b_rsBlock.blockID)
		{par3World.setBlockMetadataWithNotify(par4, par5, par6-1, 15, 3);	return true;}
    	if (bid == 0)
		{par3World.setBlockAndMetadataWithNotify(par4, par5, par6-1, YC_Mod.b_rsBlock.blockID, 15, 0);	return true;}

    	
    	//RS replacement method
    	bid = par3World.getBlockId(par4, par5, par6);
    	if (bid == Block.redstoneWire.blockID)
    	{
    		par3World.setBlockAndMetadataWithNotify(par4, par5, par6, YC_Mod.b_rsWire.blockID, 15, 0);
    		return true;
    	}
    	if (bid == YC_Mod.b_rsWire.blockID)
    	{
    		par3World.setBlockMetadataWithNotify(par4, par5, par6, 15, 0);
    		return true;
    	}
    	
    	return true;
    	//return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5,
    	//		par6, par7, par8, par9, par10);
    }
}
