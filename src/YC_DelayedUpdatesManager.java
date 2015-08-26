package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class YC_DelayedUpdatesManager {
	public static List crystals = new ArrayList<YC_DelayedCycledRerenderUpdate>();
	public static List ores = new ArrayList<YC_DelayedCycledRerenderUpdate>();
	
	public static void Update()
	{
		if (Minecraft.getMinecraft().theWorld == null) return;
		for(int i = 0; i<crystals.size(); i++)
		{
			((YC_DelayedCycledRerenderUpdate)crystals.get(i)).Update();
		}
		crystals.clear();
		for(int i = 0; i<ores.size(); i++)
		{
			((YC_DelayedCycledRerenderUpdate)ores.get(i)).Update();
		}
		ores.clear();
	}
	
	public static void AddCrystal(int x, int y, int z)
	{
		crystals.add(new YC_DelayedCycledRerenderUpdate(x, y, z));
	}
	
	public static void AddOre(int x, int y, int z)
	{
		ores.add(new YC_DelayedCycledRerenderUpdate(x, y, z));
	}
}
