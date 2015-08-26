package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class YC_CommonProxy implements IGuiHandler {

	
	
	/**
	 * Client side only register stuff...
	 */
	public void registerRenderInformation() {
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof YC_TileEntityBlockDetector) 
		{
			return new YC_ContainerBlockDetector(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockWorkbench) 
		{
			return new YC_ContainerBlockWorkbench(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockSymbolInput) 
		{
			return new YC_ContainerSymbolInput(player, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockCrystalizer) 
		{
			return new YC_ContainerBlockCrystalizer(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockResearchTable) 
		{
			return new YC_ContainerBlockResearchTable(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockResearchShardGenerator) 
		{
			return new YC_ContainerBlockResearchShardGenerator(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockChest) 
		{
			TileEntity te2 = world.getBlockTileEntity(
					((YC_TileEntityBlockChest)te).mx, 
					((YC_TileEntityBlockChest)te).my, 
					((YC_TileEntityBlockChest)te).mz);
			if (te2 == null) return null;
			return new YC_ContainerBlockChest(player, (IInventory) te2);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof YC_TileEntityBlockDetector) 
		{
			return new YC_GUIBlockDetector(player.inventory,(IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockWorkbench) 
		{
			return new YC_GUIBlockWorkbench(player.inventory,(IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockSymbolInput) 
		{
			return new YC_GUIBlockSymbolInput(player,(IInventory) te, x, y, z);
		}
		if (te instanceof YC_TileEntityBlockCrystalizer) 
		{
			return new YC_GUIBlockCrystalizer(player.inventory,(IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockResearchTable) 
		{
			return new YC_GUIBlockResearchTable(player.inventory,(IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockResearchShardGenerator) 
		{
			return new YC_GUIBlockResearchShardGenerator(player.inventory, (IInventory) te);
		}
		if (te instanceof YC_TileEntityBlockChest) 
		{
			TileEntity te2 = world.getBlockTileEntity(
					((YC_TileEntityBlockChest)te).mx, 
					((YC_TileEntityBlockChest)te).my, 
					((YC_TileEntityBlockChest)te).mz);
			if (te2 == null) return null;
			return new YC_GUIBlockChest(player, (IInventory) te2, 
					x, y, z);
		}
		return null;
	}
	
	public static void NullifyBlock(World world, int x, int y, int z)
	{
		world.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
		world.setBlockTileEntity(x, y, z, null);
	}
	
	public static void MarkBlockForGlobalRenderUpdate(int par2, int par3, int par4)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			Minecraft.getMinecraft().renderGlobal.markBlocksForUpdate(par2, par3, par4, par2, par3, par4);
	}

}