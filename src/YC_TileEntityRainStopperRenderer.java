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

public class YC_TileEntityRainStopperRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 0.2f, 0.2f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/RainToggle0.png"));
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        float f = YC_ClientTickHandler.TicksInGame % 40f / 40f;
        GL11.glRotatef(f * 360, 0, 1, 0);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        GL11.glTranslatef(0, (float)(Math.sin(Math.PI*2*f)/4f), 0);
		YC_Mod.m_Sphere.DrawWOTranslation();
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		YC_TileEntityBlockRainStopper te = (YC_TileEntityBlockRainStopper) world.getBlockTileEntity(x, y, z);

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        Tessellator t = Tessellator.instance;
        t.draw();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/RainToggle"+String.valueOf(te.CurState%4)+".png"));
        float scale = te.CurState<40?1-0.5f*te.CurState/40f : 0.55f;
        float color = te.CurState<40?1-te.CurState/40f : 0;
        float dy = te.CurState<40?0:(te.CurState-39)*2f;
        Random r = new Random();

		GL11.glColor3f(1f, color, color);
        
		GL11.glPushMatrix();
		double t1 = 0,t2 = 0,t3 = 0;
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
			GL11.glTranslatef(x, y, z);
			t1 = Tessellator.instance.xOffset;
			t2 = Tessellator.instance.yOffset;
			t3 = Tessellator.instance.zOffset;
			Tessellator.instance.setTranslation(0, 0, 0);
		}
		else
		{
			GL11.glTranslated(x+Tessellator.instance.xOffset, y+Tessellator.instance.yOffset, z+Tessellator.instance.zOffset);
		}
		if (te.CurState<100)
		{
			GL11.glTranslatef(0, dy, 0);
			GL11.glTranslatef(0.5f,0.5f,0.5f);
			if (te.CurState<40)
			{
		        float f = (YC_ClientTickHandler.TicksInGame % 40f) / 40f;
		        GL11.glRotatef(f * 360 * (te.CurState!=0?te.CurState:10) / 10f, 0, 1, 0);
		        //dy += (float)(Math.sin(Math.PI*2*f)/4f) / (float)Math.max(te.CurState * 20f, 1f);
			}
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(-0.5f,-0.5f+dy,-0.5f);
			YC_Mod.m_Sphere.DrawWOTranslation(1,color,color);
			/*if (YC_Options.RenderContourSecondPass)
			{
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/blank.png"));
				GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
				GL11.glColor3f(0.25f, 0.25f, 0.25f);
				YC_Mod.m_Sphere.Draw();
				GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
			}*/
		}
		else
		{
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_FOG);
			GL11.glTranslatef(0, 100, 0);
			GL11.glColor4f(1f, 0.25f+((float)r.nextInt(100))/1000f, 0.25f+((float)r.nextInt(100))/1000f, 
					0.5f+ ((float)r.nextInt(100))/200f);
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(-1000, 0, -1000);
			//GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(1000, 0, -1000);
			//GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(1000, 0, 1000);
			//GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(-1000, 0, 1000);
			
			GL11.glEnd();
			GL11.glEnable(GL11.GL_FOG);
			GL11.glDisable(GL11.GL_BLEND);
		}
		if (!YC_Options.EnableOptifineCompability)
		{
			Tessellator.instance.setTranslation(t1,t2,t3);
		}
        GL11.glPopMatrix();
        GL11.glPopAttrib();
        
        t.startDrawingQuads();

		GL11.glColor4f(1f, 1f, 1f, 1f);
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
		return YC_Mod.c_rainStopperRenderID;
	}
}
