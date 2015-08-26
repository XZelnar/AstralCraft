package net.minecraft.src;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class YC_ResearchesDataList {
	public static List players = new ArrayList();
	
	public static void AddEmpty(String name)
	{
		if (name == "") return;
		players.add(new YC_ResearchesData(name));
	}
	
	public static int GetIndexByName(String name)
	{
		for(int i = 0; i<players.size(); i++)
		{
			if (((YC_ResearchesData)players.get(i)).PlayerName.equals(name))
			{
				return i;
			}
		}
		return -1;
	}
	
	public static YC_ResearchesData GetPlayerData(String name)
	{
		int i = GetIndexByName(name);
		if (i == -1) AddEmpty(name);
		i = GetIndexByName(name);
		return (YC_ResearchesData) players.get(i);
	}
	
	public static void IncResearchLevel(String name)
	{
		((YC_ResearchesData) players.get(GetIndexByName(name))).researchLevel++;
	}
	
	public static YC_ResearchesData GetPlayerData(int index)
	{
		return (YC_ResearchesData) players.get(index);
	}
	
	public static boolean Exists(String name)
	{
		return GetIndexByName(name) != -1;
	}
	
	public static boolean CreateIfNotExists(String name)
	{
		int t = GetIndexByName(name);
		if (t == -1) AddEmpty(name);
		return t != -1;
	}
	
	public static void HandleIntData(int[] intData)
	{
        int l = intData[4];
        String PlayerName = "";
        for(int i = 0; i<l; i++)
        {
      	  PlayerName = PlayerName + ((char)intData[i+5]);
        }
        int t = GetIndexByName(PlayerName);
        if (t == -1)
        {
        	AddEmpty(PlayerName);
        	t = GetIndexByName(PlayerName);
        }
        
        GetPlayerData(t).HandleIntData(intData);
	}
	
	public static void AddToPlayer(String PlayerName, int water, int fire, int nature, int ender)
	{
        int t = GetIndexByName(PlayerName);
        if (t == -1)
        {
        	AddEmpty(PlayerName);
        	t = GetIndexByName(PlayerName);
        }
        
        GetPlayerData(t).IncVars(water, fire, nature, ender);
	}

	
	

	public static void Save(PrintWriter writer)
	{
		for(int i = 0; i<players.size(); i++)
		{
			((YC_ResearchesData)players.get(i)).Save(writer);
		}
	}
	
	public static void PrintStatus()
	{
		for(int i = 0; i<players.size(); i++)
		{
			((YC_ResearchesData)players.get(i)).Print();
		}
	}
	
	public static void Add(String s)//kinda like load
	{
		String[] ss = s.split(String.valueOf((char)4));
		if (ss[0] == "") return;
		YC_ResearchesData d = new YC_ResearchesData(ss[0]);
		d.fire = Integer.valueOf(ss[1]);
		d.nature = Integer.valueOf(ss[2]);
		d.water = Integer.valueOf(ss[3]);
		d.ender = Integer.valueOf(ss[4]);
		d.researchLevel = Integer.valueOf(ss[5]);
		//TODO remove in next version
		if (ss.length <= 7)
			d.DecypherResearches(ss[6]);
		else
		{
			d.techLevel = Integer.valueOf(ss[6]);
			d.DecypherResearches(ss[7]);
		}
		players.add(d);
		//Add(	ss[0],
		//		Double.parseDouble(ss[1]),Double.parseDouble(ss[2]),Double.parseDouble(ss[3]),
		//		Integer.parseInt(ss[4]),
		//		Double.parseDouble(ss[5]),Double.parseDouble(ss[6]),Double.parseDouble(ss[7]));
	}
}
