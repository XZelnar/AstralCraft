package net.minecraft.src;

import net.minecraft.tileentity.TileEntity;

public class YC_TileEntityBlockUpdater extends TileEntity {

	public int delay = 1;
	public int tss = 0;
	
	public YC_TileEntityBlockUpdater(int ticks)
	{
		delay = ticks;
	}
	
	@Override
	public void updateEntity() {
		tss++;
		if (tss >= delay)
		{
			tss -= delay;
			worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord), 1);
		}
	}
	
}
