package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class YC_EntityFXAstralAura extends EntityFX
{
    float particleScaleOverTime;
    public float[] lines = new float[40];

    public YC_EntityFXAstralAura(World par1World, double par2, double par4, double par6, double par8, double par10, double par12)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
    }

    public YC_EntityFXAstralAura(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2+0.5f, par4+0.5f, par6+0.5f, 0.0D, 0.0D, 0.0D);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
        this.particleScale *= 2.75F;
        this.particleScale *= par14;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 16;
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
        GL11.glColor4f(1f,1f,1f,0.5f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/particles.png"));
        Tessellator.instance.setBrightness(0x00F0);
        
        renderParticle(par2, par3, par4, par5, par6, par7);
        
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glDisable(GL11.GL_BLEND);
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
    }
}
