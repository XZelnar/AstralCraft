package net.minecraft.src;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class YC_BlockPyramidStoneBrick extends Block
{
    public YC_BlockPyramidStoneBrick(int par1)
    {
        super(par1, Material.rock);
        //setCreativeTab(CreativeTabs.tabMisc);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	this.field_94336_cN = par1IconRegister.func_94245_a("stonebricksmooth");
    }

    //TODO
    /*
    @Override
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
    	return 54;
    }//*/

    @Override
    public int damageDropped(int par1)
    {
        return 0;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public int idPicked(World par1World, int par2, int par3, int par4) {
    	return Block.stoneBrick.blockID;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
    	return Block.stoneBrick.blockID;
    }
    
    
    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3,
    		int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
    		float par8, float par9) {
    	if (!par1World.isRemote)
    	{
    		if (HasCrystalCount(par1World, par2, par3, par4, 10) && HasDiamondCount(par1World, par2, par3, par4, 1))
    		{
    			if(YC_ResearchesDataList.Exists(par5EntityPlayer.username) && YC_ResearchesDataList.GetPlayerData(par5EntityPlayer.username).techLevel > 0)
    				return false;
    			YC_ResearchesDataList.CreateIfNotExists(par5EntityPlayer.username);
    			YC_ResearchesData d = YC_ResearchesDataList.GetPlayerData(par5EntityPlayer.username);
    			DecrCrystalCount(par1World, par2, par3, par4, 10);
    			DecrDiamondCount(par1World, par2, par3, par4, 1);
    			d.techLevel = 1;
    			PacketDispatcher.sendPacketToAllAround(par2, par3, par4, 64, par5EntityPlayer.dimension, getDescriptionPacket(par2, par3, par4));
    			return true;
    		}
    	}
    	return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer,
    			par6, par7, par8, par9);
    }
    
	public Packet getDescriptionPacket(int xCoord, int yCoord, int zCoord) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(3);
			outputStream.writeInt(xCoord);
			outputStream.writeInt(yCoord);
			outputStream.writeInt(zCoord);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_FX";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
    
    
    
    
    
    
	
	public boolean HasCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				countt+=((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).stackSize;
				if (countt>=count) return true;
			}
    	}
    	
    	return false;
	}
	
	public void DecrCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
    		ItemStack is = ((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10);
			if (is.itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				if (is.stackSize<=count)
				{
					count -= is.stackSize;
					((EntityItem)a.get(i)).setDead();
				}
				else
				{
					is.stackSize -= count;
				}
				((EntityItem)a.get(i)).setEntityItemStack(is);
				if (count == 0) return;
			}
    	}
	}
	
	public boolean HasDiamondCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == Item.diamond.itemID && !((EntityItem)a.get(i)).isDead)
			{
				countt+=((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).stackSize;
				if (countt>=count) return true;
			}
    	}
    	
    	return false;
	}
	
	public void DecrDiamondCount(World par1World, int par2, int par3, int par4, int count)
	{
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-1.1f, par3, par4-1.1f, par2+2.1f, par3+1.3f, par4+2.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
    		ItemStack is = ((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10);
			if (is.itemID == Item.diamond.itemID && !((EntityItem)a.get(i)).isDead)
			{
				if (is.stackSize<=count)
				{
					count -= is.stackSize;
					((EntityItem)a.get(i)).setDead();
				}
				else
				{
					is.stackSize -= count;
				}
				((EntityItem)a.get(i)).setEntityItemStack(is);
				if (count == 0) return;
			}
    	}
	}
}
