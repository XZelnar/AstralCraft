package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import org.omg.CORBA.DynAnyPackage.Invalid;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockSymbolInput extends TileEntity implements IInventory
{

    /** Server sync counter (once per 20 ticks) */
    private int ticksSinceSync;
    
    public int CurState = 0;
    public boolean generated = true;
    public ItemStack[] chestContents = new ItemStack[4];
    public boolean IsOpening = false;
    public int[] itemIDsRequired = new int[]{YC_Mod.i_sCogwheel.itemID, YC_Mod.i_sChip.itemID, YC_Mod.i_sMatrix.itemID, YC_Mod.i_sEngine.itemID};
    public int[] itemStacksRequired = new int[]{3,3,3,3};
    public int numUsingPlayers = 0;
    public EntityPlayerMP player;
    public int lastmd = 2;
    
    public YC_TileEntityBlockSymbolInput()
    {
    	generated = false;
    }
    
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        CurState = par1NBTTagCompound.getInteger("CurState");
        itemStacksRequired[0] = par1NBTTagCompound.getInteger("s1");
        itemStacksRequired[1] = par1NBTTagCompound.getInteger("s2");
        itemStacksRequired[2] = par1NBTTagCompound.getInteger("s3");
        itemStacksRequired[3] = par1NBTTagCompound.getInteger("s4");
        generated = true;
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("CurState", CurState);
        par1NBTTagCompound.setInteger("s1", itemStacksRequired[0]);
        par1NBTTagCompound.setInteger("s2", itemStacksRequired[1]);
        par1NBTTagCompound.setInteger("s3", itemStacksRequired[2]);
        par1NBTTagCompound.setInteger("s4", itemStacksRequired[3]);
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
		packet.channel = "YC_SymbolInput";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public void handlePacketData(int[] intData) {
        YC_TileEntityBlockSymbolInput chest = this;
        if (intData != null) {
        	Side s = FMLCommonHandler.instance().getEffectiveSide();
        	for(int i = 0; i<chestContents.length; i+=4)
        	{
        		chestContents[i/4] = intData[i] == -1 ? null : new ItemStack(intData[i], intData[i+1], 0);
        		itemIDsRequired[i/4] = intData[i+2];
        		itemStacksRequired[i/4] = intData[i+3];
        	}
        	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, intData[chestContents.length * 4], 3);
        	CurState = intData[chestContents.length * 4 + 1];
        }
     }
    
    public int[] buildIntDataList() {
          int[] sortList = new int[chestContents.length * 4 + 3];
          for(int i = 0; i<chestContents.length; i++)
          {
        	  sortList[i*4] = chestContents[i] == null?-1:chestContents[i].itemID;
        	  sortList[i*4+1] = chestContents[i] == null?-1:chestContents[i].stackSize;
        	  sortList[i*4+2] = itemIDsRequired[i];
        	  sortList[i*4+3] = itemStacksRequired[i];
          }
          sortList[chestContents.length * 4] = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
          sortList[chestContents.length * 4 + 1] = CurState;
          return sortList;
     }

    @Override
    public boolean receiveClientEvent(int par1, int par2)
    {
        if (par1 == 1)
        {
            numUsingPlayers = par2;
        }
        return true;
    }
    
    @Override
    public void updateEntity() {
    	if (!generated)
    	{
    		Generate();
    	}
    	
    	int md = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    	if (md == 2)
    	{
    		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
    		{
    			if (CurState == 0 && lastmd != 2)
    			{
    				YC_ClientProxy.SpawnSymbolInputParticle(worldObj, xCoord, yCoord, zCoord);
    			}
        		CurState++;
    			return;
    		}
    		
    		CurState++;
    		if (worldObj.isRemote && CurState == 100)
    		{
    			worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
    			invalidate();
    		}
    		if (!worldObj.isRemote && CurState == 105)//tp player
    		{
    			Teleport();
    		}
    		if (!worldObj.isRemote && CurState >= 110)
    		{
    			//new YC_wgPyramidDown().generate(worldObj, new Random(), xCoord-6, yCoord-10, zCoord-26);
    			worldObj.setBlockAndMetadataWithNotify(xCoord, yCoord, zCoord, 0, 0, 3);
        		//worldObj.removeBlockTileEntity(xCoord, yCoord, zCoord);
        		invalidate();
            	return;
    		}
    	}
    	lastmd = md;
    }
    
    public void Teleport()
    {
    	if (player == null) return;
    	MinecraftServer mcServer = MinecraftServer.getServer();
		YC_ServerPlayerAstralData d = YC_ServerPlayerAstralDataList.GetPlayerData(player.username);
		if (d == null)
		{
			if (YC_ServerPlayerAstralDataList.PlayerExists(player.username))
			{
				d = YC_ServerPlayerAstralDataList.GetPlayerData(player.username);
			}
			else
			{
				double ax = ((YC_ServerPlayerAstralDataList.data.size() % 15) * 1600) + 800 + 8;
				double az = ((YC_ServerPlayerAstralDataList.data.size() / 15) * 1600) + 800 + 8;
				if (player.dimension != YC_Mod.d_astralDimID)
				{
					YC_ServerPlayerAstralDataList.Add(player.username, 
							player.posX, player.posY, player.posZ, 
							0, ax, 82, az);
				}
				else
				{
					YC_ServerPlayerAstralDataList.Add(player.username, 
							0, GetHeightAtPotint(0, 0, mcServer.worldServerForDimension(0))+1.5, 0, 
							0, ax, 82, az);
				}
				YC_ServerPlayerAstralDataList.ClearAllRepeating();
				d = YC_ServerPlayerAstralDataList.GetPlayerData(player.username);
			}
		}
		
		double lx = player.posX;
		double ly = player.posY;
		double lz = player.posZ;
		int ld = player.dimension;
		if (player.dimension != YC_Mod.d_astralDimID)
		{
			d.x = player.posX;
			d.y = player.posY;
			d.z = player.posZ;
			player.posX = -d.AstralXPos+12;
			player.posY = 82;
			player.posZ = -d.AstralZPos+78;
			d.dim = player.dimension;
			YC_Variables.SaveAstralTeleporters();
			YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)player, YC_Mod.d_astralDimID, new YC_Teleporter());
    		new YC_wgLabyrinth().generate(player.worldObj, new Random(), (int)-d.AstralXPos+8, 80, (int)-d.AstralZPos+8);
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
    
    public void Generate()
    {
    	Random r = new Random((long)xCoord * (long)zCoord * (long)yCoord);
    	for(int i = 0; i<itemStacksRequired.length; i++)
    	{
    		itemStacksRequired[i] = r.nextInt(10) + 1;
    	}
    }
    
    public void CheckActivate()
    {
    	if (worldObj.isRemote) return;
    	if (worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 2) return;
    	IsOpening = true;
    	boolean NullSlots = true;
    	for(int i = 0; i<chestContents.length; i++)
    	{
    		if (chestContents[i]==null || chestContents[i].itemID != itemIDsRequired[i] || chestContents[i].stackSize != itemStacksRequired[i])
    		{
    			IsOpening = false;
    			//if (chestContents[i].itemID!=itemIDsRequired[i])
    			//{
    			//	NullSlots = false;
    			//}
    		}
    	}
    	if (NullSlots)
    	{
    		for(int i = 0; i<chestContents.length; i++)
    		{
    			chestContents[i] = null;
    		}
    	}
    	if (IsOpening)
    		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 3);
    	PacketDispatcher.sendPacketToAllInDimension(getDescriptionPacket(), worldObj.provider.dimensionId);
    }

	@Override
	public int getSizeInventory() {
		return chestContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
        return this.chestContents[var1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
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

	@Override
	public ItemStack getStackInSlotOnClosing(int par1) {
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

	@Override
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.chestContents[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "SI";
	}

	@Override
	public int getInventoryStackLimit() {
		return 10;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
    public void openChest()
    {
        ++this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
    }

	@Override
    public void closeChest()
    {
        --this.numUsingPlayers;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, Block.chest.blockID, 1, this.numUsingPlayers);
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
