package net.minecraft.src;

public class YC_Options {
	public static boolean HighPolyModels = true;
	public static boolean RenderContourSecondPass = true;
	public static boolean GenerateCrystals = true;
	public static boolean GenerateSymbols = true;
	public static boolean GenerateEnergyPockets = true;
	public static boolean GenerateStructures = true;
	public static boolean AllowVBO = true;
	public static boolean UseVBO = true;
	public static boolean ForceNativeRender = false;
	public static boolean AllowTexturesFX = false;
	public static int RenderDistance = 48;
	
	public static boolean EnableOptifineCompability = false;

	public static int ResearchShardTimeValue = 60;
	public static int ResearchSpeed = 1;
	
	public static String GetStringForLog()
	{
		String s;
		s = GetStringForBoolean(HighPolyModels) + GetStringForBoolean(RenderContourSecondPass) + GetStringForBoolean(GenerateCrystals) + GetStringForBoolean(GenerateSymbols) + 
				GetStringForBoolean(GenerateEnergyPockets) + GetStringForBoolean(GenerateStructures) + GetStringForBoolean(AllowVBO) + GetStringForBoolean(UseVBO) + 
				GetStringForBoolean(ForceNativeRender) + GetStringForBoolean(AllowTexturesFX) + GetStringForBoolean(EnableOptifineCompability) + "; " + RenderDistance + "; " + 
				ResearchShardTimeValue + "; " + ResearchSpeed;
		return s;
	}
	
	public static String GetStringForBoolean(boolean b)
	{
		return b ? "1" : "0";
	}
}
