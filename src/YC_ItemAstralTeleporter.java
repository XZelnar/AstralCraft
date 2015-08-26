package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemAstralTeleporter extends Item
{
	protected YC_ItemAstralTeleporter(int par1)
    {
		super(par1);
        setUnlocalizedName("YC_Astral teleporter");
        setCreativeTab(CreativeTabs.tabMisc);
        setMaxStackSize(1);
    } 
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote) return par1ItemStack;

		MinecraftServer mcServer = MinecraftServer.getServer();
		YC_ServerPlayerAstralData d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
		if (d == null)
		{
			if (YC_ServerPlayerAstralDataList.PlayerExists(par3EntityPlayer.username))
			{
				d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
			}
			else
			{
				double ax = ((YC_ServerPlayerAstralDataList.data.size() % 15) * 1600) + 800 + 8;
				double az = ((YC_ServerPlayerAstralDataList.data.size() / 15) * 1600) + 800 + 8;
				if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
				{
					YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
							par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 
							0, ax, 82, az);
				}
				else
				{
					YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
							0, GetHeightAtPotint(0, 0, mcServer.worldServerForDimension(0))+1.5, 0, 
							0, ax, 82, az);
				}
				YC_ServerPlayerAstralDataList.ClearAllRepeating();
				d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
			}
		}
		
		double lx = par3EntityPlayer.posX;
		double ly = par3EntityPlayer.posY;
		double lz = par3EntityPlayer.posZ;
		int ld = par3EntityPlayer.dimension;
		if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
		{
			d.x = par3EntityPlayer.posX;
			d.y = par3EntityPlayer.posY;
			d.z = par3EntityPlayer.posZ;
			par3EntityPlayer.posX = d.AstralXPos;
			par3EntityPlayer.posY = d.AstralYPos;
			par3EntityPlayer.posZ = d.AstralZPos;
			d.dim = par3EntityPlayer.dimension;
			YC_Variables.SaveAstralTeleporters();
			YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, YC_Mod.d_astralDimID, new YC_Teleporter());
		}
		else
		{
			if (d.dim == YC_Mod.d_astralDimID) d.dim = 0;
			par3EntityPlayer.posX = d.x;
			par3EntityPlayer.posY = d.y;
			par3EntityPlayer.posZ = d.z;
			int dimToTp = d.dim;
			YC_Variables.SaveAstralTeleporters();
			YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, dimToTp, new YC_Teleporter());
		}
		
		return par1ItemStack;
	}
	
	public int GetHeightAtPotint(int x, int z, World w)
	{
		for(int i = 128; i>0; i--)
		{
			if (w.getBlockId(x, i, z)!=0)
				return i;
		}
		return 70;
	}
	
	
}
