package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

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

public class YC_TileEntityBlockWorkbench extends TileEntity implements IInventory
{
    public ItemStack[] chestContents = new ItemStack[24];
    public int FuelValue = 0;
    public int MaxFuel = 100;
    public int FuelDelta = 10;
    
    public int FuelCurTexture = 0;

    /** The number of players currently using this chest */
    public int numUsingPlayers;
    public EntityPlayer curPlayer = null;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    //public int _Type = 14189;//0 == Equals; 1 == Not equals

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return 24;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.chestContents[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
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
                
                
                SlotChanged(par1);
        
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
                
                
                SlotChanged(par1);
        
                return var3;
            }
        }
        else
        {
            return null;
        }
        
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
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

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
        
        SlotChanged(par1);
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Wokrbench";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        
        FuelDelta = par1NBTTagCompound.getInteger("FuelDelta");
        FuelValue= par1NBTTagCompound.getInteger("FuelValue");
        
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
        
        par1NBTTagCompound.setInteger("FuelValue", FuelValue);
        par1NBTTagCompound.setInteger("FuelDelta", FuelDelta);
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
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
	}

	@Override
	public void closeChest() {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
        curPlayer = null;
	}
	
	@Override
	public void updateEntity() {
		//if (worldObj.isRemote) return;
		CheckConsumeFuel();
		if (!worldObj.isRemote)
		{
			ticksSinceSync++;
			if (ticksSinceSync >= 10)
			{
				ticksSinceSync = 0;
				if (curPlayer != null)
				{
					PacketDispatcher.sendPacketToPlayer(getDescriptionPacket(), (Player) curPlayer);
				}
			}
		}
	}
	
	
	
	public void CheckConsumeFuel()
	{
		if (chestContents[22]!= null  && chestContents[22].getItem()!=null &&
			chestContents[22].getItem() instanceof ItemBlock &&
			((ItemBlock)chestContents[22].getItem()).getBlockID() == YC_Mod.b_astralCrystals.blockID)
		{
			if (FuelValue <= MaxFuel - FuelDelta)
			{
				DoReactOnSlotChange = false;
				decrStackSize(22, 1);
				FuelValue += FuelDelta;
				
				if (chestContents[23] == null)
				{
					CheckCraftingGrid();
				}
				
				DoReactOnSlotChange = true;
			}
		}
	}
	
	boolean DoReactOnSlotChange = true;
	public void SlotChanged(int slot)
	{
		//if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER) return;
		if (!DoReactOnSlotChange) return;
		
		//============================================CRAFTING===========================================================
		if (slot<=20)
		{
			CheckCraftingGrid();
		}
		
		if (slot == 23)
		{
			DoReactOnSlotChange = false;
			String[] grid = GetGrid();
			int ri = YC_WorkbenchRecipes.GetRecipe(grid);
			if (ri>-1)
			{
				FuelValue -= YC_WorkbenchRecipes.recipes[ri].FuelCost;
			}
			
			for(int i = 0; i<=20; i++)
			{
				decrStackSize(i, 1);
			}
			DoReactOnSlotChange = true;
		}
		//==============================================FUEL=============================================================
		
		if (slot == 22)
		{
			if (chestContents[22] != null && chestContents[22].getItem() != null &&
					chestContents[22].getItem() instanceof ItemBlock &&
					((ItemBlock)chestContents[22].getItem()).getBlockID() == YC_Mod.b_astralCrystals.blockID)
			{
				if (FuelValue <= MaxFuel - FuelDelta)
				{
					DoReactOnSlotChange = false;
					decrStackSize(22, 1);
					FuelValue += FuelDelta;
					
					if (chestContents[23] == null)
					{
						CheckCraftingGrid();
					}
					
					DoReactOnSlotChange = true;
				}
			}
		}
		
		if (!worldObj.isRemote)
			PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 10, worldObj.provider.dimensionId, getDescriptionPacket());
	}
	
	public void CheckCraftingGrid()
	{
		String[] grid = GetGrid();
		int ri = YC_WorkbenchRecipes.GetRecipe(grid);
		
		DoReactOnSlotChange = false;
		if (ri != -1)
		{
		    YC_WorkbenchRecipe tr = YC_WorkbenchRecipes.recipes[ri];
		    YC_ResearchesData tre = null;
		    if (curPlayer != null)
		    {
		    	YC_ResearchesDataList.CreateIfNotExists(curPlayer.username);
		    	tre = ((YC_ResearchesData)YC_ResearchesDataList.GetPlayerData(curPlayer.username));
		    }
		    if (tre != null && (tr.ResearchIndex == -1 || ((YC_ResearchData)tre.researches.get(tr.ResearchIndex)).researched))
		    {
		    	if (YC_WorkbenchRecipes.recipes[ri].output != chestContents[23])
				{
					if (YC_WorkbenchRecipes.recipes[ri].FuelCost <= FuelValue)
					{
						chestContents[23] = YC_WorkbenchRecipes.recipes[ri].output.copy();
					}
					else
					{
						chestContents[23] = null;
					}
				}
		    }
			else
			{
				chestContents[23] = null;
			}
		}
		else
		{
			chestContents[23] = null;
		}
		DoReactOnSlotChange = true;
	}
	
	
	public String[] GetGrid()
	{
		String[] s = new String[5];
		s[0] =  GetCharByItemstack(chestContents[0]) +GetCharByItemstack(chestContents[1])+
				GetCharByItemstack(chestContents[2]);
		s[1] =  GetCharByItemstack(chestContents[3]) +GetCharByItemstack(chestContents[4])+
				GetCharByItemstack(chestContents[5]) +GetCharByItemstack(chestContents[6])+
				GetCharByItemstack(chestContents[7]);
		s[2] =  GetCharByItemstack(chestContents[8]) +GetCharByItemstack(chestContents[9])+
				GetCharByItemstack(chestContents[10]) +GetCharByItemstack(chestContents[11])+
				GetCharByItemstack(chestContents[12]);
		s[3] =  GetCharByItemstack(chestContents[13]) +GetCharByItemstack(chestContents[14])+
				GetCharByItemstack(chestContents[15]) +GetCharByItemstack(chestContents[16])+
				GetCharByItemstack(chestContents[17]);
		s[4] =  GetCharByItemstack(chestContents[18]) +GetCharByItemstack(chestContents[19])+
				GetCharByItemstack(chestContents[20]);
		
		return s;
	}
	
	public String GetCharByItemstack(ItemStack is)
	{
		if (is== null || is.stackSize == 0) return " ";
		if (is.getItem().itemID == YC_Mod.i_sCogwheel.itemID) return "w";
		if (is.getItem().itemID == YC_Mod.i_sChip.itemID) return "h";
		if (is.getItem().itemID == YC_Mod.i_sMatrix.itemID) return "m";
		if (is.getItem().itemID == YC_Mod.i_sEngine.itemID) return "e";
		if (is.getItem().itemID == YC_Mod.i_rEnderKnowledgePiece.itemID) return "n";
		if (is.getItem().itemID == Item.diamond.itemID) return "d";
		
		if (is.getItem() instanceof ItemBlock && 
				((ItemBlock)is.getItem()).getBlockID() == YC_Mod.b_astralCrystals.blockID) 
			return "c";
		
		return "-";
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
		packet.channel = "YC_Workbench";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockWorkbench chest = this;
        if (intData != null) {
          for(int i = 0; i<24; i++)
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
          FuelValue=intData[192];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[193];
          for(int i = 0; i<24; i++)
          {
        	  sortList[i*3] = chestContents[i]==null?-1:chestContents[i].itemID;
        	  sortList[i*3+1] = chestContents[i]==null?-1:chestContents[i].stackSize;
        	  sortList[i*3+2] = chestContents[i]==null?-1:chestContents[i].getItemDamage();
          }
          sortList[192]=FuelValue;
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
