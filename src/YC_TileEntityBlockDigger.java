package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
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
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockDigger extends TileEntity
{
    private int ticksSinceSync;
    
    public int CurState = 0;
    public int CurY = -1;
    public static int[] idBlacklist = null;

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurY = par1NBTTagCompound.getInteger("CurY");
        CurState = par1NBTTagCompound.getInteger("CurState");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CurState", CurState);
        par1NBTTagCompound.setInteger("CurY", CurY);
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
    
    public void ClearEmptyBlocks()
    {
    	worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord-1, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord+1, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord-1, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord+1, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord-1, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord, 0, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord+1, 0, 0, 3);
    }
    
    public void FillEmptyBlocks()
    {
    	worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord+1, yCoord, zCoord+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord-1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord, YC_Mod.b_spaceOccupier.blockID, 0, 3);
		worldObj.setBlockAndMetadataWithNotify(xCoord-1, yCoord, zCoord+1, YC_Mod.b_spaceOccupier.blockID, 0, 3);
    }
    
    public void PlayExplosionSound()
    {
        this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
    }
    
    @Override
    public void updateEntity() {
    	if (worldObj.getBlockId(xCoord, yCoord, zCoord)!= YC_Mod.b_digger.blockID)
    	{
    		CurY = -1;
    		CurState = 0;
    		return;
    	}
    	if (FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
    	{
    		Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    	    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) < 5)
    		{
    			return;
    		}
    		
    		CurState++;
    	    
    	    if (CurState >= 400+(yCoord-1)*25-5)
    	    {
                this.worldObj.spawnParticle("largeexplode", xCoord+0.5, yCoord+0.5, zCoord+0.5, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("largeexplode", xCoord+1.5, yCoord+0.5, zCoord+0.5, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("largeexplode", xCoord+0.5, yCoord+0.5, zCoord+1.5, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("largeexplode", xCoord+0.5, yCoord+0.5, zCoord-0.5, 1.0D, 0.0D, 0.0D);
                this.worldObj.spawnParticle("largeexplode", xCoord-0.5, yCoord+0.5, zCoord+0.5, 1.0D, 0.0D, 0.0D);
                //Minecraft.getMinecraft().theWorld.playSoundEffect(xCoord+0.5, yCoord+0.5, zCoord+0.5, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
    	    }
    		return;
    	}
    	
    	//FillEmptyBlocks();
    	if (CurState>=400)
    	{
    		if (worldObj.getBlockId(xCoord, yCoord, zCoord)!= YC_Mod.b_digger.blockID){
    			this.worldObj.setBlockTileEntity(xCoord, yCoord, zCoord, null);
    			return;
    		}

    		if (CurY == -1)
    		{
    			if (worldObj.rand.nextInt(2)==1)
    			{
    				Drop(YC_Mod.b_digger.blockID, 1, 0);
    			}
    			ClearEmptyBlocks();
    			PlayExplosionSound();
    			worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 0);
    			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
    			if (!worldObj.isRemote)
    			{
    				invalidate();
    			}
        		return;
    		}
    		TileEntity tetp = worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord);
    		boolean chest = tetp != null && tetp instanceof IInventory;
    		IInventory te = (IInventory) (chest?tetp:null);
    		int x = ((CurState-400)%25)/5 + xCoord - 2;
    		int z = ((CurState-400)%25)%5 + zCoord - 2;
    		
    		DigBlock(x, CurY , z, chest, te);
    		
    		if ((CurState - 400) % 25 == 24)
    			CurY--;
    	}
    	else
    	{
    		if (CurY == -1)
    		{
    			CurY = yCoord-1;
    		}
    	}

		if (CurY == 0)
		{
			if (worldObj.rand.nextInt(2)==1)
			{
				Drop(YC_Mod.b_digger.blockID, 1, 0);
			}
			ClearEmptyBlocks();
			PlayExplosionSound();
			worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
			worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
			if (!worldObj.isRemote)
			{
				invalidate();
			}
    		return;
		}
		if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) >= 5)
		{
			if (CurState == 0)
	    		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 100, worldObj.provider.dimensionId, 
	    				getDescriptionPacket());
			CurState++;
		}
    	
    	if (CurState % 20 == 0 && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
    	{
    		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 100, worldObj.provider.dimensionId, 
    				getDescriptionPacket());
    		//ModLoader.sendPacket(getDescriptionPacket());
    	}
    }
    
    public void DigBlock(int x, int y, int z, boolean HasUpChest, IInventory inv)
    {
		//if (worldObj.canMineBlock(null, xCoord, yCoord-CurY, zCoord))
		{
			Block b = Block.blocksList[worldObj.getBlockId(x, y, z)];
			if (b != null &&
				b.blockID != Block.stone.blockID &&
				b.blockID != Block.cobblestone.blockID &&
				b.blockID != Block.dirt.blockID &&
				b.blockID != Block.grass.blockID &&
				b.blockID != Block.mobSpawner.blockID &&
				b.blockID != Block.cobblestoneMossy.blockID &&
				b.blockID != Block.chest.blockID &&
				b.blockID != Block.gravel.blockID &&
				b.blockID != Block.lavaMoving.blockID &&
				b.blockID != Block.lavaStill.blockID &&
				b.blockID != Block.waterMoving.blockID &&
				b.blockID != Block.waterStill.blockID &&
				b.blockID != Block.bedrock.blockID &&
				b.blockID != Block.sand.blockID &&
				b.blockID != Block.sandStone.blockID &&
				b.blockID != Block.planks.blockID &&
				b.blockID != Block.fence.blockID &&
				b.blockID != Block.rail.blockID &&
				b.blockID != Block.web.blockID &&
				b.blockID != Block.torchWood.blockID &&
				b.blockID != Block.mycelium.blockID &&
				b.blockID != YC_Mod.b_fromAstralPortal.blockID && 
				b.blockID != YC_Mod.b_energyPocket.blockID && 
				!IsBlockBlacklisted(b.blockID))
			{
				int md = worldObj.getBlockMetadata(x, y, z);
				Random r = new Random();
				int d = b.idDropped(md, r, 0);
				int c = b.quantityDropped(md, 0, r);
				int dam = b.damageDropped(md);
				
				if (HasUpChest)
				{
					PlaceItemInChest(d, c, dam, inv);
				}
				else
				{
					Drop(d,c,dam);
				}
				
				worldObj.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
			}
			//else
			//{
			//	if (b!=null && b.blockID != Block.bedrock.blockID)
			//	worldObj.setBlock(x, y, z, 0);
			//}
		}
    }
    
    public boolean IsBlockBlacklisted(int id)
    {
    	for(int i = 0; i<idBlacklist.length; i++)
    	{
    		if (idBlacklist[i] == id) 
    			return true;
    	}
    	return false;
    }
    
    public void PlaceItemInChest(int id, int count, int damage, IInventory te)
    {
    	if (id<=0) return;
    	int EmptySlot = -1;
		if (Item.itemsList[id]==null)//Block
		{
			for(int i = 0; i<te.getSizeInventory(); i++)
    		{
    			ItemStack ti = te.getStackInSlot(i);
    			if (ti != null && 
    					ti.getItem() instanceof ItemBlock && 
    					ti.stackSize<64 && 
    					((ItemBlock)ti.getItem()).getBlockID() == id)
    			{
    				if (ti.stackSize+count<=64)
    				{
    					ti.stackSize += count;
    					te.setInventorySlotContents(i, ti);
    					count = 0;
    					if (count == 0) return;
    				}
    				else
    				{
    					count = count - 64 +ti.stackSize;
    					ti.stackSize = 64;
    					te.setInventorySlotContents(i, ti);
    				}
    			}
    			else
    			{
    				if (ti == null && EmptySlot == -1) EmptySlot = i;
    			}
    		}
			
			if (count != 0)
			{
				if (EmptySlot != -1)
				{
					te.setInventorySlotContents(EmptySlot, 
							new ItemStack(Block.blocksList[id], count));
				}
				else
				{
					Drop(id,count, damage);
				}
			}
		}
		else//Item
		{
			for(int i = 0; i<te.getSizeInventory(); i++)
    		{
    			ItemStack ti = te.getStackInSlot(i);
    			if (ti != null && 
    					ti.getItem().itemID == id && 
    					ti.stackSize<64 && ti.getItemDamage() == damage)
    			{
    				if (ti.stackSize+count<=64)
    				{
    					ti.stackSize += count;
    					te.setInventorySlotContents(i, ti);
    					count = 0;
    					if (count == 0) return;
    				}
    				else
    				{
    					count = count - 64 +ti.stackSize;
    					ti.stackSize = 64;
    					te.setInventorySlotContents(i, ti);
    				}
    			}
    			else
    			{
    				if (ti == null && EmptySlot == -1) EmptySlot = i;
    			}
    		}
			
			if (count != 0)
			{
				if (EmptySlot != -1)
				{
					te.setInventorySlotContents(EmptySlot, 
							new ItemStack(Item.itemsList[id], count, damage));
				}
				else
				{
					Drop(id,count, damage);
				}
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
		packet.channel = "YC_Digger";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockDigger chest = this;
        if (intData != null) {
        	CurState = intData[0];
        	CurY = intData[1];
        	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, intData[2], 3);
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[3];
          sortList[0] = CurState;
          sortList[1] = CurY;
          sortList[2] = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
          return sortList;
     }
	
}
