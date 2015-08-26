package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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

public class YC_TileEntityCrystalizerRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		//if (!YC_Options.EnableOptifineCompability)
		//GL11.glTranslatef(0, 0, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/crystalizer_base.png"));
		YC_Mod.m_CrystalizerBase.DrawCwCcw(0,0,1);
		GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		Tessellator.instance.draw();
		GL11.glPushMatrix();
		double t1 = 0,t2 = 0,t3 = 0;
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
			t1 = Tessellator.instance.xOffset;
			t2 = Tessellator.instance.yOffset;
			t3 = Tessellator.instance.zOffset;
		}

		GL11.glColor4f(1f, 1f, 1f, 1f);
		YC_Mod.m_CrystalizerBase.DrawCwCcw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/crystalizer_base.png"),x,y,z+1);
		
		//if (YC_Options.RenderContourSecondPass)
		//{
		//	//GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/blank.png"));
		//	GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
		//	GL11.glColor3f(0f, 0f, 0f);
		//	YC_Mod.m_CrystalizerBase.DrawCwCcw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/crystalizer_base.png"));
		//	GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		//	GL11.glColor3f(1f, 1f, 1f);
		//}


		YC_TileEntityBlockCrystalizer te = (YC_TileEntityBlockCrystalizer) world.getBlockTileEntity(x, y, z);
		if (te.TimeLeft != YC_TileEntityBlockCrystalizer.MAX_GEN_TIME)
		{
			if (YC_Options.EnableOptifineCompability)
			{
				GL11.glTranslated(x+Tessellator.instance.xOffset, y+Tessellator.instance.yOffset, z+Tessellator.instance.zOffset+1);
			}
			else
			{
				GL11.glTranslatef(x, y, z+1);
				Tessellator.instance.setTranslation(0, 0, 0);
			}
			
			float f = (YC_ClientTickHandler.TicksInGame % 40f) / 40f;
        	GL11.glTranslatef(0.5f, 0f, -0.5f);
        	GL11.glRotatef((float) (f * 360), 0, 1, 0);
        	GL11.glTranslatef(-0.5f, 0f, 0.5f);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_crystal.png"));
			YC_Mod.m_CrystalizerCrystal.DrawCwCcwWOTranslation(x,y,z+1);
			
			if (YC_Options.RenderContourSecondPass)
			{
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/blank.png"));
				GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
				GL11.glColor3f(0f, 0f, 0f);
				YC_Mod.m_CrystalizerCrystal.DrawCwCcwWOTranslation(x,y,z+1);
				GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
			}
		}

		if (!YC_Options.EnableOptifineCompability)
		{
			Tessellator.instance.setTranslation(t1,t2,t3);
		}
        GL11.glPopMatrix();
        GL11.glPopAttrib();

		Tessellator.instance.startDrawingQuads();

		GL11.glColor3f(1f, 1f, 1f);
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
		return YC_Mod.c_crystalizerRenderID;
	}
}
