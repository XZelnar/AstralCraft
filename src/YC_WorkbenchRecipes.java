package net.minecraft.src;

public class YC_WorkbenchRecipes {

	public static YC_WorkbenchRecipe[] recipes = new YC_WorkbenchRecipe[0];
	
	public static void AddRecipe(YC_WorkbenchRecipe r)
	{
		YC_WorkbenchRecipe[] rec = new YC_WorkbenchRecipe[recipes.length+1];
		for(int i = 0; i<recipes.length; i++)
		{
			rec[i]=recipes[i];
		}
		rec[recipes.length]=r;
		recipes = rec;
	}
	
	public static int GetRecipe(String[] s)
	{
		for(int i = 0; i<recipes.length; i++)
		{
			if (recipes[i].IsRecipe(s)) return i;
		}
		return -1;
	}
	
}
