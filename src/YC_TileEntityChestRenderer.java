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

public class YC_TileEntityChestRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_Chest.png"));
		YC_ClientProxy.blockRenderer.renderInventoryBlock(block, metadata, modelID, renderer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		if (world.getBlockMetadata(x, y, z) == 0)
		{
			//YC_ClientProxy.blockRenderer.renderWorldBlock(world, x, y, z, block, modelId, renderer);

			boolean b = Tessellator.instance.isDrawing;
			if (b) Tessellator.instance.draw();
			Tessellator.instance.startDrawingQuads();
			Tessellator.instance.setColorOpaque(255, 255, 255);
			renderer.renderWestFace(block, x, y, z, YC_BlockChest.side);
			renderer.renderEastFace(block, x, y, z, YC_BlockChest.side);
			renderer.renderNorthFace(block, x, y, z, YC_BlockChest.side);
			renderer.renderSouthFace(block, x, y, z, YC_BlockChest.side);
			renderer.renderTopFace(block, x, y, z, YC_BlockChest.top);
			renderer.renderBottomFace(block, x, y, z, YC_BlockChest.bottom);
			Tessellator.instance.draw();
			if (b) Tessellator.instance.startDrawingQuads();
			return true;
		}
		if (world.getBlockMetadata(x, y, z)!=7) return true;

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		if (Tessellator.instance.isDrawing) Tessellator.instance.draw();
		GL11.glPushMatrix();
		double t1 = 0,t2 = 0,t3 = 0;
		if (!YC_Options.EnableOptifineCompability)
		{
			ResetTranslation(x, y, z);
			t1 = Tessellator.instance.xOffset;
			t2 = Tessellator.instance.yOffset;
			t3 = Tessellator.instance.zOffset;
		}

		YC_TileEntityBlockChest te = (YC_TileEntityBlockChest) world.getBlockTileEntity(x, y, z);
		
		GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/YC_Chest.png"));
		YC_Mod.m_ChestBase.DrawCwCcw(x,y,z+2);
		YC_Mod.m_ChestLid.DrawCwCcw(x,y+1.375f+te.dy,z+2);

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
	
	public void DrawChest(int x, int y, int z, float scale)
	{ 
		
        /*
        float s = 2f*scale;
        float s2 = 0.5f*scale;
        float s3 = 1.45454545f*scale;
        float s1 = 2f * scale;
        Tessellator t = Tessellator.instance;
        if (t.isDrawing) t.draw();
        t.setBrightness(0x00F0);
        
        if (t.isDrawing) t.draw();  

		GL11.glDisable(GL11.GL_CULL_FACE);
        t.startDrawingQuads();
        t.addVertexWithUV(x, y+s3, z+0.001, 0, 0.15625);
        t.addVertexWithUV(x+s, y+s3, z+0.001, 0.5, 0.15625);
        t.addVertexWithUV(x+s, y, z+0.001, 0.5, 0.5);
        t.addVertexWithUV(x, y, z+0.001, 0, 0.5);

        t.addVertexWithUV(x+0.001, y, z, 0.5, 0.5);
        t.addVertexWithUV(x+0.001, y, z+s, 0, 0.5);
        t.addVertexWithUV(x+0.001, y+s3, z+s, 0, 0.15625);
        t.addVertexWithUV(x+0.001, y+s3, z, 0.5, 0.15625);

        t.addVertexWithUV(x+s-0.001, y+s3, z, 0, 0.15625);
        t.addVertexWithUV(x+s-0.001, y+s3, z+s, 0.5, 0.15625);
        t.addVertexWithUV(x+s-0.001, y, z+s, 0.5, 0.5);
        t.addVertexWithUV(x+s-0.001, y, z, 0, 0.5);

        t.addVertexWithUV(x, y, z+s-0.001, 0.5, 0.5);
        t.addVertexWithUV(x+s, y, z+s-0.001, 0, 0.5);
        t.addVertexWithUV(x+s, y+s3, z+s-0.001, 0, 0.15625);
        t.addVertexWithUV(x, y+s3, z+s-0.001, 0.5, 0.15625);

        t.addVertexWithUV(x, y+0.001, z, 0, 0.5);
        t.addVertexWithUV(x+s, y+0.001, z, 0.5, 0.5);
        t.addVertexWithUV(x+s, y+0.001, z+s, 0.5, 1);
        t.addVertexWithUV(x, y+0.001, z+s, 0, 1);
        
        
        
        
     
        t.addVertexWithUV(x, y+s, z+0.001, 0, 0);
        t.addVertexWithUV(x+s, y+s, z+0.001, 0.5, 0);
        t.addVertexWithUV(x+s, y+s3, z+0.001, 0.5, 0.15625);
        t.addVertexWithUV(x, y+s3, z+0.001, 0, 0.15625);

        t.addVertexWithUV(x+0.001, y+s3, z, 0.5, 0.15625);
        t.addVertexWithUV(x+0.001, y+s3, z+s, 0, 0.15625);
        t.addVertexWithUV(x+0.001, y+s, z+s, 0, 0);
        t.addVertexWithUV(x+0.001, y+s, z, 0.5, 0);

        t.addVertexWithUV(x+s-0.001, y+s, z, 0, 0);
        t.addVertexWithUV(x+s-0.001, y+s, z+s, 0.5, 0);
        t.addVertexWithUV(x+s-0.001, y+s3, z+s, 0.5, 0.15625);
        t.addVertexWithUV(x+s-0.001, y+s3, z, 0, 0.15625);

        t.addVertexWithUV(x, y+s3, z+s-0.001, 0.5, 0.15625);
        t.addVertexWithUV(x+s, y+s3, z+s-0.001, 0, 0.15625);
        t.addVertexWithUV(x+s, y+s, z+s-0.001, 0, 0);
        t.addVertexWithUV(x, y+s, z+s-0.001, 0.5, 0);

        t.addVertexWithUV(x, y+s1, z+s, 1, 0);
        t.addVertexWithUV(x+s, y+s1, z+s, 0.5, 0);
        t.addVertexWithUV(x+s, y+s1, z, 0.5, 0.5);
        t.addVertexWithUV(x, y+s1, z, 1, 0.5);
        
        t.draw();
		GL11.glEnable(GL11.GL_CULL_FACE);//*/
        
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
		return YC_Mod.c_chestRenderID;
	}
}
