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
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YC_TileEntityAstralBeaconRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		YC_Mod.m_AstralBeacon1.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/astralBeacon.png"),0,0,1);
		GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
        GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		YC_TileEntityBlockAstralBeacon te = (YC_TileEntityBlockAstralBeacon) world.getBlockTileEntity(x, y, z);

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		if (Tessellator.instance.isDrawing)
			Tessellator.instance.draw();
		GL11.glPushMatrix();

		GL11.glColor4f(1f, 1f, 1f, 1f);
		double t1 = 0,t2 = 0,t3 = 0;
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
		}
		if (te.CurPlayer.equals(""))
		{
			YC_Mod.m_AstralBeacon1.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/astralBeacon.png"),x,y,z+1);
		}
		else
		{
			YC_Mod.m_AstralBeacon2.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/astralBeacon2.png"),x,y,z+1);
	        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		}
		
		if (!YC_Options.EnableOptifineCompability)
		{
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
		
		GL11.glTranslated(0.5, 0, 0.5);
		GL11.glPushMatrix();
        float f = (YC_ClientTickHandler.TicksInGame % 40f) / 40f;
        GL11.glRotatef(f * 360, 0, 1, 0);
        GL11.glTranslated(-0.5, 0, 0.5);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/astralBeacon.png"));
        if (te.RenderRay)
        	YC_Mod.m_AstralBeaconMain.DrawWOTranslation(0.10f,0.10f,0.75f);
        else
        	YC_Mod.m_AstralBeaconMain.DrawWOTranslation(0.75f,0.0f,0.0f);
		GL11.glColor3f(1f, 1f, 1f);
		
		GL11.glPopMatrix();
		
		if (te.RenderRay)
		{
			GL11.glRotatef(-2*f * 360, 0, 1, 0);
        	GL11.glTranslated(-0.5, 0, 0.5);
        	GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/astralBeaconBeam.png"));
			YC_Mod.m_AstralBeaconBeam.DrawWOTranslation();
		}
		//Draw beacon itself
		
		/*if (YC_Options.RenderContourSecondPass)
		{
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
			GL11.glColor3f(0f, 0f, 0f);
			YC_Mod.m_Crystal.Draw();
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		}*/
			
        GL11.glPopMatrix();
		if (!YC_Options.EnableOptifineCompability)
		{
			Tessellator.instance.setTranslation(t1,t2,t3);
		}

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
		return YC_Mod.c_astBeaconRenderID;
	}
}
