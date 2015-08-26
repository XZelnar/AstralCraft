package net.minecraft.src;

import net.minecraft.tileentity.*;

public class YC_TileEntityBlockRenderUpdater extends TileEntity {

	@Override
	public void updateEntity() {
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
}
