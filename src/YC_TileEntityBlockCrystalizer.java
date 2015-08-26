package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.block.Block;
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
import net.minecraft.tileentity.TileEntity;

import cpw.mods.fml.common.network.PacketDispatcher;

public class YC_TileEntityBlockCrystalizer extends TileEntity implements IInventory
{
    public ItemStack[] chestContents = new ItemStack[10];

    /** The number of players currently using this chest */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    public int TimeLeft = 7*60*20;
    public static int MAX_GEN_TIME = 7*60*20;//15 mins
    
    
    
    
    int tempTickCounts = 0;
    @Override
    public void updateEntity() {
    	if (worldObj.isRemote)
    	{
    		Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
	    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		return;
    	}
    	tempTickCounts++;
    	if (tempTickCounts >= 20)
    	{
    		tempTickCounts = 0;
    		//if (TimeLeft == MAX_GEN_TIME) TimeLeft = 200;
    		TimeLeft -= 20;
    		if (chestContents[9] == null || (chestContents[9].getItem() instanceof ItemBlock && 
    						((ItemBlock)chestContents[9].getItem()).getBlockID() == YC_Mod.b_astralCrystals.blockID && 
    						chestContents[9].stackSize <= 60))
    		{
    			if (CanGenerationItemsProceed())
    			{
    				if (TimeLeft <= 0)
    				{
            			TimeLeft = MAX_GEN_TIME;
    					if (chestContents[9] != null)
    					{
    						chestContents[9].stackSize+=4;
    					}
    					else
    					{
    						chestContents[9] = new ItemStack(YC_Mod.b_astralCrystals, 4);
    					}
    					for(int i = 0; i<9; i++)
    					{
    						decrStackSize(i, 1);
    					}
    				}
    			}
    			else
    			{
        			TimeLeft = MAX_GEN_TIME;
    			}
    		}
    		else
    		{
    			TimeLeft = MAX_GEN_TIME;
    		}
    	}
    	if (TimeLeft % 5 == 0)
    	{
    		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 15, worldObj.provider.dimensionId, getDescriptionPacket());
    	}
    }
    
    
    public boolean CanGenerationItemsProceed()
    {
    	for(int i = 0; i<8; i++)
    	{
    		if (chestContents[i] == null || chestContents[i].itemID != Item.coal.itemID)
    			return false;
    	}
    	if (chestContents[8]== null || chestContents[8].itemID != Item.diamond.itemID)
    		return false;
    	return true;
    }
    
    public int getSizeInventory()
    {
        return 10;
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
        return "Research Shard Generator";
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
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
        TimeLeft = par1NBTTagCompound.getInteger("TimeLeft");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("TimeLeft", this.TimeLeft);
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
    }

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
        //    this.numUsingPlayers = par2;
        }
        return true;
    }
    
    public void invalidate()
    {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

	@Override
	public void openChest() {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}

	@Override
	public void closeChest() {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
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
		packet.channel = "YC_Crystalizer";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockCrystalizer chest = this;
        if (intData != null) {
          for(int i = 0; i<10; i++)
          {
        	  if (intData[i*3]!=-1)
        	  {
        		  chestContents[i] = new ItemStack(intData[i*3], intData[i*3+1], intData[i*3+2]);
        	  }
        	  else
        	  {
        		  chestContents[i] = null;
        	  }
          }
          TimeLeft=intData[30];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[31];
          for(int i = 0; i<10; i++)
          {
        	  sortList[i*3] = chestContents[i]==null?-1:chestContents[i].itemID;
        	  sortList[i*3+1] = chestContents[i]==null?-1:chestContents[i].stackSize;
        	  sortList[i*3+2] = chestContents[i]==null?-1:chestContents[i].getItemDamage();
          }
          sortList[30]=TimeLeft;
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
    
    //public int Type()
    //{
    //	//return _Type;
    //	//return chestContents[8]==null?0:1;
    //}
	
}
