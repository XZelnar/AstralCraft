package net.minecraft.src;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class YC_ModelBeaconGuardian extends ModelBiped
{
  
	ModelRenderer BipedLeftLeg;
	ModelRenderer BipedRightLeg;
	//ModelRenderer BipedHead;
	
	public YC_ModelBeaconGuardian() {
		textureWidth = 128;
		textureHeight = 64;
/*
		BipedHead = new ModelRenderer(this, 104, 0);
		BipedHead.addBox(-3F, -6F, -3F, 6, 6, 6);
		BipedHead.setRotationPoint(0F, -18F, 0F);
		BipedHead.setTextureSize(128, 64);
		BipedHead.mirror = true;
		setRotation(bipedHead, 0F, 0F, 0F);
//*/		
		bipedBody = new ModelRenderer(this, 0, 0);
		bipedBody.addBox(-6F, 0F, -4F, 12, 24, 8);
		bipedBody.setRotationPoint(0F, -18F, 0F);
		bipedBody.setTextureSize(128, 64);
		bipedBody.mirror = true;
		setRotation(bipedBody, 0F, 0F, 0F);
		
		BipedLeftLeg = new ModelRenderer(this, 112, 42);
		BipedLeftLeg.addBox(0F, 0F, -2F, 4, 18, 4);
		BipedLeftLeg.setRotationPoint(2F, 6F, 0F);
		BipedLeftLeg.setTextureSize(128, 64);
		BipedLeftLeg.mirror = true;
		setRotation(BipedLeftLeg, 0F, 0F, 0F);
		
		BipedRightLeg = new ModelRenderer(this, 96, 42);
		BipedRightLeg.addBox(-4F, 0F, -2F, 4, 18, 4);
		BipedRightLeg.setRotationPoint(-2F, 6F, 0F);
		BipedRightLeg.setTextureSize(128, 64);
		BipedRightLeg.mirror = true;
		setRotation(BipedRightLeg, 0F, 0F, 0F);
		
		bipedLeftArm = new ModelRenderer(this, 113, 12);
		bipedLeftArm.addBox(0F, 0F, -2F, 4, 20, 4);
		bipedLeftArm.setRotationPoint(6F, -17F, 0F);
		bipedLeftArm.setTextureSize(128, 64);
		bipedLeftArm.mirror = true;
		setRotation(bipedLeftArm, 0F, 0F, -0.0349066F);
		
		bipedRightArm = new ModelRenderer(this, 97, 12);
		bipedRightArm.addBox(-4F, 0F, -2F, 4, 20, 4);
		bipedRightArm.setRotationPoint(-6F, -17F, 0F);
		bipedRightArm.setTextureSize(128, 64);
		bipedRightArm.mirror = true;
		setRotation(bipedRightArm, 0F, 0F, 0.0349066F);
	}
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    //super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    //BipedHead.render(f5);
    bipedBody.render(f5);
    BipedLeftLeg.render(f5);
    BipedRightLeg.render(f5);
    bipedLeftArm.render(f5);
    bipedRightArm.render(f5);
    
    RenderWeapon(f5);
    RenderHead(f5);
  }
  
	public void RenderWeapon(float par1) {
		GL11.glPushMatrix();
		GL11.glTranslatef(-6 * par1, -17 * par1, 0);

		if (bipedRightArm.rotateAngleZ != 0.0F) GL11.glRotatef(bipedRightArm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
		if (bipedRightArm.rotateAngleY != 0.0F) GL11.glRotatef(bipedRightArm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		if (bipedRightArm.rotateAngleX != 0.0F) GL11.glRotatef(30+bipedRightArm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);

		GL11.glTranslatef(-0.5f * par1, 25 * par1, -9 * par1);
		GL11.glRotatef(180f, 0, 0, 1);
		YC_Mod.m_BeaconGuardianWeapon.DrawWOTranslationAndWOBrightnessReset();

		GL11.glPopMatrix();
	}

	public void RenderHead(float par1) {
		GL11.glPushMatrix();

		GL11.glTranslatef(0 * par1, -18 * par1, 0 * par1);
		if (bipedHead.rotateAngleZ != 0.0F) GL11.glRotatef(bipedHead.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
		if (bipedHead.rotateAngleY != 0.0F) GL11.glRotatef(bipedHead.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
		if (bipedHead.rotateAngleX != 0.0F) GL11.glRotatef(bipedHead.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);

		GL11.glTranslatef(-4f * par1, 0 * par1, -4 * par1);
		GL11.glRotatef(180f, 1, 0, 0);
		YC_Mod.m_BeaconGuardianHead.DrawWOTranslationAndWOBrightnessReset();

		GL11.glPopMatrix();
	}
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity e)
  {
      super.setRotationAngles(f, f1, f2, f3, f4, f5, e);
	  setRotation(BipedLeftLeg, bipedLeftLeg.rotateAngleX, bipedLeftLeg.rotateAngleY, bipedLeftLeg.rotateAngleZ);
	  setRotation(BipedRightLeg, bipedRightLeg.rotateAngleX, bipedRightLeg.rotateAngleY, bipedRightLeg.rotateAngleZ);
	  //setRotation(BipedHead, bipedHead.rotateAngleX, bipedHead.rotateAngleY, bipedHead.rotateAngleZ);
  }

}
