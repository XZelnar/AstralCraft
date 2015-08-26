package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class YC_GUIBlockChest extends GuiContainer
{
    private IInventory upperChestInventory;
    private YC_TileEntityBlockChest tileEntity = null;
    private YC_ContainerBlockChest container = null;
    //public GuiButton b_Type;
    public int mouseX=0, mouseY=0;
    public int PageCur = 1, PageCount = 0;
    int x=0,y=0,z=0;

    public GuiButton b_Prev;
    public GuiButton b_Next;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public YC_GUIBlockChest(EntityPlayer par1Player, IInventory par2IInventory, int xc, int yc, int zc)
    {
        super(new YC_ContainerBlockChest(par1Player, par2IInventory));
        container = (YC_ContainerBlockChest) inventorySlots;
        tileEntity = (YC_TileEntityBlockChest) par2IInventory;
        this.upperChestInventory = par1Player.inventory;
        this.allowUserInput = false;
        this.inventoryRows = par2IInventory.getSizeInventory() / 3;
        this.ySize = 237;
        xSize = 198;
        PageCount = tileEntity.chestContents.size();
        x=xc;
        y=yc;
        z=zc;
        
        b_Prev = new GuiButton(4, 7, 3, 16, 20, "<<");
        this.buttonList.add(b_Prev);
        b_Next = new GuiButton(5, 153, 3, 16, 20, ">>");
        this.buttonList.add(b_Next);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        //this.fontRenderer.drawString(String.valueOf(tileEntity.TimeLeft), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    int var5 = 0, var6 = 0;
    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	int prevtext = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
    	if (tileEntity == null || tileEntity.isInvalid() || Minecraft.getMinecraft().theWorld.getBlockTileEntity(tileEntity.mx, tileEntity.my, tileEntity.mz) == null)
    	{
    		Minecraft.getMinecraft().thePlayer.closeScreen();
    	}
    	
    	if (tileEntity.numUsingPlayers == 0) tileEntity.numUsingPlayers = 1;
    	
        if (PageCount != tileEntity.chestContents.size())
        {
        	PageCount = tileEntity.chestContents.size();
			PacketDispatcher.sendPacketToServer(GetPacketForChanged());
        	container.ReloadContainer();
        }

        int var4 = this.mc.renderEngine.getTexture("/gui/YC_Chest.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var4);
        var5 = (this.width - this.xSize) / 2;
        var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, ySize);

        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, prevtext);
        printStringCentered(11,PageCur+"/"+PageCount,0x000000);
    	


        b_Prev.xPosition = (this.width - this.xSize) / 2 + 7;
        b_Prev.yPosition = (this.height - this.ySize) / 2 + 6;
        b_Prev.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);

        b_Next.xPosition = (this.width - this.xSize) / 2 + 153;
        b_Next.yPosition = (this.height - this.ySize) / 2 + 6;
        b_Next.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    }
    
    boolean lastMouse = false;
    @Override
    public void handleMouseInput() {
    	mouseX = org.lwjgl.input.Mouse.getEventX() * this.width / this.mc.displayWidth;
    	mouseY = this.height - org.lwjgl.input.Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
    	if (!lastMouse && org.lwjgl.input.Mouse.isButtonDown(0))
    	{
    		if (mouseX >= b_Prev.xPosition && mouseY >= b_Prev.yPosition && mouseX < b_Prev.xPosition + 16 && mouseY < b_Prev.yPosition + 20)
    		{//<<
    			if (PageCur > 1)
    			{
    				PageCur--;
    				PacketDispatcher.sendPacketToServer(GetPacketForChanged());
    				container.InitSlots(PageCur);
    			}
    		}
    		if (mouseX >= b_Next.xPosition && mouseY >= b_Next.yPosition && mouseX < b_Next.xPosition + 16 && mouseY < b_Next.yPosition + 20)
    		{//>>
    			if (PageCur < PageCount)
    			{
    				PageCur++;
    				PacketDispatcher.sendPacketToServer(GetPacketForChanged());
    				container.InitSlots(PageCur);
    			}
    		}
    	}
    	lastMouse = org.lwjgl.input.Mouse.isButtonDown(0);
    	super.handleMouseInput();
    }
    
    public void printString(int x, int y, String text, int color)
    {
    	this.fontRenderer.drawString(text, x+var5, y+var6, color);
    }
    
    public void printStringCentered(int y, String text, int color)
    {
    	int x = (int)((xSize - 22 - fontRenderer.getStringWidth(text))/2f);
    	this.fontRenderer.drawString(text, x+var5, y+var6, color);
    }
    

    
	public Packet GetPacketForChanged() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(tileEntity.mx);
			outputStream.writeInt(tileEntity.my);
			outputStream.writeInt(tileEntity.mz);
			outputStream.writeInt(Minecraft.getMinecraft().thePlayer.dimension);
			outputStream.writeInt(PageCur);
			String PlayerName = container.player.username;
			char[] t = PlayerName.toCharArray();
			outputStream.writeInt(t.length);
			for (int i = 0; i < t.length; i++) {
				outputStream.writeInt(t[i]);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "YC_ChestPage";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		return packet;
	}
    
    
}
