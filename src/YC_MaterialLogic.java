package net.minecraft.src;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class YC_MaterialLogic extends Material
{
    public YC_MaterialLogic(MapColor par1MapColor)
    {
        super(par1MapColor);
    }

    public boolean isSolid()
    {
        return true;
    }

    /**
     * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
     */
    public boolean getCanBlockGrass()
    {
        return false;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement()
    {
        return true;
    }
    
    @Override
    public boolean isOpaque() {
    	return false;
    }
    
    
}
