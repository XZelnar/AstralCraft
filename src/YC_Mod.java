package net.minecraft.src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.IconUIResource;

import net.minecraft.block.Block;
import net.minecraft.block.EnumMobType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod( modid="AstralCraft", name="AstralCraft", version="1.1.00pr2")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
channels={"YC_BlockDetector","YC_SymbolInput","YC_Workbench", "YC_RainStarter",
		"YC_RainStopper", "YC_Digger", "YC_TimeOfDay", "YC_FX", "YC_SymbolInputSC",
		"YC_Crystalizer", "YC_Research", "YC_ResearchS", "YC_Researched",
		"YC_ResShardGen", "YC_DSD", "YC_AdvAstrTel", "YC_AstrBeacon",
		"YC_EnergyPocket", "YC_Chest", "YC_ChestPage"}, 
packetHandler=YC_PacketHandler.class)

public class YC_Mod {
	//TODO
	@SidedProxy(clientSide = "YC_ClientProxy", serverSide = "YC_CommonProxy")
	public static YC_CommonProxy proxy; //This object will be populated with the class that you choose for the environment
	@Instance
	public static YC_Mod instance; //The instance of the mod that will be defined, populated, and callable
	
	public static Material m_blockCircuit = (new YC_MaterialLogic(MapColor.airColor));
	
	
	
	public static YC_BlockPlayerPressurePlate b_playerPlate;// = new YC_BlockPlayerPressurePlate(180, 22, EnumMobType.players, Material.iron);
	public static YC_BlockWaterDetector b_waterDetector;// = new YC_BlockWaterDetector(181);
	public static YC_BlockRainDetector b_rainDetector;// = new YC_BlockRainDetector(182);
	public static YC_BlockDoorStateDetector b_doorDetector;// = new YC_BlockDoorStateDetector(183);
	public static YC_BlockRedstoneWirePowered b_rsWire;// = new YC_BlockRedstoneWirePowered(184,164);
	public static YC_BlockRSBlock b_rsBlock;// = new YC_BlockRSBlock(185,164);
	public static YC_BlockLavaDetector b_lavaDetector;// = new YC_BlockLavaDetector(186);
	public static YC_BlockTimeOfDayDetector b_todDetector;// = new YC_BlockTimeOfDayDetector(187);
	public static YC_BlockDetector b_blockDetector;// = new YC_BlockDetector(188);
	public static YC_BlockLightBlock b_lightBlock;// = new YC_BlockLightBlock(189,164);
	public static YC_BlockOre b_ore;// = new YC_BlockOre(190,233);
	public static YC_BlockSymbolInput b_symbolInput;// = new YC_BlockSymbolInput(191);
	public static YC_BlockWorkbench b_workbench;// = new YC_BlockWorkbench(192);
	public static YC_BlockAstralCrystal b_astralCrystals;// = new YC_BlockAstralCrystal(193,225);
	public static YC_BlockLavaGenerator b_lavaMaker;// = new YC_BlockLavaMaker(194,1);
	public static YC_BlockRainStopper b_rainStopper;// = new YC_BlockRainStopper(195,1);
	public static YC_BlockRainStarter b_rainStarter;// = new YC_BlockRainStarter(196,1);
	public static YC_BlockDigger b_digger;// = new YC_BlockDigger(197,1);
	public static YC_BlockFromAstralPortal b_fromAstralPortal;
	public static YC_BlockCrystalizer b_crystalizer;
	public static YC_BlockResearchTable b_researchTable;
	public static YC_BlockResearchShardGenerator b_researchShardGenerator;
	public static YC_BlockSpaceOccupier b_spaceOccupier;
	public static YC_BlockAdvAstralTeleporter b_advAstralTeleporter;
	public static YC_BlockAstralBeacon b_astralBeacon;
	public static YC_BlockEnergyPocket b_energyPocket;
	public static YC_BlockChest b_chest;
	public static YC_BlockPyramidStoneBrick b_pyramidBrick;
	public static YC_BlockFromAstralAdvancedPortal b_fromAstralAdvTeleporter;
	
	public static Block b_treeLog;
	public static YC_BlockTreeLogTE b_treeLogTE;
	public static YC_BlockTreeCrystal b_treeCrystal;
	
	
	public static YC_BlockBiomeTechGrass[] b_biomeTechGrass = new YC_BlockBiomeTechGrass[8];

	
	public static YC_RBlockWood b_rWood;
	public static YC_RBlockWater b_rWater;
	public static YC_RBlockLava b_rLava;
	public static Item i_rEnderKnowledgePiece;
	
	public static YC_ItemRSActivator i_rsActivator;// = new YC_ItemRSActivator(4400, EnumToolMaterial.EMERALD);
	public static YC_ItemFlashlight i_flashlight;// = new YC_ItemFlashlight(4401);
	public static YC_ItemAstralTeleporter i_astralTeleporter;// = new YC_ItemAstralTeleporter(4401);
	public static YC_ItemCrystalPowder i_crystalPowder;
	public static YC_ItemGravityExplosionGrenade i_gravityExplosionGrenade;
	public static YC_ItemGravityImplosionGrenade i_gravityImplosionGrenade;
	public static YC_ItemAstralResetPosition i_astralResetPosition;
	public static YC_ItemSubsonicEmitter i_subsonicEmitter;
	
	//Symbols
	public static YC_ItemSymbolCogwheel i_sCogwheel;// = new YC_ItemSymbolAir(4410);
	public static YC_ItemSymbolChip i_sChip;// = new YC_ItemSymbolWater(4411);
	public static YC_ItemSymbolMatrix i_sMatrix;// = new YC_ItemSymbolEarth(4412);
	public static YC_ItemSymbolEngine i_sEngine;// = new YC_ItemSymbolFire(4413);
	public static Item i_EssenceOfKnowledge;
	
	public static YC_WorldGen worldGen = new YC_WorldGen();
	//public static Achievement a_LavaGenerator;// = new Achievement(9640, "a_LavaGenerator", 4, 9, i_sCogwheel, null).registerAchievement();
	
	
	
	@Init
    public void Load(FMLInitializationEvent event)
    {
		FMLLog.log(Level.INFO, "[AstralCraft] Attempting main initialization");
		
		proxy.registerRenderInformation();
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);

		FMLLog.log(Level.INFO, "[AstralCraft] Registering blocks and items");
		RegisterBlocks();
		RegisterNames();
		RegisterTileEntities();

		FMLLog.log(Level.INFO, "[AstralCraft] Registering recipes");
		RegisterRecipes();
		RegisterWorkbenchRecipes();
		
		//GameRegistry.registerWorldGenerator(new YC_WorldGen());
		FMLLog.log(Level.INFO, "[AstralCraft] Registering Forge hooks");
		MinecraftForge.EVENT_BUS.register(new YC_Hooks());

		FMLLog.log(Level.INFO, "[AstralCraft] Adding to Ore Dictionnary");
		OreDictionary.registerOre("astralCrystals", b_astralCrystals);
		OreDictionary.registerOre("ansientSymbols", b_ore);
		
		YC_TileEntityBlockTreeLog.InitCrystals();
		
		FMLLog.log(Level.INFO, "[AstralCraft] Finished main initialization");
    }
	
	public static int c_BlockBiomeGrassFirstID = 1000;
	public void InitTechGrassBlock()
	{
		//*
		b_biomeTechGrass[0] = (YC_BlockBiomeTechGrass) new YC_BlockBiomeTechGrass(c_BlockBiomeGrassFirstID);//.setCreativeTab(CreativeTabs.tabMisc);
		GameRegistry.registerBlock(b_biomeTechGrass[0]);
		for(int j = 1; j<8; j++)
		{
			b_biomeTechGrass[j] = new YC_BlockBiomeTechGrass(c_BlockBiomeGrassFirstID + j);
			GameRegistry.registerBlock(b_biomeTechGrass[j]);
		}
		//*/
	}
	
	public void RegisterBlocks()
	{
		//GENERAL BLOCKS
		GameRegistry.registerBlock(b_playerPlate);
		GameRegistry.registerBlock(b_waterDetector);
		GameRegistry.registerBlock(b_lavaDetector);
		GameRegistry.registerBlock(b_rainDetector);
		GameRegistry.registerBlock(b_doorDetector);
		GameRegistry.registerBlock(b_todDetector);
		GameRegistry.registerBlock(b_blockDetector);
		GameRegistry.registerBlock(b_ore);
		GameRegistry.registerBlock(b_workbench);
		GameRegistry.registerBlock(b_astralCrystals);
		GameRegistry.registerBlock(b_lavaMaker);
		GameRegistry.registerBlock(b_rainStopper);
		GameRegistry.registerBlock(b_rainStarter);
		GameRegistry.registerBlock(b_digger);
		GameRegistry.registerBlock(b_crystalizer);
		GameRegistry.registerBlock(b_fromAstralPortal);
		GameRegistry.registerBlock(b_researchTable);
		GameRegistry.registerBlock(b_researchShardGenerator);
		GameRegistry.registerBlock(b_spaceOccupier);
		GameRegistry.registerBlock(b_advAstralTeleporter);
		GameRegistry.registerBlock(b_treeLog);
		GameRegistry.registerBlock(b_treeLogTE);
		GameRegistry.registerBlock(b_treeCrystal);
		GameRegistry.registerBlock(b_astralBeacon);
		GameRegistry.registerBlock(b_energyPocket);
		GameRegistry.registerBlock(b_chest);
		GameRegistry.registerBlock(b_pyramidBrick);
		GameRegistry.registerBlock(b_fromAstralAdvTeleporter);
		
		
		//RESEARCH
		GameRegistry.registerBlock(b_rWood);
		GameRegistry.registerBlock(b_rWater);
		GameRegistry.registerBlock(b_rLava);
		
		
		
		//MISC
		GameRegistry.registerBlock(b_rsWire);
		GameRegistry.registerBlock(b_rsBlock);
		GameRegistry.registerBlock(b_lightBlock);
		GameRegistry.registerBlock(b_symbolInput);
	}
	
	public void RegisterNames()
	{
		LanguageRegistry.addName(b_playerPlate, "Pressure Plate");
		LanguageRegistry.addName(b_waterDetector, "Water Detector");
		LanguageRegistry.addName(b_lavaDetector, "Lava Detector");
		LanguageRegistry.addName(b_rainDetector, "Rain Detector");
		LanguageRegistry.addName(b_doorDetector, "Door State Detector");
		LanguageRegistry.addName(b_todDetector, "Time Detector");
		LanguageRegistry.addName(b_blockDetector, "Block Detector");
		LanguageRegistry.addName(b_ore, "Anscient Remains");
		LanguageRegistry.addName(b_workbench, "Workbench");
		LanguageRegistry.addName(b_astralCrystals, "Astral Crystal");
		LanguageRegistry.addName(b_lavaMaker, "Lava Generator");
		LanguageRegistry.addName(b_rainStopper, "Rain Stopper");
		LanguageRegistry.addName(b_rainStarter, "Rain Starter");
		LanguageRegistry.addName(b_digger, "Digger");
		LanguageRegistry.addName(b_crystalizer, "Crystalizer");
		LanguageRegistry.addName(b_researchTable, "Research Table");
		LanguageRegistry.addName(b_researchShardGenerator, "Research Shard Generator");
		LanguageRegistry.addName(b_advAstralTeleporter, "Advanced Astral Teleporter");
		LanguageRegistry.addName(b_treeLog, "Log");
		LanguageRegistry.addName(b_treeLogTE, "Log");
		LanguageRegistry.addName(b_treeCrystal, "Crystal");
		LanguageRegistry.addName(b_astralBeacon, "Astral Beacon");
		LanguageRegistry.addName(b_energyPocket, "Energy Pocket");
		LanguageRegistry.addName(b_chest, "Chest");
		
		
		//RESEARCH
		LanguageRegistry.addName(b_rWood, "\u00a7aRelic\u00a7f");
		LanguageRegistry.addName(b_rWater, "\u00a7bRelic\u00a7f");
		LanguageRegistry.addName(b_rLava, "\u00a7cRelic\u00a7f");
		
		
		//ITEMS
		LanguageRegistry.addName(i_rsActivator, "Redstone Activator");
		LanguageRegistry.addName(i_flashlight, "Flashlight");
		LanguageRegistry.addName(i_astralTeleporter, "Astral Teleporter");
		LanguageRegistry.addName(i_crystalPowder, "Crystal Powder");
		LanguageRegistry.addName(i_gravityExplosionGrenade, "Gravity Explosion Grenade");
		LanguageRegistry.addName(i_gravityImplosionGrenade, "Gravity Implosion Grenade");
		LanguageRegistry.addName(i_astralResetPosition, "Astral Retransmitter");
		LanguageRegistry.addName(i_subsonicEmitter, "Subsonic Emitter");
		
		//SYMBOLS
		LanguageRegistry.addName(i_sCogwheel, "\u00a79Cogwheel\u00a7f");
		LanguageRegistry.addName(i_sChip, "\u00a79Microchip\u00a7f");
		LanguageRegistry.addName(i_sMatrix, "\u00a79Data Matrix\u00a7f");
		LanguageRegistry.addName(i_sEngine, "\u00a79Engine\u00a7f");
		LanguageRegistry.addName(i_EssenceOfKnowledge, "\u00a79Essence of Knowledge\u00a7f");
		LanguageRegistry.addName(i_rEnderKnowledgePiece, "\u00a73Relic\u00a7f");
	}

	public void RegisterTileEntities()
	{
		GameRegistry.registerTileEntity(YC_TileEntityBlockDetector.class, "BlockDetector");
		GameRegistry.registerTileEntity(YC_TileEntityBlockSymbolInput.class, "SymbolInput");
		GameRegistry.registerTileEntity(YC_TileEntityBlockWorkbench.class, "Workbench");
		GameRegistry.registerTileEntity(YC_TileEntityBlockRainStopper.class, "RainStopper");
		GameRegistry.registerTileEntity(YC_TileEntityBlockRainStarter.class, "RainStarter");
		GameRegistry.registerTileEntity(YC_TileEntityBlockDigger.class, "Digger");
		GameRegistry.registerTileEntity(YC_TileEntityBlockTimeOfDay.class, "TimeOfDay");
		GameRegistry.registerTileEntity(YC_TileEntityBlockCrystalizer.class, "Crystalizer");
		GameRegistry.registerTileEntity(YC_TileEntityBlockResearchTable.class, "ResearchTable");
		GameRegistry.registerTileEntity(YC_TileEntityBlockResearchShardGenerator.class, "ResearchShardGenerator");
		GameRegistry.registerTileEntity(YC_TileEntityBlockDoorStateDetector.class, "DoorStateDetector");
		GameRegistry.registerTileEntity(YC_TileEntityBlockAdvAstralTeleporter.class, "AdvAstralTeleporter");
		GameRegistry.registerTileEntity(YC_TileEntityBlockTreeLog.class, "TreeLogTE");
		GameRegistry.registerTileEntity(YC_TileEntityBlockAstralBeacon.class, "AstralBeacon");
		GameRegistry.registerTileEntity(YC_TileEntityBlockEnergyPocket.class, "EnergyPocket");
		GameRegistry.registerTileEntity(YC_TileEntityBlockChest.class, "YCChest");
		GameRegistry.registerTileEntity(YC_TileEntityBlockRenderUpdater.class, "YCBRenderUpdater");
		GameRegistry.registerTileEntity(YC_TileEntityBlockUpdater.class, "YCBUpdater");
	}
	
    @PreInit
    public void Preload(FMLPreInitializationEvent evt)
    {
		FMLLog.log(Level.INFO, "[AstralCraft] Attempting preinitialization");
    	InitBlocksItems(evt);
		FMLLog.log(Level.INFO, "[AstralCraft] Finished preinitialization");

		///////////////////////////////////////////////////RegisterAchievements();
    }
	
	/*public void RegisterAchievements()
	{
		a_LavaGenerator = new Achievement(9640, "a_LavaGenerator", 0, 1, b_lavaMaker, null).registerAchievement();
		ModLoader.addAchievementDesc(a_LavaGenerator, "Lava Generator", "Need more fuel!");
		
		AchievementPage p = new AchievementPage("AstralCraft", a_LavaGenerator);
		AchievementPage.registerAchievementPage(p);
	}*/

    @PostInit
    public void Postload(FMLPostInitializationEvent evt)
    {
		FMLLog.log(Level.INFO, "[AstralCraft] Attempting postinitialization");
    	if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
    	{
    		YC_ClientProxy.SetupConstants();
    		FinalRenderersInit();
    		YC_ClientProxy.InitEntities();
    	}
		FMLLog.log(Level.INFO, "[AstralCraft] Finished postinitialization");
    }
    
    public void FinalRenderersInit()
    {
    	Minecraft mc = Minecraft.getMinecraft();
    	YC_TileEntityCrystalRenderer.mc = mc;
    	YC_TileEntityOreRenderer.mc = mc;
    	YC_DelayedCycledRerenderUpdate.mc = mc;
    }
    
    
    public void InitBlocksItems(FMLPreInitializationEvent event)
    {
    	File f = event.getSuggestedConfigurationFile();
		FMLLog.log(Level.INFO, "[AstralCraft] Attempting to load configs from "+f.getAbsolutePath());
		Configuration cfg = new Configuration(f);
		boolean NewConfigs = false;
		if (f.length()!=0)
		{
			FMLLog.log(Level.INFO, "[AstralCraft] Config file found. Loading from it");
		}
		else
		{
			NewConfigs = true;
			FMLLog.log(Level.INFO, "[AstralCraft] Config file not found. Creating a new one");
		}
		try
		{
			cfg.load();

			FMLLog.log(Level.INFO, "[AstralCraft] Loading blocks");
			//RESEARCH!!!
			int id = GetNextFreeBlockID();
			b_rWood = (YC_RBlockWood) new YC_RBlockWood(cfg.getBlock("ResearchWood", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rWater = (YC_RBlockWater) new YC_RBlockWater(cfg.getBlock("ResearchWater", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rLava = (YC_RBlockLava) new YC_RBlockLava(cfg.getBlock("ResearchLava", id).getInt(id));
			id = GetNextFreeItemID();
			i_rEnderKnowledgePiece = new Item(cfg.get("item", "ResearchEnder", id).getInt(id)-256).
					setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("YC_Relic");
			
			
			
			//BLOCKS
			id = GetNextFreeBlockID();
			b_playerPlate = new YC_BlockPlayerPressurePlate(cfg.getBlock("PlayerPressurePlate", id).getInt(id), EnumMobType.players, Material.iron);
			id = GetNextFreeBlockID();
			b_waterDetector = new YC_BlockWaterDetector(cfg.getBlock("WaterDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rainDetector = new YC_BlockRainDetector(cfg.getBlock("RainDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_doorDetector = new YC_BlockDoorStateDetector(cfg.getBlock("DoorDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rsWire = new YC_BlockRedstoneWirePowered(cfg.getBlock("RedstoneWire", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rsBlock = new YC_BlockRSBlock(cfg.getBlock("RedstoneBlock", id).getInt(id));
			id = GetNextFreeBlockID();
			b_lavaDetector = new YC_BlockLavaDetector(cfg.getBlock("LavaDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_todDetector = new YC_BlockTimeOfDayDetector(cfg.getBlock("TimeOfDayDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_blockDetector = new YC_BlockDetector(cfg.getBlock("BlockDetector", id).getInt(id));
			id = GetNextFreeBlockID();
			b_lightBlock = new YC_BlockLightBlock(cfg.getBlock("LightBlock", id).getInt(id));
			id = GetNextFreeBlockID();
			b_ore = new YC_BlockOre(cfg.getBlock("OreBlock", id).getInt(id));
			id = GetNextFreeBlockID();
			b_symbolInput = new YC_BlockSymbolInput(cfg.getBlock("PyramidInput", id).getInt(id));
			id = GetNextFreeBlockID();
			b_workbench = new YC_BlockWorkbench(cfg.getBlock("Workbench", id).getInt(id));
			id = GetNextFreeBlockID();
			b_astralCrystals = new YC_BlockAstralCrystal(cfg.getBlock("AstralCrystals", id).getInt(id));
			id = GetNextFreeBlockID();
			b_lavaMaker = new YC_BlockLavaGenerator(cfg.getBlock("LavaMaker", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rainStopper = new YC_BlockRainStopper(cfg.getBlock("RainStopper", id).getInt(id));
			id = GetNextFreeBlockID();
			b_rainStarter = new YC_BlockRainStarter(cfg.getBlock("RainStarter", id).getInt(id));
			id = GetNextFreeBlockID();
			b_digger = new YC_BlockDigger(cfg.getBlock("Digger", id).getInt(id));
			id = GetNextFreeBlockID();
			b_crystalizer = new YC_BlockCrystalizer(cfg.getBlock("Crystalizer", id).getInt(id));
			id = GetNextFreeBlockID();
			b_fromAstralPortal = new YC_BlockFromAstralPortal(cfg.getBlock("FromAstralPortal", id).getInt(id));
			id = GetNextFreeBlockID();
			b_researchTable = new YC_BlockResearchTable(cfg.getBlock("ResearchTable", id).getInt(id));
			id = GetNextFreeBlockID();
			b_researchShardGenerator = new YC_BlockResearchShardGenerator(cfg.getBlock("ResearchShardGenerator", id).getInt(id));
			id = GetNextFreeBlockID();
			b_spaceOccupier = new YC_BlockSpaceOccupier(cfg.getBlock("SpaceOccupier", id).getInt(id));
			id = GetNextFreeBlockID();
			b_advAstralTeleporter = new YC_BlockAdvAstralTeleporter(cfg.getBlock("AdvancedAstralTeleporter", id).getInt(id));
			id = GetNextFreeBlockID();
			b_treeLog = new Block(cfg.getBlock("TreeLog", id).getInt(id), Material.wood).setUnlocalizedName("YC_Log").setHardness(2).setResistance(2).setHardness(2).setCreativeTab(CreativeTabs.tabMisc).
					setStepSound(Block.soundWoodFootstep);
			id = GetNextFreeBlockID();
			b_treeLogTE = new YC_BlockTreeLogTE(cfg.getBlock("TreeCrystalTE", id).getInt(id));
			id = GetNextFreeBlockID();
			b_treeCrystal = new YC_BlockTreeCrystal(cfg.getBlock("TreeCrystal", id).getInt(id));
			id = GetNextFreeBlockID();
			b_astralBeacon = new YC_BlockAstralBeacon(cfg.getBlock("AstralBeacon", id).getInt(id));
			id = GetNextFreeBlockID();
			b_energyPocket = new YC_BlockEnergyPocket(cfg.getBlock("EnergyPocket", id).getInt(id));
			id = GetNext8FreeBlockIDs();
			Property prop = cfg.getBlock("TechGrass", id);
			prop.comment = "Requires this ID and 7 IDs after it";
			c_BlockBiomeGrassFirstID = prop.getInt(id);//c_BlockBiomeGrassFirstID
			InitTechGrassBlock();
			id = GetNextFreeBlockID();
			b_chest = new YC_BlockChest(cfg.getBlock("Chest", id).getInt(id));
			id = GetNextFreeBlockID();
			b_pyramidBrick = new YC_BlockPyramidStoneBrick(cfg.getBlock("PyramidBrick", id).getInt(id));
			id = GetNextFreeBlockID();
			b_fromAstralAdvTeleporter = new YC_BlockFromAstralAdvancedPortal(cfg.getBlock("FromAstralAdvPortal", id).getInt(id));

			

			//SYMBOLS
			id = GetNextFreeItemID();
			i_sCogwheel = new YC_ItemSymbolCogwheel(cfg.get("item", "sCogwheel", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_sChip = new YC_ItemSymbolChip(cfg.get("item", "sChip", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_sMatrix = new YC_ItemSymbolMatrix(cfg.get("item", "sMatrix", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_sEngine = new YC_ItemSymbolEngine(cfg.get("item", "sEngine", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_EssenceOfKnowledge = new Item(cfg.get("item", "EssenceOfKnowledge", id).getInt(id)-256).
					setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("YC_EssenceOfKnowledge");
			
			
			

			FMLLog.log(Level.INFO, "[AstralCraft] Loading items");
			//ITEMS
			id = GetNextFreeItemID();
			i_rsActivator = new YC_ItemRSActivator(cfg.get("item", "RSToggler", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_flashlight = new YC_ItemFlashlight(cfg.get("item", "Flashlight", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_astralTeleporter = new YC_ItemAstralTeleporter(cfg.get("item", "AstralTeleporter", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_crystalPowder = new YC_ItemCrystalPowder(cfg.get("item", "CrystalPowder", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_gravityExplosionGrenade = new YC_ItemGravityExplosionGrenade(cfg.get("item", "GravityExplosionGrenade", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_gravityImplosionGrenade = new YC_ItemGravityImplosionGrenade(cfg.get("item", "GravityImplosionGrenade", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_astralResetPosition = new YC_ItemAstralResetPosition(cfg.get("item", "AstralRetransmitter", id).getInt(id)-256);
			id = GetNextFreeItemID();
			i_subsonicEmitter = new YC_ItemSubsonicEmitter(cfg.get("item", "SubsonicEmitter", id).getInt(id)-256);
			
			

			FMLLog.log(Level.INFO, "[AstralCraft] Registering entities");
			//ENTITIES
			//mob class; name; id; mod class; tracking range; update frequency; send velocity updates
			
			//gravity grenades
			EntityRegistry.registerModEntity(YC_EntityGravityExplosionGrenade.class, "YC_GravityExplosionGrenade", cfg.get("entity", "GravityExplosionGrenade", 1).getInt(1), this, 64, 2, true);
			EntityRegistry.registerModEntity(YC_EntityGravityImplosionGrenade.class, "YC_GravityImplosionGrenade", cfg.get("entity", "GravityImplosionGrenade", 2).getInt(2), this, 64, 2, true);
			
			EntityRegistry.registerGlobalEntityID(YC_EntityBeaconGuardian.class, "BeaconGuardian", cfg.get("entity", "BeaconGuardian", 3).getInt(3), 0xffffff, 0x000000);
			LanguageRegistry.instance().addStringLocalization("entity.BeaconGuardian.name", "Beacon Guardian");
			
			
			
			FMLLog.log(Level.INFO, "[AstralCraft] Loading settings");
			//SETTINGS
			YC_Options.HighPolyModels = cfg.get("general", "HighPolyModels", false).getBoolean(false);
			YC_Options.RenderContourSecondPass = cfg.get("general", "RenderSecondPass", true).getBoolean(true);
			YC_Options.GenerateCrystals = cfg.get("general", "GenerateCrystals", true).getBoolean(true);
			YC_Options.GenerateSymbols = cfg.get("general", "GenerateRemains", true).getBoolean(true);
			YC_Options.GenerateEnergyPockets = cfg.get("general", "GenerateEnergyPockets", true).getBoolean(true);
			YC_Options.GenerateStructures = cfg.get("general", "GenerateStructures", true).getBoolean(true);
			YC_Options.ForceNativeRender = cfg.get("general", "ForceNativeRender", true).getBoolean(true);
			YC_Options.AllowTexturesFX = cfg.get("general", "AllowTexturesFX", true).getBoolean(true);
			YC_Options.AllowVBO = cfg.get("general", "AllowVBO", true).getBoolean(true);
			YC_Options.RenderDistance = cfg.get("general", "RenderDistance", 48).getInt(48);
			YC_EntityGravityExplosionGrenade.SPEED_MODIFICATOR = cfg.get("general", "GrenadeSpeedModificator", 8).getInt(8);
			YC_Options.UseVBO = YC_Options.AllowVBO;
			id = GetNextBiomeGenID();
			bg_astral = new YC_BiomeGenAstral(cfg.get("general", "AstralBiomeGenID", id).getInt(id));
			//YC_Options.ResearchSpeed = cfg.get("general", "ResearchSpeed", 1).getInt(1);
			YC_Options.ResearchSpeed = 1;
			YC_Options.ResearchShardTimeValue = cfg.get("general", "ResearchShardTimeValue", 240).getInt(240);
			

			FMLLog.log(Level.INFO, "[AstralCraft] Options loaded: " + YC_Options.GetStringForLog());


			FMLLog.log(Level.INFO, "[AstralCraft] Registering dimension");
			if (!cfg.hasKey("general", "AstralDimensionID"))
			{
				d_astralDimProviderType = 2;
				d_astralDimID = -3;
				while(true)
				{
					if (DimensionManager.registerProviderType(d_astralDimProviderType, YC_WorldProviderAstral.class, false))
					{
						WorldServer w;
						do
						{
							d_astralDimID--;
							w = DimensionManager.getWorld(d_astralDimID);
						}
						while (w != null);
						DimensionManager.registerDimension(d_astralDimID, d_astralDimProviderType);
						break;
					}
					d_astralDimProviderType++;
				}
				cfg.get("general", "AstralDimensionID", d_astralDimID).getInt(d_astralDimID);
				cfg.get("general", "AstralDimensionProviderType", d_astralDimProviderType).getInt(d_astralDimProviderType);
			}
			else
			{
				d_astralDimID = cfg.get("general", "AstralDimensionID", -4).getInt(-4);
				d_astralDimProviderType = cfg.get("general", "AstralDimensionProviderType", 2).getInt(2);
				DimensionManager.registerProviderType(d_astralDimProviderType, YC_WorldProviderAstral.class, false);
				DimensionManager.registerDimension(d_astralDimID, d_astralDimProviderType);
			}
			
			

			FMLLog.log(Level.INFO, "[AstralCraft] Loading digger blacklist");
			//Digger blacklist
			prop = cfg.get("general", "DiggerBlacklist", new int[]{});
			prop.comment = "IDs of blocks Digger shouldn't dig. One block ID per line. \r\nEg. \r\n\"<\r\n41\r\n42\r\n57\r\n>\" \r\nwill prevent it from diggin iron, gold and diamond blocks";
			YC_TileEntityBlockDigger.idBlacklist  = prop.getIntList();
			
			prop = cfg.get("general", "GuardianBlocksBlacklist", new int[]{});
			prop.comment = "IDs of blocks Guardian shouldn't move. One block ID per line. \r\nEg. \r\n\"<\r\n41\r\n42\r\n57\r\n>\" \r\nwill prevent it from moving iron, gold and diamond blocks. Blocks with TileEntities are non-movable by default.";
			YC_EntityBeaconGuardian.blacklist = prop.getIntList();
			
			
		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, "[AstralCraft] Exception during loading from configs");
			e.printStackTrace();
			//No Config
		}
		finally
		{
			cfg.save();
			if (NewConfigs)
				FMLLog.log(Level.INFO, "[AstralCraft] New configs created and saved successfully");
		}
		
		//optifine
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && FMLClientHandler.instance().hasOptifine())
		{
			FMLLog.log(Level.INFO, "[AstralCraft] Optifine detected. Forcing compatibility");
			YC_Options.EnableOptifineCompability = true;
			//System.out.println(YC_Options.EnableOptifineCompability);
		}
		else
		{
			FMLLog.log(Level.INFO, "[AstralCraft] No Optifine detected");
		}
    }
    
    public void RegisterRecipes()
    {
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 4), b_treeLog);
		GameRegistry.addShapelessRecipe(new ItemStack(Block.planks, 4), b_rWood);
		GameRegistry.addShapelessRecipe(new ItemStack(i_crystalPowder, 32), b_astralCrystals);
    	
		ShapedOreRecipe r = new ShapedOreRecipe(
				new ItemStack(b_blockDetector), 
				new String[] {"ici",
					          "ipi",
					          "iri"},
				'i', Item.ingotIron,
				'c', Block.chest,
				'p', Block.pistonStickyBase,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_doorDetector), 
				new String[] {"cbc",
					          "cpc",
					          "crc"},
				'c', Block.cobblestone,
				'b', Item.doorSteel,
				'p', Block.pistonBase,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_lavaDetector), 
				new String[] {"ibi",
					          "ipi",
					          "iri"},
				'i', Item.ingotIron,
				'b', Item.bucketLava,
				'p', Block.pressurePlateStone,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_playerPlate), 
				new String[] {"isi"},
				'i', Item.ingotIron,
				's', Block.stone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_rainDetector), 
				new String[] {"cbc",
					          "cpc",
					          "crc"},
				'c', Block.cobblestone,
				'b', Item.bucketEmpty,
				'p', Block.pressurePlateStone,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_todDetector), 
				new String[] {"iri",
					          "rcr",
					          "iri"},
				'i', Item.ingotIron,
				'c', Item.pocketSundial,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_waterDetector), 
				new String[] {"ibi",
					          "ipi",
					          "iri"},
				'i', Item.ingotIron,
				'b', Item.bucketWater,
				'p', Block.pressurePlateStone,
				'r', Item.redstone);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_workbench), 
				new String[] {"sis",
					          "fdf",
					          "scs"},
				'i', Block.blockSteel,
				's', Block.stone,
				'f', b_astralCrystals,
				'd', Item.diamond,
				'c', Block.workbench);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_astralCrystals), 
				new String[] {" c ",
					          "ccc",
					          "sss"},
				's', Block.stone,
				'c', b_treeCrystal);
		GameRegistry.addRecipe(r);
		
		
		//======================================================ITEMS====================================================


		GameRegistry.addShapelessRecipe(new ItemStack(i_crystalPowder, 32), b_astralCrystals);
		
		r = new ShapedOreRecipe(
				new ItemStack(i_rsActivator), 
				new String[] {"t",
					          "e",
					          "b"},
				't', Block.torchRedstoneActive,
				'e', Item.emerald,
				'b', Block.stoneButton);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(i_flashlight), 
				new String[] {"g",
					          "b",
					          "f"},
				'g', Block.glass,
				'f', b_astralCrystals,
				'b', Block.stoneButton);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(i_rEnderKnowledgePiece), 
				new String[] {" p ",
					          "pdp",
					          " p "},
				'p', Item.enderPearl,
				'd', Item.diamond);
		GameRegistry.addRecipe(r);
		
		r = new ShapedOreRecipe(
				new ItemStack(b_treeCrystal), 
				new String[] {"ddd",
					          "ddd",
					          "ddd"},
				'd', i_crystalPowder);
		GameRegistry.addRecipe(r);
    }

    public void RegisterWorkbenchRecipes()
    {
    	//m = matrix
    	//w = cogwheel
    	//c = crystal
    	//e = engine
    	//h = chip
    	//n = enderRelic
    	//d = diamond
    	
    	//digger 100 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
    			 "mem",
    			"mwhwm",
    			"mecem",
    			"mwhwm",
    			 "eee"
    	}, new ItemStack(b_digger, 1),
    	100, 3));
    	

    	//lava maker 50 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
    			 "eee",
    			"eccce",
    			"hccch",
    			"eccce",
    			 "mmm"
    	}, new ItemStack(b_lavaMaker, 1),
    	50, 0));
    	

    	//rain starter 70 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
    			 "mwm",
    			"mhchm",
    			"wcecw",
    			"mhchm",
    			 "mwm"
    	}, new ItemStack(b_rainStarter, 1),
    	70, 2));
    	

    	//rain stopper 70 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "mwm",
   			 	"mhehm",
   			 	"wecew",
   			 	"mhehm",
   			 	 "mwm"
    	}, new ItemStack(b_rainStopper, 1),
    	70, 1));
    	

    	//Astral teleporter 100 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "heh",
   			 	"hcmch",
   			 	"emnme",
   			 	"hcmch",
   			 	 "heh"
    	}, new ItemStack(i_astralTeleporter, 1),
    	100, 5));
    	

    	//Crystalizer 80 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "mwm",
   			 	"hccch",
   			 	"wcecw",
   			 	"hccch",
   			 	 "mwm"
    	}, new ItemStack(b_crystalizer, 1),
    	80, 4));
    	

    	//Research shard generator 50 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "mhm",
   			 	"hwewh",
   			 	"mecem",
   			 	"hwewh",
   			 	 "mhm"
    	}, new ItemStack(b_researchShardGenerator, 1),
    	50, -1));
    	

    	//Research table 40 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "mmm",
   			 	"medem",
   			 	"hdcdh",
   			 	"medem",
   			 	 "mmm"
    	}, new ItemStack(b_researchTable, 1),
    	40, -1));
    	

    	//AdvAstralTeleporter 60 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "hwh",
   			 	"medem",
   			 	"wdndw",
   			 	"medem",
   			 	 "hwh"
    	}, new ItemStack(b_advAstralTeleporter, 1),
    	60, 6));
    	

    	//Astral Retransmitter 80 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "wcw",
   			 	"emhme",
   			 	"chdhc",
   			 	"emhme",
   			 	 "wcw"
    	}, new ItemStack(i_astralResetPosition, 1),
    	80, 7));
    	

    	//Chest 100 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "www",
   			 	"emmme",
   			 	"dmnmd",
   			 	"nmmmn",
   			 	 "ccc"
    	}, new ItemStack(b_chest, 4),
    	100, 8));
    	

    	//Gravity Explosion Grenade 30 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "eee",
   			 	"ewcwe",
   			 	"echce",
   			 	"ewcwe",
   			 	 "eee"
    	}, new ItemStack(i_gravityExplosionGrenade, 4),
    	30, 9));
    	

    	//Gravity Implosion Grenade 30 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "eee",
   			 	"ewcwe",
   			 	"ecmce",
   			 	"ewcwe",
   			 	 "eee"
    	}, new ItemStack(i_gravityImplosionGrenade, 4),
    	30, 10));
    	

    	//Subsonic Emitter 40 fuel
    	YC_WorkbenchRecipes.AddRecipe(new YC_WorkbenchRecipe(new String[]{
   			 	 "ehe",
   			 	"ewmwe",
   			 	"hmhmh",
   			 	"ewmwe",
   			 	 "ehe"
    	}, new ItemStack(i_subsonicEmitter, 1),
    	40, 11));
    }
    
    
    
    
    public static YC_Model m_Sphere;
    public static YC_Model m_digBsae, m_digArm1, m_digArm2;
    public static YC_Model m_Crystal;
    public static YC_Model m_Block;
    public static YC_Model m_Ore;
    public static YC_Model m_Workbench;
    public static YC_Model m_CrystalizerBase;
    public static YC_Model m_CrystalizerCrystal;
    public static YC_Model m_AdvAstTel1, m_AdvAstTel2, m_AdvAstTelSelect;
    public static YC_Model m_TreeCrystal;
    public static YC_Model m_AstralBeacon1, m_AstralBeacon2, m_AstralBeaconMain, m_AstralBeaconBeam;
    public static YC_Model m_EnergyPocket;
    public static YC_Model m_BeaconGuardianHead, m_BeaconGuardianWeapon;
    public static YC_Model m_ChestBase, m_ChestLid;
    
    
    
    //GUIS
    public static int c_blockDetectorGuiID = 44;
    public static int c_blockWorkbenchGuiID = 45;
    public static int c_blockSymbolInputGuiID = 46;
    public static int c_blockCrystalizerGuiID = 47;
    public static int c_blockResearchTableGuiID = 48;
    public static int c_blockResearchShardGeneratorGuiID = 49;
    public static int c_blockChestGuiID = 50;
    //RENDERERS
    public static int c_symbolInputRenderID = 1;
    public static int c_lavaGeneratorRenderID = 2;
    public static int c_rainStopperRenderID = 3;
    public static int c_rainStarterRenderID = 4;
    public static int c_diggerRenderID = 5;
    public static int c_crystalRenderID = 6;
    public static int c_oreRenderID = 7;
    public static int c_workbenchRenderID = 8;
    public static int c_crystalizerRenderID = 9;
    public static int c_advAstTelRenderID = 10;
    public static int c_blockRenderID = 11;
    public static int c_treeCrystalRenderID = 12;
    public static int c_astBeaconRenderID = 13;
    public static int c_energyPocketRenderID = 14;
    public static int c_chestRenderID = 15;
    public static int c_techGrassRenderID = 16;
    public static int c_transparentBlockRenderID = 17;
    
    
    

    
    public int GetNextFreeBlockIDWGen()
    {
    	for(int i = 127; i<4096; i++)
    	{
    		if (Block.blocksList[i] == null && Item.itemsList[i] == null)
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    public int GetNextFreeBlockID()
    {
    	for(int i = 257; i<4096; i++)
    	{
    		if (Block.blocksList[i] == null && Item.itemsList[i] == null)
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    public int GetNext8FreeBlockIDs()
    {
    	for(int i = 257; i<4096; i++)
    	{
    		if (IsIDNull(i) &&
    			IsIDNull(i+1) &&
    			IsIDNull(i+2) &&
    			IsIDNull(i+3) &&
    			IsIDNull(i+4) &&
    			IsIDNull(i+5) &&
    			IsIDNull(i+6) &&
    			IsIDNull(i+7))
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    public boolean IsIDNull(int i)
    {
    	return Block.blocksList[i] == null && Item.itemsList[i] == null;
    }
    
    public int GetNextFreeItemID()
    {
    	for(int i = 4096; i<Item.itemsList.length; i++)
    	{
    		if (Item.itemsList[i] == null)
    		{
    			return i;
    		}
    	}
    	return -1;
    }
    
    
    
    
    public static YC_BiomeGenAstral bg_astral;
    public static int d_astralDimID = 0; 
    public static int d_astralDimProviderType = 0; 
    
    public static int GetNextBiomeGenID()
    {
    	for(int i = 0; i<BiomeGenBase.biomeList.length; i++)
    	{
    		if (BiomeGenBase.biomeList[i] == null) return i;
    	}
    	return -1;
    }
    
    public static float[] CircleValuesSin = new float[]{(float) Math.sin(0), 
    		(float) Math.sin(22.5*Math.PI/180f),
    		(float) Math.sin(45*Math.PI/180f),
    		(float) Math.sin(67.5*Math.PI/180f),
    		(float) Math.sin(90*Math.PI/180f),
    		(float) Math.sin(112.5*Math.PI/180f),
    		(float) Math.sin(135*Math.PI/180f),
    		(float) Math.sin(157.5*Math.PI/180f),
    		(float) Math.sin(180*Math.PI/180f),
    		(float) Math.sin(202.5*Math.PI/180f),
    		(float) Math.sin(225*Math.PI/180f),
    		(float) Math.sin(247.5*Math.PI/180f),
    		(float) Math.sin(270*Math.PI/180f),
    		(float) Math.sin(292.5*Math.PI/180f),
    		(float) Math.sin(315*Math.PI/180f),
    		(float) Math.sin(337.5*Math.PI/180f)};
    public static float[] CircleValuesCos = new float[]{(float) Math.cos(0), 
			(float) Math.cos(22.5*Math.PI/180f),
			(float) Math.cos(45*Math.PI/180f),
			(float) Math.cos(67.5*Math.PI/180f),
			(float) Math.cos(90*Math.PI/180f),
			(float) Math.cos(112.5*Math.PI/180f),
			(float) Math.cos(135*Math.PI/180f),
			(float) Math.cos(157.5*Math.PI/180f),
			(float) Math.cos(180*Math.PI/180f),
			(float) Math.cos(202.5*Math.PI/180f),
			(float) Math.cos(225*Math.PI/180f),
			(float) Math.cos(247.5*Math.PI/180f),
			(float) Math.cos(270*Math.PI/180f),
			(float) Math.cos(292.5*Math.PI/180f),
			(float) Math.cos(315*Math.PI/180f),
			(float) Math.cos(337.5*Math.PI/180f)};
    
    public static float sqrt2 = (float) Math.sqrt(2);
    
    private static int CurListIndex = 75423;
    public static int GetNextListIndex()
    {
    	CurListIndex++;
    	return CurListIndex;
    }
    
    
    
}
