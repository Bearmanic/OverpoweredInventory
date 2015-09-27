package com.lothrazar.powerinventory.inventory.client;

import com.lothrazar.powerinventory.network.SoloGuiPacket;
import com.lothrazar.powerinventory.ModInv;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiButtonSolo extends GuiButton 
{ 
    public GuiButtonSolo(int buttonId, int x, int y, int w,int h)
    {
    	super(buttonId, x, y, w,h,"S"); 
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	//Minecraft.getMinecraft().displayGuiScreen(new GuiOverpoweredPlayer(mc.thePlayer));
    	if(pressed)
    	{
    		 
    		NBTTagCompound tags = new NBTTagCompound();

			ModInv.instance.network.sendToServer(new SoloGuiPacket(tags));
    		  
    	}
    	
    	return pressed;
    }
}

