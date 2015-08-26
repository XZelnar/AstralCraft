package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.BlockProxy;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import static net.minecraftforge.common.ForgeDirection.*;

public class YC_BlockCrystalizer extends BlockContainer implements BlockProxy
{
    private Random random = new Random();

    protected YC_BlockCrystalizer(int par1)
    {
        super(par1, Material.rock);
        //func_94332_a(YC_ClientProxy.textureMap);
        this.setCreativeTab(CreativeTabs.tabMisc);
        setHardness(1);
        setResistance(5);
        setTickRandomly(true);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("blockIron");
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    //public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    //{
    //    return true;
    //}

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	YC_TileEntityBlockCrystalizer var7 = (YC_TileEntityBlockCrystalizer)par1World.getBlockTileEntity(par2, par3, par4);

        if (var7 != null)
        {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
            {
                ItemStack var9 = var7.getStackInSlot(var8);

                if (var9 != null)
                {
                    float var10 = this.random.nextFloat() * 0.8F + 0.1F;
                    float var11 = this.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem var14;

                    for (float var12 = this.random.nextFloat() * 0.8F + 0.1F; var9.stackSize > 0; par1World.spawnEntityInWorld(var14))
                    {
                        int var13 = this.random.nextInt(21) + 10;

                        if (var13 > var9.stackSize)
                        {
                            var13 = var9.stackSize;
                        }

                        var9.stackSize -= var13;
                        var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                        float var15 = 0.05F;
                        var14.motionX = (double)((float)this.random.nextGaussian() * var15);
                        var14.motionY = (double)((float)this.random.nextGaussian() * var15 + 0.2F);
                        var14.motionZ = (double)((float)this.random.nextGaussian() * var15);

                        if (var9.hasTagCompound())
                        {
                        	//func_92014_d() == getitem
                            var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                        }
                    }
                }
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Crystalizer";
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	YC_TileEntityBlockCrystalizer w = (YC_TileEntityBlockCrystalizer)world.getBlockTileEntity(x, y, z);
	    PacketDispatcher.sendPacketToPlayer(w.getDescriptionPacket(), (Player) player);
		//YC_GUIBlockDetector g = new YC_GUIBlockDetector(ModLoader.getMinecraftInstance().thePlayer.inventory, w);
		player.openGui(YC_Mod.instance, YC_Mod.c_blockCrystalizerGuiID, world, x, y, z);
		
        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new YC_TileEntityBlockCrystalizer();
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new YC_TileEntityBlockCrystalizer();
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_crystalizerRenderID;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World par1World, int par2, int par3,
    		int par4, Random par5Random) {

        //Minecraft.getMinecraft().effectRenderer.addEffect(
        //		(EntityFX)new YC_EntityFXAstralAura(par1World, par2, par3, par4, 0, 0, 0), 
        //		null);
    }
    
    @Override
    public int tickRate(World par1World) {
    	return 1;
    }
    
    
}
