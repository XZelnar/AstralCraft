package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockRainStopper extends TileEntity
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
    	int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	if ((md >= 1 && CurState<39) || (md >= 2))
    		CurState++;
    	if (CurState >= 100 && md >= 2)
    	{
    		if (CurState >= 150)
    		{
        		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && worldObj.getWorldInfo().isRaining())
        		{
        	        worldObj.getWorldInfo().setRaining(false);
        	        worldObj.getWorldInfo().setThundering(false);
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
		packet.channel = "YC_RainStopper";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockRainStopper chest = this;
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
