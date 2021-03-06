package net.minecraft.src;

import java.util.List;

import javax.swing.text.html.parser.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class YC_EntityGravityExplosionGrenade extends EntityThrowable
{
    
    public static float MAX_DISTANCE = 5f;
    public static float SPEED_MODIFICATOR = 4f;
    
    
    public YC_EntityGravityExplosionGrenade(World par1World)
    {
        super(par1World);
        ignoreFrustumCheck = true;
    }

    public YC_EntityGravityExplosionGrenade(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
        ignoreFrustumCheck = true;
    }

    public YC_EntityGravityExplosionGrenade(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
        ignoreFrustumCheck = true;
    }

    @Override
    protected float getGravityVelocity()
    {
        return 0.07F;
    }

    @Override
    protected float func_70182_d()
    {
        return 0.7F;
    }

    @Override
    protected float func_70183_g()
    {
        return -20.0F;
    }
    
    //*
    @Override
    public boolean canBeCollidedWith() {
    	return false;
    }//*/
    
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderVec3D(Vec3 par1Vec3) {
    	return true;
    }
    
    

    @Override
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	//System.out.println(posX+";"+posY+";"+posZ);
        //if (!this.worldObj.isRemote)
        {
            //this.worldObj.playAuxSFX(2002, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
            //if (1<2)
            //	{setDead();
            //	return;
            //	}
            this.worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
            
            AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(posX-5, posY-5, posZ-5, posX+5, posY+5, posZ+5);
            List l = worldObj.getEntitiesWithinAABB(EntityLiving.class, bb);
            EntityLiving e = null;
            float t = 0;
            for(int i = 0; i<l.size(); i++)
            {
            	e = (EntityLiving)l.get(i);
            	if (e instanceof EntityPlayer) continue;
            	
            	//e.motionX += 1 / ((e.posX - posX) / MAX_DISTANCE) * SPEED_MODIFICATOR;
            	//e.motionY += 1 / ((e.posY - posY) / MAX_DISTANCE) * SPEED_MODIFICATOR;
            	//e.motionZ += 1 / ((e.posZ - posZ) / MAX_DISTANCE) * SPEED_MODIFICATOR;
            	t=(float) (e.posX - posX);
            	e.motionX += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            	t=(float) (e.posY - posY);
            	e.motionY += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            	e.motionY = e.motionY>0.9 ? 0.9 : e.motionY;
            	e.motionY = e.motionY<-0.9 ? -0.9 : e.motionY;
            	t=(float) (e.posZ - posZ);
            	e.motionZ += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            }

            l = worldObj.getEntitiesWithinAABB(EntityItem.class, bb);
            EntityItem e1 = null;
            for(int i = 0; i<l.size(); i++)
            {
            	e1 = (EntityItem)l.get(i);
            	t=(float) (e1.posX - posX);
            	e1.motionX += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            	t=(float) (e1.posY - posY);
            	e1.motionY += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            	e.motionY = e.motionY>0.9 ? 0.9 : e.motionY;
            	e.motionY = e.motionY<-0.9 ? -0.9 : e.motionY;
            	t=(float) (e1.posZ - posZ);
            	e1.motionZ += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR;
            }

            this.setDead();
        }/*
        else
        {
        	EntityLiving e = Minecraft.getMinecraft().thePlayer;
        	float t=(float) (e.posX - posX);
        	e.motionX += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR/2;
        	t=(float) (e.posY - posY);
        	e.motionY += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR/2;
        	t=(float) (e.posZ - posZ);
        	e.motionZ += 1f / (float)(Math.signum(t)*(Math.abs(t)+0.5)) / MAX_DISTANCE * SPEED_MODIFICATOR/2;
        }//*/
    }
}
