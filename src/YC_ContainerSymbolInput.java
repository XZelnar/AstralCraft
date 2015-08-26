package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class YC_ContainerSymbolInput extends Container
{
    private IInventory entityInventory;
    private IInventory playerInventory;

    public YC_ContainerSymbolInput(EntityPlayer par1IInventory, IInventory par2IInventory)
    {
        this.playerInventory = par1IInventory.inventory;
        this.entityInventory = par2IInventory;
        if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT)
        {
        	((YC_TileEntityBlockSymbolInput)entityInventory).player = (EntityPlayerMP) par1IInventory;
        }
        par2IInventory.openChest();
        int var4 = 0, var5 = 0;
        

        this.addSlotToContainer(new Slot(par2IInventory, 0, 26, 22));
        this.addSlotToContainer(new Slot(par2IInventory, 1, 62, 22));
        this.addSlotToContainer(new Slot(par2IInventory, 2, 98, 22));
        this.addSlotToContainer(new Slot(par2IInventory, 3, 134, 22));

        

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(playerInventory, var4, 8 + var4 * 18, 142));
        }
        
        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(playerInventory, var5 + var4 * 9 + 9, 8 + var5 * 18, var4 * 18 + 84));
            }
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.entityInventory.isUseableByPlayer(par1EntityPlayer);
    }

    public ItemStack func_82846_b(EntityPlayer par1EntityPlayer, int par2)
    {
    	return null;
    }

    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.entityInventory.closeChest();

        if (!((YC_TileEntityBlockSymbolInput)this.entityInventory).worldObj.isRemote)
        {
        	YC_TileEntityBlockSymbolInput te = (YC_TileEntityBlockSymbolInput) entityInventory;
        	if (te.numUsingPlayers != 0) return;
            for (int var2 = 0; var2 < entityInventory.getSizeInventory(); ++var2)
            {
                ItemStack var3 = this.entityInventory.getStackInSlotOnClosing(var2);

                if (var3 != null)
                {
                    par1EntityPlayer.dropPlayerItem(var3);
                }
                
                this.entityInventory.setInventorySlotContents(var2, null);
            }
        }
    }
    
    @Override
    protected void retrySlotClick(int par1, int par2, boolean par3,
    		EntityPlayer par4EntityPlayer) {
    	if (par1< 4) // from crystaalizer
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
    	par2-=4;
    	ItemStack src = playerInventory.getStackInSlot(par2);
    	if (src == null || playerInventory == null || entityInventory == null) return false;
    	ItemStack dst = null;
    	for(int i = 0; i<entityInventory.getSizeInventory(); i++)
    	{
    		dst = entityInventory.getStackInSlot(i);
    		if (dst != null && dst.itemID == src.itemID && dst.getItemDamage() == src.getItemDamage())
    		{
    			if (src.stackSize + dst.stackSize <= 10)//max stack size 4 this container
    			{
    				entityInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						src.stackSize + dst.stackSize, src.getItemDamage()));
    				playerInventory.setInventorySlotContents(i, null);
    				return true;
    			}
    			else
    			{
    				playerInventory.decrStackSize(par2, src.getMaxStackSize() - dst.stackSize);
    		    	src = playerInventory.getStackInSlot(par2);
    		    	entityInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 
    						10, src.getItemDamage()));
    			}
    		}
    	}
    	for(int i = 0; i<entityInventory.getSizeInventory(); i++)
    	{
    		dst = entityInventory.getStackInSlot(i);
    		if (dst == null)
    		{
    			if (src.stackSize <= 10)
    			{
    				entityInventory.setInventorySlotContents(i, src);
    				playerInventory.setInventorySlotContents(par2, null);
    			}
    			else
    			{
    				entityInventory.setInventorySlotContents(i, new ItemStack(src.itemID, 10, src.getItemDamage()));
    				playerInventory.decrStackSize(par2, 10);
    			}
    			return true;
    		}
    	}
    	
    	return false;
    }
}
