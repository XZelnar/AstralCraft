package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class YC_DelayedCycledRerenderUpdate {
	public static Minecraft mc;
	
	public int x,y,z;
	
	public YC_DelayedCycledRerenderUpdate(int xc, int yc, int zc) {
		x=xc;y=yc;z=zc;
	}
	
	public void Update()
	{
		mc.renderGlobal.markBlocksForUpdate(x, y, z, x, y, z);
	}
	
	public void Tick()
	{
		mc.renderGlobal.markBlocksForUpdate(x, y, z, x, y, z);
	}
}
