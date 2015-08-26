package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.GameRegistry;

public class YC_TileEntityBlockAstralBeacon extends TileEntity
{
    private int ticksSinceSync;
    
    public String CurPlayer = "";
    public boolean RenderRay = false;
    
    

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurPlayer = par1NBTTagCompound.getString("CurPlayer");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setString("CurPlayer", CurPlayer);
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
    	ticksSinceSync++;
    	
       	if (ticksSinceSync >= 20)
       	{
       		if (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord))
       		{
       			int id = 0;
       			for (int i = yCoord+1; i<=256; i++)
       			{
       				id = worldObj.getBlockId(xCoord, i, zCoord);
       				if (id != 0)
       				{
       					if (id == Block.blockDiamond.blockID && !CurPlayer.equals(""))
       					{
       						if (worldObj.getBlockId(xCoord-1, i, zCoord) == YC_Mod.b_astralCrystals.blockID &&
       							worldObj.getBlockId(xCoord+1, i, zCoord) == YC_Mod.b_astralCrystals.blockID &&
       							worldObj.getBlockId(xCoord, i, zCoord-1) == YC_Mod.b_astralCrystals.blockID &&
       							worldObj.getBlockId(xCoord, i, zCoord+1) == YC_Mod.b_astralCrystals.blockID &&
       							worldObj.getBlockId(xCoord-1, i, zCoord-1) == Block.blockLapis.blockID &&
       							worldObj.getBlockId(xCoord-1, i, zCoord+1) == Block.blockLapis.blockID &&
       							worldObj.getBlockId(xCoord+1, i, zCoord-1) == Block.blockLapis.blockID &&
       							worldObj.getBlockId(xCoord+1, i, zCoord+1) == Block.blockLapis.blockID)
       						{
       							if (!worldObj.isRemote)
       							{
       								worldObj.setBlockAndMetadataWithNotify(xCoord-1, i, zCoord-1, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord, i, zCoord-1, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord+1, i, zCoord-1, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord-1, i, zCoord, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord, i, zCoord, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord+1, i, zCoord, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord-1, i, zCoord+1, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord, i, zCoord+1, 0, 0, 3);
       								worldObj.setBlockAndMetadataWithNotify(xCoord+1, i, zCoord+1, 0, 0, 3);
       								
       								YC_ResearchesDataList.GetPlayerData(CurPlayer).techLevel++;
       							}
       							else
       							{
       								YC_ClientProxy.SpawnAstralBeaconActivationParticle(worldObj, xCoord, yCoord, zCoord);
       								//YC_ResearchesDataList.GetPlayerData(CurPlayer).techLevel++;
       							}
       						}
       						else
       						{
           						YC_BlockAstralBeacon.onBlockDestroyed(worldObj, xCoord, yCoord, zCoord);
       						}
       					
       						if (!worldObj.isRemote)
       						{
       							worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
       							invalidate();
       						}
       					}
       					else
       					{
       						if (!worldObj.isRemote)
       							YC_BlockAstralBeacon.onBlockDestroyed(worldObj, xCoord, yCoord, zCoord);
       					}
       				}
       			}
       		}
       		
       		
       		
        		ticksSinceSync -= 20;
       	}
       	if (ticksSinceSync % 5 == 1)
       	{
           	if (!worldObj.isRemote)
           	{
           		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 128, 
           				worldObj.provider.dimensionId, getDescriptionPacket());
           	}
           	else
           	{
           		Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
           		RenderRay = new Random().nextBoolean();
           	}
       	}
    }
    
    public void Drop(int id, int c, int damage)
    {
    	ItemStack is = null;
		if (Item.itemsList[id]==null)//Block
		{
			is = new ItemStack(Block.blocksList[id],c);
		}
		else//Item
		{
			is = new ItemStack(Item.itemsList[id],c, damage);
		}
    	EntityItem e = new EntityItem(worldObj, xCoord+0.5f, yCoord+1, zCoord+0.5f, is);
    	e.delayBeforeCanPickup = 20;
		e.motionY = 0.2f;
		worldObj.spawnEntityInWorld(e);
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
		packet.channel = "YC_AstrBeacon";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        //YC_TileEntityBlockAdvAstralTeleporter chest = this;
        if (intData != null) {
            int l = intData[0];
            CurPlayer = "";
            for(int i = 0; i<l; i++)
            {
            	CurPlayer = CurPlayer + ((char)intData[i+1]);
            }
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[257];
    	  char[] t = CurPlayer.toCharArray();
    	  sortList[0]=t.length;
          for(int i = 0; i<t.length; i++)
          {
        	  sortList[1+i]=t[i];
          }
          return sortList;
     }
	
}
