package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class YC_ContainerBlockChest extends Container
{
	public EntityPlayer player;
    private IInventory playerInventory;
    private IInventory entityInventory;
    private int numRows;
    public int CurPage = 1;

    public YC_ContainerBlockChest(EntityPlayer par1IInventory, IInventory par2IInventory)
    {
    	if (par2IInventory == null) return;
        this.playerInventory = par1IInventory.inventory;
        this.entityInventory = par2IInventory;
        ((YC_TileEntityBlockChest)entityInventory).numUsingPlayers = 0;
        this.entityInventory.openChest();
        this.playerInventory.openChest();
        player = par1IInventory;
    	((YC_TileEntityBlockChest) par2IInventory).container = this;
        this.numRows = par2IInventory.getSizeInventory() / 3;
        //par2IInventory.openChest();
        //int var3 = (this.numRows - 4) * 18;

        InitSlots(1);
    }
    
    public void ReloadContainer()
    {
    	InitSlots(CurPage);
    }
    
    public void InitSlots(int page)
    {
    	CurPage = page;
    	page--;
    	//page = 1;
    	inventoryItemStacks.clear();
    	inventorySlots.clear();
    	

        int var4;
        int var5;
        
        this.addSlotToContainer(new Slot(entityInventory, -1, 174, 17));
        
        /*
        if (page != 0)
        {
        	for(int i = 0; i < page; i++)
        	{
        		for(int j = 0; j < 54; j++)
        		{
                    this.addSlotToContainer(new Slot(entityInventory, 
                    		j + i * 54, //id
                    		8, //x
                    		3));//y
        		}
        	}
        }//*/
        //*
        for (var4 = 0; var4 < 6; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(entityInventory, 
                		var5 + var4 * 9 + page * 54, //id
                		8 + var5 * 18, //x
                		33 + var4 * 18));//y
            }
        }//*/
        //this.addSlotToContainer(new YC_Slot(entityInventory, 
        //		54, //id
        //		8, //x
        //		33));//y
        //*
        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(playerInventory, var4, 8 + var4 * 18, 213));
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(playerInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + 155));
            }
        }//*/
        /*
        //if (page != 0)
        {
        	for(int i = 0; i < inventorySlots.size(); i++)
        	{
        		((Slot)inventorySlots.get(i)).setBackgroundIconIndex(0);
        	}
        }//*/
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.playerInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    public ItemStack transferStackInSlot(int par1)
    {
        return null;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.playerInventory.closeChest();
        this.entityInventory.closeChest();
    }
    
    @Override
    protected void retrySlotClick(int par1, int par2, boolean par3,
    		EntityPlayer par4EntityPlayer) {
    	if (par1 == -1) return;
    	if (par1< entityInventory.getSizeInventory())
    	{
    		if (TransferFromEntity(par1-1)) return;
    	}
    	else // from player
    	{
    		if (TransferFromPlayer(par1)) return;
    	}
        this.slotClick(par1, par2, 0, par4EntityPlayer);
    }
    
    public boolean TransferFromEntity(int par2)
    {
    	ItemStack src = entityInventory.getStackInSlot(par2);
    	if (src == null || playerInventory == null || entityInventory == null) return false;
    	ItemStack dst = null;
    	for(int i = 0; i<playerInventory.getSizeInventory(); i++)
    	{
    		dst = playerInventory.getStackInSlot(i);
    		if (dst != null && dst.itemID == src.itemID && dst.getItemDamage() == src.getItemDamage())
    		{
    			if (src.stackSize + dst.stackSize <= src.getMaxStackSize())
    			{
    				playerInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						src.stackSize + dst.stackSize, src.getItemDamage()));
    				entityInventory.setInventorySlotContents(par2, null);
    				return true;
    			}
    			else
    			{
    				entityInventory.decrStackSize(par2, src.getMaxStackSize() - dst.stackSize);
    		    	src = entityInventory.getStackInSlot(par2);
    				playerInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						src.getMaxStackSize(), src.getItemDamage()));
    			}
    		}
    	}
    	for(int i = 0; i<playerInventory.getSizeInventory(); i++)
    	{
    		dst = playerInventory.getStackInSlot(i);
    		if (dst == null)
    		{
    			playerInventory.setInventorySlotContents(i, src);
    			entityInventory.setInventorySlotContents(par2, null);
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean TransferFromPlayer(int par2)
    {
    	par2-=entityInventory.getSizeInventory()+1;
    	ItemStack src = playerInventory.getStackInSlot(par2);
    	if (src == null || playerInventory == null || entityInventory == null) return false;
    	ItemStack dst = null;
    	for(int i = 0; i<entityInventory.getSizeInventory(); i++)
    	{
    		dst = entityInventory.getStackInSlot(i);
    		if (dst != null && dst.itemID == src.itemID && dst.getItemDamage() == src.getItemDamage())
    		{
    			if (src.stackSize + dst.stackSize <= src.getMaxStackSize())
    			{
    				entityInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						src.stackSize + dst.stackSize, src.getItemDamage()));
    				playerInventory.setInventorySlotContents(par2, null);
    				return true;
    			}
    			else
    			{
    				playerInventory.decrStackSize(par2, src.getMaxStackSize() - dst.stackSize);
    		    	src = playerInventory.getStackInSlot(par2);
    		    	entityInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						src.getMaxStackSize(), src.getItemDamage()));
    			}
    		}
    	}
    	for(int i = 0; i<entityInventory.getSizeInventory(); i++)
    	{
    		dst = entityInventory.getStackInSlot(i);
    		if (dst == null)
    		{
    			entityInventory.setInventorySlotContents(i, src);
    			playerInventory.setInventorySlotContents(par2, null);
    			return true;
    		}
    	}
    	
    	return false;
    }
}
