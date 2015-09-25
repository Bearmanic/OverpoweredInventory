package com.lothrazar.powerinventory.inventory;

import java.util.List;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModConfig;
import com.lothrazar.powerinventory.inventory.client.GuiButtonDump;
import com.lothrazar.powerinventory.inventory.client.GuiButtonExp;
import com.lothrazar.powerinventory.inventory.client.GuiButtonFilter;
import com.lothrazar.powerinventory.inventory.client.GuiButtonOpenInventory;
import com.lothrazar.powerinventory.inventory.client.GuiButtonSort;
import com.lothrazar.powerinventory.inventory.client.GuiButtonUnc;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class InventoryBuilder
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

	public static void setupGui(IOverpoweredGui self, EntityPlayer thePlayer, List buttonList)
	{
		int xSize = Const.texture_width;
		int ySize = Const.texture_height;
		
		
		final int height = 20;
		int width = 26;
		final int widthlrg = 58;
		final int padding = 6;
		//final int tiny = 12;
		int button_id = 99;
		 
		if(ModConfig.showMergeDeposit)
		{
			buttonList.add(new GuiButtonDump(button_id++,
					self.guiLeft() + Const.texture_width - widthlrg - padding, 
					self.guiTop() + padding,
					widthlrg,height));

			buttonList.add(new GuiButtonFilter(button_id++,
					self.guiLeft() + Const.texture_width - widthlrg - 2*padding - widthlrg, 
					self.guiTop() + padding,
					widthlrg,height));
		}
		 
		GuiButtonUnc btnUncraft = new GuiButtonUnc(button_id++, 
				self.guiLeft() + InventoryBuilder.uncraftX - 51 ,
				self.guiTop() + InventoryBuilder.uncraftY - 1,
				width + 20,height,StatCollector.translateToLocal("button.unc"));
		buttonList.add(btnUncraft); 
		btnUncraft.enabled = false;// turn it on based on ender chest present or not
		btnUncraft.visible = btnUncraft.enabled;
		self.btnUncraft(btnUncraft);

		GuiButtonOpenInventory btnEnder = new GuiButtonOpenInventory(button_id++, 
				self.guiLeft() + InventoryBuilder.echestX + 19, 
				self.guiTop() + InventoryBuilder.echestY - 1,
				12,height, "I",Const.INV_ENDER); 
		buttonList.add(btnEnder); 
		btnEnder.enabled = false;// turn it on based on ender chest present or not
		btnEnder.visible = btnEnder.enabled;
		self.btnEnder(btnEnder);
		
		GuiButtonExp btnExp = new GuiButtonExp(button_id++, 
				self.guiLeft() + InventoryBuilder.bottleX - width - padding+1, 
				self.guiTop() + InventoryBuilder.bottleY-2,
				width,height,StatCollector.translateToLocal("button.exp"));
		buttonList.add(btnExp);
		btnExp.enabled = false;
		btnExp.visible = btnExp.enabled;
		self.btnExp(btnExp);
	 
		if(ModConfig.showSortButtons)
		{  
			width = 18;
			int x_spacing = width + padding/2;
			int x = self.guiLeft() + Const.texture_width - 5*x_spacing - padding+1;
			int y = self.guiTop() + Const.texture_height - height - padding;
			 
			GuiButton btn;
			 //was this.mc.thePlayer
			btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_LEFTALL,"<<",false);
			buttonList.add(btn);

			x += x_spacing;
		 
			btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_LEFT,"<",false);
			buttonList.add(btn);

			x += x_spacing;
		 
			btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_SMART,StatCollector.translateToLocal("button.sort"),false);
			buttonList.add(btn);
			
			x += x_spacing;

			btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_RIGHT,">",false);
			buttonList.add(btn);
			  
			x += x_spacing;
			
			btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_RIGHTALL,">>",false);
			buttonList.add(btn);
		}
		
	}
	
	
	public static void setupContainer(IOverpoweredContainer self)
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
