package net.minecraft.src;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class YC_GUIBlockResearchShardGenerator extends GuiContainer
{
    private IInventory upperChestInventory;
    private YC_TileEntityBlockResearchShardGenerator tileEntity = null;
    //public GuiButton b_Type;
    public int mouseX=0, mouseY=0;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public YC_GUIBlockResearchShardGenerator(IInventory par1IInventory, IInventory par2IInventory)
    {
        super(new YC_ContainerBlockResearchShardGenerator((InventoryPlayer) par1IInventory, par2IInventory));
        tileEntity = (YC_TileEntityBlockResearchShardGenerator) par2IInventory;
        this.upperChestInventory = par1IInventory;
        this.allowUserInput = false;
        this.inventoryRows = par2IInventory.getSizeInventory() / 3;
        this.ySize = 170;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(String.valueOf(tileEntity.TimeLeft), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    int var5 = 0, var6 = 0;
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	int prevtext = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
        int var4 = this.mc.renderEngine.getTexture("/gui/YC_ResearchShardGenerator.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        var5 = (this.width - this.xSize) / 2;
        var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, ySize);
        this.drawTexturedModalRect(var5+72, var6+37, 176, 0, 
        		(int) (24 * (float)(6000 - tileEntity.TimeLeft) / 6000f), 17);

    	if (tileEntity.TimeLeft != 6000)
    	{
    		String s2 = String.valueOf((tileEntity.TimeLeft / 20) / 60);
    		if (s2.length()<2) s2 = "0" + s2;
    		String s3 = String.valueOf((tileEntity.TimeLeft / 20) % 60);
    		if (s3.length()<2) s3 = "0" + s3;
    		String s = s2+":"+s3;
    		GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevtext);
        	printString(71,41,s,0x000000);
    	}
    }
    
    public void printString(int x, int y, String text, int color)
    {
    	this.fontRenderer.drawString(text, x+var5, y+var6, color);
    }
}
