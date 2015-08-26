package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

public class YC_TileEntityBlockDoorStateDetector extends TileEntity
{
    private int ticksSinceSync;
    
    public int CurState = 0;
    public int CurMD = -1;
    public boolean isHandOpened = false;

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurMD = par1NBTTagCompound.getInteger("CurY");
        CurState = par1NBTTagCompound.getInteger("CurState");
        isHandOpened = par1NBTTagCompound.getBoolean("isHandOpened");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CurState", CurState);
        par1NBTTagCompound.setInteger("CurY", CurMD);
        par1NBTTagCompound.setBoolean("isHandOpened", isHandOpened);
    }

    @Override
    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }
    
    @Override
    public void updateEntity() {
    	if (worldObj.isRemote) return;
    	int id = worldObj.getBlockId(xCoord, yCoord+1, zCoord);
    	if (id != Block.doorSteel.blockID && id != Block.doorWood.blockID)
    	{
    		CurMD = 0;
    		CurState = 0;
        	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, CurState, 3);
    		return;
    	}
    	
    	int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	int md1 = worldObj.getBlockMetadata(xCoord, yCoord+1, zCoord);
    	
    	if (id == Block.doorSteel.blockID)
    	{
    		//works for now... but not with RSToggler
    		if (((BlockDoor)Block.doorSteel).isDoorOpen(worldObj, xCoord, yCoord+1, zCoord)) CurState = 1;
    		else CurState = 0;
    	}
    	else
    	{
    		//think this should work...
    		if ((worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord+1, zCoord)) && //TODO removed IsBlockGettingPowered
				!(worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord, zCoord, 0) > 0))
    		{
    			CurState = 1;
    			isHandOpened = false;
    		}
    		else
    		{
    			if (worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord, zCoord, 0) > 0)
    			{
    				if (worldObj.isBlockIndirectlyProvidingPowerTo(xCoord+1, yCoord+1, zCoord, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord-1, yCoord+1, zCoord, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord+1, zCoord+1, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord+1, zCoord-1, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord+1, yCoord+2, zCoord, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord-1, yCoord+2, zCoord, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord+2, zCoord+1, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord+2, zCoord-1, 0) > 0 ||
    						worldObj.isBlockIndirectlyProvidingPowerTo(xCoord, yCoord+3, zCoord, 0) > 0)
    				{
    					isHandOpened = false;
    				}
    			}
    			else
    			{
    				if (CurMD == md1)
    				{
    					if (isHandOpened)
    					{
    						CurState = md1>=4 ? 1 : 0;
    					}
    					else
    					{
    						CurState = 0;
    					}
    				}
    				else
    				{
    					if (md1>=4)
    					{
    						CurState = 1;
    						isHandOpened = true;
    					}
    					else
    					{
    						CurState = 0;
    						isHandOpened = false;
    					}
    					//CurState = md1>=4 ? 1 : 0;
    				}
    			}
    		}
    	}
    	CurMD = md1;
    	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, CurState, 3);
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
		packet.channel = "YC_DSD";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockDoorStateDetector chest = this;
        if (intData != null) {
        	CurState = intData[0];
        	CurMD = intData[1];
        	isHandOpened = intData[2] == 1;
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[3];
          sortList[0] = CurState;
          sortList[1] = CurMD;
          sortList[2] = isHandOpened ? 1 : 0;
          return sortList;
     }
	
}
