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
public class YC_GUIBlockWorkbench extends GuiContainer
{
    private IInventory upperChestInventory;
    public IInventory lowerChestInventory;
    public int mouseX=0, mouseY=0;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public YC_GUIBlockWorkbench(IInventory par1IInventory, IInventory par2IInventory)
    {
        super(new YC_ContainerBlockWorkbench((InventoryPlayer) par1IInventory, par2IInventory));
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        this.inventoryRows = par2IInventory.getSizeInventory() / 3;
        this.ySize = 202;
        this.xSize = 200;
    }

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
        int var4 = this.mc.renderEngine.getTexture("/gui/YC_Workbench.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, 202);
        
        //FUEL
        float fullness = (float)((YC_TileEntityBlockWorkbench)lowerChestInventory).FuelValue / 
        		(float)((YC_TileEntityBlockWorkbench)lowerChestInventory).MaxFuel;
        int maxy = (int) ((1-fullness)*92);
        if (fullness > 0.5f) maxy += 1;
        this.drawTexturedModalRect(var5+174, var6+15+maxy, //x,y
        		200+((YC_TileEntityBlockWorkbench)lowerChestInventory).FuelCurTexture/10*11, maxy, //u,v
        		11, 92-maxy);//w,h
        
        
        ((YC_TileEntityBlockWorkbench)lowerChestInventory).FuelCurTexture++;
        if (((YC_TileEntityBlockWorkbench)lowerChestInventory).FuelCurTexture>=40)
        {
        	((YC_TileEntityBlockWorkbench)lowerChestInventory).FuelCurTexture-=40;
        }
    }
}
