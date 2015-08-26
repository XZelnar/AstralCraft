package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Dimension;

import cpw.mods.fml.client.TextureFXManager;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.old.TextureFX;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

public class YC_ClientProxy extends YC_CommonProxy {
	
	public static List AdvAstTelRemoveCoord = new ArrayList();
	public static int biomeFXTexture = 0;
	public static YC_TextureFXBiome[] biomeTexturesFX = new YC_TextureFXBiome[65];
	
	@Override
	public void registerRenderInformation() 
	{
		FMLLog.log(Level.INFO, "[AstralCraft] Preloading textures");
		PreloadTextures();
		FMLLog.log(Level.INFO, "[AstralCraft] Registering renderers");
		RenderersSetup();
		
		if (YC_Options.AllowVBO)
		{
			YC_Options.UseVBO = GLContext.getCapabilities().GL_ARB_vertex_buffer_object;
		}
		
		if (YC_Options.HighPolyModels)
		{
			FMLLog.log(Level.INFO, "[AstralCraft] Attempting to load high-poly models");
			LoadModels();
		}
		else
		{
			FMLLog.log(Level.INFO, "[AstralCraft] Attempting to load low-poly models");
			LoadModelsLowPoly();
		}
		FMLLog.log(Level.INFO, "[AstralCraft] Models loaded");

		RenderingRegistry.registerEntityRenderingHandler(YC_EntityBeaconGuardian.class, new YC_RenderBeaconGuardian());
		TickRegistry.registerTickHandler(new YC_ClientTickHandler(), Side.CLIENT);
	}
	
	public void PreloadTextures()
	{
		MinecraftForgeClient.preloadTexture("/gui/YC_blockDetector.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_Workbench.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_SymbolInput.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_Research.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_ResearchBorder.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_ResearchIcons.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_CrystalGenerator.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_ResearchShardGenerator.png");
		MinecraftForgeClient.preloadTexture("/gui/YC_Chest.png");
		MinecraftForgeClient.preloadTexture("/YC/beaconGuardian.png");
		MinecraftForgeClient.preloadTexture("/YC/YC_textures.png");
		MinecraftForgeClient.preloadTexture("/YC/dig_texture.png");
		MinecraftForgeClient.preloadTexture("/YC/blank.png");
		MinecraftForgeClient.preloadTexture("/YC/workbench.png");
		MinecraftForgeClient.preloadTexture("/YC/symbolInput.png");
		MinecraftForgeClient.preloadTexture("/YC/crystalizer_base.png");
		MinecraftForgeClient.preloadTexture("/YC/AdvAstTel1.png");
		MinecraftForgeClient.preloadTexture("/YC/AdvAstTel2.png");
		MinecraftForgeClient.preloadTexture("/YC/biomeFX.png");
		MinecraftForgeClient.preloadTexture("/YC/tree.png");
		MinecraftForgeClient.preloadTexture("/YC/astralBeacon.png");
		MinecraftForgeClient.preloadTexture("/YC/astralBeacon2.png");
		MinecraftForgeClient.preloadTexture("/YC/astralBeaconBeam.png");
		MinecraftForgeClient.preloadTexture("/YC/YC_Chest.png");
		
		MinecraftForgeClient.preloadTexture("/YC/YC_crystal.png");
		MinecraftForgeClient.preloadTexture("/YC/LRes/YC_crystal.png");
		MinecraftForgeClient.preloadTexture("/YC/ore.png");
		MinecraftForgeClient.preloadTexture("/YC/YC_tg.png");
		
		MinecraftForgeClient.preloadTexture("/YC/particles.png");
		MinecraftForgeClient.preloadTexture("/YC/particles/AdvAstTel.png");
		
		MinecraftForgeClient.preloadTexture("/YC/RainToggle0.png");
		MinecraftForgeClient.preloadTexture("/YC/RainToggle1.png");
		MinecraftForgeClient.preloadTexture("/YC/RainToggle2.png");
		MinecraftForgeClient.preloadTexture("/YC/RainToggle3.png");
		
		MinecraftForgeClient.preloadTexture("/textures/blocks/YC_techGrass.png");
		
		//AdvAstTelGlowTextures
		for(int i = 0; i<16; i++)
		{
			MinecraftForgeClient.preloadTexture("/YC/AdvAstTel/AdvAstTelGlow"+i+".png");
			MinecraftForgeClient.preloadTexture("/YC/AdvAstTel/LP/AdvAstTelGlow"+i+".png");
		}
		
		for(int i = 0; i < 8; i++)
		{
			MinecraftForgeClient.preloadTexture("/YC/EP/EP"+i+".png");
		}
		
		PreloadResearchTextures();
		biomeFXTexture = Minecraft.getMinecraft().renderEngine.getTexture("/YC/biomeFX.png");
		

		RegisterTexturesFX();
		
		
		//InitEntities();
	}
	
	public static void SetupConstants()
	{
		if (YC_Options.HighPolyModels)
		{
			YC_Constants.CrystalTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_crystal.png");
		}
		else
		{
			YC_Constants.CrystalTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/YC/LRes/YC_crystal.png");
		}
		YC_Constants.RemainsTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/YC/ore.png");
		YC_Constants.TerrainTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png");
		YC_Constants.TreeCrystalTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/YC/tree.png");
		YC_Constants.TechGrassTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_tg.png");
		YC_Constants.TechGrassSideTextureID = Minecraft.getMinecraft().renderEngine.getTexture("/textures/blocks/YC_techGrass.png");
	}
	
	public void RegisterTexturesFX()
	{
		YC_TextureFXBiome t;
		for(int i = 0; i<65; i++)
		{
			t = new YC_TextureFXBiome(i);
			net.minecraft.src.old.TextureFXManager.registerTexture(t);//TODO
			biomeTexturesFX[i] = t;
		}
		YC_TextureFXBiome.InitPaths();
		YC_TextureFXBiome.InitColors();
		YC_BiomeTextureFXManager.InitPaths();
	}
	
	public void PreloadResearchTextures()
	{
		MinecraftForgeClient.preloadTexture("/gui/YC_Tooltip.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/lavaGenerator.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/rainStarter.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/rainStopper.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/astralTeleporter.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/digger.png");
		MinecraftForgeClient.preloadTexture("/YC/Researches/crystalizer.png");
	}
	
	public static YC_TileEntityBlockRenderer blockRenderer = new YC_TileEntityBlockRenderer();
	
	public void RenderersSetup()
	{
		YC_Mod.c_symbolInputRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_lavaGeneratorRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_rainStopperRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_rainStarterRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_diggerRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_crystalRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_oreRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_workbenchRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_crystalizerRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_advAstTelRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_blockRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_treeCrystalRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_astBeaconRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_energyPocketRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_chestRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_techGrassRenderID = RenderingRegistry.getNextAvailableRenderId();
		YC_Mod.c_transparentBlockRenderID = RenderingRegistry.getNextAvailableRenderId();
		
		/*
		YC_Mod.c_symbolInputRenderID = 0;
		YC_Mod.c_lavaGeneratorRenderID = 0;
		YC_Mod.c_rainStopperRenderID = 0;
		YC_Mod.c_rainStarterRenderID = 0;
		YC_Mod.c_diggerRenderID = 0;
		//YC_Mod.c_crystalRenderID = 0;
		YC_Mod.c_oreRenderID = 0;
		YC_Mod.c_workbenchRenderID = 0;
		YC_Mod.c_crystalizerRenderID = 0;
		YC_Mod.c_advAstTelRenderID = 0;
		YC_Mod.c_blockRenderID = 0;
		YC_Mod.c_treeCrystalRenderID = 0;
		YC_Mod.c_astBeaconRenderID = 0;
		YC_Mod.c_energyPocketRenderID = 0;
		YC_Mod.c_chestRenderID = 0;//*/
		
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntitySymbolInputRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityLavaGeneratorRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityRainStopperRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityRainStarterRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityDiggerRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityCrystalRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityOreRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityWorkbenchRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityCrystalizerRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityAdvAstTelRenderer());
		RenderingRegistry.instance().registerBlockHandler(blockRenderer);
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityTreeCrystalRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityAstralBeaconRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityEnergyPocketRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityChestRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityTechGrassRenderer());
		RenderingRegistry.instance().registerBlockHandler(new YC_TileEntityTransparentBlockRenderer());
	}
	
	
	public void LoadModels()
	{
		try {
			YC_Mod.m_Sphere = YC_Model.Load("/YC_Res/models/sphere.obj");
			YC_Mod.m_digBsae = YC_Model.Load("/YC_Res/models/dig_base.obj");
			YC_Mod.m_digArm1 = YC_Model.Load("/YC_Res/models/dig_arm1.obj");
			YC_Mod.m_digArm2 = YC_Model.Load("/YC_Res/models/dig_arm2.obj");
			YC_Mod.m_Crystal = YC_Model.Load("/YC_Res/models/crystal.obj");
			YC_Mod.m_Block = YC_Model.Load("/YC_Res/models/block.obj");
			YC_Mod.m_Ore = YC_Model.Load("/YC_Res/models/ore.obj");
			YC_Mod.m_Workbench = YC_Model.Load("/YC_Res/models/Workbench.obj");
			YC_Mod.m_CrystalizerBase = YC_Model.Load("/YC_Res/models/crystalizer_box.obj");
			YC_Mod.m_CrystalizerCrystal = YC_Model.Load("/YC_Res/models/crystalizer_crystal.obj");
			YC_Mod.m_AdvAstTel1 = YC_Model.Load("/YC_Res/models/AdvAstTel1.obj");
			YC_Mod.m_AdvAstTel2 = YC_Model.Load("/YC_Res/models/AdvAstTel2.obj");
			YC_Mod.m_TreeCrystal = YC_Model.Load("/YC_Res/models/treeCrystal.obj");
			YC_Mod.m_AstralBeacon1 = YC_Model.Load("/YC_Res/models/astralBeacon.obj");
			YC_Mod.m_AstralBeacon2 = YC_Model.Load("/YC_Res/models/astralBeacon2.obj");
			YC_Mod.m_AstralBeaconMain = YC_Model.Load("/YC_Res/models/astralBeaconMain.obj");
			YC_Mod.m_AstralBeaconBeam = YC_Model.Load("/YC_Res/models/astralBeaconBeam.obj");
			YC_Mod.m_EnergyPocket = YC_Model.Load("/YC_Res/models/energyPocket.obj");
			YC_Mod.m_BeaconGuardianWeapon = YC_Model.Load("/YC_Res/models/beaconGuardianWeapon.obj");
			YC_Mod.m_BeaconGuardianHead = YC_Model.Load("/YC_Res/models/beaconGuardianHead.obj");
			YC_Mod.m_ChestBase = YC_Model.Load("/YC_Res/models/chestBase.obj");
			YC_Mod.m_ChestLid = YC_Model.Load("/YC_Res/models/chestLid.obj");
		} catch (IOException e) {
			FMLLog.log(Level.SEVERE, "[AstralCraft] Error loading hp meshes");
			e.printStackTrace();
		}
	}
	
	
	public void LoadModelsLowPoly()
	{
		try {
			YC_Mod.m_Sphere = YC_Model.Load("/YC_Res/models/sphere.obj");
			YC_Mod.m_digBsae = YC_Model.Load("/YC_Res/models/dig_base.obj");
			YC_Mod.m_digArm1 = YC_Model.Load("/YC_Res/models/dig_arm1.obj");
			YC_Mod.m_digArm2 = YC_Model.Load("/YC_Res/models/dig_arm2.obj");
			YC_Mod.m_Crystal = YC_Model.Load("/YC_Res/models/l_Crystal.obj");
			YC_Mod.m_Block = YC_Model.Load("/YC_Res/models/block.obj");
			YC_Mod.m_Ore = YC_Model.Load("/YC_Res/models/l_ore.obj");
			YC_Mod.m_Workbench = YC_Model.Load("/YC_Res/models/Workbench.obj");
			YC_Mod.m_CrystalizerBase = YC_Model.Load("/YC_Res/models/crystalizer_box.obj");
			YC_Mod.m_CrystalizerCrystal = YC_Model.Load("/YC_Res/models/crystalizer_crystal.obj");
			YC_Mod.m_AdvAstTel1 = YC_Model.Load("/YC_Res/models/l_AdvAstTel1.obj");
			YC_Mod.m_AdvAstTel2 = YC_Model.Load("/YC_Res/models/l_AdvAstTel2.obj");
			YC_Mod.m_AdvAstTelSelect = YC_Model.Load("/YC_Res/models/l_AdvAstTelSelect.obj");
			YC_Mod.m_TreeCrystal = YC_Model.Load("/YC_Res/models/treeCrystal.obj");
			YC_Mod.m_AstralBeacon1 = YC_Model.Load("/YC_Res/models/astralBeacon.obj");
			YC_Mod.m_AstralBeacon2 = YC_Model.Load("/YC_Res/models/astralBeacon2.obj");
			YC_Mod.m_AstralBeaconMain = YC_Model.Load("/YC_Res/models/astralBeaconMain.obj");
			YC_Mod.m_AstralBeaconBeam = YC_Model.Load("/YC_Res/models/astralBeaconBeam.obj");
			YC_Mod.m_EnergyPocket = YC_Model.Load("/YC_Res/models/energyPocket.obj");
			YC_Mod.m_BeaconGuardianWeapon = YC_Model.Load("/YC_Res/models/beaconGuardianWeapon.obj");
			YC_Mod.m_BeaconGuardianHead = YC_Model.Load("/YC_Res/models/beaconGuardianHead.obj");
			YC_Mod.m_ChestBase = YC_Model.Load("/YC_Res/models/chestBase.obj");
			YC_Mod.m_ChestLid = YC_Model.Load("/YC_Res/models/chestLid.obj");
		} catch (IOException e) {
			FMLLog.log(Level.SEVERE, "[AstralCraft] Error loading lp meshes");
			e.printStackTrace();
		}
	}
	
	public static void InitEntities()
	{
		RenderingRegistry.registerEntityRenderingHandler(YC_EntityGravityExplosionGrenade.class, new YC_EntityRenderGravityExplosionGrenade(1));
		RenderingRegistry.registerEntityRenderingHandler(YC_EntityGravityImplosionGrenade.class, new YC_EntityRenderGravityExplosionGrenade(2));
        //RenderManager.instance.entityRenderMap.put(YC_EntityGravityExplosionGrenade.class, new YC_EntityRenderGravityExplosionGrenade(1));
        //RenderManager.instance.entityRenderMap.put(YC_EntityMobBird.class, new YC_EntityMobBirdRender(new ModelCow(), 0.7F));
	}
	
	public static void SpawnSymbolInputParticle(World worldObj, int xCoord, int yCoord, int zCoord)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(
				(EntityFX)new YC_EntityFXEnergyFromAirGaining(worldObj, xCoord, yCoord, zCoord, 0, 0, 0));
	}
	
	public static void SpawnAstralBeaconActivationParticle(World worldObj, int xCoord, int yCoord, int zCoord)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(
				(EntityFX)new YC_EntityFXAstralBeaconActivation(worldObj, xCoord, yCoord, zCoord, 0, 0, 0));
	}

	public static void SpawnParticle(int type, int xCoord, int yCoord, int zCoord)
	{
		
		Minecraft m = Minecraft.getMinecraft();
		EntityFX e = null;
		if (type == 1)//rain start
		{
			e = (EntityFX)new YC_EntityFXRainToggle(m.theWorld, xCoord, yCoord, zCoord, 0, 0, 0,1);
		}
		if (type == 2)//rain stop
		{
			e = (EntityFX)new YC_EntityFXRainToggle(m.theWorld, xCoord, yCoord, zCoord, 0, 0, 0,2);
		}
		if (e != null)
		{
			m.effectRenderer.addEffect(e);
			return;
		}
		if (type == 3)//pyramid block
		{
			Random r = new Random();
			for(int i = 0; i < 500; i++)
				m.theWorld.spawnParticle("smoke", xCoord+0.4 + r.nextFloat()/5, yCoord+0.85 + r.nextFloat()/2, zCoord+0.4 + r.nextFloat()/5, 
						1*r.nextFloat()-0.5, 0.5*r.nextFloat(), 1*r.nextFloat()-0.5);
		}
	}
	
	public static Packet GetTileEntityWipePacket(int d, int x, int y, int z)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(d);
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_TEWipe";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public static void AddAdvAstTelParticle(int xCoord, int yCoord, int zCoord, World worldObj)
	{
		Minecraft.getMinecraft().effectRenderer.addEffect(new YC_EntityFXAdvAstralPortal(worldObj, 
				xCoord, yCoord+0.5f, zCoord, 0, 0, 0, xCoord, yCoord, zCoord));
	}
	
	
}
