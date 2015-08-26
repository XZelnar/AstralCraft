package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.World;

public class YC_GarbageCollector {
	public static List<Integer> TileEntityesToRemove = new ArrayList<Integer>();
	public static List<World> TileEntityesToRemoveWorlds = new ArrayList<World>();
	
	public static void WipeTileEntityes()
	{
		for(int i = 0; i<TileEntityesToRemoveWorlds.size(); i++)
		{
			TileEntityesToRemoveWorlds.get(i).setBlockTileEntity(
					TileEntityesToRemove.get(i*3), TileEntityesToRemove.get(i*3+1), 
					TileEntityesToRemove.get(i*3+2), null);
		}
		TileEntityesToRemove.clear();
		TileEntityesToRemoveWorlds.clear();
	}
	
}
