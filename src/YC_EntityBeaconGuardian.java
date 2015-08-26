package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Calendar;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class YC_EntityBeaconGuardian extends EntityMob
{

    public YC_EntityBeaconGuardian(World par1World)
    {
        super(par1World);
        func_98053_h(false);//can pick up loot
        height = 3f;
        this.texture = "/mob/zombie.png";
        this.moveSpeed = 0.23F;
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
    }
    
    public int blockPickupTimeLeft = 0;
    public static int BLOCK_PICKUP_COOLDOWN = 60;

    @Override
    public float getSpeedModifier()
    {
        return 1.75f;
    }
    
    @Override
    protected boolean canDespawn() {
    	return false;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public String getTexture()
    {
        return "/YC/beaconGuardian.png";
    }

    @Override
    public int getMaxHealth()
    {
        return 75;
    }
    
    @Override
    protected boolean isAIEnabled()
    {
        return true;
    }

    @Override
    public boolean isChild()
    {
    	return false;
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.61999998688697815D;

        if (this.isPotionActive(Potion.jump))
        {
            this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        if (this.isSprinting())
        {
            float var1 = this.rotationYaw * 0.017453292F;
            this.motionX -= (double)(MathHelper.sin(var1) * 0.2F);
            this.motionZ += (double)(MathHelper.cos(var1) * 0.2F);
        }

        this.isAirBorne = true;
        ForgeHooks.onLivingJump(this);
    }
    
    public static int tpx,tpy,tpz;
	float rotRad;
	
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        fallDistance = 0;
        if (blockPickupTimeLeft > 0)
        	blockPickupTimeLeft = 0;
        
        if (!worldObj.isRemote && blockPickupTimeLeft == 0)
        {
            rotRad = (rotationYawHead+90) * (float)Math.PI / 180f;
            double t = 0;
            
        	float x = (float)Math.floor(posX);
        	if ((t = Math.cos(rotRad))>0.5) x+=1;
        	if (t<-0.5) x-=1;
        	float z = (float)Math.floor(posZ);
        	if ((t=Math.sin(rotRad))>0.5) z+=1;
        	if (t<-0.5) z-=1;
        	
        	
            int id;
            if ((id = worldObj.getBlockId((int)x, (int)posY+1, (int)z)) != 0 && !worldObj.blockHasTileEntity((int)x, (int)posY+1,(int)z) && !IsBlockBlacklisted(id))
            {
            	if (ReplaceBlock((int)x, (int)posY+1,(int)z,id))
            	{
            		blockPickupTimeLeft = BLOCK_PICKUP_COOLDOWN;
            		return;
            	}
            }
            if ((id = worldObj.getBlockId((int)x, (int)posY+2, (int)z)) != 0 && !worldObj.blockHasTileEntity((int)x, (int)posY+2,(int)z) && !IsBlockBlacklisted(id))
            {
            	if (ReplaceBlock((int)x, (int)posY+2,(int)z,id))
            	{
            		blockPickupTimeLeft = BLOCK_PICKUP_COOLDOWN;
            		return;
            	}
            }
        }
    }
    
    public static int[] blacklist;
    public boolean IsBlockBlacklisted(int id)
    {
    	if (id == Block.bedrock.blockID) return true;
    	for (int i = 0; i < blacklist.length; i++)
    		if (id == blacklist[i]) return true;
    	return false;
    }
    
    public boolean ReplaceBlock(int x, int y, int z, int id)
    {
        double t = 0;
        
    	float x1 = (float)Math.floor(posX);
    	if ((t = Math.cos(rotRad))>0.5) x1-=1;
    	if (t<-0.5) x1+=1;
    	float z1 = (float)Math.floor(posZ);
    	if ((t=Math.sin(rotRad))>0.5) z1-=1;
    	if (t<-0.5) z1+=1;

        if (worldObj.getBlockId((int)x1, y-1, (int)z1) == 0)
        {
        	worldObj.setBlockAndMetadataWithNotify((int)x1, y-1, (int)z1, id, 0, 3);
        	worldObj.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
        	return true;
        }
        if (worldObj.getBlockId((int)x1, y, (int)z1) == 0)
        {
        	worldObj.setBlockAndMetadataWithNotify((int)x1, y, (int)z1, id, 0, 3);
        	worldObj.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
        	return true;
        }
        if (worldObj.getBlockId((int)x1, y+1, (int)z1) == 0)
        {
        	worldObj.setBlockAndMetadataWithNotify((int)x1, y+1, (int)z1, id, 0, 3);
        	worldObj.setBlockAndMetadataWithNotify(x, y, z, 0, 0, 3);
        	return true;
        }
        return false;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return 8;
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.zombie.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.zombie.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.zombie.death";
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    @Override
    protected int getDropItemId()
    {
    	Random r = new Random();
    	int t1 = r.nextInt(4);
    	DropOre(t1);
        int t2 = 0;
        while ((t2 = r.nextInt(4)) == t1) ;
    	DropOre(t2);
        
        return 0;
    }
    
    public void DropOre(int t1)
    {
        switch(t1)
        {
        	case 0: 
        		dropItem(YC_Mod.i_sCogwheel.itemID, 5);
        		break;
        	case 1: 
        		dropItem(YC_Mod.i_sMatrix.itemID, 5);
    			break;
        	case 2: 
        		dropItem(YC_Mod.i_sEngine.itemID, 5);
    			break;
        	default: 
        		dropItem(YC_Mod.i_sChip.itemID, 5);
    			break;
        }
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void dropRareDrop(int par1)
    {
    	entityDropItem(new ItemStack(Item.potion, 1, 8226), 0);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
    }

    @Override    //This method gets called when the entity kills another one.
    public void onKillEntity(EntityLiving par1EntityLiving)
    {
        super.onKillEntity(par1EntityLiving);
    }

    @Override
    public void initCreature()
    {
        func_98053_h(false);//can pick up loot
        super.initCreature();
    }

    @Override    //Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 16)
        {
            this.worldObj.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "mob.zombie.remedy", 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }
}
