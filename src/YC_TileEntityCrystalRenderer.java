package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.FloatBuffer;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YC_TileEntityCrystalRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		//if (!YC_Options.EnableOptifineCompability)
		//GL11.glTranslatef(0, 0, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.CrystalTextureID);
		YC_Mod.m_Crystal.Draw(0,0,1);
		//GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TerrainTextureID);
        GL11.glEnable(GL11.GL_LIGHTING);
	}

	public static Minecraft mc;
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		YC_DelayedUpdatesManager.AddCrystal(x, y, z);

		if (Math.abs(mc.thePlayer.posX - x)+Math.abs(mc.thePlayer.posY - y)+Math.abs(mc.thePlayer.posZ - z)>YC_Options.RenderDistance) return true;

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		if (Tessellator.instance.isDrawing)
			Tessellator.instance.draw();
		GL11.glPushMatrix();

		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
		}
		//GL11.glTranslatef(x, y, z+1);
		YC_Mod.m_Crystal.Draw(YC_Constants.CrystalTextureID,x,y,z+1);
		if (YC_Options.RenderContourSecondPass)
		{
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
			GL11.glColor3f(0f, 0f, 0f);
			YC_Mod.m_Crystal.Draw(YC_Constants.CrystalTextureID,x,y,z+1);
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		}
			
        GL11.glPopMatrix();
        GL11.glPopAttrib();

		Tessellator.instance.startDrawingQuads();

		GL11.glColor3f(1f, 1f, 1f);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TerrainTextureID);
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
		return YC_Mod.c_crystalRenderID;
	}
}
