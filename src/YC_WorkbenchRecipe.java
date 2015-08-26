package net.minecraft.src;

import net.minecraft.item.ItemStack;

public class YC_WorkbenchRecipe {
	
	public String[] recipe = new String[5];
	public ItemStack output = null;
	public int FuelCost = 0;
	public int ResearchIndex = 0;
	
	
	public YC_WorkbenchRecipe(String[] r, ItemStack o, int fuel, int ResIndex)
	{
		output = o;
		recipe = r;
		FuelCost = fuel;
		ResearchIndex = ResIndex;
	}
	
	public boolean IsRecipe(String[] r)
	{
		if (recipe[0].equals(r[0]) && recipe[1].equals(r[1]) && 
				recipe[2].equals(r[2]) && recipe[3].equals(r[3]) && 
				recipe[4].equals(r[4]))
			return true;
		return false;
	}
	
}
