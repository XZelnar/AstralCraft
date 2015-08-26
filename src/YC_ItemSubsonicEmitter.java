package net.minecraft.src;

import java.util.List;

import org.lwjgl.input.Mouse;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class YC_ItemSubsonicEmitter extends Item 
{

	public YC_ItemSubsonicEmitter(int par1) {
		super(par1);
		setCreativeTab(CreativeTabs.tabMisc);
		setMaxDamage(750);
		setMaxStackSize(1);
		setUnlocalizedName("YC_ACSubsonicEmitter"); 
	} 
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote) return par1ItemStack;
		ItemStack r = par1ItemStack.copy();
		
    	List a = par2World.getEntitiesWithinAABB(EntityGhast.class, 
    			AxisAlignedBB.getBoundingBox(par3EntityPlayer.posX-110f, par3EntityPlayer.posY-100f, par3EntityPlayer.posZ-110f, 
    					par3EntityPlayer.posX+110f, par3EntityPlayer.posY+100f, par3EntityPlayer.posZ+110f));
		r.setItemDamage(par1ItemStack.getItemDamage() + a.size() * 2);
		EntityGhast e;
		for(int i = 0; i < a.size(); i++)
		{
			e = (EntityGhast) a.get(i);
			float dx = (float) (e.posX - par3EntityPlayer.posX);
			float dz = (float) (e.posZ - par3EntityPlayer.posZ);
			if (Math.abs(dx) > Math.abs(dz))
			{
				e.motionX = Math.signum(dx);
			}
			else
			{
				e.motionZ = Math.signum(dz);
			}
		}
		
		if (r.getItemDamage() >= 1000)
		{
			r.stackSize = 0;
		}
		return r;
	}

}
