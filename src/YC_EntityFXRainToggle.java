package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class YC_EntityFXRainToggle extends EntityFX
{
    float particleScaleOverTime;
    public float[] lines = new float[240];
    public float[] linesAlpha = new float[80];
    Random r = new Random();
    float rc=0f, gc=0f, bc=0f;

    public YC_EntityFXRainToggle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int t)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
        if (t == 1)
        {
        	rc = 0f;
        	gc = 0.2f;
        	bc = 1f;
        }
        if (t == 2)
        {
        	rc = 1f;
        	gc = 0.3f;
        	bc = 0.3f;
        }
    }

    public YC_EntityFXRainToggle(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2+0.5f, par4+0.5f, par6+0.5f, 0.0D, 0.0D, 0.0D);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
        this.particleScale *= 2.75F;
        this.particleScale *= par14;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 40;
        this.noClip = false;
        this.setParticleTextureIndex(0);
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var81 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0F;

        if (var81 < 0.0F)
        {
            var81 = 0.0F;
        }

        if (var81 > 1.0F)
        {
            var81 = 1.0F;
        }

        this.particleScale = this.particleScaleOverTime * var81;

        

        GL11.glEnable(GL11.GL_BLEND);
        
        //renderParticle(par2, par3, par4, par5, par6, par7);
        //GenLine(0);
        double x = Tessellator.instance.xOffset;
        double y = Tessellator.instance.yOffset;
        double z = Tessellator.instance.zOffset;
        if (!YC_Options.EnableOptifineCompability) Tessellator.instance.setTranslation(0, 0, 0);
        boolean b = Tessellator.instance.isDrawing;
        if (Tessellator.instance.isDrawing)
        	Tessellator.instance.draw();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/particles.png"));
        if (particleAge<=38)
        {
        	if (particleAge<=18)
        	{
        		int t = (particleAge%20)*12;
        		GenLine(t);
        		GenLine(t+6);
        	}
            DrawLines(par2, par3, par4, par5, par6, par7);
        }

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/particles.png"));
        //Tessellator.instance.draw();
        if (b)
        	Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setTranslation(x, y, z);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public void GenLine(int i)
    {
		float ax = (float) (r.nextFloat()*Math.PI*2);
		float ay = (float) (r.nextFloat()*Math.PI*2);
		float l = r.nextFloat()*0.5f+1.75f;
		lines[i] = (float) (Math.sin(ax) * l);
		lines[i+1] = (float) (Math.sin(ay) * l);
		lines[i+2] = (float) (Math.cos(ax) * l);
		linesAlpha[i/3] = l/2f;

		l = r.nextFloat()*0.25f+1.25f;
		lines[i+3] = (float) (Math.sin(ax) * l);
		lines[i+4] = (float) (Math.sin(ay) * l);
		lines[i+5] = (float) (Math.cos(ax) * l);
		linesAlpha[i/3+1] = l/2f;
    }
    
    //from YC_EntityFXEnergyFromAirGaining
    public void DrawLines(float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var12 = 0.1F * 0;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
    	Tessellator.instance.startDrawingQuads();
    	Tessellator.instance.setBrightness(0x00F0);
    	Tessellator.instance.setTranslation(Tessellator.instance.xOffset+var13 - par3 * var12 - par6 * var12, 
    			Tessellator.instance.yOffset+var14 - par4 * var12, 
    			Tessellator.instance.zOffset + var15 - par5 * var12 - par7 * var12);
    	for(int i = 0; i<lines.length; i+=6)
    	{
    		Tessellator.instance.setColorRGBA_F(rc,gc,bc,linesAlpha[i/3]/2f);
    		Tessellator.instance.addVertexWithUV(lines[i]+0.1f, lines[i+1], lines[i+2],0.51,0.5);
    		Tessellator.instance.addVertexWithUV(lines[i], lines[i+1], lines[i+2],0.5,0.5);
    		Tessellator.instance.setColorRGBA_F(rc,gc,bc,linesAlpha[i/3+1]/2f);
    		Tessellator.instance.addVertexWithUV(lines[i+3], lines[i+4]+0.1f, lines[i+5],0.5,0.51);
    		Tessellator.instance.addVertexWithUV(lines[i+3]+0.1f, lines[i+4]+0.1f, lines[i+5],0.51,0.51);
    	}
    	Tessellator.instance.draw();
        GL11.glEnable(GL11.GL_CULL_FACE);
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    public void DrawLinesOld(float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var12 = 0.1F * 0;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
    	GL11.glLineWidth(5f);
    	GL11.glPushMatrix();
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/particles.png"));
    	GL11.glTranslated((double)(var13 - par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 - par5 * var12 - par7 * var12));
    	GL11.glBegin(GL11.GL_LINES);
    	for(int i = 0; i<lines.length; i+=6)
    	{
        	GL11.glColor4f(rc,gc,bc,linesAlpha[i/3]/2f);
    		GL11.glVertex3f(lines[i], lines[i+1], lines[i+2]);
        	GL11.glColor4f(rc,gc,bc,linesAlpha[i/3+1]/2f);
    		GL11.glVertex3f(lines[i+3], lines[i+4], lines[i+5]);
    	}
    	GL11.glEnd();
    	GL11.glPopMatrix();
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	//GL11.glTranslated(posX, posY, posZ);
    	GL11.glLineWidth(1f);
    }
    
    public void renderParticle(float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	float var8 = (float)this.field_94054_b / 16.0F;
        float var9 = var8 + 0.0624375F;
        float var10 = (float)this.field_94055_c / 16.0F;
        float var11 = var10 + 0.0624375F;
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
        GL11.glBegin(GL11.GL_QUADS);
        //par1Tessellator.setColorOpaque_F(this.particleRed * var16, this.particleGreen * var16, this.particleBlue * var16);
        VertexParticle((double)(var13 - par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 - par5 * var12 - par7 * var12), (double)var9, (double)var11);
        VertexParticle((double)(var13 - par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 - par5 * var12 + par7 * var12), (double)var9, (double)var10);
        VertexParticle((double)(var13 + par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 + par5 * var12 + par7 * var12), (double)var8, (double)var10);
        VertexParticle((double)(var13 + par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 + par5 * var12 - par7 * var12), (double)var8, (double)var11);
        GL11.glEnd();
    }
    
    public void VertexParticle(double x, double y, double z, double u, double v)
    {
    	GL11.glTexCoord2d(u,v);
    	GL11.glVertex3d(x, y, z);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        
        for(int i = 0; i<lines.length; i++)
        {
        	lines[i]*=0.8f;
        }
        for(int i = 0; i<linesAlpha.length; i++)
        {
        	linesAlpha[i]*=1.3f;
        }
    }
}
