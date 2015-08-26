package net.minecraft.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class YC_ContainerBlockResearchShardGenerator extends Container
{
    private IInventory playerInventory;
    private IInventory entityInventory;

    public YC_ContainerBlockResearchShardGenerator(InventoryPlayer par1IInventory, IInventory par2IInventory)
    {
        this.playerInventory = par1IInventory;
        this.entityInventory = par2IInventory;
        par2IInventory.openChest();
        //int var3 = (this.numRows - 4) * 18;
        int var4;
        int var5;

        this.addSlotToContainer(new Slot(par2IInventory, 0, 52, 37));
        this.addSlotToContainer(new SlotFurnace(par1IInventory.player, par2IInventory, 1, 105, 37));
        //for (var4 = 0; var4 < this.numRows; ++var4)
        //{
        //    for (var5 = 0; var5 < 3; ++var5)
        //    {
                
        //    }
        //}

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(par1IInventory, var4, 8 + var4 * 18, 146));
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(par1IInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + 88));
            }
        }
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
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 < 27)
            {
                if (!this.mergeItemStack(var4, 27, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, 27, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.entityInventory.closeChest();
    }
    
    @Override
    protected void retrySlotClick(int par1, int par2, boolean par3,
    		EntityPlayer par4EntityPlayer) {
    	if (par1< 2) // from crystaalizer
    	{
    		if (TransferFromEntity(par1)) return;
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
    	par2-=2;
    	ItemStack src = playerInventory.getStackInSlot(par2);
    	if (src == null || playerInventory == null || entityInventory == null) return false;
    	ItemStack dst = null;
    	for(int i = 0; i<entityInventory.getSizeInventory()-1; i++)//-1 4 result
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
    	for(int i = 0; i<entityInventory.getSizeInventory()-1; i++)
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
