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

public class YC_TileEntityAdvAstTelRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glColor3f(1f, 1f, 1f);
		//if (!YC_Options.EnableOptifineCompability)
		//GL11.glTranslatef(0, 0, 1f);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_crystal.png"));
		YC_Mod.m_AdvAstTel1.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/AdvAstTel1.png"),0,0,1);
		GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
        GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		//if (YC_TileEntityBlockAdvAstralTeleporter.particle == null)
		//{
			//YC_TileEntityBlockAdvAstralTeleporter.particle = new YC_EntityFXAdvAstralPortal((World) world, x+0.5f, y+1, z+0.5f, 0, 0, 0);
		//}
		
		YC_TileEntityBlockAdvAstralTeleporter te = (YC_TileEntityBlockAdvAstralTeleporter) world.getBlockTileEntity(x, y, z);

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		if (Tessellator.instance.isDrawing)
			Tessellator.instance.draw();
		GL11.glPushMatrix();

		GL11.glColor4f(1f, 1f, 1f, 1f);
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
			//GL11.glTranslatef(x, y, z+1);
		}
		if (te.CurPlayer.equals(""))
		{
			YC_Mod.m_AdvAstTel1.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/AdvAstTel1.png"),x,y,z+1);
		}
		else
		{
			YC_Mod.m_AdvAstTel2.Draw(Minecraft.getMinecraft().renderEngine.getTexture("/YC/AdvAstTel2.png"),x,y,z+1);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture(
	        		"/YC/AdvAstTel"+(YC_Options.HighPolyModels?"":"/LP")+
	        "/AdvAstTelGlow"+(YC_ClientTickHandler.TicksInGame % 16)+".png"));
			YC_Mod.m_AdvAstTelSelect.Draw(0f,0.5f,1f,x,y,z+1);
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		}
		/*if (YC_Options.RenderContourSecondPass)
		{
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
			GL11.glColor3f(0f, 0f, 0f);
			YC_Mod.m_Crystal.Draw();
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		}*/
			
        GL11.glPopMatrix();
        GL11.glPopAttrib();

		Tessellator.instance.startDrawingQuads();

		GL11.glColor3f(1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
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
		return YC_Mod.c_advAstTelRenderID;
	}
}
