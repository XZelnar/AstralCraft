package net.minecraft.src;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;

public class YC_Variables {
	
	public static void Save()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			SaveAstralTeleporters();
			SaveResearches();
		}
		else
		{
			
		}
	}
	
	public static void SaveAstralTeleporters()
	{
		MinecraftServer mc = MinecraftServer.getServer();
		String s = ".";
		try
		{
			Minecraft m = Minecraft.getMinecraft();
			s=s+"\\saves\\"+MinecraftServer.getServer().getFolderName()+"\\YC.dat";
		}
		catch (NoClassDefFoundError e)
		{
			s+="\\";
			s=s+MinecraftServer.getServer().getFolderName()+"\\YC.dat";
		}
		finally {}
		YC_ServerPlayerAstralDataList.ClearAllRepeating();
	    File file = new File(s);
	    PrintWriter writer = null;
	    try {
	    	if (file.exists()) file.delete();
	    	file.createNewFile();
	        writer = new PrintWriter(new FileWriter(file, false));
	        YC_ServerPlayerAstralDataList.Save(writer);
	    } catch (IOException e) {
	    } finally {
	    	if (writer != null)
	        writer.close();
	    }
	}

	public static void SaveResearches()
	{
		MinecraftServer mc = MinecraftServer.getServer();
		String s = ".";
		try
		{
			Minecraft m = Minecraft.getMinecraft();
			s=s+"\\saves\\"+MinecraftServer.getServer().getFolderName()+"\\YCr.dat";
		}
		catch (NoClassDefFoundError e)
		{
			s+="\\";
			s=s+MinecraftServer.getServer().getFolderName()+"\\YCr.dat";
		}
		finally {}
		/////////////////////////////////////////////////////YC_ServerPlayerAstralDataList.ClearAllRepeating();
	    File file = new File(s);
	    PrintWriter writer = null;
	    try {
	    	if (file.exists()) file.delete();
	    	file.createNewFile();
	        writer = new PrintWriter(new FileWriter(file, false));
	        YC_ResearchesDataList.Save(writer);
	    } catch (IOException e) {
	    } finally {
	    	if (writer != null)
	        writer.close();
	    }
	}
	
	//===============================================================================================================================================
	
	public static void Load()
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			FMLLog.log(Level.INFO, "[AstralCraft] Attempting to load saved data for world " + MinecraftServer.getServer().getFolderName());
			LoadAstralTeleporters();
			LoadResearches();
		}
		else
		{
			
		}
	}
	
	public static void LoadAstralTeleporters()
	{
		YC_ServerPlayerAstralDataList.data.clear();
		MinecraftServer mc = MinecraftServer.getServer();
		String s = ".";
		try
		{
			Minecraft m = Minecraft.getMinecraft();
			s=s+"\\saves\\"+MinecraftServer.getServer().getFolderName()+"\\YC.dat";
		}
		catch (NoClassDefFoundError e)
		{
			s+="\\";
			s=s+MinecraftServer.getServer().getFolderName()+"\\YC.dat";
		}
		finally {}

		try{
			  FileInputStream fstream = new FileInputStream(s);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			   String strLine;
			  while ((strLine = br.readLine()) != null)   {
				  if (strLine != "")
					  YC_ServerPlayerAstralDataList.Add(strLine);
			  }
			  in.close();
			}
		catch (Exception e){
		}
		YC_ServerPlayerAstralDataList.ClearAllRepeating();
	}
	
	public static void LoadResearches()
	{
		YC_ResearchesDataList.players.clear();
		MinecraftServer mc = MinecraftServer.getServer();
		String s = ".";
		try
		{
			Minecraft m = Minecraft.getMinecraft();
			s=s+"\\saves\\"+MinecraftServer.getServer().getFolderName()+"\\YCr.dat";
		}
		catch (NoClassDefFoundError e)
		{
			s+="\\";
			s=s+MinecraftServer.getServer().getFolderName()+"\\YCr.dat";
		}
		finally {}

		try{
			  FileInputStream fstream = new FileInputStream(s);
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			   String strLine;
			  while ((strLine = br.readLine()) != null)   {
				  if (strLine != "")
					  YC_ResearchesDataList.Add(strLine);
			  }
			  in.close();
			}
		catch (Exception e){
		}
		/////////////////////////////////////////YC_ServerPlayerAstralDataList.ClearAllRepeating();
	}
	
	
	
}
