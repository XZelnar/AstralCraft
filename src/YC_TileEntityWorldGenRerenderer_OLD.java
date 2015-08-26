package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.input.Cursor;

import cpw.mods.fml.common.FMLCommonHandler;

public class YC_TileEntityWorldGenRerenderer_OLD extends TileEntity
{
    private int ticksSinceSync;

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
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
    	/*ticksSinceSync++;
    	if (ticksSinceSync>=20)
    	{
    		ticksSinceSync -= 20;
    		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
    	}//*/
    }
    
    /*
    @Override
    public void onChunkUnload() {
    	if (!worldObj.isRemote)
    	{
    		if ((Minecraft.getMinecraft() != null && worldObj.getWorldInfo().getDimension() != Minecraft.getMinecraft().thePlayer.dimension))
    		{
    			for(int i = 0; i<worldObj.loadedTileEntityList.size(); i++)
    			{
    				if (worldObj.loadedTileEntityList.get(i) instanceof YC_TileEntityWorldGenRerenderer)
    					worldObj.loadedTileEntityList.remove(i--);
    			}
    		}
    		else
    		{
        		//*
    			int cx = (xCoord/16)*16, cz = (zCoord/16)*16;
        		TileEntity tp;
    			for(int i = 0; i<worldObj.loadedTileEntityList.size(); i++)
    			{
    				tp = (TileEntity) worldObj.loadedTileEntityList.get(i);
    				if (tp instanceof YC_TileEntityWorldGenRerenderer && tp.xCoord >= cx && tp.zCoord>=cz && tp.xCoord<=cx+16 && tp.zCoord<=cz+16)
    					worldObj.loadedTileEntityList.remove(i--);
    			}//*|/
    		}
    	}
    }//*/
	
}
