package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class YC_TileEntityBlockResearchTable extends TileEntity implements IInventory
{
    public ItemStack[] chestContents = new ItemStack[1];
    public int numUsingPlayers;
    private int ticksSinceSync;
    
    public int TimeRemaing = 0, ResearchID = -1, TimeCanResearch = 0;
    public String PlayerName = "";
    
    @Override
    public void updateEntity() {
    	if (worldObj.isRemote) return;
    	
    	if (chestContents != null && chestContents[0] != null)
    	{
    		Item i = chestContents[0].getItem();
    		if (i instanceof ItemBlock)
    		{
    			int bi = ((ItemBlock)i).getBlockID();
    			if (bi == YC_Mod.b_rLava.blockID)
    			{
    				YC_ResearchesDataList.AddToPlayer(PlayerName, 0, chestContents[0].stackSize, 0, 0);
    				chestContents[0] = null;
    				PacketDispatcher.sendPacketToPlayer(YC_ResearchesDataList.GetPlayerData(PlayerName).getDescriptionPacket(), 
    						(Player) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(PlayerName));
    			}
    			if (bi == YC_Mod.b_rWater.blockID)
    			{
    				YC_ResearchesDataList.AddToPlayer(PlayerName, chestContents[0].stackSize, 0, 0, 0);
    				chestContents[0] = null;
    				PacketDispatcher.sendPacketToPlayer(YC_ResearchesDataList.GetPlayerData(PlayerName).getDescriptionPacket(), 
    						(Player) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(PlayerName));
    			}
    			if (bi == YC_Mod.b_rWood.blockID)
    			{
    				YC_ResearchesDataList.AddToPlayer(PlayerName, 0, 0, chestContents[0].stackSize, 0);
    				chestContents[0] = null;
    				PacketDispatcher.sendPacketToPlayer(YC_ResearchesDataList.GetPlayerData(PlayerName).getDescriptionPacket(), 
    						(Player) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(PlayerName));
    			}
    		}
    		else
    		{
    			if (i.itemID == YC_Mod.i_rEnderKnowledgePiece.itemID)
    			{
    				YC_ResearchesDataList.AddToPlayer(PlayerName, 0, 0, 0, chestContents[0].stackSize);
    				chestContents[0] = null;
    				PacketDispatcher.sendPacketToPlayer(YC_ResearchesDataList.GetPlayerData(PlayerName).getDescriptionPacket(), 
    						(Player) FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getPlayerForUsername(PlayerName));
    			}
    			else
    			{
    				if (TimeCanResearch <= 0 && TimeRemaing > 0)
    				{
    					if (chestContents != null && chestContents[0] != null)
    					{
    						if (i.itemID == YC_Mod.i_EssenceOfKnowledge.itemID)
    						{
    							TimeCanResearch = 20 * YC_Options.ResearchShardTimeValue / 
    									YC_Options.ResearchSpeed;
    							chestContents[0].stackSize--;
    							if (chestContents[0].stackSize <= 0)
    								chestContents[0] = null;
    						}
    					}
    				}
    			}
    		}//else
    	}
    	
    	
    	if (ResearchID != -1)
    	{
    		if (TimeRemaing > 0)
    		{
    			if (TimeCanResearch > 0)
    			{
    				TimeRemaing--;
    				TimeCanResearch--;
    			}
    		}
    		else
    		{
    			((YC_ResearchData)YC_ResearchesDataList.GetPlayerData(PlayerName).researches.get(ResearchID)).researched = true;
    			ResearchID = -1;
    			YC_ResearchesDataList.IncResearchLevel(PlayerName);
    		}
    	}
    	ticksSinceSync ++;
    	if (ticksSinceSync >= 20)
    	{
    		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 30, worldObj.provider.dimensionId, getDescriptionPacket());
    	}
    }

    public int getSizeInventory()
    {
        return 1;
    }

    public ItemStack getStackInSlot(int par1)
    {
        return this.chestContents[par1];
    }

    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.chestContents[par1] != null)
        {
            ItemStack var3;

            if (this.chestContents[par1].stackSize <= par2)
            {
                var3 = this.chestContents[par1];
                this.chestContents[par1] = null;
                this.onInventoryChanged();
                
        
                return var3;
            }
            else
            {
                var3 = this.chestContents[par1].splitStack(par2);

                if (this.chestContents[par1].stackSize == 0)
                {
                    this.chestContents[par1] = null;
                }

                this.onInventoryChanged();
                
        
                return var3;
            }
        }
        else
        {
            return null;
        }
        
    }

    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.chestContents[par1] != null)
        {
            ItemStack var2 = this.chestContents[par1];
            this.chestContents[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
    }

    public String getInvName()
    {
        return "ResearchTable";
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);

        TimeCanResearch = par1NBTTagCompound.getInteger("TimeCanResearch");
        TimeRemaing = par1NBTTagCompound.getInteger("TimeRemain");
        //ResearchLevel= par1NBTTagCompound.getInteger("TimeTotal");
        ResearchID= par1NBTTagCompound.getInteger("ResearchID");
        PlayerName= par1NBTTagCompound.getString("PlayerName");
        
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        
        this.chestContents = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.chestContents.length)
            {
                this.chestContents[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.chestContents.length; ++var3)
        {
            if (this.chestContents[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.chestContents[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);

        par1NBTTagCompound.setString("PlayerName", PlayerName);
        par1NBTTagCompound.setInteger("ResearchID", ResearchID);
        //par1NBTTagCompound.setInteger("TimeTotal", ResearchLevel);
        par1NBTTagCompound.setInteger("TimeRemain", TimeRemaing);
        par1NBTTagCompound.setInteger("TimeCanResearch", TimeCanResearch);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
        }
        return true;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
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
		packet.channel = "YC_Research";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public Packet getDescriptionPacketS(int resID) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(xCoord);
			outputStream.writeInt(yCoord);
			outputStream.writeInt(zCoord);
			outputStream.writeInt(Minecraft.getMinecraft().thePlayer.dimension);

			int[] t = buildIntDataList();
			for(int i = 0; i<t.length; i++)
			{
				outputStream.writeInt(t[i]);
			}
			outputStream.writeInt(resID);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_ResearchS";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketDataS(int[] intData) {
        YC_TileEntityBlockResearchTable chest = this;
        if (intData != null) {
          for(int i = 0; i<1; i++)
          {
        	  if (intData[i*3]!=-1 && intData[i*3+1]!=-1 && intData[i*3+2]!=-1)
        	  {
        		  chestContents[i] = new ItemStack(intData[i*3], intData[i*3+1], intData[i*3+2]);
        	  }
        	  else
        	  {
        		  chestContents[i] = null;
        	  }
          }
          TimeRemaing = intData[3];
          ResearchID = intData[4];
          //ResearchLevel = intData[5];
          int l = intData[6];
          PlayerName = "";
          for(int i = 0; i<l; i++)
          {
        	  PlayerName = PlayerName + ((char)intData[i+7]);
          }
          ResearchID = intData[intData.length - 1];
          TimeRemaing = (YC_ResearchesDataList.GetPlayerData(PlayerName).researchLevel + 1) * 20 * YC_Options.ResearchShardTimeValue / 
				YC_Options.ResearchSpeed;
        }
     }
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockResearchTable chest = this;
        if (intData != null) {
          for(int i = 0; i<1; i++)
          {
        	  if (intData[i*3]!=-1 && intData[i*3+1]!=-1 && intData[i*3+2]!=-1)
        	  {
        		  chestContents[i] = new ItemStack(intData[i*3], intData[i*3+1], intData[i*3+2]);
        	  }
        	  else
        	  {
        		  chestContents[i] = null;
        	  }
          }
          TimeRemaing = intData[3];
          ResearchID = intData[4];
          //ResearchLevel = intData[5];
          int l = intData[6];
          PlayerName = "";
          for(int i = 0; i<l; i++)
          {
        	  PlayerName = PlayerName + ((char)intData[i+7]);
          }
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[6+257];
          for(int i = 0; i<1; i++)
          {
        	  sortList[i*3] = chestContents[i]==null?-1:chestContents[i].itemID;
        	  sortList[i*3+1] = chestContents[i]==null?-1:chestContents[i].stackSize;
        	  sortList[i*3+2] = chestContents[i]==null?-1:chestContents[i].getItemDamage();
          }
          sortList[3] = TimeRemaing;
          sortList[4] = ResearchID;
          //sortList[5] = ResearchLevel;
    	  char[] t = PlayerName.toCharArray();
    	  sortList[6]=t.length;
          for(int i = 0; i<t.length; i++)
          {
        	  sortList[7+i]=t[i];
          }
          return sortList;
     }

	@Override
	public boolean func_94042_c() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean func_94041_b(int i, ItemStack itemstack) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
