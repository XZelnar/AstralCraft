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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_ItemGravityImplosionGrenade extends Item
{
	//public static int cooldown = 100;
	
	protected YC_ItemGravityImplosionGrenade(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("YC_Gravity Implosion Grenade");
        //setMaxDamage(cooldown);
    } 
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (!par2World.isRemote)
		{
			//if (par1ItemStack.getItemDamage() != 1) return par1ItemStack;
    		Entity e = new YC_EntityGravityImplosionGrenade(par2World, par3EntityPlayer);
    		par2World.spawnEntityInWorld(e);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            //par1ItemStack.setItemDamage(cooldown);
    		par1ItemStack.stackSize --;
		}
		return par1ItemStack;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		/*
		if (!par2World.isRemote)
		{
			if (par1ItemStack.getItemDamage()>1)
				par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);

			if (par1ItemStack.getItemDamage()<1)
				par1ItemStack.setItemDamage(1);
		}//*/
	}
}
