package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class YC_GUIBlockResearchTable extends GuiContainer
{
    private IInventory upperChestInventory;
    private YC_TileEntityBlockResearchTable tileEntity;
    public int mouseX=0, mouseY=0;
    public int dragX=0, dragY=0;
    public int lastX=0, lastY=0;
    public final int DDAreaWidth = 194, DDAreaHeight = 111, DDDeltaAreaWidth = 140, DDDeltaAreaHeight = 50, DDAreaX = 56, DDAreaY = 6;
    public boolean IsDragDrop = false;
    int var5=0, var6=0;
    boolean LastMouseState = false;
    public int CurElementSelected = -1;
    public int CurElementHighlighted = -1;
    public boolean IsClicked = false;
    
    public static int[] TechLevelYCoord = new int[]{0,63,93,126};//30; 63; 96
    
    public YC_ResearchesData resData = null;

    public GuiButton b_Activate;
    public boolean[] habitat;

    public YC_GUIBlockResearchTable(IInventory par1IInventory, IInventory par2IInventory)
    {
        super(new YC_ContainerBlockResearchTable((InventoryPlayer) par1IInventory, par2IInventory));
        this.upperChestInventory = par1IInventory;
        this.tileEntity = (YC_TileEntityBlockResearchTable) par2IInventory;
        this.allowUserInput = false;
        this.ySize = 215;
        this.xSize = 256;

        b_Activate = new GuiButton(4, 8, 76, 30, 20, "Go!");
        b_Activate.enabled = false;
        this.buttonList.add(b_Activate);
        
        resData = YC_ResearchesDataList.GetPlayerData(Minecraft.getMinecraft().thePlayer.username);
        
    	habitat = getHabitat(Minecraft.getMinecraft().theWorld, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }

    protected void drawGuiContainerForegroundLayer()
    {
        //this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        //this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString(dragX+";"+dragY, 8, this.ySize - 96 + 2, 4210752);
    }

    int prevtext, prevtext2;
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	//System.out.println(habitat[0] + " ; " + habitat[1] + " ; " + habitat[2] + " ; " + habitat[3] + " ; " + habitat[4]);
    	//resData.techLevel = 5;

    	prevtext = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        int var4 = this.mc.renderEngine.getTexture("/gui/YC_Research.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        var5 = (this.width - this.xSize) / 2;
        var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, ySize);
        
        DrawHabitatState();

        int var41 = this.mc.renderEngine.getTexture("/gui/YC_ResearchIcons.png");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var41);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        int f = sr.getScaleFactor();
        GL11.glScissor((var5+DDAreaX)*f, (var6+ySize - (DDAreaY+DDAreaHeight)+1)*f, DDAreaWidth*f, DDAreaHeight*f);
        
        YC_ResearchData d = null;
        for(int i = 0; i<resData.researches.size(); i++)
        {
        	d = (YC_ResearchData) resData.researches.get(i);
        	if (!d.researched)
        	{
        		if (!areHabitatRequirementsMet(d))
        			continue;
        		if (!areRequirementsVisible(d))
        			continue;
        	}
        	//if (((!areHabitatRequirementsMet(d) && !areRequirementsResearched(d)) && !d.researched)) continue;
        	drawNode(d);
        	if (d.RequirementsIDs != null && d.RequirementsIDs.length > 0)
        	{
        		GL11.glDisable(GL11.GL_TEXTURE_2D);
        		GL11.glColor3f(0f, 0f, 0f);
        		GL11.glBegin(GL11.GL_LINES);
        		for(int j = 0; j<d.RequirementsIDs.length; j++)
        		{
        			YC_ResearchData d1 = (YC_ResearchData)resData.researches.get(d.RequirementsIDs[j]);
        			int x1 = d1.x + 11 - dragX;
        			int y1 = d1.y + 22 - dragY;
        			int x2 = d.x + 11 - dragX;
        			int y2 = d.y - dragY;

        			GL11.glVertex2f(x1+var5+DDAreaX, y1+var6+DDAreaY);
        			GL11.glVertex2f(x2+var5+DDAreaX, y2+var6+DDAreaY);
        		}
        		GL11.glEnd();
        		GL11.glColor3f(1f, 1f, 1f);
        		GL11.glEnable(GL11.GL_TEXTURE_2D);
        	}
        }
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/YC_ResearchShadow.png"));
        GL11.glEnable(GL11.GL_BLEND);
        if (resData.techLevel < TechLevelYCoord.length)
        {
        	this.drawTexturedModalRect(var5+DDAreaX, var6+TechLevelYCoord[resData.techLevel]-dragY, 0, 0, 256, 256);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var41);
        
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        
        //Progress bar
        this.drawTexturedModalRect(var5+6, var6+97, 0, 238, 46, 18);
        if (tileEntity.TimeRemaing > 0)
        	this.drawTexturedModalRect(var5+6, var6+97, 46, 238, 
        			(int)(46f - 46f * (float)tileEntity.TimeRemaing / ((float)(resData.researchLevel + 1) * 20 * 
        					YC_Options.ResearchShardTimeValue / YC_Options.ResearchSpeed)), 
        			18);
        printTextProgressBar();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        GL11.glColor3f(1f, 1f, 1f);
        ProcessToolTip();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        
        b_Activate.xPosition = (this.width - this.xSize) / 2 + 24;
        b_Activate.yPosition = (this.height - this.ySize) / 2 + 74;
        b_Activate.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
        
        
        printText();
        
        int var42 = this.mc.renderEngine.getTexture("/gui/YC_ResearchBorder.png");
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var42);
        GL11.glEnable(GL11.GL_BLEND);
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, ySize);
        GL11.glDisable(GL11.GL_BLEND);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevtext);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    	super.drawGuiContainerForegroundLayer(par1, par2);
    	GL11.glPushMatrix();
    	GL11.glTranslated(-guiLeft, -guiTop, 0);
    	GL11.glDisable(GL11.GL_DEPTH_TEST);
        ProcessToolTipHabitat();
    	GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }
    
    public void printText()
    {
    	printString(8,9,"Nature",0x00ff00);
    	printString(37,16,NumberToString(resData.nature),0x00ff00);
    	printString(8,23,"Aquic",0x0000ff);
    	printString(37,30,NumberToString(resData.water),0x0000ff);
    	printString(8,37,"Fire",0xff0000);
    	printString(37,44,NumberToString(resData.fire),0xff0000);
    	printString(8,51,"Ender",0x00786d);
    	printString(37,58,NumberToString(resData.ender),0x00786d);
    }
    
    public void printTextProgressBar()
    {
    	if (tileEntity.ResearchID != -1)
    	{
    		String s1 = String.valueOf(tileEntity.TimeRemaing / 20 / 3600);
    		if (s1.length()<2) s1 = "0" + s1;
    		String s2 = String.valueOf(((tileEntity.TimeRemaing / 20) % 3600) / 60);
    		if (s2.length()<2) s2 = "0" + s2;
    		String s3 = String.valueOf(((tileEntity.TimeRemaing / 20) % 3600) % 60);
    		if (s3.length()<2) s3 = "0" + s3;
    		String s = s1+":"+s2+":"+s3;
        	printString(9,102,s,0x000000);
    	}
    }
    
    public String NumberToString(int n)
    {
    	if (n/10 == 0) return " "+n;
    	return String.valueOf(n);
    }
    
    public void printString(int x, int y, String text, int color)
    {
    	prevtext2 = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevtext);
    	this.fontRenderer.drawString(text, x+var5, y+var6, color);//last = color
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevtext2);
    }
    
    public boolean AreNodeRequirementsMet(YC_ResearchData node)
    {
    	if (node.RequirementsIDs == null) return true;
    	for(int i = 0; i < node.RequirementsIDs.length; i++)
    	{
    		int t = node.RequirementsIDs[i];
    		if (!((YC_ResearchData)resData.researches.get(t)).researched)
    			return false;
    	}
    	return true;
    }
    
    public void drawNode(YC_ResearchData node)
    {
    	GL11.glEnable(GL11.GL_BLEND);
    	if (!node.researched)
    		if (resData.nature>=node.requirements[0] && resData.water>=node.requirements[1] && 
    				resData.fire>=node.requirements[2] && resData.ender>=node.requirements[3] && 
    				resData.techLevel>=node.requirements[4] && AreNodeRequirementsMet(node))
    			GL11.glColor3f(0.66f, 0.66f, 0.66f);
    		else
    			GL11.glColor3f(0.33f, 0.33f, 0.33f);
    	DrawBorder(node.x, node.y);
    	GL11.glColor3f(1f, 1f, 1f);
    	
    	if (node.index == CurElementSelected)
    	{
    		DrawSelection(node.x, node.y, 1);
    	}
    	else
    	{
    		if (node.index == CurElementHighlighted)
    		{
    			DrawSelection(node.x, node.y, 0);
    		}
    	}
    	
    	//1: Not in drawing area
    	if (node.x + 16 < dragX || node.y + 16 < dragY || node.x > dragX+DDAreaWidth || node.y > dragY + DDAreaHeight)
    		return;
    	//2: Inside. Count position and size
    	int x = node.x - dragX + var5 + DDAreaX+3,
    			y = node.y - dragY + var6 + DDAreaY+3,
    			u = node.u,v = node.v,
    			w = 16,h = 16;
    	if (node.x+3<dragX)
    	{
    		w -= dragX - node.x-3;
    		x += (16 - w);
    		u += (16 - w);
    	}
    	if (node.y+2<dragY)
    	{
    		h -= dragY - node.y-2;
    		y += (16 - h);
    		v += (16 - h);
    	}
    	if (node.x+3 + 16>dragX + DDAreaWidth)
    	{
    		w = DDAreaWidth - node.x-3 + dragX;
    	}
    	if (node.y+2 + 16>dragY + DDAreaHeight)
    	{
    		h = DDAreaHeight - node.y-2 + dragY;
    	}

        this.drawTexturedModalRect(x, y, //x,y
        		u, v, //u,v
        		w, h);//w,h
		GL11.glDisable(GL11.GL_BLEND);
    }
    
    public void DrawSelection(int x1, int y1, int t)
    {

    	//1: Not in drawing area
    	if (x1 + 22 < dragX || y1 + 22 < dragY || x1 > dragX+DDAreaWidth || y1 > dragY + DDAreaHeight)
    		return;
    	//2: Inside. Count position and size
    	int x = x1 - dragX + var5 + DDAreaX,
    			y = y1 - dragY + var6 + DDAreaY,
    			u = t == 0 ? 234 : 212,v = 215,
    			w = 22,h = 22;
    	if (x1<dragX)
    	{
    		w -= dragX - x1;
    		x += (22 - w);
    		u += (22 - w);
    	}
    	if (y1<dragY)
    	{
    		h -= dragY - y1;
    		y += (22 - h);
    		v += (22 - h);
    	}
    	if (x1 + 22>dragX + DDAreaWidth)
    	{
    		w = DDAreaWidth - x1 + dragX;
    	}
    	if (y1 + 22>dragY + DDAreaHeight)
    	{
    		h = DDAreaHeight - y1 + dragY;
    	}

        this.drawTexturedModalRect(x, y, //x,y
        		u, v, //u,v
        		w, h);//w,h
    }
    
    public void DrawBorder(int x1, int y1)
    {

    	//1: Not in drawing area
    	if (x1 + 22 < dragX || y1 + 22 < dragY || x1 > dragX+DDAreaWidth || y1 > dragY + DDAreaHeight)
    		return;
    	//2: Inside. Count position and size
    	int x = x1 - dragX + var5 + DDAreaX,
    			y = y1 - dragY + var6 + DDAreaY,
    			u = 0,v = 215,
    			w = 22,h = 22;
    	if (x1<dragX)
    	{
    		w -= dragX - x1;
    		x += (22 - w);
    		u += (22 - w);
    	}
    	if (y1<dragY)
    	{
    		h -= dragY - y1;
    		y += (22 - h);
    		v += (22 - h);
    	}
    	if (x1 + 22>dragX + DDAreaWidth)
    	{
    		w = DDAreaWidth - x1 + dragX;
    	}
    	if (y1 + 22>dragY + DDAreaHeight)
    	{
    		h = DDAreaHeight - y1 + dragY;
    	}

        this.drawTexturedModalRect(x, y, //x,y
        		u, v, //u,v
        		w, h);//w,h
    }
    
    public void DrawHabitatState()
    {
    	//lava lake
    	if (habitat[0]) DrawHabitatActive(216, 133);
    	else DrawHabitatInactive(216, 133);
    	//dark room
    	if (habitat[1]) DrawHabitatActive(216, 150);
    	else DrawHabitatInactive(216, 150);
    	//desert
    	if (habitat[2]) DrawHabitatActive(216, 167);
    	else DrawHabitatInactive(216, 167);
    	//crystals
    	if (habitat[3]) DrawHabitatActive(25, 133);
    	else DrawHabitatInactive(25, 133);
    	//ores
    	if (habitat[4]) DrawHabitatActive(25, 150);
    	else DrawHabitatInactive(25, 150);
    }
    
    public void DrawHabitatActive(int x, int y)
    {
        this.drawTexturedModalRect(var5+x, var6+y, 224, 239, 16, 16);
    }
    
    public void DrawHabitatInactive(int x, int y)
    {
        this.drawTexturedModalRect(var5+x, var6+y, 240, 239, 16, 16);
    }
    		
    
    
    
    
    //0 = lava lake
    //1 = dark room
    //2 = desert (hot biome)
    //3 = crystal field
    //4 = resource field
    public boolean[] getHabitat(World w, int x, int y, int z)
    {
    	//laval lake
    	boolean[] r = new boolean[]{false, false, false, false, false};
    	if (y < 25)
    	{
    		int lava = 0;
    		for(int x1 = x-5; x1 <= x+5; x1++)
    		{
        		for(int y1 = y-3; y1 <= y; y1++)
        		{
            		for(int z1 = z-5; z1 <= z+5; z1++)
            		{
            			if (w.getBlockId(x1, y1, z1) == Block.lavaStill.blockID)
            				lava++;
            			if (lava > 5) break;
            		}
        			if (lava > 5) break;
        		}
    			if (lava > 5) break;
    		}
			if (lava > 5)
			{
				r[0] = true;
			}
    	}
    	
    	//dark room
    	if (w.getBlockLightValue(x+1, y, z) < 4 && 
    		w.getBlockLightValue(x-1, y, z) < 4 && 
    		w.getBlockLightValue(x, y+1, z) < 4 && 
    		w.getBlockLightValue(x, y-1, z) < 4 && 
    		w.getBlockLightValue(x, y, z+1) < 4 && 
    		w.getBlockLightValue(x, y, z-1) < 4) 
    	{
    		r[1] = true;
    	}
    	
    	
    	//desert
    	if (w.getBiomeGenForCoords(x, z).temperature >= 2)
    	{
    		r[2] = true;
    	}
    	
    	
    	//crystal field
    	{
    		int crystals = 0;
    		for(int x1 = x-5; x1 <= x+5; x1++)
    		{
        		for(int y1 = y-3; y1 <= y+3; y1++)
        		{
            		for(int z1 = z-5; z1 <= z+5; z1++)
            		{
            			if (w.getBlockId(x1, y1, z1) == YC_Mod.b_astralCrystals.blockID)
            				crystals++;
            			if (crystals >= 25) break;
            		}
        			if (crystals >= 25) break;
        		}
    			if (crystals >= 25) break;
    		}
			if (crystals >= 25)
			{
				r[3] = true;
			}
    	}
    	
    	
    	//resource field
    	{
    		int ore = 0;
    		for(int x1 = x-5; x1 <= x+5; x1++)
    		{
        		for(int y1 = y-3; y1 <= y+3; y1++)
        		{
            		for(int z1 = z-5; z1 <= z+5; z1++)
            		{
            			if (isOre(w.getBlockId(x1, y1, z1)))
            				ore++;
            			if (ore >= 20) break;
            		}
        			if (ore >= 20) break;
        		}
    			if (ore >= 20) break;
    		}
			if (ore >= 20)
			{
				r[4] = true;
			}
    	}
    	
    	
    	return r;
    }
    
    public boolean isOre(int id)
    {
		return id == Block.oreCoal.blockID || id == Block.oreDiamond.blockID
				|| id == Block.oreGold.blockID || id == Block.oreIron.blockID
				|| id == Block.oreLapis.blockID;
    }
    
    public boolean areHabitatRequirementsMet(YC_ResearchData d)
    {
    	for(int i = 0; i < d.RequiredHabitat.length; i++)
    	{
    		if (!habitat[d.RequiredHabitat[i]]) return false;
    	}
    	return true;
    }
    
    public boolean areRequirementsVisible(YC_ResearchData d)
    {
    	for(int i = 0; i < d.RequirementsIDs.length; i++)
    	{
    		YC_ResearchData d1 = (YC_ResearchData)(resData.researches.get(d.RequirementsIDs[i]));
    		if (!(d1.researched)) {
    		if (!d1.researched && !areHabitatRequirementsMet(d1)) return false;}
    	}
    	return true;
    }
    
    
    
    
    
    
    
    
    
    @Override
    public void handleMouseInput() {
    	boolean tempLastMouseButtonState = LastMouseState;
    	if (org.lwjgl.input.Mouse.isButtonDown(0) != LastMouseState)
    	{
    		LastMouseState = !LastMouseState;
    		if (b_Activate.enabled && mouseX >= b_Activate.xPosition && mouseY >= b_Activate.yPosition && 
    				mouseX < b_Activate.xPosition + 30 && //w
    				mouseY < b_Activate.yPosition + 20 && //h
    				LastMouseState && ! IsDragDrop)//mouse
    		{
    			//tileEntity.ResearchID = CurElementSelected;
    			//tileEntity.TimeRemaing = tileEntity.ResearchLevel * 20 * YC_Options.ResearchShardTimeValue / 
    			//		YC_Options.ResearchSpeed;
    			PacketDispatcher.sendPacketToServer(tileEntity.getDescriptionPacketS(CurElementSelected));
    		}
    		if (LastMouseState)
    		{
    			if (IsDragDrop)
    			{
    				//dragX-=mouseX-lastX;
    				//dragY-=mouseY-lastY;
    				//dragX=dragX<0?0:dragX>DDDeltaAreaWidth?DDDeltaAreaWidth:dragX;
    				//dragY=dragY<0?0:dragY>DDDeltaAreaHeight?DDDeltaAreaHeight:dragY;
    			}
    			else
    			{
    				if (mouseX>DDAreaX+var5 && mouseX<DDAreaX+DDAreaWidth+var5 && mouseY>DDAreaY+var6 && mouseY<DDAreaY+DDAreaHeight+var6) IsDragDrop = true;
    			}
    		}
    		else
    		{
    			IsDragDrop = false;
    		}
    	}
		if (LastMouseState)
		{
			if (IsDragDrop)
			{
				dragX-=mouseX-lastX;
				dragY-=mouseY-lastY;
				dragX=dragX<0?0:dragX>DDDeltaAreaWidth?DDDeltaAreaWidth:dragX;
				dragY=dragY<0?0:dragY>DDDeltaAreaHeight?DDDeltaAreaHeight:dragY;
			}
		}
		
        int var1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int var2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
    	lastX=mouseX;
    	lastY=mouseY;
		mouseX=var1;
		mouseY=var2;
		
		//if (!LastMouseState)
		{
			if (mouseX>DDAreaX+var5 && mouseX<DDAreaX+DDAreaWidth+var5 && mouseY>DDAreaY+var6 && mouseY<DDAreaY+DDAreaHeight+var6)
			{
				int t = resData.GetIndexByCoord(mouseX-var5-DDAreaX+dragX, mouseY-var6-DDAreaY+dragY);
				if (t!=-1)
				{
					if (LastMouseState && !tempLastMouseButtonState)
					{
						YC_ResearchData node = (YC_ResearchData) resData.researches.get(t);
			    		if (!node.researched && resData.nature>=node.requirements[0] && 
			    				resData.water>=node.requirements[1] && 
			    				resData.fire>=node.requirements[2] && 
				    	    	resData.ender>=node.requirements[3] && 
						    	resData.techLevel>=node.requirements[4] && 
			    				AreNodeRequirementsMet(node))
			    		{
			    			IsClicked = true;
			    			CurElementSelected = t;
			    			IsDragDrop = false;
			    		}
					}
				}
				else
				{
					if (LastMouseState && !tempLastMouseButtonState)
					{
						IsClicked = false;
						CurElementSelected = -1;
					}
				}
				CurElementHighlighted = t;
			}
			else
			{
				if (!IsClicked)
				{
					CurElementSelected = -1;
				}
			}
		}
		
		if (CurElementSelected != -1 && tileEntity.ResearchID == -1)
		{
			if (!((YC_ResearchData)resData.researches.get(CurElementSelected)).researched)
			{
				b_Activate.enabled=true;
			}
			else
			{
				b_Activate.enabled=false;
				CurElementSelected = -1;
			}
		}
		else
		{
			b_Activate.enabled=false;
		}
    	super.handleMouseInput();
    }
    
    
    
    
    public void ProcessToolTip()
    {
    	/*
    	resData.techLevel = 5;
    	resData.fire = 20;
    	resData.nature = 20;
    	resData.water = 20;
    	resData.ender = 20;//*/
        if (mouseX< var5+DDAreaX || mouseY < var6+DDAreaY || 
        		mouseX > var5+DDAreaX+DDAreaWidth || mouseY > var6+DDAreaY + DDAreaHeight)
        	return;
        //if (CurElementHighlighted != -1)
        {
            if (CurElementHighlighted != -1)
            {
            	YC_ResearchData d = (YC_ResearchData) resData.researches.get(CurElementHighlighted);
            	if (!d.researched)
            	{
            		if (!areHabitatRequirementsMet(d))
            			return;
            		if (!areRequirementsVisible(d))
            			return;
            	}
            	if (!d.researched)
            	{
            		if (resData.techLevel >= d.requirements[4])
            		{
            			int var44 = this.mc.renderEngine.getTexture("/gui/YC_Tooltip.png");
            			GL11.glBindTexture(GL11.GL_TEXTURE_2D, var44);
            			if (resData.nature>=d.requirements[0] &&	//can research
	    						resData.water>=d.requirements[1] && 
	    						resData.fire>=d.requirements[2] && 
	    	    				resData.ender>=d.requirements[3] && 
	    	    				AreNodeRequirementsMet(d))
            			{
            				DrawToolTip(mouseX, mouseY, d.ToolTipWidth, d.ToolTipHeight);
            				for(int i = 0; i<d.Description.length; i++)
            					printString(mouseX - var5+4, mouseY+4 + 10*i - var6, d.Description[i], 0x000000);
            			}
            			else
            			{
            				DrawToolTip(mouseX, mouseY, 50, 28);
            				printString(mouseX - var5+4, mouseY+4 - var6, NumberToString(d.requirements[2]), 0xff2200);
            				printString(mouseX - var5+32, mouseY+4 - var6, NumberToString(d.requirements[1]), 0x0000ff);
            				printString(mouseX - var5+4, mouseY+14 - var6, NumberToString(d.requirements[0]), 0x00ff00);
            				printString(mouseX - var5+32, mouseY+14 - var6, NumberToString(d.requirements[3]), 0x00786d);
            			}
            		}
            	}
            	else
            	{
            		GL11.glEnable(GL11.GL_BLEND);
            		GL11.glScalef(0.5f, 0.5f, 0.5f);
            		GL11.glColor4f(1f, 1f, 1f, 0.90f);
                    int var44 = this.mc.renderEngine.getTexture(d.ResearchedBG);
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, var44);
                    int w = d.ResearchedWidth/2;
                    if (w + mouseX - var5 - DDAreaX > DDAreaWidth - 4) w = DDAreaWidth - 4 - (mouseX - var5 - DDAreaX);
                    int h = d.ResearchedHeight/2;
                    if (h + mouseY - var6 - DDAreaY > DDAreaHeight - 4) h = DDAreaHeight - 4 - (mouseY - var6 - DDAreaY);
                    drawTexturedModalRect((mouseX+4)*2, (mouseY+4)*2, 0, 0, w*2, h*2);
            		GL11.glColor4f(1f, 1f, 1f, 1f);
            		GL11.glScalef(2f, 2f, 2f);
            		GL11.glDisable(GL11.GL_BLEND);
            	}
            }
            else
            {
            }
        }
    }
    
    public void ProcessToolTipHabitat()
    {
        if (mouseX> var5+DDAreaX && mouseY > var6+DDAreaY && 
        		mouseX < var5+DDAreaX+DDAreaWidth && mouseY < var6+DDAreaY + DDAreaHeight)
        	return;
        int x = mouseX - var5;
        int y = mouseY - var6;
        if (x < 0 || y < 0 || x > xSize || y > ySize)
        	return;
		int var44 = this.mc.renderEngine.getTexture("/gui/YC_Tooltip.png");
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, var44);
    	//lava lake
        if (x >= 232 && x <= 248 && y >= 133 && y <= 149)
        {
			DrawToolTip(mouseX, mouseY, 75, 26);
			printString(mouseX - var5+4, mouseY+4 - var6, "Underground", 0x000000);
			printString(mouseX - var5+4, mouseY+14 - var6, "lava lake", 0x000000);
        }
        //dark room
        if (x >= 232 && x <= 248 && y > 149 && y <= 165)
        {
			DrawToolTip(mouseX, mouseY, 60, 16);
			printString(mouseX - var5+4, mouseY+4 - var6, "Dark room", 0x000000);
        }
        //desert
        if (x >= 232 && x <= 248 && y > 166 && y <= 183)
        {
			DrawToolTip(mouseX, mouseY, 45, 16);
			printString(mouseX - var5+4, mouseY+4 - var6, "Desert", 0x000000);
        }
        //crystals
        if (x >= 8 && x <= 24 && y >= 133 && y <= 149)
        {
			DrawToolTip(mouseX, mouseY, 75, 16);
			printString(mouseX - var5+4, mouseY+4 - var6, "Crystal field", 0x000000);
        }
        //ores
        if (x >= 8 && x <= 24 && y > 149 && y <= 165)
        {
			DrawToolTip(mouseX, mouseY, 50, 16);
			printString(mouseX - var5+4, mouseY+4 - var6, "Ore vein", 0x000000);
        }
    }
    
    public void DrawToolTip(int x, int y, int w, int h)
    {
    	if (w<6) w = 6;
    	if (h<6) h = 6;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        drawTexturedModalRect2(x, y, 0, 0, w-3, h-3);
        drawTexturedModalRect2(x+w-3, y, 253, 0, 3, h-3);
        drawTexturedModalRect2(x, y+h-3, 0, 253, w-3, 3);
        drawTexturedModalRect2(x+w-3, y+h-3, 253, 253, 3, 3);
        var9.draw();
    }


    public void drawTexturedModalRect2(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        //var9.startDrawingQuads();
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        //var9.draw();
    }




}
