package net.minecraft.src;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemSymbolEngine extends Item
{
	protected YC_ItemSymbolEngine(int par1)
    {
		super(par1);
        setUnlocalizedName("YC_Anscient symbol3");
        setCreativeTab(CreativeTabs.tabMaterials);
    } 
	
	
}
