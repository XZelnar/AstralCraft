package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class YC_TileEntityBlockTreeLog extends TileEntity
{
    private int ticksSinceSync;
    public static int[][][] crystals = new int[1][][];
    public static int[] CrystalsCount = new int[1]; 
    public int type = 0;
    
    public static void InitCrystals()
    {
    	CrystalsCount[0] = 37;
    	crystals[0] = new int[CrystalsCount[0]][4];
    	crystals[0][0] = new int[]{-1, 4, -1, 0};
    	crystals[0][1] = new int[]{1, 4, -1, 0};
    	crystals[0][2] = new int[]{1, 4, 1, 0};
    	crystals[0][3] = new int[]{-1, 4, 1, 0};
    	crystals[0][4] = new int[]{0, 6, 0, 0};
    	//
    	crystals[0][5] = new int[]{-2, 2, 0, 1};
    	crystals[0][6] = new int[]{-1, 2, -1, 1};
    	crystals[0][7] = new int[]{-1, 2, 0, 1};
    	crystals[0][8] = new int[]{-1, 2, 1, 1};
    	crystals[0][9] = new int[]{0, 2, -2, 1};
    	crystals[0][10] = new int[]{0, 2, -1, 1};
    	crystals[0][11] = new int[]{0, 2, 1, 1};
    	crystals[0][12] = new int[]{0, 2, 2, 1};
    	crystals[0][13] = new int[]{1, 2, -1, 1};
    	crystals[0][14] = new int[]{1, 2, 0, 1};
    	crystals[0][15] = new int[]{1, 2, 1, 1};
    	crystals[0][16] = new int[]{2, 2, 0, 1};
    	//
    	crystals[0][17] = new int[]{-3, 3, 0, 2};
    	crystals[0][18] = new int[]{-2, 3, -1, 2};
    	crystals[0][19] = new int[]{-2, 3, 1, 2};
    	crystals[0][20] = new int[]{-2, 4, 0, 2};
    	crystals[0][21] = new int[]{-1, 5, 0, 2};
    	//
    	crystals[0][22] = new int[]{0, 3, 3, 3};
    	crystals[0][23] = new int[]{-1, 3, 2, 3};
    	crystals[0][24] = new int[]{1, 3, 2, 3};
    	crystals[0][25] = new int[]{0, 4, 2, 3};
    	crystals[0][26] = new int[]{0, 5, 1, 3};
    	//
    	crystals[0][27] = new int[]{3, 3, 0, 4};
    	crystals[0][28] = new int[]{2, 3, -1, 4};
    	crystals[0][29] = new int[]{2, 3, 1, 4};
    	crystals[0][30] = new int[]{2, 4, 0, 4};
    	crystals[0][31] = new int[]{1, 5, 0, 4};
    	//
    	crystals[0][32] = new int[]{0, 3, -3, 5};
    	crystals[0][33] = new int[]{-1, 3, -2, 5};
    	crystals[0][34] = new int[]{1, 3, -2, 5};
    	crystals[0][35] = new int[]{0, 4, -2, 5};
    	crystals[0][36] = new int[]{0, 5, -1, 5};
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        type = par1NBTTagCompound.getInteger("type");
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("type", type);
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
    
    @Override
    public void updateEntity() {
    	if (worldObj.isRemote) return;
    	ticksSinceSync++;
    	if (ticksSinceSync >= 400)
    	{
    		ticksSinceSync = 0;
    		Random r = new Random();
    		int i = r.nextInt(CrystalsCount[type]);
    		int id = worldObj.getBlockId(xCoord + crystals[type][i][0], yCoord + crystals[type][i][1], zCoord + crystals[type][i][2]);
    		if (id == 0 && CheckTreeIntegrity())
    		{
    			worldObj.setBlockAndMetadataWithNotify(xCoord + crystals[type][i][0], yCoord + crystals[type][i][1], zCoord + crystals[type][i][2], YC_Mod.b_treeCrystal.blockID, crystals[type][i][3], 3);
    		}
    	}
    }
    
    public boolean CheckTreeIntegrity()
    {
    	if (type == 0)
    	{
    		if (
    				worldObj.getBlockId(xCoord, yCoord+1, zCoord) == YC_Mod.b_treeLog.blockID &&
    				//
    				worldObj.getBlockId(xCoord, yCoord+2, zCoord) == YC_Mod.b_treeLog.blockID &&
    				//
    				worldObj.getBlockId(xCoord-2, yCoord+3, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord-1, yCoord+3, zCoord-1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord-1, yCoord+3, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord-1, yCoord+3, zCoord+1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+3, zCoord-2) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+3, zCoord-1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+3, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+3, zCoord+1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+3, zCoord+2) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord+1, yCoord+3, zCoord-1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord+1, yCoord+3, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord+1, yCoord+3, zCoord+1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord+2, yCoord+3, zCoord) == YC_Mod.b_treeLog.blockID &&
    				//
    				worldObj.getBlockId(xCoord-1, yCoord+4, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+4, zCoord-1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+4, zCoord) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord, yCoord+4, zCoord+1) == YC_Mod.b_treeLog.blockID &&
    				worldObj.getBlockId(xCoord+1, yCoord+4, zCoord) == YC_Mod.b_treeLog.blockID &&
    				//
    				worldObj.getBlockId(xCoord, yCoord+5, zCoord) == YC_Mod.b_treeLog.blockID)
    			return true;
    	}
    	return false;
    }
	
}
