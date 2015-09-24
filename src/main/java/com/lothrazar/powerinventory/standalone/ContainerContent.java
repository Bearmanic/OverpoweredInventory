package com.lothrazar.powerinventory.standalone;

import java.util.List;

import com.lothrazar.powerinventory.Const;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;

public class ContainerContent
{
	final static int padding = 6;
	public static final int pearlX = 80; 
	public static final int pearlY = 8; 
	public static final int compassX = pearlX;
	public static final int compassY = pearlY + Const.square;
	public static final int clockX = pearlX;
	public static final int clockY = pearlY + 2*Const.square;
	public static final int echestX = pearlX;
	public static final int echestY = pearlY + 3*Const.square;
	public static final int bottleX = Const.texture_width - Const.square - padding - 1;
	public static final int bottleY = 20 + 2 * Const.square;
	public static final int uncraftX = bottleX;
	public static final int uncraftY = bottleY - 24;

	public static void setupGui(IOverpoweredGui self, List buttonList,int guiLeft,int guiTop)
	{
		int xSize = Const.texture_width;
		int ySize = Const.texture_height;
		
		
	}
	public static void setupContainer()
	{
		
	}
	
	
	
	
	
	
	
	
	public static void drawTexturedQuadFit(double x, double y, double width, double height, double zLevel)
	{
		//because the vanilla code REQUIRES textures to be powers of two AND are force dto be max of 256??? WHAT?
		//so this one actually works
		//THANKS hydroflame  ON FORUMS 
		//http://www.minecraftforge.net/forum/index.php/topic,11229.msg57594.html#msg57594
		
		Tessellator tessellator = Tessellator.instance;
  
		//WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		tessellator.startDrawingQuads();
        
		tessellator.addVertexWithUV(x + 0, y + height, zLevel, 0,1);
		tessellator.addVertexWithUV(x + width, y + height, zLevel, 1, 1);
		tessellator.addVertexWithUV(x + width, y + 0, zLevel, 1,0);
		tessellator.addVertexWithUV(x + 0, y + 0, zLevel, 0, 0);
        tessellator.draw();
	}
}
