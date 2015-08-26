package net.minecraft.src;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class YC_ServerPlayerAstralDataList {
	public static List data = new ArrayList<YC_ServerPlayerAstralData>();
	
	public static void Add(String s)
	{
		String[] ss = s.split(String.valueOf((char)4));
		Add(	ss[0],
				Double.parseDouble(ss[1]),Double.parseDouble(ss[2]),Double.parseDouble(ss[3]),
				Integer.parseInt(ss[4]),
				Double.parseDouble(ss[5]),Double.parseDouble(ss[6]),Double.parseDouble(ss[7]));
	}
	
	public static void Add(String name, double x, double y, double z, int d, double axp, double ayp, double azp)
	{
		for(int i = 0; i<data.size(); i++)
		{
			if (((YC_ServerPlayerAstralData)data.get(i)).PlayerName.equals(name))
				return;
		}
		data.add(new YC_ServerPlayerAstralData(name,x,y,z,d,axp,ayp,azp));
	}
	
	public static void SetXYZD(String name, double x, double y, double z, int dim)
	{
		for(int i = 0; i<data.size(); i++)
		{
			if (((YC_ServerPlayerAstralData)data.get(i)).PlayerName.equals(name))
			{
				YC_ServerPlayerAstralData d = (YC_ServerPlayerAstralData)data.get(i);
				d.x=x;
				d.y=y;
				d.z=z;
				d.dim=dim;
				//data.set(i, d);
				return;
			}
		}
	}
	
	public static YC_ServerPlayerAstralData GetPlayerData(String name)
	{
		for(int i = 0; i<data.size(); i++)
		{
			if (((YC_ServerPlayerAstralData)data.get(i)).PlayerName.equals(name))
			{
				return (YC_ServerPlayerAstralData) data.get(i);
			}
		}
		return null;
	}

	public static void Save(PrintWriter writer)
	{
		for(int i = 0; i<data.size(); i++)
		{
			((YC_ServerPlayerAstralData)data.get(i)).Save(writer);
		}
	}
	
	public static void PrintStatus()
	{
		for(int i = 0; i<data.size(); i++)
		{
			((YC_ServerPlayerAstralData)data.get(i)).Print();
		}
	}
	
	public static void ClearAllRepeating()
	{
		for(int i = 0; i<data.size(); i++)
		{
			YC_ServerPlayerAstralData d1 = (YC_ServerPlayerAstralData) data.get(i);
			for(int j = i+1; j<data.size(); j++)
			{
				YC_ServerPlayerAstralData d2 = (YC_ServerPlayerAstralData) data.get(j);
				if (d1.PlayerName.equals(d2.PlayerName))
				{
					data.remove(j);
					j--;
				}
			}
		}
	}
	
	public static boolean PlayerExists(String name)
	{
		for(int i = 0; i<data.size(); i++)
		{
			if (((YC_ServerPlayerAstralData)data.get(i)).PlayerName.equals(name))
				return true;
		}
		return false;
	}
	
	public static boolean NullifyDataButSaveOrder(String name)
	{
		YC_ServerPlayerAstralData d = GetPlayerData(name);
		if (d == null) return false;
		int cur = 0;
		boolean retry = true;
		while(retry)
		{
			retry = false;
			for(int i = 0; i<data.size(); i++)
			{
				if (((YC_ServerPlayerAstralData)data.get(i)).PlayerName.equals(String.valueOf(cur)))
				{
					cur++;
					retry = true;
					break;
				}
			}
		}
		d.PlayerName = String.valueOf(cur);
		return true;
	}
}
