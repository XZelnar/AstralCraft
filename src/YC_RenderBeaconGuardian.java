package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

@SideOnly(Side.CLIENT)
public class YC_RenderBeaconGuardian extends RenderBiped
{
    private ModelBiped field_82434_o;
    private YC_ModelBeaconGuardian field_82432_p;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    private int field_82431_q = 1;

    public YC_RenderBeaconGuardian()
    {
        super(new YC_ModelBeaconGuardian(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
        this.field_82432_p = new YC_ModelBeaconGuardian();
    }

    protected void func_82421_b()
    {
        this.field_82423_g = new YC_ModelBeaconGuardian();
        this.field_82425_h = new YC_ModelBeaconGuardian();
        this.field_82437_k = this.field_82423_g;
        this.field_82435_l = this.field_82425_h;
        this.field_82436_m = new YC_ModelBeaconGuardian();
        this.field_82433_n = new YC_ModelBeaconGuardian();
    }

    protected int func_82429_a(YC_EntityBeaconGuardian par1YC_EntityBeaconGuardian, int par2, float par3)
    {
        this.func_82427_a(par1YC_EntityBeaconGuardian);
        return super.shouldRenderPass(par1YC_EntityBeaconGuardian, par2, par3);
    }

    public void func_82426_a(YC_EntityBeaconGuardian par1YC_EntityBeaconGuardian, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82427_a(par1YC_EntityBeaconGuardian);
        super.doRenderLiving(par1YC_EntityBeaconGuardian, par2, par4, par6, par8, par9);
    }

    protected void func_82428_a(YC_EntityBeaconGuardian par1YC_EntityBeaconGuardian, float par2)
    {
        this.func_82427_a(par1YC_EntityBeaconGuardian);
        super.renderEquippedItems(par1YC_EntityBeaconGuardian, par2);
    }

    private void func_82427_a(YC_EntityBeaconGuardian par1YC_EntityBeaconGuardian)
    {
        this.mainModel = this.field_82434_o;
        this.field_82423_g = this.field_82437_k;
        this.field_82425_h = this.field_82435_l;

        this.modelBipedMain = (ModelBiped)this.mainModel;
    }

    protected void func_82430_a(YC_EntityBeaconGuardian par1YC_EntityBeaconGuardian, float par2, float par3, float par4)
    {
        super.rotateCorpse(par1YC_EntityBeaconGuardian, par2, par3, par4);
    }

    @Override
    protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        this.func_82428_a((YC_EntityBeaconGuardian)par1EntityLiving, par2);
    }

    @Override
    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82426_a((YC_EntityBeaconGuardian)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.func_82429_a((YC_EntityBeaconGuardian)par1EntityLiving, par2, par3);
    }

    @Override
    protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        this.func_82430_a((YC_EntityBeaconGuardian)par1EntityLiving, par2, par3, par4);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82426_a((YC_EntityBeaconGuardian)par1Entity, par2, par4, par6, par8, par9);
    }
}
