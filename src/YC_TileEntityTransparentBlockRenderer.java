package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YC_TileEntityTransparentBlockRenderer implements
		ISimpleBlockRenderingHandler {
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return true;
	}
	
	public void ResetTranslation(int x, int y, int z)
	{
		if (z<=0)z=z+1;
		if (x<=0)x=x+1;
		if (x>0)
		{
	        GL11.glTranslatef(-x+x%16, 0,0);
		}
		if (x<=0)
		{
	        GL11.glTranslatef(-x+x%16+16, 0,0);
		}

		if (y>0)
		{
	        GL11.glTranslatef(0,-y+y%16, 0);
		}
		if (y<=0)
		{
	        GL11.glTranslatef(0,-y+y%16+16, 0);
		}

		if (z>0)
		{
	        GL11.glTranslatef(0,0,-z+z%16);
		}
		if (z<=0)
		{
	        GL11.glTranslatef(0,0,-z+z%16+16);
		}
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public int getRenderId() {
		return YC_Mod.c_transparentBlockRenderID;
	}
}
