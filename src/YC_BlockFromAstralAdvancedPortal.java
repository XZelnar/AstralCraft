package net.minecraft.src;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class YC_BlockFromAstralAdvancedPortal extends Block
{
    protected YC_BlockFromAstralAdvancedPortal(int par1)
    {
        super(par1, Material.portal);
        //func_94332_a(YC_ClientProxy.textureMap);
        setBlockUnbreakable();
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister) {
    	field_94336_cN = par1IconRegister.func_94245_a("YC_transparent");
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public int getRenderType() {
    	return YC_Mod.c_transparentBlockRenderID;
    }
    
    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
    	return false;
    }
    
    @Override
    public boolean isCollidable() {
    	return false;
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3,
    		int par4, Entity par5Entity) {
    	if (par1World.isRemote) return;
    	if (!(par5Entity instanceof EntityPlayer)) return;
    	
    	EntityPlayer par3EntityPlayer = (EntityPlayer)par5Entity;
		MinecraftServer mcServer = MinecraftServer.getServer();
		YC_ServerPlayerAstralData d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
		if (d == null)
		{
			if (YC_ServerPlayerAstralDataList.PlayerExists(par3EntityPlayer.username))
			{
				d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
			}
			else
			{
				double ax = ((YC_ServerPlayerAstralDataList.data.size() % 15) * 1600) + 800 + 8;
				double az = ((YC_ServerPlayerAstralDataList.data.size() / 15) * 1600) + 800 + 8;
				if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
				{
					YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
							par3EntityPlayer.posX, par3EntityPlayer.posY, par3EntityPlayer.posZ, 
							0, ax, 82, az);
				}
				else
				{
					YC_ServerPlayerAstralDataList.Add(par3EntityPlayer.username, 
							0, GetHeightAtPotint(0, 0, mcServer.worldServerForDimension(0))+1.5, 0, 
							0, ax, 82, az);
				}
				YC_ServerPlayerAstralDataList.ClearAllRepeating();
				d = YC_ServerPlayerAstralDataList.GetPlayerData(par3EntityPlayer.username);
			}
		}
		
		double lx = par3EntityPlayer.posX;
		double ly = par3EntityPlayer.posY;
		double lz = par3EntityPlayer.posZ;
		int ld = par3EntityPlayer.dimension;
		if (par3EntityPlayer.dimension != YC_Mod.d_astralDimID)
		{
			d.x = par3EntityPlayer.posX;
			d.y = par3EntityPlayer.posY;
			d.z = par3EntityPlayer.posZ;
			par3EntityPlayer.posX = d.AstralXPos;
			par3EntityPlayer.posY = d.AstralYPos;
			par3EntityPlayer.posZ = d.AstralZPos;
			d.dim = par3EntityPlayer.dimension;
			YC_Variables.SaveAstralTeleporters();
			YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, YC_Mod.d_astralDimID, new YC_Teleporter());
		}
		else
		{
			if (d.dim == YC_Mod.d_astralDimID) d.dim = 0;
			par3EntityPlayer.posX = d.x;
			par3EntityPlayer.posY = d.y;
			par3EntityPlayer.posZ = d.z;
			int dimToTp = d.dim;
			YC_Variables.SaveAstralTeleporters();
			YC_Teleporter.transferPlayerToDimension((EntityPlayerMP)par3EntityPlayer, dimToTp, new YC_Teleporter());
		}
    }
	
	public int GetHeightAtPotint(int x, int z, World w)
	{
		for(int i = 128; i>0; i--)
		{
			if (w.getBlockId(x, i, z)!=0)
				return i;
		}
		return 70;
	}
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
            float f = 0.3625F;
            return AxisAlignedBB.getBoundingBox((float)i, j, (float)k, (float)(i + 1), (float)(j + 0.01), (float)(k + 1));
    }
    
}
