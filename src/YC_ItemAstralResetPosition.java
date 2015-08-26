package net.minecraft.src;

import java.util.Iterator;

import org.lwjgl.util.vector.Matrix;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemAstralResetPosition extends Item
{
	
	protected YC_ItemAstralResetPosition(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("YC_Astral Retransmitter");
        setMaxStackSize(1);
    } 
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		par1ItemStack.stackSize = 0;
		if (par2World.isRemote) return par1ItemStack;
			YC_ServerPlayerAstralDataList.NullifyDataButSaveOrder(par3EntityPlayer.username);
		return par1ItemStack;
	}
}
