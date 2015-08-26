package net.minecraft.src;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class YC_EntityMobBirdRender extends RenderLiving
{

	public YC_EntityMobBirdRender(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}
	
	@Override
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		render(par2,par4,par6);
		//super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
	
	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2,
			double par4, double par6, float par8, float par9) {
		render(par2,par4,par6);
		//super.doRenderLiving(par1EntityLiving, par2, par4, par6, par8, par9);
	}
	
	public void render(double x, double y, double z)
	{
		int i = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, YC_Constants.TerrainTextureID);
		//System.out.println(Tessellator.instance.xOffset+":"+Tessellator.instance.yOffset+";"+Tessellator.instance.zOffset);
		Tessellator.instance.startDrawingQuads();
		Tessellator.instance.addVertexWithUV(x, y, z, 0, 0);
		Tessellator.instance.addVertexWithUV(x+1, y, z, 1, 0);
		Tessellator.instance.addVertexWithUV(x+1, y+1, z, 1, 1);
		Tessellator.instance.addVertexWithUV(x, y+1, z, 0, 1);
		Tessellator.instance.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, i);
	}
	
	@Override
	public void doRenderShadowAndFire(Entity par1Entity, double par2,
			double par4, double par6, float par8, float par9) {
		return;
	}

}
