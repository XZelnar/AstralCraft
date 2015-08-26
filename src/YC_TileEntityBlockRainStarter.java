package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockRainStarter extends TileEntity
{
    private int ticksSinceSync;
    
    public int CurState = 0;

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurState=par1NBTTagCompound.getInteger("CurState");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CurState", CurState);
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
		if (!worldObj.isRemote)
		{
			int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if (md < 2)
				if (HasCrystalCount(worldObj, xCoord, yCoord, zCoord, 1))
				{
					DecrCrystalCount(worldObj, xCoord, yCoord, zCoord, 1);
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, md+1, 3);
				}
		}
		
		
    	int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	if ((md >= 1 && CurState<39) || (md >= 2))
    		CurState++;
    	if (CurState >= 100 && md >= 2)
    	{
    		if (CurState >= 130)
    		{
    			if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
    			{
    				if (!worldObj.getWorldInfo().isRaining())
    				{
    					worldObj.getWorldInfo().setRaining(true);
    					worldObj.getWorldInfo().setThundering(true);
    				}
    			}
    			worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
    			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
    			//if (!worldObj.isRemote)
    			{
    				invalidate();
    			}
        		return;
    		}
    	}
    	if (worldObj.isRemote)
    		Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
	
	public boolean HasCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-0.1f, par3, par4-0.1f, par2+1.1f, par3+0.7f, par4+1.1f));

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
    			AxisAlignedBB.getBoundingBox(par2-0.1f, par3, par4-0.1f, par2+1.1f, par3+0.7f, par4+1.1f));

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
		packet.channel = "YC_RainStarter";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockRainStarter chest = this;
        if (intData != null) {
        	CurState = intData[0];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[1];
          sortList[0]=CurState;
          return sortList;
     }
	
}
