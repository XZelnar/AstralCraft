package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class YC_BlockRainStopper extends Block
{
    public YC_BlockRainStopper(int par1)
    {
        super(par1, Material.clay);
        this.setCreativeTab(CreativeTabs.tabMisc);
        //setBlockUnbreakable();
        setLightValue(1f);
        setBlockBounds(0, 0, 0, 1, 1, 1);
    } 
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("YC_transparent");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World,
    		int par2, int par3, int par4) {
    	return null;
    }
    
    @Override
    public String getUnlocalizedName() {
    	return "Rain stopper";
    }
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return YC_Mod.c_rainStopperRenderID;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int tickRate(World par1World) {
		return 1;
	}
	
	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		YC_TileEntityBlockRainStopper te = new YC_TileEntityBlockRainStopper();
		return te;
	}
	
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (par1World.getWorldInfo().isRaining() && par1World.canBlockSeeTheSky(par2, par3, par4))
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			return true;
		}
		return false;
	}
	
	
	

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4,
			Random par5Random) {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			YC_ClientProxy.SpawnSymbolInputParticle(par1World, par2,par3,par4);
		
		if (!par1World.isRemote)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, blockID, tickRate(par1World));
			int md = par1World.getBlockMetadata(par2, par3, par4);
			if (md < 2)
				if (HasCrystalCount(par1World, par2, par3, par4, 1))
				{
					DecrCrystalCount(par1World, par2, par3, par4, 1);
					par1World.setBlockMetadataWithNotify(par2, par3, par4, md+1, 3);
				}
		}
	}

	public Packet getDescriptionPacket(int xCoord, int yCoord, int zCoord) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(2);
			outputStream.writeInt(xCoord);
			outputStream.writeInt(yCoord);
			outputStream.writeInt(zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_FX";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}
	
	public boolean HasCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
		int countt = 0;
		
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-0.1f, par3, par4-0.1f, par2+1.1f, par3+0.7f, par4+1.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
			if (((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				countt+=((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10).stackSize;
				if (countt>=count) return true;
			}
    	}
    	
    	return false;
	}
	
	public void DecrCrystalCount(World par1World, int par2, int par3, int par4, int count)
	{
    	List a = par1World.getEntitiesWithinAABB(EntityItem.class, 
    			AxisAlignedBB.getBoundingBox(par2-0.1f, par3, par4-0.1f, par2+1.1f, par3+0.7f, par4+1.1f));

    	for(int i = 0; i<a.size(); i++)
    	{
    		ItemStack is = ((EntityItem)a.get(i)).getDataWatcher().getWatchableObjectItemStack(10);
			if (is.itemID == YC_Mod.b_astralCrystals.blockID && !((EntityItem)a.get(i)).isDead)
			{
				if (is.stackSize<=count)
				{
					count -= is.stackSize;
					((EntityItem)a.get(i)).setDead();
				}
				else
				{
					is.stackSize -= count;
				}
				((EntityItem)a.get(i)).setEntityItemStack(is);
				if (count == 0) return;
			}
    	}
	}
	
	
}
