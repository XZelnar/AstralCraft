package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class YC_ItemCrystalPowder extends Item
{
	protected YC_ItemCrystalPowder(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabMaterials);
        setUnlocalizedName("YC_Crystal Powder");
    } 

	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (!par3World.isRemote && par3World.provider instanceof YC_WorldProviderAstral)
		{
			int id = par3World.getBlockId(par4, par5, par6);
			if (id == Block.grass.blockID || id == Block.dirt.blockID)
			{
				par1ItemStack.stackSize--;
				if (par1ItemStack.stackSize <= 0) par1ItemStack = null;
				YC_WorldGenAstral.AddTechGrassBlock(par3World, par4, par5, par6);
				return false;
			}
			if (id >= YC_Mod.b_biomeTechGrass[0].blockID && id <= YC_Mod.b_biomeTechGrass[0].blockID+7)
			{
				par1ItemStack.stackSize--;
				if (par1ItemStack.stackSize <= 0) par1ItemStack = null;
				par3World.setBlockAndMetadataWithNotify(par4, par5, par6, Block.grass.blockID, 0, 3);
				return false;
			}
		}
		return false;
	}
}
