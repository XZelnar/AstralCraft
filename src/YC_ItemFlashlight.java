package net.minecraft.src;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemFlashlight extends Item
{
	protected YC_ItemFlashlight(int par1)
    {
		super(par1);
        setUnlocalizedName("YC_Flashlight");
        setCreativeTab(CreativeTabs.tabRedstone);
    } 
	
	//par4==Place in hotbar; par5 = is in hand
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		if (par2World.isRemote)
		{
			return;
		}
		if (par5)
		{
			if (par3Entity.posY < 0) return;
			int x = (int) (par3Entity.posX<0?par3Entity.posX-1:par3Entity.posX);
			int z = (int) (par3Entity.posZ<0?par3Entity.posZ-1:par3Entity.posZ);
			if (par2World.getBlockId(x, (int)par3Entity.posY+1, z)==0)
			{
				par2World.setBlockAndMetadataWithNotify(x, (int)par3Entity.posY+1, z, 
						YC_Mod.b_lightBlock.blockID, 6, 3);
				return;
			}
			if (par2World.getBlockId(x, (int)par3Entity.posY+1, z)==YC_Mod.b_lightBlock.blockID)
			{
				par2World.setBlockMetadataWithNotify(x, (int)par3Entity.posY+1, z, 6, 3);
				return;
			}
		}
		super.onUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	
}
