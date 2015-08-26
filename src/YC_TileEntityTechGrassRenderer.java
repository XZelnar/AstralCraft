package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.src.old.TextureFXManager;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YC_TileEntityTechGrassRenderer implements
		ISimpleBlockRenderingHandler {//TODO texture files
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glColor4f(1f, 1f, 1f, 1f);
		renderer.renderBlockAllFaces(block, 0, 0, 0);
		//render(0, 0, 0, 0, 0, block, renderer);
		if (Tessellator.instance.isDrawing) Tessellator.instance.draw();
	}
	
	public static Icon sides;

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		//if (block != null) return true;
		if (Tessellator.instance.isDrawing) Tessellator.instance.draw();
		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		//GL11.glDisable(GL11.GL_LIGHTING);
		
		if (world != null)
			render(x, y, z, world.getBlockId(x, y, z) - YC_Mod.c_BlockBiomeGrassFirstID, world.getBlockMetadata(x, y, z), block, renderer);
		else
			render(x, y, z, 0, 0, block, renderer);
		
		//GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TerrainTextureID);
		//Minecraft.getMinecraft().renderEngine.func_98187_b("/gui/items.png");
		//Minecraft.getMinecraft().renderEngine.func_98187_b("/terrain.png");
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		Tessellator.instance.setBrightness(0x00F0);
		return true;
	}
	
	public void render(int x, int y, int z, int u, int v, Block block, RenderBlocks renderer)
	{
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setColorOpaque(255, 255, 255);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TechGrassSideTextureID);
		renderXP(x, y, z);//TODO
		renderXN(x, y, z);
		renderZP(x, y, z);
		renderZN(x, y, z);
		renderBottom(x, y, z);
		
		Tessellator.instance.draw();
		renderTop(x,y,z,u,v);
		
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.setColorOpaque(255, 255, 255);
	}
	
	public void renderZN(int x, int y, int z)
	{
		Tessellator t = Tessellator.instance;

		t.addVertexWithUV(x, y, z, 0, 1);
		t.addVertexWithUV(x, y+1, z, 1, 1);
		t.addVertexWithUV(x+1, y+1, z, 1, 0);
		t.addVertexWithUV(x+1, y, z, 0, 0);
	}
	
	public void renderZP(int x, int y, int z)
	{
		Tessellator t = Tessellator.instance;

		t.addVertexWithUV(x+1, y, z+1, 0, 0);
		t.addVertexWithUV(x+1, y+1, z+1, 1, 0);
		t.addVertexWithUV(x, y+1, z+1, 1, 1);
		t.addVertexWithUV(x, y, z+1, 0, 1);
	}
	
	public void renderXN(int x, int y, int z)
	{
		Tessellator t = Tessellator.instance;

		t.addVertexWithUV(x, y, z+1, 0, 1);
		t.addVertexWithUV(x, y+1, z+1, 1, 1);
		t.addVertexWithUV(x, y+1, z, 1, 0);
		t.addVertexWithUV(x, y, z, 0, 0);
	}
	
	public void renderXP(int x, int y, int z)
	{
		Tessellator t = Tessellator.instance;

		t.addVertexWithUV(x+1, y, z, 0, 0);
		t.addVertexWithUV(x+1, y+1, z, 1, 0);
		t.addVertexWithUV(x+1, y+1, z+1, 1, 1);
		t.addVertexWithUV(x+1, y, z+1, 0, 1);
	}
	
	public void renderBottom(int x, int y, int z)
	{
		Tessellator t = Tessellator.instance;

		t.addVertexWithUV(x, y, z, 0, 0);
		t.addVertexWithUV(x+1, y, z, 1, 0);
		t.addVertexWithUV(x+1, y, z+1, 1, 1);
		t.addVertexWithUV(x, y, z+1, 0, 1);
	}
	
	public void renderTop(int x, int y, int z, float u, float v)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setColorOpaque(255, 255, 255);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TechGrassTextureID);
		
		float u0 = u / 16f;
		float v0 = v / 16f;
		float u1 = (u + 1) / 16f;
		float v1 = (v + 1) / 16f;
		
		t.addVertexWithUV(x, y+1, z+1, u0, v1);
		t.addVertexWithUV(x+1, y+1, z+1, u1, v1);
		t.addVertexWithUV(x+1, y+1, z, u1, v0);
		t.addVertexWithUV(x, y+1, z, u0, v0);
		
		t.draw();
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
		return YC_Mod.c_techGrassRenderID;
	}
	
	boolean useInventoryTint = true;
    public void renderBlockAsItem(Block par1Block, int par2, float par3, RenderBlocks render)
    {
        Tessellator var4 = Tessellator.instance;
        boolean var5 = par1Block.blockID == Block.grass.blockID;
        int var6;
        float var7;
        float var8;
        float var9;

        if (this.useInventoryTint)
        {
            var6 = par1Block.getRenderColor(par2);

            if (var5)
            {
                var6 = 16777215;
            }

            var7 = (float)(var6 >> 16 & 255) / 255.0F;
            var8 = (float)(var6 >> 8 & 255) / 255.0F;
            var9 = (float)(var6 & 255) / 255.0F;
            GL11.glColor4f(var7 * par3, var8 * par3, var9 * par3, 1.0F);
        }

        var6 = par1Block.getRenderType();
        render.setRenderBoundsFromBlock(par1Block);
        int var14;
        {
            if (var6 == 16)
            {
                par2 = 1;
            }

            par1Block.setBlockBoundsForItemRender();
            render.setRenderBoundsFromBlock(par1Block);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            render.renderBottomFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(0, par2));
            var4.draw();

            if (var5 && this.useInventoryTint)
            {
                var14 = par1Block.getRenderColor(par2);
                var8 = (float)(var14 >> 16 & 255) / 255.0F;
                var9 = (float)(var14 >> 8 & 255) / 255.0F;
                float var10 = (float)(var14 & 255) / 255.0F;
                GL11.glColor4f(var8 * par3, var9 * par3, var10 * par3, 1.0F);
            }

            var4.startDrawingQuads();
            var4.setNormal(0.0F, 1.0F, 0.0F);
            render.renderTopFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(1, par2));
            var4.draw();

            if (var5 && this.useInventoryTint)
            {
                GL11.glColor4f(par3, par3, par3, 1.0F);
            }

            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, -1.0F);
            render.renderEastFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(2, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, 1.0F);
            render.renderWestFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(3, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0F, 0.0F, 0.0F);
            render.renderNorthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(4, par2));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0F, 0.0F, 0.0F);
            render.renderSouthFace(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getBlockTextureFromSideAndMetadata(5, par2));
            var4.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }
}
