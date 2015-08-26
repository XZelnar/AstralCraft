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

public class YC_TileEntityBlockAdvAstralTeleporter extends TileEntity
{
    private int ticksSinceSync;
    
    public String CurPlayer = "";
    public short particleSpawnOnTick = 255;
    boolean HasParticle = false;
    int md = 0;
    //public static YC_EntityFXAdvAstralPortal particle = null;
    
    

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
	
	public void AddParticleToRemoveList(int par2, int par3, int par4)
	{
		boolean add = true;
    	for (int i = 0; i<YC_ClientProxy.AdvAstTelRemoveCoord.size(); i+=3)
    	{
    		if (    ((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i  )) == par2 || 
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+1)) == par3 ||
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+2)) == par4)
    		{
    			add = false;
    			break;
    		}
    	}
    	if (add)
    	{
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par2);
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par3);
    		YC_ClientProxy.AdvAstTelRemoveCoord.add(par4);
    	}
	}
    
    @Override
    public void updateEntity() {
    	if (worldObj.isRemote)
    	{
    		if (YC_ClientTickHandler.TicksInGame % 80 == particleSpawnOnTick && !CurPlayer.equals("") && 
    				md == 1)
    		{
    			YC_ClientProxy.AddAdvAstTelParticle(xCoord, yCoord, zCoord, worldObj);
    			HasParticle = true;
    		}
    		if (particleSpawnOnTick == 255 && !CurPlayer.equals("") && 
    				md == 1)
    		{
    			YC_ClientProxy.AddAdvAstTelParticle(xCoord, yCoord, zCoord, worldObj);
    			particleSpawnOnTick = (short) (YC_ClientTickHandler.TicksInGame % 80);
    			HasParticle = true;
    		}
    		if (CurPlayer.equals("") || md == 0)
    		{
    			if (HasParticle)
    			{
    				HasParticle = false;
    				AddParticleToRemoveList(xCoord, yCoord, zCoord);
    			}
    		}
    		
    		//if (ticksSinceSync >= 5)
    		//{
    		//	ticksSinceSync -= 5;

    			Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    	    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		//}
    		return;
    	}
    	
    	if (worldObj.getBlockId(xCoord, yCoord, zCoord) != YC_Mod.b_advAstralTeleporter.blockID) invalidate();

    	ticksSinceSync++;
    	if (ticksSinceSync%5==0)
    	{
    		//ticksSinceSync = 0;
    		if (!CurPlayer.equals(""))
    		{
    			if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0)
    			{
    				List e = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, 
    						xCoord + 1, yCoord + 1, zCoord + 1));
    				for(int i = 0; i<e.size(); i++)
    				{
    					EntityItem t = (EntityItem) e.get(i);
    					if (t != null && t.getDataWatcher().getWatchableObjectItemStack(10) != null && t.getDataWatcher().getWatchableObjectItemStack(10).getItem() instanceof ItemBlock && 
    							((ItemBlock)t.getDataWatcher().getWatchableObjectItemStack(10).getItem()).getBlockID() == YC_Mod.b_astralCrystals.blockID)
    					{
    						if (t.getDataWatcher().getWatchableObjectItemStack(10).stackSize == 1)
    						{
    							t.setDead();
    						}
    						else
    						{
    							t.getDataWatcher().getWatchableObjectItemStack(10).stackSize--;
    						}
    						worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 3);
    					}
    				}
    			}
    			else
    			{
    				List e = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, 
    						xCoord + 1, yCoord + 2, zCoord + 1));
    				if (e != null && e.size() > 0 && e.get(0) != null)
    				{
    					MinecraftServer mcServer = MinecraftServer.getServer();
    					EntityPlayer par3EntityPlayer = (EntityPlayer) e.get(0);
    					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 3);
    					//Copied
    					YC_ServerPlayerAstralData d = YC_ServerPlayerAstralDataList.GetPlayerData(CurPlayer);
    					if (d == null)
    					{
    						if (YC_ServerPlayerAstralDataList.PlayerExists(CurPlayer))
    						{
    							d = YC_ServerPlayerAstralDataList.GetPlayerData(CurPlayer);
    						}
    						else
    						{
    							double ax = ((YC_ServerPlayerAstralDataList.data.size() % 15) * 1600) + 800 + 8;
    							double az = ((YC_ServerPlayerAstralDataList.data.size() / 15) * 1600) + 800 + 8;
    							if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
    							{
    								YC_ServerPlayerAstralDataList.Add(CurPlayer, 
    										par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 
    										0, ax, 82, az);
    							}
    							else
    							{
    								YC_ServerPlayerAstralDataList.Add(CurPlayer, 
    										0, GetHeightAtPotint(0, 0, mcServer.worldServerForDimension(0))+1.5, 0, 
    										0, ax, 82, az);
    							}
    							YC_ServerPlayerAstralDataList.ClearAllRepeating();
    							d = YC_ServerPlayerAstralDataList.GetPlayerData(CurPlayer);
    						}
    					}
    					YC_ServerPlayerAstralData d1 = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
    					if (d1 == null)
    					{
    						if (YC_ServerPlayerAstralDataList.PlayerExists(par3EntityPlayer.username))
    						{
    							d1 = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
    						}
    						else
    						{
    							double ax = ((YC_ServerPlayerAstralDataList.data.size() % 15) * 1600) + 800 + 8;
    							double az = ((YC_ServerPlayerAstralDataList.data.size() / 15) * 1600) + 800 + 8;
    							if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
    							{
    								YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
    										par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 
    										0, ax, 82, az);
    							}
    							else
    							{
    								YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
    										0, GetHeightAtPotint(0, 0, mcServer.worldServerForDimension(0))+1.5, 0, 
    										0, ax, 82, az);
    							}
    							YC_ServerPlayerAstralDataList.ClearAllRepeating();
    							d1 = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
    						}
    					}
    					
    					double lx = par3EntityPlayer.posX;
    					double ly = par3EntityPlayer.posY;
    					double lz = par3EntityPlayer.posZ;
    					int ld = par3EntityPlayer.dimension;
    					if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
    					{
    						d1.x = par3EntityPlayer.posX;
    						d1.y = par3EntityPlayer.posY;
    						d1.z = par3EntityPlayer.posZ;
    						par3EntityPlayer.posX = d.AstralXPos;
    						par3EntityPlayer.posY = d.AstralYPos;
    						par3EntityPlayer.posZ = d.AstralZPos;
    						d1.dim = par3EntityPlayer.dimension;
    						YC_Variables.SaveAstralTeleporters();
    						
    						YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, YC_Mod.d_astralDimID, new YC_Teleporter());
    						par3EntityPlayer.posX = d.AstralXPos;
    						par3EntityPlayer.posY = d.AstralYPos;
    						par3EntityPlayer.posZ = d.AstralZPos;
    					    
    						//YC_ServerPlayerAstralDataList.SetXYZD(par3EntityPlayer.username, lx,ly,lz,ld);
    					}
    					else
    					{
    						if (d1.dim == YC_Mod.d_astralDimID) d1.dim = 0;
    						par3EntityPlayer.posX = d1.x;
    						par3EntityPlayer.posY = d1.y;
    						par3EntityPlayer.posZ = d1.z;
    						YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, d1.dim, new YC_Teleporter());
    						par3EntityPlayer.posX = d1.x;
    						par3EntityPlayer.posY = d1.y;
    						par3EntityPlayer.posZ = d1.z;
    					}
    				}
    			}
    		}
    	}
    	if (ticksSinceSync >= 10)
    	{
    		ticksSinceSync -= 10;
    		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 64, 
    				worldObj.provider.dimensionId, getDescriptionPacket());
    	}
    }
    
	public int GetHeightAtPotint(int x, int z, World w)
	{
		for(int i = 128; i>0; i--)
		{
			if (w.getBlockId(x, i, z)!=0)
				return i;
		}
		return 70;
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
		packet.channel = "YC_AdvAstrTel";
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
            md = intData[257];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[258];
    	  char[] t = CurPlayer.toCharArray();
    	  sortList[0]=t.length;
          for(int i = 0; i<t.length; i++)
          {
        	  sortList[1+i]=t[i];
          }
          sortList[257] = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
          return sortList;
     }
	
}
