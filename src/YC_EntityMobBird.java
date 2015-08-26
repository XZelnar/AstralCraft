package net.minecraft.src;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class YC_EntityMobBird extends EntityAnimal
{

	public YC_EntityMobBird(World par1World) {
		super(par1World);
        this.texture = "/mob/pig.png";
		this.moveSpeed = 0.5F;
		experienceValue = 1;
		Init();
	}
	
	public YC_EntityMobBird(World w, float x, float y, float z)
	{
		super(w);
		setPosition(x, y, z);
		Init();
	}
	
	public void Init()
	{
        //this.tasks.addTask(0, new EntityAIWander(this, moveSpeed));
	}

	@Override
    public boolean isAIEnabled()
    {
        return true;
    }

	@Override
	public int getMaxHealth() {
		return 4;
	}
	
	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}
	
	@Override
	public String getEntityName() {
		return "Bird";
	}

	@Override
	public EntityAgeable createChild(EntityAgeable var1) {
		return null;
	}
	
	
	
	

}
