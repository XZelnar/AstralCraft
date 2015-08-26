package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class YC_EntityFXAdvAstralPortal extends EntityFX
{
    float particleScaleOverTime;
    
    public int xc=0,yc=0,zc=0;

    public YC_EntityFXAdvAstralPortal(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, int x1, int y1, int z1)
    {
        this(par1World, par2, par4, par6, par8, par10, par12, 2.0F);
    	xc=x1;
    	yc=y1;
    	zc=z1;
    }

    public YC_EntityFXAdvAstralPortal(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, float par14)
    {
        super(par1World, par2+0.5f, par4+0.5f, par6+0.5f, 0.0D, 0.0D, 0.0D);
        motionX = 0;
        motionY = 0;
        motionZ = 0;
        //this.particleScale *= 2.75F;
        //this.particleScale *= par14;
        this.particleScale = 2;
        this.particleScaleOverTime = this.particleScale;
        this.particleMaxAge = 80;
        this.noClip = false;
        this.setParticleTextureIndex(0);
    }

    @Override
    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	boolean t = par1Tessellator.isDrawing;
    	if (t)
    		Tessellator.instance.draw();
        //GL11.glDisable(GL11.GL_TEXTURE_2D);
        //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	//GL11.glColor4f(1f,1f,1f,1f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/YC/particles/AdvAstTel.png"));
        //int brightness = 6291456;
        
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setBrightness(getBrightnessForRender(0.38235426f));
        //if (worldObj.getBlockId(xc, yc, zc) == 1)
        //{
        	Tessellator.instance.setColorRGBA_F(1f,1f,1f,1f);
        //}
        //else
        //{
        //	Tessellator.instance.setColorRGBA_F(1f,1f,1f,0.25f);
        //}
        renderPortal(par2, par3, par4, par5, par6, par7, (int) (((YC_ClientTickHandler.TicksInGame%80)/1.875)%16));
        Tessellator.instance.draw();
        
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, Minecraft.getMinecraft().renderEngine.getTexture("/particles.png"));
        //GL11.glEnable(GL11.GL_TEXTURE_2D);
        //GL11.glDisable(GL11.GL_BLEND);
        if (t)
        	Tessellator.instance.startDrawingQuads();
    }
    
    public void renderParticle(float par2, float par3, float par4, float par5, float par6, float par7)
    {
    	float var8 = (float)this.field_94054_b / 16.0F;//used to be iconIndex%16 / 16f
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
    
    public void renderPortal(float par2, float par3, float par4, float par5, float par6, float par7, int textureIndex)
    {
    	//par4*=2;
    	float var8 = (float)(textureIndex % 4) / 4.0F;
        float var9 = var8 + 0.25F;
        float var10 = (float)(textureIndex / 4) / 4.0F;
        float var11 = var10 + 0.25F;
        float var12 = 0.1F * this.particleScale;
        float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)par2 - interpPosX);
        float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)par2 - interpPosY);
        float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)par2 - interpPosZ);
        float var16 = 1.0F;
        //GL11.glBegin(GL11.GL_QUADS);
        //var8=0;var9=1;var10=0;var11=1;
        //Tessellator.instance.setColorRGBA_F(1,1,1,1);
        //Tessellator.instance.disableColor();
        //Tessellator.instance.setColorOpaque(255, 255, 255);
        VertexParticle((double)(var13 - par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 - par5 * var12 - par7 * var12), (double)var9, (double)var11);
        VertexParticle((double)(var13 - par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 - par5 * var12 + par7 * var12), (double)var9, (double)var10);
        VertexParticle((double)(var13 + par3 * var12 + par6 * var12), (double)(var14 + par4 * var12), (double)(var15 + par5 * var12 + par7 * var12), (double)var8, (double)var10);
        VertexParticle((double)(var13 + par3 * var12 - par6 * var12), (double)(var14 - par4 * var12), (double)(var15 + par5 * var12 - par7 * var12), (double)var8, (double)var11);
        //GL11.glEnd();
    }
    
    public void VertexParticle(double x, double y, double z, double u, double v)
    {
    	Tessellator.instance.addVertexWithUV(x, y, z, u, v);
    	//GL11.glTexCoord2d(u,v);
    	//GL11.glVertex3d(x, y, z);
    }

    public void onUpdate()
    {
    	for (int i = 0; i<YC_ClientProxy.AdvAstTelRemoveCoord.size(); i+=3)
    	{
    		if (    ((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i  )) == xc && 
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+1)) ==yc &&
    				((Integer)YC_ClientProxy.AdvAstTelRemoveCoord.get(i+2)) == zc)
    		{
    			setDead();
    			YC_ClientProxy.AdvAstTelRemoveCoord.remove((int)(i+2));
    			YC_ClientProxy.AdvAstTelRemoveCoord.remove((int)(i+1));
    			YC_ClientProxy.AdvAstTelRemoveCoord.remove((int)(i));
    			i-=3;
    		}
    	}
    	
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
        	//if (worldObj.getBlockId(xc, yc, zc)!=YC_Mod.b_advAstralTeleporter.blockID)
        	//{
        	this.setDead();
        	//}
        	//else
        	//{
        	//	particleAge = 0;
        	//}
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }
    
    
}
