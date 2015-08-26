package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.world.World;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockChest extends TileEntity implements IInventory
{
    public List<ItemStack[]> chestContents = new ArrayList();//x*54
    														 //NOTE: Only bottom x-, z- block stores the items
    public ItemStack SideSlot = null; 

    /** The number of players currently using this chest */
    public int numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    public boolean IsChestComplete = false;
    public boolean IsMainBlock = false;
    public int ThisBlockType = 0;//0-3 - bottom; 4-7 - top; 
    							 //0,4 - x-,z-; 1,5 - x+,z-;
    							 //2,6 - x+,z+; 3,7 - x-,z+.
    public int mx = 0, my = 0, mz = 0;
    private int curState = 0;
    public float dy = 0f;
    
    public YC_ContainerBlockChest container = null;
    
    public YC_TileEntityBlockChest() {
	}
    
    
    
    @Override
    public void updateEntity() {
    	
    	if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 7 && worldObj.isRemote)
    	{
    		if (numUsingPlayers > 0 && curState < 20)
    		{
    			curState++;
    			dy += (float)Math.sin(curState * Math.PI / 20f)/64f;
    			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    		}
    		if (numUsingPlayers == 0 && curState > 0)
    		{
    			curState--;
    			dy -= (float)Math.sin(curState * Math.PI / 20f)/64f;
    			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    		}
    		if (numUsingPlayers == 0 && curState == 0)
    		{
    			dy = 0;
    			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    		}
    	}
    	
    	ticksSinceSync ++;
    	if (ticksSinceSync >= 10)
    	{
    		ticksSinceSync -= 10;
    		if (!worldObj.isRemote)
    		{
    			if (!IsChestComplete)
    			{
					curState = 0;
					dy = 0;
    				if (CheckChestComplete())//chest has been completed. only checks bottom x-, z- block and sets the positions accordingly
    				{
    					chestContents.clear();
    					chestContents.add(new ItemStack[54]);//initial chest size
    					//chestContents.add(new ItemStack[54]);//initial chest size
    					//chestContents.add(new ItemStack[54]);//initial chest size
    					//for(int i = 0; i<54*3; i++)
    					//{
    					//	setInventorySlotContents(i, null);
    					//}
    					
    					
    					
    					
    					IsMainBlock = true;
    					ThisBlockType = 0;
    					IsChestComplete = true;
    					mx = xCoord; my = yCoord; mz = zCoord;
    					ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, ThisBlockType + 7, 3);
    					
    					YC_TileEntityBlockChest te;
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord));
    					te.ThisBlockType = 1;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord+1));
    					te.ThisBlockType = 2;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1));
    					te.ThisBlockType = 3;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord));
    					te.ThisBlockType = 4;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord+1, zCoord));
    					te.ThisBlockType = 5;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord+1, zCoord+1));
    					te.ThisBlockType = 6;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    					
    					te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord+1));
    					te.ThisBlockType = 7;
    					te.IsChestComplete = true;
    					te.mx = xCoord; te.my = yCoord; te.mz = zCoord;
    					te.ServerSendUpdate();
    					worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, te.ThisBlockType + 7, 3);
    				}
    			}
    			else
    			{
    				if (IsMainBlock && !CheckChestCompleteBlocksOnly())//chest has been destroyed. drop items
    				{
    					OnChestStopsExisting();
    					chestContents.clear();
    				}
    			}
    			
    		}
    	}
    	super.updateEntity();
    }
    
    public boolean CheckChestComplete()//chest has been completed. only checks bottom x-, z- block and sets the positions accordingly
    {
    	if (// bottom, 
        	worldObj.getBlockId(xCoord, yCoord, zCoord+1) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord, yCoord+1, zCoord) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord+1, yCoord, zCoord) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord, zCoord+1) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord+1, zCoord+1) == YC_Mod.b_chest.blockID && 
    		!IsChestComplete && 
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord+1)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord+1)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).IsChestComplete &&
    		!((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).IsChestComplete)
    			return true;
    	return false;
    }
    
    public boolean CheckChestCompleteBlocksOnly()//chest has been completed. only checks bottom x-, z- block and sets the positions accordingly
    {
    	if (// bottom, 
        	worldObj.getBlockId(xCoord, yCoord, zCoord+1) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord, yCoord+1, zCoord) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == YC_Mod.b_chest.blockID &&
        	worldObj.getBlockId(xCoord+1, yCoord, zCoord) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord, zCoord+1) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == YC_Mod.b_chest.blockID &&
    		worldObj.getBlockId(xCoord+1, yCoord+1, zCoord+1) == YC_Mod.b_chest.blockID)
    			return true;
    	return false;
    }
    
    public void DropItems()
    {
    	float x = xCoord + 1;
    	float y = yCoord + 2;
    	float z = zCoord + 1;
    	
    	Random random = new Random();

        if (this != null)
        {
            for (int var8 =-1; var8 < this.getSizeInventory(); ++var8)
            {
                ItemStack var9 = this.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = random.nextFloat() * 0.8F + 0.1F;
                    float var11 = random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; worldObj.spawnEntityInWorld(var14))
                    {
                        int var13 = random.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(worldObj, (double)((float)x + var10), (double)((float)y + var11), (double)((float)z + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)random.nextGaussian() * var15);
                        var14.motionY = (double)((float)random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)random.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }
    }
    
    public void OnChestStopsExisting()
    {
		DropItems();
		
		


		IsMainBlock = false;
		ThisBlockType = 0;
		IsChestComplete = false;
		mx = 0; my = 0; mz = 0;
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0);
		ServerSendUpdate();
		
		YC_TileEntityBlockChest te;
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord+1));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord+1, zCoord));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord+1, yCoord+1, zCoord+1));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
		
		te = ((YC_TileEntityBlockChest)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord+1));
		if (te != null)
		{
			te.ThisBlockType = 0;
			te.IsChestComplete = false;
			te.mx = 0; te.my = 0; te.mz = 0;
			worldObj.setBlockMetadataWithNotify(te.xCoord, te.yCoord, te.zCoord, 0, 0);
			te.ServerSendUpdate();
		}
    }
    
    public void ServerSendUpdate()
    {
    	PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.getWorldInfo().getDimension());
    }
    
    
    
    public int getSizeInventory()
    {
        return chestContents.size() * 54;
    }

    public ItemStack getStackInSlot(int par1)
    {
    	if (par1 == -1) return SideSlot;
    	if (par1 >= chestContents.size() * 54) return null;
        ItemStack is = ((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54];
        if (is == null || is.itemID == 0) return null;
        return is;
    }

    public ItemStack decrStackSize(int par1, int par2)
    {
    	if (par1 == -1)
    	{
            if (SideSlot != null)
            {
                ItemStack var3;

                if (SideSlot.stackSize <= par2)
                {
                    var3 = SideSlot;
                    SideSlot = null;
                    this.onInventoryChanged();
                    return var3;
                }
                else
                {
                    var3 = SideSlot.splitStack(par2);

                    if (SideSlot.stackSize == 0)
                    {
                    	SideSlot = null;
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
        if (((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] != null)
        {
            ItemStack var3;

            if (((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54].stackSize <= par2)
            {
                var3 = ((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54];
                ((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] = null;
                this.onInventoryChanged();
                return var3;
            }
            else
            {
                var3 = ((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54].splitStack(par2);

                if (((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54].stackSize == 0)
                {
                	((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] = null;
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
    	if (par1 == -1) return SideSlot;
        if (((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] != null)
        {
            ItemStack var2 = ((ItemStack[])this.chestContents.get((par1-1)/54))[(par1-1)%54];
            ((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
    	if (par1 == -1)
    	{
    		{
    			SideSlot = par2ItemStack;
    			
            	if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
            	{
                	par2ItemStack.stackSize = this.getInventoryStackLimit();
            	}
            	
            	if (SideSlot != null && SideSlot.itemID == YC_Mod.i_rEnderKnowledgePiece.itemID)
            	{
            		int c = 0;
            		for(int i = 0; i < SideSlot.stackSize && chestContents.size() < 48; i++)
            		{
            			chestContents.add(new ItemStack[54]);
            			c++;
            			//SideSlot.stackSize--;
            		}
            		if (c >= SideSlot.stackSize)
            			SideSlot = null;
            		else
            			SideSlot.stackSize -= c;
            		ServerSendUpdate();
            		//if (container != null)
            		//{
            		//	container.ReloadContainer();
            		//}
            	}
    		}
    	}
    	else
    	{
    		((ItemStack[])this.chestContents.get((par1)/54))[(par1)%54] = par2ItemStack;
    		
        	if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        	{
            	par2ItemStack.stackSize = this.getInventoryStackLimit();
        	}
    	}

        this.onInventoryChanged();
    }

    public String getInvName()
    {
        return "Chest";
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        ThisBlockType = par1NBTTagCompound.getInteger("Type");
        IsChestComplete = par1NBTTagCompound.getBoolean("IsComplete");
        IsMainBlock = par1NBTTagCompound.getBoolean("IsMain");
        mz = par1NBTTagCompound.getInteger("mz");
        my = par1NBTTagCompound.getInteger("my");
        mx = par1NBTTagCompound.getInteger("mx");
        
        int listSize = par1NBTTagCompound.getInteger("ListSize");
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        
        this.chestContents = new ArrayList();

        for(int a = 0; a<listSize; a++)
        {
        	ItemStack[] s = new ItemStack[54];
        	for (int var3 = 0; var3 < 54; ++var3)
        	{
        		NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3+a*54);
        		//int var5 = var4.getByte("Slot") & 255;
        		
        		//if (var5 >= 0 && var5 < this.chestContents.length)
        		//{
        			s[var3] = ItemStack.loadItemStackFromNBT(var4);
        		//}
        	}
        	chestContents.add(s);
        }
		NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(listSize*54);
		SideSlot = ItemStack.loadItemStackFromNBT(var4);
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();
        ItemStack empty = new ItemStack(0,0,0);

        for(int a = 0; a<chestContents.size(); a++)
        {
        	ItemStack[] s = chestContents.get(a);
        	for (int var3 = 0; var3 < s.length; ++var3)
        	{
            	if (s[var3] != null)
            	{
            		NBTTagCompound var4 = new NBTTagCompound();
                	var4.setShort("Slot", (short)(var3+a*54));
                	s[var3].writeToNBT(var4);
                	var2.appendTag(var4);
            	}
            	else
            	{
            		NBTTagCompound var4 = new NBTTagCompound();
                	var4.setShort("Slot", (short)(var3+a*54));
                	empty.writeToNBT(var4);
                	var2.appendTag(var4);
            	}
        	}
        }
    	if (SideSlot != null)
    	{
    		NBTTagCompound var4 = new NBTTagCompound();
        	var4.setShort("Slot", (short)(chestContents.size()*54));
        	SideSlot.writeToNBT(var4);
        	var2.appendTag(var4);
    	}
    	else
    	{
    		NBTTagCompound var4 = new NBTTagCompound();
        	var4.setShort("Slot", (short)(chestContents.size()*54));
        	empty.writeToNBT(var4);
        	var2.appendTag(var4);
    	}

        par1NBTTagCompound.setTag("Items", var2);
        par1NBTTagCompound.setInteger("ListSize", chestContents.size());
        par1NBTTagCompound.setInteger("mx", mx);
        par1NBTTagCompound.setInteger("my", my);
        par1NBTTagCompound.setInteger("mz", mz);
        par1NBTTagCompound.setBoolean("IsMain", IsMainBlock);
        par1NBTTagCompound.setBoolean("IsComplete", IsChestComplete);
        par1NBTTagCompound.setInteger("Type", ThisBlockType);
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
     * @return 
     */
    @Override
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            this.numUsingPlayers = par2;
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
		packet.channel = "YC_Chest";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
		YC_TileEntityBlockChest chest = this;
		if (intData != null) {
			chestContents = new ArrayList();
			for(int a = 0; a<intData[0]; a++)
			{
				ItemStack[] s = new ItemStack[54];
				for (int i = 0; i < 54; i++) 
				{
					if (intData[(i + a * 54) * 3 + 1] != -1) 
					{
						s[i] = new ItemStack(intData[(i+a*54) * 3 + 1], intData[(i+a*54) * 3 + 2], intData[(i+a*54) * 3 + 3]);
					} 
					else 
					{
						s[i] = null;
					}
				}
				chestContents.add(s);
			}
			
			numUsingPlayers = intData[intData.length - 9];

			SideSlot = intData[intData.length - 8] == -1 ? null : new ItemStack(intData[intData.length - 8],intData[intData.length - 7],intData[intData.length - 6]); 
			
			mx = intData[intData.length - 5];
			my = intData[intData.length - 4];
			mz = intData[intData.length - 3];
			if (intData[intData.length - 2] == 1) worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, intData[intData.length - 1] + 7, 0);// ?
			else worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0);//TODO ?
		}
	}
    
    public int[] buildIntDataList() {
    	int[] sortList = new int[chestContents.size()*54*3+11];//+2 for chest size, +5 for is chest complete and main TE pos, +3 for -1 slot, +1 for numusingplayers
    	sortList[0] = sortList.length-1;
    	sortList[1] = chestContents.size();
    	for(int a = 0; a<chestContents.size(); a++)
    	{
    		ItemStack[] s = chestContents.get(a);
    		for(int i = 0; i<54; i++)
    		{
    			sortList[i*3+2] = s[i]==null?-1:s[i].itemID;
    			sortList[i*3+3] = s[i]==null?-1:s[i].stackSize;
    			sortList[i*3+4] = s[i]==null?-1:s[i].getItemDamage();
    		}
    	}
    	sortList[sortList.length - 9] = numUsingPlayers;
    	
    	sortList[sortList.length - 8] = SideSlot == null ? -1 : SideSlot.itemID;
    	sortList[sortList.length - 7] = SideSlot == null ? -1 : SideSlot.stackSize;
    	sortList[sortList.length - 6] = SideSlot == null ? -1 : SideSlot.getItemDamage();
    	
    	sortList[sortList.length - 5] = mx;
    	sortList[sortList.length - 4] = my;
    	sortList[sortList.length - 3] = mz;
    	sortList[sortList.length - 2] = IsChestComplete ? 1 : 0;
    	sortList[sortList.length - 1] = ThisBlockType;
    	return sortList;
    }
    
    public void handlePacketDataPage(int p, String name)
    {
    	if (container != null && container.player.username.equals(name))
    	{
    		container.InitSlots(p);
    		return;
    	}
    }



	@Override
	public boolean func_94042_c() {
		return false;
	}



	@Override
	public boolean func_94041_b(int i, ItemStack itemstack) {
		return false;
	}
	
}
