package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class YC_TileEntityLavaGeneratorRenderer implements
		ISimpleBlockRenderingHandler {
	
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		GL11.glDisable(GL11.GL_LIGHTING);
        MainVolume(0, 0, 0, 37);
        VolumeUpperCircle(0, 0, 0,0);
        Legs(0, 0, 0,37);
        Lava(0,0,0,255,13);
        Tessellator.instance.draw();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/terrain.png"));
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {


		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator.instance.draw();
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPushMatrix();
        MainVolume(x, y, z, 37);
        VolumeUpperCircle(x,y,z,0);
        Legs(x,y,z,37);
        Lava(x,y,z,255,world.getBlockMetadata(x, y, z));
        
		if (YC_Options.RenderContourSecondPass)
		{
	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/blank.png"));
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
			GL11.glColor3f(0.25f, 0.25f, 0.25f);
	        MainVolume(x, y, z, 37);
	        VolumeUpperCircle(x,y,z,0);
	        Legs(x,y,z,37);
			GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
		}
        
        Tessellator.instance.draw();
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();

		GL11.glPopAttrib();
        Tessellator.instance.startDrawingQuads();
        
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
	
	public void MainVolume(int x, int y, int z, int texture)
	{
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/textures/blocks/obsidian.png"));
        float u1 = 1;//((float)texture%16)/16f;
        float v1 = 1;//(texture/16)/16f;
        float u0 = 0;//((float)texture%16 + 1)/16f;
        float v0 = 0;//(texture/16 + 1f)/16f;
        float u05 = (u1+u0)/2;
        
        Tessellator t = Tessellator.instance;
        if (t.isDrawing) t.draw();
        t.startDrawingQuads();
        
        for(int i = 0; i<16; i++)//circle cycle
        {
        	//cw
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/2f+0.5f, u0, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/2f+0.5f, u1, v0);
        	t.addVertexWithUV(x+0.5f, y, z+0.5f, u05, v1);
        	t.addVertexWithUV(x+0.5f, y, z+0.5f, u05, v1);
        	
        	//ccw
        	t.addVertexWithUV(x+0.5f, y, z+0.5f, u05, v1);
        	t.addVertexWithUV(x+0.5f, y, z+0.5f, u05, v1);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/2f+0.5f, u1, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/2f+0.5f, u0, v0);
        }
        
        t.draw();
        t.startDrawingQuads();
	}
	
	public void VolumeUpperCircle(int x, int y, int z, int texture)
	{
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/textures/blocks/stone.png"));
        float u1 = 1;//((float)texture%16)/16f;
        float v1 = 1;//(texture/16)/16f;
        float u0 = 0;//((float)texture%16 + 1)/16f;
        float v0 = 0;//(texture/16 + 1f)/16f;
        float u05 = (u1+u0)/2;
        
        Tessellator t = Tessellator.instance;
        if (t.isDrawing) t.draw();
        t.startDrawingQuads();
        
        for(int i = 0; i<16; i++)//circle cycle
        {
        	//cw
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/2f+0.5f, u0, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/2f+0.5f, u1, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/1.8f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/1.8f+0.5f, u1, v1);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/1.8f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/1.8f+0.5f, u0, v1);

        	//ccw
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/1.8f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/1.8f+0.5f, u0, v1);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/1.8f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/1.8f+0.5f, u1, v1);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]/2f+0.5f, u1, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]/2f+0.5f, y+0.5f, 
        			z+YC_Mod.CircleValuesCos[i%16]/2f+0.5f, u0, v0);
        }
        
        t.draw();
        t.startDrawingQuads();
	}
	
	public void Legs(int x, int y, int z, int texture)
	{
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/textures/blocks/obsidian.png"));
        float u1 = 1;//((float)texture%16)/16f;
        float v1 = 1;//(texture/16)/16f;
        float u0 = 0;//((float)texture%16 + 1)/16f;
        float v0 = 0;//(texture/16 + 1f)/16f;
        float u05 = (u1+u0)/2;
        
        Tessellator t = Tessellator.instance;
        if (t.isDrawing) t.draw();
        GL11.glPushMatrix();
        t.startDrawingQuads();
    
        GL11.glTranslatef(x, y, z);
        DrawLegCube(x, y, z, t, u0, u05, u1, v0, v1);
        
        t.draw();
        GL11.glPopMatrix();
        t.startDrawingQuads();
	}
	
	public void DrawLegCube(int x, int y, int z, Tessellator t, float u0, float u05, float u1, float v0, float v1)
	{
        t.addVertexWithUV(0.47f, 0.25f, 0.25f, u0, v0);
        t.addVertexWithUV(0.53f, 0.25f, 0.25f, u1, v0);
        t.addVertexWithUV(0.53f, 0, 0.1f, u1, v1);
        t.addVertexWithUV(0.47f, 0, 0.1f, u0, v1);
        
        t.addVertexWithUV(0.47f, 0, 0.1f, u0, v1);
        t.addVertexWithUV(0.53f, 0, 0.1f, u1, v1);
        t.addVertexWithUV(0.53f, 0.25f, 0.25f, u1, v0);
        t.addVertexWithUV(0.47f, 0.25f, 0.25f, u0, v0);
        
        
        

        t.addVertexWithUV(0.47f, 0.25f, 0.75f, u0, v0);
        t.addVertexWithUV(0.53f, 0.25f, 0.75f, u1, v0);
        t.addVertexWithUV(0.53f, 0, 0.9f, u1, v1);
        t.addVertexWithUV(0.47f, 0, 0.9f, u0, v1);
        
        t.addVertexWithUV(0.47f, 0, 0.9f, u0, v1);
        t.addVertexWithUV(0.53f, 0, 0.9f, u1, v1);
        t.addVertexWithUV(0.53f, 0.25f, 0.75f, u1, v0);
        t.addVertexWithUV(0.47f, 0.25f, 0.75f, u0, v0);
        
        
        

        t.addVertexWithUV(0.25f, 0.25f, 0.47f, u0, v0);
        t.addVertexWithUV(0.25f, 0.25f, 0.53f, u1, v0);
        t.addVertexWithUV(0.1f, 0, 0.53f, u1, v1);
        t.addVertexWithUV(0.1f, 0, 0.47f, u0, v1);

        t.addVertexWithUV(0.1f, 0, 0.47f, u0, v1);
        t.addVertexWithUV(0.1f, 0, 0.53f, u1, v1);
        t.addVertexWithUV(0.25f, 0.25f, 0.53f, u1, v0);
        t.addVertexWithUV(0.25f, 0.25f, 0.47f, u0, v0);
        
        
        

        t.addVertexWithUV(0.75f, 0.25f, 0.47f, u0, v0);
        t.addVertexWithUV(0.75f, 0.25f, 0.53f, u1, v0);
        t.addVertexWithUV(0.9f, 0, 0.53f, u1, v1);
        t.addVertexWithUV(0.9f, 0, 0.47f, u0, v1);

        t.addVertexWithUV(0.9f, 0, 0.47f, u0, v1);
        t.addVertexWithUV(0.9f, 0, 0.53f, u1, v1);
        t.addVertexWithUV(0.75f, 0.25f, 0.53f, u1, v0);
        t.addVertexWithUV(0.75f, 0.25f, 0.47f, u0, v0);
	}

	public void Lava(int x, int y, int z, int texture, float md)
	{
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/textures/blocks/YC_lava_flow.png"));
        float text = YC_ClientTickHandler.TicksInGame % 32;
        float u1 = 0.5f;
        float v1 = (text+1)/32f;
        float u0 = 0;
        float v0 = text/32f;
        float u05 = (u1+u0)/2;
        
        md *= 2;
        float h = 0.5f/15f*md/2f;
        //float r = (float) (0.5*YC_Mod.sqrt2*md/15f)/2f;
        float r = (float)(0.5*md/15f)/2f;
        
        Tessellator t = Tessellator.instance;
        if (t.isDrawing) t.draw();
        t.startDrawingQuads();
        
        for(int i = 0; i<16; i++)//circle cycle
        {
        	//cw
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[i%16]*r+0.5f, y+h, 
        			z+YC_Mod.CircleValuesCos[i%16]*r+0.5f, u0, v0);
        	t.addVertexWithUV(x+YC_Mod.CircleValuesSin[(i+1)%16]*r+0.5f, y+h, 
        			z+YC_Mod.CircleValuesCos[(i+1)%16]*r+0.5f, u1, v0);
        	t.addVertexWithUV(x+0.5f, y+h, z+0.5f, u1, v1);
        	t.addVertexWithUV(x+0.5f, y+h, z+0.5f, u05, v1);
        }
        
        t.draw();
        t.startDrawingQuads();
	}
	
	
	public boolean shouldRender3DInInventory() {
		return true;
	}

	public int getRenderId() {
		return YC_Mod.c_lavaGeneratorRenderID;
	}
}
