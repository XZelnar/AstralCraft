package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class YC_GUIBlockDetector extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    //public GuiButton b_Type;
    public int mouseX=0, mouseY=0;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public YC_GUIBlockDetector(IInventory par1IInventory, IInventory par2IInventory)
    {
        super(new YC_ContainerBlockDetector(par1IInventory, par2IInventory));
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        this.inventoryRows = par2IInventory.getSizeInventory() / 3;
        this.ySize = 166;
        

        //int var5 = (this.width - this.xSize) / 2;
        //int var6 = (this.height - this.ySize) / 2;
        //if (((YC_TileEntityBlockDetector)lowerChestInventory).Type() == 1)
        //{
        //	b_Type = new GuiButton(4, 26, 18, 80, 20, "Equals");
        //}
        //else
        //{
        //	b_Type = new GuiButton(4, 26, 18, 80, 20, "Not equals");
        //}
        //this.controlList.add(b_Type);
    }
    
    /*@Override
    protected void actionPerformed(GuiButton par1GuiButton) {
    	if (par1GuiButton.id == 4)
    	{
    		if (b_Type.displayString == "Equals")
    		{
    			b_Type.displayString = "Not equals";
    			//((YC_TileEntityBlockDetector)lowerChestInventory).Type = 1;
    	    	((YC_TileEntityBlockDetector)lowerChestInventory)._Type = 4121;
    	    	UpdateType();
    	    	//Minecraft.getMinecraft().theWorld.scheduleBlockUpdate(t.xCoord, t.yCoord, t.zCoord, 
    	    	//		YC_Mod.b_blockDetector.blockID, 4);
    	    	//notifyWireNeighborsOfNeighborChange(t.xCoord,t.yCoord,t.zCoord);
    			return;
    		}
			b_Type.displayString = "Equals";
			((YC_TileEntityBlockDetector)lowerChestInventory)._Type = 745320;
	    	UpdateType();
	    	//YC_TileEntityBlockDetector t = ((YC_TileEntityBlockDetector)lowerChestInventory);
	    	//Minecraft.getMinecraft().theWorld.scheduleBlockUpdate(t.xCoord, t.yCoord, t.zCoord, 
	    	//		YC_Mod.b_blockDetector.blockID, 4);
	    	//notifyWireNeighborsOfNeighborChange(t.xCoord,t.yCoord,t.zCoord);
			return;
    	}
    	super.actionPerformed(par1GuiButton);
    }*/
    
    /*public void UpdateType(){
    	//Side s = FMLCommonHandler.instance().getSide();
    	
    	ModLoader.sendPacket(((YC_TileEntityBlockDetector)lowerChestInventory).getDescriptionPacket());
    }
    
    @Override
    public void onGuiClosed() {
    	//YC_TileEntityBlockDetector t = ((YC_TileEntityBlockDetector)lowerChestInventory);
    	//Minecraft.getMinecraft().theWorld.markBlocksDirty(t.xCoord, t.yCoord, t.zCoord, 
    	//		t.xCoord, t.yCoord, t.zCoord);
    	//Minecraft.getMinecraft().theWorld.scheduleBlockUpdate(t.xCoord, t.yCoord, t.zCoord, 
    	//		YC_Mod.b_blockDetector.blockID, 4);
    	//notifyWireNeighborsOfNeighborChange(t.xCoord,t.yCoord,t.zCoord);
    	super.onGuiClosed();
    }
    
    private void notifyWireNeighborsOfNeighborChange(int par2, int par3, int par4)
    {
    	World par1World = Minecraft.getMinecraft().theWorld;
    	int blockID = YC_Mod.b_blockDetector.blockID;
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, blockID);
        par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, blockID);
    }*/

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/gui/YC_blockDetector.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, 166);
        //this.drawTexturedModalRect(var5, var6 + 116, 0, 126, this.xSize, 96);
        
        //b_Type.xPosition = (this.width - this.xSize) / 2 + 85;
        //b_Type.yPosition = (this.height - this.ySize) / 2 + 33;
        //b_Type.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }
    
    /*@Override
    protected void mouseMovedOrUp(int par1, int par2, int par3) {
    	mouseX=par1;
    	mouseY=par2;
    	super.mouseMovedOrUp(par1, par2, par3);
    }
    
    @Override
    protected void mouseClicked(int par1, int par2, int par3) {
    	if (b_Type.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY))
    	{
    		actionPerformed(b_Type);
    	}
    	super.mouseClicked(par1, par2, par3);
    }*/
}
