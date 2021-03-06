package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;

public class YC_TileEntityBlockTimeOfDay extends TileEntity
{
    private int ticksSinceSync;
    
    public int CurTime = 0;

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurTime=par1NBTTagCompound.getInteger("CurState");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CurState", CurTime);
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
    		//if (worldObj.getBlockId(xCoord, yCoord, zCoord)!=YC_Mod.b_todDetector.blockID)
    		//{
    		//	worldObj.setBlockWithNotify(xCoord, yCoord, zCoord, 0);
    		//	worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, null);
    		//	return;
    		//}
    		CurTime = (int) worldObj.getWorldTime();
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
		packet.channel = "YC_TimeOfDay";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockTimeOfDay chest = this;
        if (intData != null) {
        	CurTime = intData[0];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[1];
          sortList[0]=CurTime;
          return sortList;
     }
	
}
