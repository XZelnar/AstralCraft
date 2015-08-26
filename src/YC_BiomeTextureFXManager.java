package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class YC_BiomeTextureFXManager {
	// cycle = 200 ticks (10 seconds in real life)
	public static int pathsCount = 8;//basically number of fuzes
	static byte[][][] paths = new byte[pathsCount][16][2];//pathCount; 16 - 16 blocks; 2 - index, direction
	public static byte[] delays = new byte[pathsCount];
	public static Random rand = new Random();
	

	private static int i;
	public static void OnTick()
	{
		if (!YC_Options.AllowTexturesFX) return;
		if (YC_ClientProxy.biomeTexturesFX.length < 60) return;
		int ticks = YC_ClientTickHandler.TicksInGame % 256;//convert to local ticks
		//int ticks = YC_ClientTickHandler.TicksInGame % 64;//convert to local ticks
		//ticks/=2;
		//int c = 1;
		//ticks = ticks % (256 / c);

		//int i1 = ticks/16;
		//int i2 = (ticks<YC_TextureFXBiome.fuzeSize ? 256 + ticks - YC_TextureFXBiome.fuzeSize : ticks - YC_TextureFXBiome.fuzeSize)/16;
		int i1 = ticks/(16);
		int i2 = (ticks<YC_TextureFXBiome.fuzeSize ? 256 + ticks - YC_TextureFXBiome.fuzeSize : ticks - YC_TextureFXBiome.fuzeSize)/(16);
		int it = 0;

		for(i = 0; i<pathsCount; i++)
		{
			it = i1-delays[i]<0?16+i1-delays[i]:i1-delays[i];
			YC_ClientProxy.biomeTexturesFX[paths[i][it][0]].Update(true,paths[i][it][1]);
			if (i1 != i2)
			{
				it = i2-delays[i]<0?16+i2-delays[i]:i2-delays[i];
				YC_ClientProxy.biomeTexturesFX[paths[i][it][0]].Update(false,paths[i][it][1]);
			}
		}
		
		if (ticks % 2 == 0)
		{
			int ind = rand.nextInt(64);
			if (YC_ClientProxy.biomeTexturesFX[ind].LightState==250)
			{
				YC_ClientProxy.biomeTexturesFX[ind].BlinkLight((byte) (rand.nextInt(15)+1), (byte) (rand.nextInt(15)+1));
			}
			ind = rand.nextInt(64);
			if (YC_ClientProxy.biomeTexturesFX[ind].LightState==250)
			{
				YC_ClientProxy.biomeTexturesFX[ind].BlinkLight((byte) (rand.nextInt(15)+1), (byte) (rand.nextInt(15)+1));
			}
		}
		for (i = 0; i<64; i++)
		{
			YC_ClientProxy.biomeTexturesFX[i].LightUpdate();
		}
	}
	
	public static void InitPaths()
	{
		delays[0] = 0;
		delays[1] = 3;
		delays[2] = 6;//together 1
		delays[3] = 9;
		delays[4] = 6;//together 1
		delays[5] = 2;
		delays[6] = 12;//together 2
		delays[7] = 12;//together 2
		//==============================================0=======================================
		paths[0][0][0] = 0 + 2*8;//{0,2}
		paths[0][1][0] = 1 + 2*8;//{1,2}
		paths[0][2][0] = 1 + 3*8;//{1,3}
		paths[0][3][0] = 1 + 4*8;//{1,4}
		paths[0][4][0] = 1 + 5*8;//{1,5}
		paths[0][5][0] = 1 + 6*8;//{1,6}
		paths[0][6][0] = 2 + 6*8;//{2,6}
		paths[0][7][0] = 3 + 6*8;//{3,6}
		paths[0][8][0] = 4 + 6*8;//{4,6}
		paths[0][9][0] = 5 + 6*8;//{5,6}
		paths[0][10][0] = 5 + 5*8;//{5,5}
		paths[0][11][0] = 5 + 4*8;//{5,4}
		paths[0][12][0] = 5 + 3*8;//{5,3}
		paths[0][13][0] = 5 + 2*8;//{5,2}
		paths[0][14][0] = 6 + 2*8;//{6,2}
		paths[0][15][0] = 7 + 2*8;//{7,2}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[0][0][1] = YC_TextureFXBiome.LeftRight;
		paths[0][1][1] = YC_TextureFXBiome.LeftDown;
		paths[0][2][1] = YC_TextureFXBiome.UpDown;
		paths[0][3][1] = YC_TextureFXBiome.UpDown;
		paths[0][4][1] = YC_TextureFXBiome.UpDown;
		paths[0][5][1] = YC_TextureFXBiome.UpRight;
		paths[0][6][1] = YC_TextureFXBiome.LeftRight;
		paths[0][7][1] = YC_TextureFXBiome.LeftRight;
		paths[0][8][1] = YC_TextureFXBiome.LeftRight;
		paths[0][9][1] = YC_TextureFXBiome.LeftUp;
		paths[0][10][1] = YC_TextureFXBiome.DownUp;
		paths[0][11][1] = YC_TextureFXBiome.DownUp;
		paths[0][12][1] = YC_TextureFXBiome.DownUp;
		paths[0][13][1] = YC_TextureFXBiome.DownRight;
		paths[0][14][1] = YC_TextureFXBiome.LeftRight;
		paths[0][15][1] = YC_TextureFXBiome.LeftRight;
		//==============================================1=======================================
		paths[1][0][0] = 3 + 0*8;//{3,0}
		paths[1][1][0] = 3 + 1*8;//{3,1}
		paths[1][2][0] = 3 + 2*8;//{3,2}
		paths[1][3][0] = 4 + 2*8;//{4,2}
		paths[1][4][0] = 5 + 2*8;//{5,2}
		paths[1][5][0] = 5 + 3*8;//{5,3}
		paths[1][6][0] = 4 + 3*8;//{4,3}
		paths[1][7][0] = 3 + 3*8;//{3,3}
		paths[1][8][0] = 2 + 3*8;//{2,3}
		paths[1][9][0] = 2 + 4*8;//{2,4}
		paths[1][10][0] = 2 + 5*8;//{2,5}
		paths[1][11][0] = 3 + 5*8;//{3,5}
		paths[1][12][0] = 4 + 5*8;//{4,5}
		paths[1][13][0] = 4 + 6*8;//{4,6}
		paths[1][14][0] = 3 + 6*8;//{3,6}
		paths[1][15][0] = 3 + 7*8;//{3,7}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[1][0][1] = YC_TextureFXBiome.UpDown;
		paths[1][1][1] = YC_TextureFXBiome.UpDown;
		paths[1][2][1] = YC_TextureFXBiome.UpRight;
		paths[1][3][1] = YC_TextureFXBiome.LeftRight;
		paths[1][4][1] = YC_TextureFXBiome.LeftDown;
		paths[1][5][1] = YC_TextureFXBiome.UpLeft;
		paths[1][6][1] = YC_TextureFXBiome.RightLeft;
		paths[1][7][1] = YC_TextureFXBiome.RightLeft;
		paths[1][8][1] = YC_TextureFXBiome.RightDown;
		paths[1][9][1] = YC_TextureFXBiome.UpDown;
		paths[1][10][1] = YC_TextureFXBiome.UpRight;
		paths[1][11][1] = YC_TextureFXBiome.LeftRight;
		paths[1][12][1] = YC_TextureFXBiome.LeftDown;
		paths[1][13][1] = YC_TextureFXBiome.UpLeft;
		paths[1][14][1] = YC_TextureFXBiome.RightDown;
		paths[1][15][1] = YC_TextureFXBiome.UpDown;
		//==============================================2=======================================
		paths[2][0][0] = 4 + 7*8;//{4,7}
		paths[2][1][0] = 4 + 6*8;//{4,6}
		paths[2][2][0] = 3 + 6*8;//{3,6}
		paths[2][3][0] = 2 + 6*8;//{2,6}
		paths[2][4][0] = 2 + 7*8;//{2,7}
		paths[2][5][0] = 1 + 7*8;//{1,7}
		paths[2][6][0] = 1 + 6*8;//{1,6}
		paths[2][7][0] = 1 + 5*8;//{1,5}
		paths[2][8][0] = 1 + 4*8;//{1,4}
		paths[2][9][0] = 2 + 4*8;//{2,4}
		paths[2][10][0] = 2 + 3*8;//{2,3}
		paths[2][11][0] = 3 + 3*8;//{3,3}
		paths[2][12][0] = 4 + 3*8;//{4,3}
		paths[2][13][0] = 5 + 3*8;//{5,3}
		paths[2][14][0] = 6 + 3*8;//{6,3}
		paths[2][15][0] = 7 + 3*8;//{7,3}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[2][0][1] = YC_TextureFXBiome.DownUp;
		paths[2][1][1] = YC_TextureFXBiome.DownLeft;
		paths[2][2][1] = YC_TextureFXBiome.RightLeft;
		paths[2][3][1] = YC_TextureFXBiome.RightDown;
		paths[2][4][1] = YC_TextureFXBiome.UpLeft;
		paths[2][5][1] = YC_TextureFXBiome.RightUp;
		paths[2][6][1] = YC_TextureFXBiome.DownUp;
		paths[2][7][1] = YC_TextureFXBiome.DownUp;
		paths[2][8][1] = YC_TextureFXBiome.DownRight;
		paths[2][9][1] = YC_TextureFXBiome.LeftUp;
		paths[2][10][1] = YC_TextureFXBiome.DownRight;
		paths[2][11][1] = YC_TextureFXBiome.LeftRight;
		paths[2][12][1] = YC_TextureFXBiome.LeftRight;
		paths[2][13][1] = YC_TextureFXBiome.LeftRight;
		paths[2][14][1] = YC_TextureFXBiome.LeftRight;
		paths[2][15][1] = YC_TextureFXBiome.LeftRight;
		//==============================================3=======================================
		paths[3][0][0] = 0 + 3*8;//{0,3}
		paths[3][1][0] = 0 + 4*8;//{0,4}
		paths[3][2][0] = 0 + 5*8;//{0,5}
		paths[3][3][0] = 1 + 5*8;//{1,5}
		paths[3][4][0] = 1 + 4*8;//{1,4}
		paths[3][5][0] = 2 + 4*8;//{2,4}
		paths[3][6][0] = 3 + 4*8;//{3,4}
		paths[3][7][0] = 4 + 4*8;//{4,4}
		paths[3][8][0] = 5 + 4*8;//{5,4}
		paths[3][9][0] = 5 + 5*8;//{5,5}
		paths[3][10][0] = 6 + 5*8;//{6,5}
		paths[3][11][0] = 7 + 5*8;//{7,5}
		paths[3][12][0] = 7 + 4*8;//{7,4}
		paths[3][13][0] = 6 + 4*8;//{6,4}
		paths[3][14][0] = 6 + 3*8;//{6,3}
		paths[3][15][0] = 7 + 3*8;//{7,3}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[3][0][1] = YC_TextureFXBiome.LeftDown;
		paths[3][1][1] = YC_TextureFXBiome.UpDown;
		paths[3][2][1] = YC_TextureFXBiome.UpRight;
		paths[3][3][1] = YC_TextureFXBiome.LeftUp;
		paths[3][4][1] = YC_TextureFXBiome.DownRight;
		paths[3][5][1] = YC_TextureFXBiome.LeftRight;
		paths[3][6][1] = YC_TextureFXBiome.LeftRight;
		paths[3][7][1] = YC_TextureFXBiome.LeftRight;
		paths[3][8][1] = YC_TextureFXBiome.LeftDown;
		paths[3][9][1] = YC_TextureFXBiome.UpRight;
		paths[3][10][1] = YC_TextureFXBiome.LeftRight;
		paths[3][11][1] = YC_TextureFXBiome.LeftUp;
		paths[3][12][1] = YC_TextureFXBiome.DownLeft;
		paths[3][13][1] = YC_TextureFXBiome.RightUp;
		paths[3][14][1] = YC_TextureFXBiome.DownRight;
		paths[3][15][1] = YC_TextureFXBiome.LeftRight;
		//==============================================4=======================================
		paths[4][0][0] = 0 + 3*8;//{0,3}
		paths[4][1][0] = 1 + 3*8;//{1,3}
		paths[4][2][0] = 1 + 2*8;//{1,2}
		paths[4][3][0] = 1 + 1*8;//{1,1}
		paths[4][4][0] = 2 + 1*8;//{2,1}
		paths[4][5][0] = 3 + 1*8;//{3,1}
		paths[4][6][0] = 4 + 1*8;//{4,1}
		paths[4][7][0] = 4 + 2*8;//{4,2}
		paths[4][8][0] = 5 + 2*8;//{5,2}
		paths[4][9][0] = 6 + 2*8;//{6,2}
		paths[4][10][0] = 6 + 1*8;//{6,1}
		paths[4][11][0] = 7 + 1*8;//{7,1}
		paths[4][12][0] = 7 + 0*8;//{7,0}
		paths[4][13][0] = 6 + 0*8;//{6,0}
		paths[4][14][0] = 5 + 0*8;//{5,0}
		paths[4][15][0] = 4 + 0*8;//{4,0}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[4][0][1] = YC_TextureFXBiome.LeftRight;
		paths[4][1][1] = YC_TextureFXBiome.LeftUp;
		paths[4][2][1] = YC_TextureFXBiome.DownUp;
		paths[4][3][1] = YC_TextureFXBiome.DownRight;
		paths[4][4][1] = YC_TextureFXBiome.LeftRight;
		paths[4][5][1] = YC_TextureFXBiome.LeftRight;
		paths[4][6][1] = YC_TextureFXBiome.LeftDown;
		paths[4][7][1] = YC_TextureFXBiome.UpRight;
		paths[4][8][1] = YC_TextureFXBiome.LeftRight;
		paths[4][9][1] = YC_TextureFXBiome.LeftUp;
		paths[4][10][1] = YC_TextureFXBiome.DownRight;
		paths[4][11][1] = YC_TextureFXBiome.LeftUp;
		paths[4][12][1] = YC_TextureFXBiome.DownLeft;
		paths[4][13][1] = YC_TextureFXBiome.RightLeft;
		paths[4][14][1] = YC_TextureFXBiome.RightLeft;
		paths[4][15][1] = YC_TextureFXBiome.RightUp;
		//==============================================5=======================================
		paths[5][0][0] = 0 + 6*8;//{0,6}
		paths[5][1][0] = 0 + 7*8;//{0,7}
		paths[5][2][0] = 7 + 7*8;//{7,7}
		paths[5][3][0] = 6 + 7*8;//{6,7}
		paths[5][4][0] = 5 + 7*8;//{5,7}
		paths[5][5][0] = 4 + 7*8;//{4,7}
		paths[5][6][0] = 4 + 6*8;//{4,6}
		paths[5][7][0] = 4 + 5*8;//{4,5}
		paths[5][8][0] = 5 + 5*8;//{5,5}
		paths[5][9][0] = 5 + 4*8;//{5,4}
		paths[5][10][0] = 5 + 3*8;//{5,3}
		paths[5][11][0] = 6 + 3*8;//{6,3}
		paths[5][12][0] = 6 + 4*8;//{6,4}
		paths[5][13][0] = 6 + 5*8;//{6,5}
		paths[5][14][0] = 6 + 6*8;//{6,6}
		paths[5][15][0] = 7 + 6*8;//{7,6}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[5][0][1] = YC_TextureFXBiome.LeftDown;
		paths[5][1][1] = YC_TextureFXBiome.UpLeft;
		paths[5][2][1] = YC_TextureFXBiome.RightLeft;
		paths[5][3][1] = YC_TextureFXBiome.RightLeft;
		paths[5][4][1] = YC_TextureFXBiome.RightLeft;
		paths[5][5][1] = YC_TextureFXBiome.RightUp;
		paths[5][6][1] = YC_TextureFXBiome.DownUp;
		paths[5][7][1] = YC_TextureFXBiome.DownRight;
		paths[5][8][1] = YC_TextureFXBiome.LeftUp;
		paths[5][9][1] = YC_TextureFXBiome.DownUp;
		paths[5][10][1] = YC_TextureFXBiome.DownRight;
		paths[5][11][1] = YC_TextureFXBiome.LeftDown;
		paths[5][12][1] = YC_TextureFXBiome.UpDown;
		paths[5][13][1] = YC_TextureFXBiome.UpDown;
		paths[5][14][1] = YC_TextureFXBiome.UpRight;
		paths[5][15][1] = YC_TextureFXBiome.LeftRight;
		//==============================================6=======================================
		paths[6][0][0] = 0 + 0*8;//{0,0}
		paths[6][1][0] = 0 + 1*8;//{0,1}
		paths[6][2][0] = 0 + 2*8;//{0,2}
		paths[6][3][0] = 1 + 2*8;//{1,2}
		paths[6][4][0] = 1 + 1*8;//{1,1}
		paths[6][5][0] = 1 + 0*8;//{1,0}
		paths[6][6][0] = 2 + 0*8;//{2,0}
		paths[6][7][0] = 2 + 1*8;//{2,1}
		paths[6][8][0] = 2 + 2*8;//{2,2}
		paths[6][9][0] = 2 + 3*8;//{2,3}
		paths[6][10][0] = 2 + 4*8;//{2,4}
		paths[6][11][0] = 2 + 5*8;//{2,5}
		paths[6][12][0] = 1 + 5*8;//{1,5}
		paths[6][13][0] = 1 + 6*8;//{1,6}
		paths[6][14][0] = 1 + 7*8;//{1,7}
		paths[6][15][0] = 2 + 7*8;//{2,7}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[6][0][1] = YC_TextureFXBiome.LeftDown;
		paths[6][1][1] = YC_TextureFXBiome.UpDown;
		paths[6][2][1] = YC_TextureFXBiome.UpRight;
		paths[6][3][1] = YC_TextureFXBiome.LeftUp;
		paths[6][4][1] = YC_TextureFXBiome.DownUp;
		paths[6][5][1] = YC_TextureFXBiome.DownRight;
		paths[6][6][1] = YC_TextureFXBiome.LeftDown;
		paths[6][7][1] = YC_TextureFXBiome.UpDown;
		paths[6][8][1] = YC_TextureFXBiome.UpDown;
		paths[6][9][1] = YC_TextureFXBiome.UpDown;
		paths[6][10][1] = YC_TextureFXBiome.UpDown;
		paths[6][11][1] = YC_TextureFXBiome.UpLeft;
		paths[6][12][1] = YC_TextureFXBiome.RightDown;
		paths[6][13][1] = YC_TextureFXBiome.UpDown;
		paths[6][14][1] = YC_TextureFXBiome.UpRight;
		paths[6][15][1] = YC_TextureFXBiome.LeftDown;
		//==============================================7=======================================
		paths[7][0][0] = 2 + 0*8;//{2,0}
		paths[7][1][0] = 3 + 0*8;//{3,0}
		paths[7][2][0] = 3 + 1*8;//{3,1}
		paths[7][3][0] = 3 + 2*8;//{3,2}
		paths[7][4][0] = 3 + 3*8;//{3,3}
		paths[7][5][0] = 4 + 3*8;//{4,3}
		paths[7][6][0] = 5 + 3*8;//{5,3}
		paths[7][7][0] = 5 + 2*8;//{5,2}
		paths[7][8][0] = 5 + 1*8;//{5,1}
		paths[7][9][0] = 4 + 1*8;//{4,1}
		paths[7][10][0] = 4 + 0*8;//{4,0}
		paths[7][11][0] = 5 + 0*8;//{5,0}
		paths[7][12][0] = 6 + 0*8;//{6,0}
		paths[7][13][0] = 6 + 1*8;//{6,1}
		paths[7][14][0] = 7 + 1*8;//{7,1}
		paths[7][15][0] = 7 + 0*8;//{7,0}
		//- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
		paths[7][0][1] = YC_TextureFXBiome.UpRight;
		paths[7][1][1] = YC_TextureFXBiome.LeftDown;
		paths[7][2][1] = YC_TextureFXBiome.UpDown;
		paths[7][3][1] = YC_TextureFXBiome.UpDown;
		paths[7][4][1] = YC_TextureFXBiome.UpRight;
		paths[7][5][1] = YC_TextureFXBiome.LeftRight;
		paths[7][6][1] = YC_TextureFXBiome.LeftUp;
		paths[7][7][1] = YC_TextureFXBiome.DownUp;
		paths[7][8][1] = YC_TextureFXBiome.DownLeft;
		paths[7][9][1] = YC_TextureFXBiome.RightUp;
		paths[7][10][1] = YC_TextureFXBiome.DownRight;
		paths[7][11][1] = YC_TextureFXBiome.LeftRight;
		paths[7][12][1] = YC_TextureFXBiome.LeftDown;
		paths[7][13][1] = YC_TextureFXBiome.UpRight;
		paths[7][14][1] = YC_TextureFXBiome.LeftUp;
		paths[7][15][1] = YC_TextureFXBiome.DownRight;
		
	}
}
