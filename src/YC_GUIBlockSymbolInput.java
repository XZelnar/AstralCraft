package net.minecraft.src;

import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.java.games.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.entity.player.*;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class YC_GUIBlockSymbolInput extends GuiContainer
{
    private IInventory upperChestInventory;
    private YC_TileEntityBlockSymbolInput lowerChestInventory;
    public GuiButton b_Activate;
    public int mouseX=0, mouseY=0;
    int x=0,y=0,z=0;
    byte[] curState = new byte[]{7,7,7,7};//0=up 1=down 2=equals 3=wrong 7 = null

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public YC_GUIBlockSymbolInput(EntityPlayer par1IInventory, IInventory par2IInventory, int xc, int yc, int zc)
    {
        super(new YC_ContainerSymbolInput(par1IInventory, par2IInventory));
        this.upperChestInventory = par1IInventory.inventory;
        this.lowerChestInventory = (YC_TileEntityBlockSymbolInput) par2IInventory;
        this.allowUserInput = false;
        this.inventoryRows = par2IInventory.getSizeInventory() / 3;
        this.ySize = 166;
        this.xSize = 176;
        x=xc;
        y=yc;
        z=zc;
        

        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        b_Activate = new GuiButton(4, 8, 62, 128, 20, "Activate");
        this.buttonList.add(b_Activate);
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
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	if (Minecraft.getMinecraft().theWorld.getBlockMetadata(x, y, z) == 2)
    	{
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    	}
    	
        int var4 = this.mc.renderEngine.getTexture("/gui/YC_SymbolInput.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        
        for(int i = 0; i<curState.length; i++)
        {
        	if (curState[i]!=7)
        	{
        		this.drawTexturedModalRect(var5+30 + 36 * i, var6+43, //x,y
        				176+curState[i]*8, 0, //u,v
        				8, 8);//w,h
        	}
        }

        b_Activate.xPosition = (this.width - this.xSize) / 2 + 24;
        b_Activate.yPosition = (this.height - this.ySize) / 2 + 57;
        b_Activate.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }
    
    
    /*@Override
    protected void mouseMovedOrUp(int par1, int par2, int par3) {
    	mouseX=par1;
    	mouseY=par2;
    	super.mouseMovedOrUp(par1, par2, par3);
    }*/
    
    @Override
    public void handleMouseInput() {
    	mouseX = org.lwjgl.input.Mouse.getEventX() * this.width / this.mc.displayWidth;
    	mouseY = this.height - org.lwjgl.input.Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
    	if (mouseX >= b_Activate.xPosition && mouseY >= b_Activate.yPosition && mouseX < b_Activate.xPosition + 128 && mouseY < b_Activate.yPosition + 20 &&
    			org.lwjgl.input.Mouse.isButtonDown(0))
    	{
    		PacketDispatcher.sendPacketToServer(GetPacketForChanged());
        	
        	CheckSlots();
    	}
    	super.handleMouseInput();
    }
    
    public void CheckSlots()
    {
    	YC_TileEntityBlockSymbolInput te = (YC_TileEntityBlockSymbolInput) Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
    	boolean DoReturn = false;
    	for(int i = 0; i<4; i++)
    	{
    		if (te.chestContents[i] == null)
    		{
    			DoReturn = true;
    			curState[i] = 3;
    		}
    		else
    		{
    			curState[i] = 7;
    		}
    	}
    	if (DoReturn) return;
    	for(int i = 0; i<4; i++)
    	{
    		if (te.chestContents[i] == null)
    		{
    			curState[i] = 7;
    			continue;
    		}
    		if (te.chestContents[i].itemID != te.itemIDsRequired[i])
    		{
    			curState[i] = 3;
    			continue;
    		}
    		if (te.chestContents[i].stackSize == te.itemStacksRequired[i])
    		{
    			curState[i] = 2;
    			continue;
    		}
    		if (te.chestContents[i].stackSize > te.itemStacksRequired[i])
    		{
    			curState[i] = 1;
    			continue;
    		}
    		if (te.chestContents[i].stackSize < te.itemStacksRequired[i])
    		{
    			curState[i] = 0;
    			continue;
    		}
    	}
    }
    
    public Packet GetPacketForChanged()
    {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
			outputStream.writeInt(Minecraft.getMinecraft().thePlayer.dimension);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_SymbolInputSC";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
    }
}
