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
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import static net.minecraftforge.common.ForgeDirection.*;

public class YC_BlockResearchTable extends BlockContainer implements BlockProxy
{
    private Random random = new Random();
    static Icon top, side;

    protected YC_BlockResearchTable(int par1)
    {
        super(par1, Material.rock);
        setUnlocalizedName("Research table"); 
        this.setCreativeTab(CreativeTabs.tabMisc);
        setHardness(1);
        setResistance(5);
        setTickRandomly(true);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	top = par1IconRegister.func_94245_a("YC_Research table");
    	side = par1IconRegister.func_94245_a("YC_Stone");
    }
    
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	YC_TileEntityBlockResearchTable var7 = (YC_TileEntityBlockResearchTable)par1World.getBlockTileEntity(par2, par3, par4);

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
    	return "Research table";
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	if (world.isRemote) return true;
    	YC_TileEntityBlockResearchTable w = (YC_TileEntityBlockResearchTable)world.getBlockTileEntity(x, y, z);
    	if (w.PlayerName.equals(player.username))
    	{
    		int t = YC_ResearchesDataList.GetIndexByName(player.username);
    		if (t==-1)
    		{
    			YC_ResearchesDataList.AddEmpty(player.username);
        		t = YC_ResearchesDataList.GetIndexByName(player.username);
    		}
    		PacketDispatcher.sendPacketToPlayer(((YC_ResearchesData)YC_ResearchesDataList.players.get(t)).getDescriptionPacket(), (Player) player);
    		player.openGui(YC_Mod.instance, YC_Mod.c_blockWorkbenchGuiID, world, x, y, z);
    	}
    	else
    	{
    		//TODO
    	}
		
        return true;
    }

    public TileEntity createNewTileEntity(World par1World)
    {
        return new YC_TileEntityBlockResearchTable();
    }
    
    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new YC_TileEntityBlockResearchTable();
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
    		EntityLiving par5EntityLiving, ItemStack is) {
    	super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLiving, is);
    	YC_TileEntityBlockResearchTable te = (YC_TileEntityBlockResearchTable) par1World.getBlockTileEntity(par2, par3, par4);
    	te.PlayerName = ((EntityPlayer)par5EntityLiving).username;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
    		int par3, int par4, int par5) {
    	return par5 == 1 ? top : side;
    }
    
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2) {
    	if (par1 == 1)
    	{
    		return top;
    	}
    	else
    	{
    		return side;
    	}
    }
    
    
    
    
    
}
