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

public class YC_TileEntityDiggerRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		//if (!YC_Options.EnableOptifineCompability)
		//GL11.glTranslatef(0f, 0f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/dig_texture.png"));
		YC_Mod.m_digBsae.Draw(0,0,1);
		GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		YC_TileEntityBlockDigger te = (YC_TileEntityBlockDigger) world.getBlockTileEntity(x, y, z);

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
		//GL11.glTranslatef(x, y, z+1);
		int ti = Minecraft.getMinecraft().renderEngine.getTexture("/YC/dig_texture.png");
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, ti);
		YC_Mod.m_digBsae.Draw(x,y,z+1);
		
		if (!YC_Options.EnableOptifineCompability)
		{
			Tessellator.instance.setTranslation(0, 0, 0);
		}
		float ty = te.CurState<100?0.5f-(float)te.CurState/200f:0f;
		
		//Arm 1 draw
		{
			GL11.glPushMatrix();
			Translate(x,y,z);
			GL11.glTranslatef(0.75f-ty, 0f, -0.25f);
			YC_Mod.m_digArm1.DrawWOTranslation();
			GL11.glTranslatef(-0.75f+ty, 0f, 0.25f);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(0.75f-ty, 0f, 0.75f);
			YC_Mod.m_digArm1.DrawWOTranslation();
			GL11.glTranslatef(-0.75f+ty, 0f, -0.75f);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(-0.25f-ty, 0f, 0.75f);
			YC_Mod.m_digArm1.DrawWOTranslation();
			GL11.glTranslatef(-0.25f+ty, 0f, 0.75f);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(1.25f-ty, 0f, 0.25f);
			YC_Mod.m_digArm1.DrawWOTranslation();
			GL11.glPopMatrix();
		}
		
		
		ty = te.CurState<240?0.5f-(float)(te.CurState-140f)/150f:-0.166f;
		if (te.CurState<140) ty = 0.5f;
		
		//Arm 2
		//if (te.CurState>=140)
		{
			GL11.glPushMatrix();
			Translate(x,y,z);
			//GL11.glRotatef(45, 0, 1, 0);
			//GL11.glTranslatef(0.5f, 0f, -0.25f+ty);
			GL11.glTranslatef(0.2f, 0f, -0.7f);
			GL11.glRotatef(45, 0, 1, 0);
			GL11.glTranslatef(-0.25f, 0f, 0f+ty);
			YC_Mod.m_digArm2.DrawWOTranslation();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			Translate(x,y,z);
			GL11.glTranslatef(0f, 0f, 0f);
			GL11.glRotatef(135, 0, 1, 0);
			GL11.glTranslatef(-0.25f, 0f, 0.3f+ty);
			YC_Mod.m_digArm2.DrawWOTranslation();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			Translate(x,y,z);
			GL11.glTranslatef(0f, 0f, 0f);
			GL11.glRotatef(225, 0, 1, 0);
			GL11.glTranslatef(-1f, 0f, -0.35f+ty);
			YC_Mod.m_digArm2.DrawWOTranslation();
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			Translate(x,y,z);
			GL11.glTranslatef(0f, 0f, 0f);
			GL11.glRotatef(315, 0, 1, 0);
			GL11.glTranslatef(-0.2f, 0f, -1f+ty);
			YC_Mod.m_digArm2.DrawWOTranslation();
			GL11.glPopMatrix();
		}
		

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/blank.png"));
		if (YC_Options.RenderContourSecondPass)
		{
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
			GL11.glColor3f(0.3f, 0.3f, 0.2f);

			YC_Mod.m_digBsae.Draw(x,y,z+1);
			ty = te.CurState<100?0.5f-(float)te.CurState/200f:0f;
			
			//Arm 1 draw
			{
				GL11.glPushMatrix();
				Translate(x,y,z);
				GL11.glTranslatef(0.75f-ty, 0f, -0.25f);
				YC_Mod.m_digArm1.DrawWOTranslation();
				GL11.glTranslatef(-0.75f+ty, 0f, 0.25f);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glTranslatef(0.75f-ty, 0f, 0.75f);
				YC_Mod.m_digArm1.DrawWOTranslation();
				GL11.glTranslatef(-0.75f+ty, 0f, -0.75f);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glTranslatef(-0.25f-ty, 0f, 0.75f);
				YC_Mod.m_digArm1.DrawWOTranslation();
				GL11.glTranslatef(-0.25f+ty, 0f, 0.75f);
				GL11.glRotatef(90, 0, 1, 0);
				GL11.glTranslatef(1.25f-ty, 0f, 0.25f);
				YC_Mod.m_digArm1.DrawWOTranslation();
				GL11.glPopMatrix();
			}
			
			
			ty = te.CurState<240?0.5f-(float)(te.CurState-140f)/150f:-0.166f;
			if (te.CurState<140) ty = 0.5f;
			
			//Arm 2
			//if (te.CurState>=140)
			{
				GL11.glPushMatrix();
				Translate(x,y,z);
				//GL11.glRotatef(45, 0, 1, 0);
				//GL11.glTranslatef(0.5f, 0f, -0.25f+ty);
				GL11.glTranslatef(0.2f, 0f, -0.7f);
				GL11.glRotatef(45, 0, 1, 0);
				GL11.glTranslatef(-0.25f, 0f, 0f+ty);
				YC_Mod.m_digArm2.DrawWOTranslation();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				Translate(x,y,z);
				GL11.glTranslatef(0f, 0f, 0f);
				GL11.glRotatef(135, 0, 1, 0);
				GL11.glTranslatef(-0.25f, 0f, 0.3f+ty);
				YC_Mod.m_digArm2.DrawWOTranslation();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				Translate(x,y,z);
				GL11.glTranslatef(0f, 0f, 0f);
				GL11.glRotatef(225, 0, 1, 0);
				GL11.glTranslatef(-1f, 0f, -0.35f+ty);
				YC_Mod.m_digArm2.DrawWOTranslation();
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				Translate(x,y,z);
				GL11.glTranslatef(0f, 0f, 0f);
				GL11.glRotatef(315, 0, 1, 0);
				GL11.glTranslatef(-0.2f, 0f, -1f+ty);
				YC_Mod.m_digArm2.DrawWOTranslation();
				GL11.glPopMatrix();
			}
			
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
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
	
	public void Translate(int x, int y, int z)
	{
		if (YC_Options.EnableOptifineCompability)
		{
			GL11.glTranslated(x+Tessellator.instance.xOffset, y+Tessellator.instance.yOffset, z+Tessellator.instance.zOffset+1);
		}
		else
		{
			GL11.glTranslatef(x, y, z+1);
		}
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
		return YC_Mod.c_diggerRenderID;
	}
}
