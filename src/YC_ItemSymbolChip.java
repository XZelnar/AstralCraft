package net.minecraft.src;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemSymbolChip extends Item
{
	protected YC_ItemSymbolChip(int par1)
    {
		super(par1);
        setUnlocalizedName("YC_Anscient symbol1");
        setCreativeTab(CreativeTabs.tabMaterials);
    } 
	
	
}
