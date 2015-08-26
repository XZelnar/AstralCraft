package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;

public class YC_PacketHandler implements IPacketHandler {


	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("YC_BlockDetector")) {
			handleBlockDetector(packet);
		}
		if (packet.channel.equals("YC_SymbolInput")) {
			handleSymbolInput(packet);
		}
		if (packet.channel.equals("YC_Workbench")) {
			handleWorkbench(packet);
		}
		if (packet.channel.equals("YC_Digger")) {
			handleDigger(packet);
		}
		if (packet.channel.equals("YC_RainStarter")) {
			handleRainStarter(packet);
		}
		if (packet.channel.equals("YC_RainStopper")) {
			handleRainStopper(packet);
		}
		if (packet.channel.equals("YC_FX"))
		{
			if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			{
				DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
				int t = 0, x = 0, y = 0, z = 0;
				try {
					t = inputStream.readInt();
					x = inputStream.readInt();
					y = inputStream.readInt();
					z = inputStream.readInt();
			    	YC_ClientProxy.SpawnParticle(t,x,y,z);
				} 
				catch (IOException e) {e.printStackTrace();}
			}
		}
		
		if (packet.channel.equals("YC_SymbolInputSC"))
		{
			handleSymbolInputServerCheck(packet);
		}
		
		if (packet.channel.equals("YC_Research"))
		{
			handleResearchTable(packet);
		}
		
		if (packet.channel.equals("YC_ResearchS"))
		{
			handleResearchTableS(packet);
		}
		
		if (packet.channel.equals("YC_Researched"))
		{
			handleResearched(packet);
		}
		
		if (packet.channel.equals("YC_ResShardGen"))
		{
			handleResearchShardGenerator(packet);
		}
		
		if (packet.channel.equals("YC_Crystalizer"))
		{
			handleCrystalizer(packet);
		}
		
		if (packet.channel.equals("YC_DSD"))
		{
			handleDSD(packet);
		}
		
		if (packet.channel.equals("YC_AdvAstrTel"))
		{
			handleAdvAstrTel(packet);
		}
		
		if (packet.channel.equals("YC_AstrBeacon"))
		{
			handleAstrBeacon(packet);
		}
		
		if (packet.channel.equals("YC_EnergyPocket"))
		{
			handleEnergyPocket(packet);
		}
		
		if (packet.channel.equals("YC_Chest"))
		{
			handleChestPocket(packet);
		}
		
		if (packet.channel.equals("YC_ChestPage"))
		{
			handleChestPage(packet);
		}
		
	}
	
	private void handleChestPage(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			int d = inputStream.readInt();

			MinecraftServer s = MinecraftServer.getServer();
			WorldServer w = s.worldServerForDimension(d);
			TileEntity te = w.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockChest)
			{
				int t = 0;
				t = inputStream.readInt();
		        int l = inputStream.readInt();
		        String PlayerName = "";
		        for(int i = 0; i<l; i++)
		        {
		            PlayerName = PlayerName + ((char)inputStream.readInt());
		        }
				((YC_TileEntityBlockChest)te).handlePacketDataPage(t, PlayerName);
				PacketDispatcher.sendPacketToAllAround(x, y, z, 16, d, te.getDescriptionPacket());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleChestPocket(Packet250CustomPayload packet) {
		//if (packet == null || packet.data == null) return;
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockChest)
			{
				int[] t = new int[inputStream.readInt()];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockChest)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleEnergyPocket(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockEnergyPocket)
			{
				int[] t = new int[1];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockEnergyPocket)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleAstrBeacon(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockAstralBeacon)
			{
				int[] t = new int[257];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockAstralBeacon)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleAdvAstrTel(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockAdvAstralTeleporter)
			{
				int[] t = new int[258];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockAdvAstralTeleporter)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleDSD(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockDoorStateDetector)
			{
				int[] t = new int[3];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockDoorStateDetector)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleCrystalizer(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockCrystalizer)
			{
				int[] t = new int[31];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockCrystalizer)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleResearchShardGenerator(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockResearchShardGenerator)
			{
				int[] t = new int[7];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockResearchShardGenerator)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleResearched(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int[] t = new int[4+257 + YC_ResearchesData.C_RESEARCHES + 2];
			for(int i = 0;i < t.length; i++)
			{
				t[i]=inputStream.readInt();
			}
			YC_ResearchesDataList.HandleIntData(t);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleResearchTableS(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			int d = inputStream.readInt();

			MinecraftServer s = MinecraftServer.getServer();
			WorldServer w = s.worldServerForDimension(d);
			TileEntity te = w.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockResearchTable)
			{
				int[] t = new int[6+257+1];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockResearchTable)te).handlePacketDataS(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleResearchTable(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockResearchTable)
			{
				int[] t = new int[6+257];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockResearchTable)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleSymbolInputServerCheck(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			int d = inputStream.readInt();

			MinecraftServer s = MinecraftServer.getServer();
			WorldServer w = s.worldServerForDimension(d);
			TileEntity te = w.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockSymbolInput)
			{
				((YC_TileEntityBlockSymbolInput)te).CheckActivate();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleTimeOfDay(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockTimeOfDay)
			{
				int[] t = new int[1];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockTimeOfDay)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleRainStopper(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockRainStopper)
			{
				int[] t = new int[1];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockRainStopper)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleRainStarter(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockRainStarter)
			{
				int[] t = new int[1];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockRainStarter)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleDigger(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockDigger)
			{
				int[] t = new int[3];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockDigger)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleWorkbench(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockWorkbench)
			{
				int[] t = new int[193];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockWorkbench)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleSymbolInput(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockSymbolInput)
			{
				int[] t = new int[((YC_TileEntityBlockSymbolInput) te).chestContents.length * 4 + 3];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockSymbolInput)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	private void handleBlockDetector(Packet250CustomPayload packet) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		try {
			int x = inputStream.readInt();
			int y = inputStream.readInt();
			int z = inputStream.readInt();
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
			if (te instanceof YC_TileEntityBlockDetector)
			{
				int[] t = new int[28];
				for(int i = 0;i < t.length; i++)
				{
					t[i]=inputStream.readInt();
				}
				((YC_TileEntityBlockDetector)te).handlePacketData(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

}