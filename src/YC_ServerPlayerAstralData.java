package net.minecraft.src;

import java.io.PrintWriter;

public class YC_ServerPlayerAstralData {
	public String PlayerName = "";
	public double x = 0, y = 0, z = 0;
	public double AstralXPos = 0, AstralYPos = 0, AstralZPos = 0;
	public int dim = 0;
	
	public YC_ServerPlayerAstralData(String name, double xc, double yc, double zc, int d, 
			double axp, double ayp, double azp)
	{
		PlayerName = name;
		x=xc;
		y=yc;
		z=zc;
		dim = d;
		AstralXPos = axp;
		AstralYPos = ayp;
		AstralZPos = azp;
	}
	
	public void Save(PrintWriter writer)
	{
		writer.write(PlayerName + (char)4);
		writer.write(String.valueOf(x));
		writer.write((char)4);
		writer.write(String.valueOf(y));
		writer.write((char)4);
		writer.write(String.valueOf(z));
		writer.write((char)4);
		writer.write(String.valueOf(dim));
		writer.write((char)4);
		writer.write(String.valueOf(AstralXPos));
		writer.write((char)4);
		writer.write(String.valueOf(AstralYPos));
		writer.write((char)4);
		writer.write(String.valueOf(AstralZPos));
		writer.println();
	}
	
	public void Print()
	{
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
		System.out.println("");
	}
}
