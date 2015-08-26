package net.minecraft.src;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.old.TextureFXManager;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class YC_ClientTickHandler implements ITickHandler
{
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {}

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.RENDER)))
        {
            onRenderTick();
        }
        else if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen != null)
            {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }
        }
        if (type.equals(EnumSet.of(TickType.SERVER)))
        {
            ServerTick();
        }
    }
    
    public void ServerTick()
    {
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
        // In my testing only RENDER, CLIENT, & PLAYER did anything on the client side.
        // Read 'cpw.mods.fml.common.TickType.java' for a full list and description of available types
    }

    @Override
    public String getLabel() { return null; }


    public void onRenderTick()
    {
    }

    public void onTickInGUI(GuiScreen guiscreen)
    {
    	TextureFXManager.onTick();
    	if (!(guiscreen instanceof net.minecraft.client.gui.GuiMainMenu) && !(guiscreen instanceof net.minecraft.client.gui.GuiMultiplayer) && 
    			!(guiscreen instanceof net.minecraft.client.gui.GuiRenameWorld) && !(guiscreen instanceof net.minecraft.client.gui.GuiScreenAddServer) &&
    			!(guiscreen instanceof net.minecraft.client.gui.GuiScreenServerList) && !(guiscreen instanceof net.minecraft.client.gui.GuiSelectWorld))
    	{
    		TicksInGame++;
    		YC_BiomeTextureFXManager.OnTick();
    		UpdateEPTexture();
    		if (TicksInGame>=MaxTicks) TicksInGame -= MaxTicks;
    	}
    	if (TicksInGame % 80 == 1)
    	//{
    		YC_DelayedUpdatesManager.Update();
    	//}
    }

    public void onTickInGame()
    {
    	TextureFXManager.onTick();
    	TicksInGame++;
    	YC_BiomeTextureFXManager.OnTick();
    	UpdateEPTexture();
    	if (TicksInGame % 80 == 1)
    	//{
    		YC_DelayedUpdatesManager.Update();
    	//}
    	if (TicksInGame>=MaxTicks) TicksInGame -= MaxTicks;
    }
    
    public void UpdateEPTexture()
    {
    	YC_Constants.CurrentEnergyPocketTexture = Minecraft.getMinecraft().renderEngine.getTexture("/YC/EP/EP"+(TicksInGame % 8)+".png");
    }
    
    
    
    
    
    
    public static int MaxTicks = 6400;
    public static int TicksInGame = 0;//0..MaxTicks;
    
    
}