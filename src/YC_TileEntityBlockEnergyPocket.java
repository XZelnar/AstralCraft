package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class YC_TileEntityBlockEnergyPocket extends TileEntity
{
    private int ticksSinceSync;
    public float xRot = 0, yRot = 0, zRot = 0;
    public static Random rand = new Random();
    public float scale = 1f;

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    @Override
    public void updateEntity() {
		float md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		if (md != 0) //10 crystals required to operate
		{
			if (scale < 1 + md / 10f)
			{
				scale += 0.02f;
			}
		}
		if (md >= 10 && scale >= 1.95f)
		{
			if (!worldObj.isRemote)
			{
				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 32, worldObj.getWorldInfo().getDimension(), getDescriptionPacket());
			}
			//create explosion
			YC_ExplosionEP var11 = new YC_ExplosionEP(worldObj, null, xCoord+0.5d, yCoord+0.5d, zCoord+0.5d, 30);
	        var11.isFlaming = false;
	        var11.isSmoking = true;
	        var11.doExplosionA();
	        var11.doExplosionB(true);
	        worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
	        invalidate();
	        return;
	        
		}
		
		
		
    	if (worldObj.isRemote)
    	{
    		xRot += rand.nextFloat()*5f;
    		yRot += rand.nextFloat()*5f;
    		zRot += rand.nextFloat()*5f;
    		xRot += 9;
    		yRot += 8;
    		if (worldObj.getBlockId(xCoord-1, yCoord, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord+1, yCoord, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord-1, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord, zCoord-1) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord, zCoord+1) == 0)
    			Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    	}
    	else
    	{
    		if (md < 10 && HasCrystalCount(worldObj, xCoord, yCoord, zCoord, 1))
    		{
    			DecrCrystalCount(worldObj, xCoord, yCoord, zCoord, 1);
    			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, (int)md+1, 3);
    		}
    		ticksSinceSync++;
    		if (ticksSinceSync > 10)
    		{
    			if (worldObj.getBlockId(xCoord-1, yCoord, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord+1, yCoord, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord-1, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord, zCoord-1) == 0 || 
    			worldObj.getBlockId(xCoord, yCoord, zCoord+1) == 0)
    			{
    				PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 32, worldObj.getWorldInfo().getDimension(), getDescriptionPacket());
    			}
    			ticksSinceSync-=10;
    		}
    	}
    }
	
	public boolean HasCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-0.6f, par3, par4-0.6f, par2+1.6f, par3+1.3f, par4+1.6f));

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
    			AxisAlignedBB.getBoundingBox(par2-0.6f, par3, par4-0.6f, par2+1.6f, par3+1.3f, par4+1.6f));

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
    
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(xCoord);
			outputStream.writeInt(yCoord);
			outputStream.writeInt(zCoord);
			
			int[] t = buildIntDataList();
			for(int i = 0; i<t.length; i++)
			{
				outputStream.writeInt(t[i]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_EnergyPocket";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        //YC_TileEntityBlockAdvAstralTeleporter chest = this;
        if (intData != null) {
            //worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, intData[0], 0);
        	scale = intData[0] / 1000f;
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[1];
    	  //sortList[0]=worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
          sortList[0] = (int)(scale * 1000);
          return sortList;
     }
	
}
