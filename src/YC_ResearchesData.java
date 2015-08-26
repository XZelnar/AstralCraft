package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public class YC_ResearchesData {
	public static int C_RESEARCHES = 12; // TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public List researches = new ArrayList();
	
	public int fire = 0, nature = 0, water = 0, ender = 0;
	public String PlayerName = "";
	public int researchLevel = 0;//number of researches commited
	public int techLevel = 0;//tech level, gained from astral
	
	public YC_ResearchesData(String name)
	{
		PlayerName = name;
		Init();
	}
	
	public void Init()
	{
		//lava generator
		YC_ResearchData r = new YC_ResearchData(28, 5, 22, 0, 0, new int[]{0,0,1,0,1}, 85, 36, 
				new String[]{"A simple device","that generates","lava"}, 
				"/YC/Researches/lavaGenerator.png", 181, 102, new int[]{}, new int[]{0});
		researches.add(r);
		//rain stopper
		r = new YC_ResearchData(4, 35, 38, 0, 1, new int[]{1,0,2,0,1}, 105, 18, 
				new String[]{"Device to stop rain"}, 
				"/YC/Researches/rainStopper.png", 178, 100, new int[]{0}, new int[]{0});
		researches.add(r);
		//rain starter
		r = new YC_ResearchData(52, 35, 54, 0, 2, new int[]{1,1,1,0,1}, 108, 18, 
				new String[]{"Device to start rain"}, 
				"/YC/Researches/rainStarter.png", 179, 102, new int[]{0}, new int[]{0});
		researches.add(r);
		//digger
		r = new YC_ResearchData(95, 5, 70, 0, 3, new int[]{2,0,0,1,1}, 142, 36, 
				new String[]{"Advanced machine that","digs out useful resources","leaving terrain unharmed"}, 
				"/YC/Researches/digger.png", 184, 104, new int[]{}, new int[]{4});
		researches.add(r);
		//crystalizer
		r = new YC_ResearchData(95, 35, 86, 0, 4, new int[]{2,1,2,0,1}, 116, 36, 
				new String[]{"Machine to compress","8 coal and a diamond","into crystals"}, 
				"/YC/Researches/crystalizer.png", 180, 104, new int[]{3}, new int[]{3});
		researches.add(r);
		//astral teleporter
		r = new YC_ResearchData(156, 5, 102, 0, 5, new int[]{1,1,1,3,1}, 150, 36, 
				new String[]{"Device that can rip","through space to teleport","it's owner to a distant place"}, 
				"/YC/Researches/astralTeleporter.png", 182, 102, new int[]{}, new int[]{});
		researches.add(r);
		//advanced astral teleporter
		r = new YC_ResearchData(136, 65, 118, 0, 6, new int[]{2,2,2,4,2}, 155, 36, 
				new String[]{"Addition to Astral Teleporter.","Allows to create more stable","passages in space."}, 
				"/YC/Researches/advAstTel.png", 179, 103, new int[]{5}, new int[]{3});
		researches.add(r);
		//astral retransmitter
		r = new YC_ResearchData(177, 65, 134, 0, 7, new int[]{2,2,2,5,2}, 145, 36, 
				new String[]{"Allows you to link yourself","with a different island in","astral."}, 
				"/YC/Researches/astralRetransmitter.png", 179, 103, new int[]{5}, new int[]{2});
		researches.add(r);
		//chest
		r = new YC_ResearchData(156, 95, 150, 0, 8, new int[]{3,2,5,8,3}, 155, 36, 
				new String[]{"A chest that has no storrage","limit! Can hold as many items,","as you put."}, 
				"/YC/Researches/chest.png", 179, 103, new int[]{5}, new int[]{1});
		researches.add(r);
		//gravity explosion grenade
		r = new YC_ResearchData(4, 65, 166, 0, 9, new int[]{4,3,4,5,2}, 155, 27, 
				new String[]{"Pushes everything away from","it when explodes."}, 
				"/YC/Researches/GravExpGren.png", 179, 103, new int[]{}, new int[]{});
		researches.add(r);
		//gravity implosion grenade
		r = new YC_ResearchData(52, 65, 182, 0, 10, new int[]{4,3,4,5,2}, 150, 27, 
				new String[]{"Pushes everything towards","it when explodes."}, 
				"/YC/Researches/GravImpGren.png", 179, 103, new int[]{}, new int[]{});
		researches.add(r);
		//gravity implosion grenade
		r = new YC_ResearchData(4, 95, 198, 0, 11, new int[]{6,2,6,3,3}, 123, 36, 
				new String[]{"Device that, when","activated, scares away","Ghasts"}, 
				"/YC/Researches/subEmitter.png", 179, 103, new int[]{9}, new int[]{});
		researches.add(r);
		
		//C_RESEARCHES++;              !!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!++++!!!!
	}
	
	public int GetIndexByCoord(int x, int y)
	{
		for(int i = 0; i<researches.size(); i++)
		{
			if (((YC_ResearchData)researches.get(i)).IsPointIn(x, y)) return i;
		}
		return -1;
	}
	
	
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			int[] t = buildIntDataList();
			for(int i = 0; i<t.length; i++)
			{
				outputStream.writeInt(t[i]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_Researched";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
    
    public int[] buildIntDataList() {
          int[] sortList = new int[4+257 + C_RESEARCHES + 2];
          sortList[0] = fire;
          sortList[1] = water;
          sortList[2] = nature;
          sortList[3] = ender;
    	  char[] t = PlayerName.toCharArray();
    	  sortList[4]=t.length;
          for(int i = 0; i<t.length; i++)
          {
        	  sortList[5+i]=t[i];
          }
          for(int i = 0; i<C_RESEARCHES; i++)
          {
        	  sortList[261+i] = ((YC_ResearchData)researches.get(i)).researched ? 1 : 0;
          }
          sortList[sortList.length - 2] = techLevel;
          sortList[sortList.length - 1] = researchLevel;
          return sortList;
     }
    
    public void HandleIntData(int[] intData)
    {
    	fire = intData[0];
    	water = intData[1];
    	nature = intData[2];
    	ender = intData[3];
    	
    	for(int i = 0; i<C_RESEARCHES; i++)
    	{
    		if (intData[261+i] == 1) ((YC_ResearchData)researches.get(i)).researched = true;
    		else ((YC_ResearchData)researches.get(i)).researched = false;
    	}
    	techLevel = intData[intData.length - 2];
    	researchLevel = intData[intData.length - 1];
    }
    
    public void IncVars(int w, int f, int n, int e)
    {
    	water += w;
    	fire += f;
    	nature += n;
    	ender += e;
    }
    
    
    
    
    public String GetResearchDescription()
    {
    	String s = "";
    	for (int i = 0; i < C_RESEARCHES; i++)
    	{
    		s=s+(((YC_ResearchData)researches.get(i)).researched ? "1" : "0");
    	}
    	return s;
    }
    
    public void DecypherResearches(String s)
    {
    	char[] a = s.toCharArray();
    	for(int i = 0; i<s.length(); i++)
    	{
    		((YC_ResearchData)researches.get(i)).researched = (a[i] == '1' ? true : false);
    	}
    }
	
	public void Save(PrintWriter writer)
	{
		writer.write(PlayerName + (char)4);
		writer.write(String.valueOf(fire));
		writer.write((char)4);
		writer.write(String.valueOf(nature));
		writer.write((char)4);
		writer.write(String.valueOf(water));
		writer.write((char)4);
		writer.write(String.valueOf(ender));
		writer.write((char)4);
		writer.write(String.valueOf(researchLevel));
		writer.write((char)4);
		writer.write(String.valueOf(techLevel));
		writer.write((char)4);
		writer.write(GetResearchDescription());
		writer.println();
	}
	
	public void Print()
	{/*
		System.out.print(PlayerName);
		System.out.print("  ;  ");
		System.out.print(x);
		System.out.print("  ;  ");
		System.out.print(y);
		System.out.print("  ;  ");
		System.out.print(z);
		System.out.print("  ;  ");
		System.out.print(AstralXPos);
		System.out.print("  ;  ");
		System.out.print(AstralYPos);
		System.out.print("  ;  ");
		System.out.print(AstralZPos);
		System.out.println("");*/
	}
	
}
