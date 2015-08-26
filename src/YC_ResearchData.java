package net.minecraft.src;

public class YC_ResearchData {
	public int x=0,y=0;
	public int u=0,v=0;
	public boolean researched = false;
	public int index = 0;
	public int[] requirements = new int[0];
	
	public int ToolTipWidth = 50, ToolTipHeight = 30;
	public String[] Description = new String[0];
	public String ResearchedBG = "";
	public int ResearchedWidth = 0, ResearchedHeight = 0;
	public int[] RequirementsIDs = new int[0];//required researches
	public int[] RequiredHabitat = new int[0];
	
	public YC_ResearchData(int xc, int yc, int uc, int vc, int ind, int[] req, int ttw, int tth, String[] desc, String resBG, int resW, int resH, int[] reqs, int[] habitat)
	{
		researched = false;
		x=xc;
		y=yc;
		u=uc;
		v=vc;
		index = ind;
		requirements = req;//n,w,f,e
		ToolTipWidth = ttw;
		ToolTipHeight = tth;
		Description = desc;
		ResearchedBG = resBG;
		ResearchedWidth = resW;
		ResearchedHeight = resH;
		RequirementsIDs = reqs;
		RequiredHabitat = habitat;
	}
	
	public boolean IsPointIn(int xc, int yc)
	{
		return (xc>=x && yc>=y && xc<=x+22 && yc<=y+22);
	}
	
	
	
}
