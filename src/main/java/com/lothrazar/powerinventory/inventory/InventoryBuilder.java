package com.lothrazar.powerinventory.inventory;

import java.util.List;

import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModConfig;
import com.lothrazar.powerinventory.inventory.client.GuiButtonDump;
import com.lothrazar.powerinventory.inventory.client.GuiButtonExp;
import com.lothrazar.powerinventory.inventory.client.GuiButtonFilter;
import com.lothrazar.powerinventory.inventory.client.GuiOpenEnder;
import com.lothrazar.powerinventory.inventory.client.GuiButtonSort;
import com.lothrazar.powerinventory.inventory.client.GuiButtonUnc;
import com.lothrazar.powerinventory.inventory.slot.SlotBottle;
import com.lothrazar.powerinventory.inventory.slot.SlotClock;
import com.lothrazar.powerinventory.inventory.slot.SlotCompass;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderChest;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderPearl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class InventoryBuilder
{
	final static int padding = 6;
	public static final int pearlX = 80; 
	public static final int pearlY = 8; 
	public static final int compassX = pearlX;
	public static final int compassY = pearlY + Const.SQ;
	public static final int clockX = pearlX;
	public static final int clockY = pearlY + 2*Const.SQ;
	public static final int echestX = pearlX;
	public static final int echestY = pearlY + 3*Const.SQ;
	public static final int bottleX = ModConfig.texture_width - Const.SQ - padding - 1;
	public static final int bottleY = 20 + 2 * Const.SQ;
	public static final int uncraftX = bottleX;
	public static final int uncraftY = bottleY - 24;

	
	 
	public static int S_RESULT;
	public static int S_CRAFT_START;
	public static int S_CRAFT_END;
	public static int S_ARMOR_START;
	public static int S_ARMOR_END;
	public static int S_BAR_START;
	public static int S_BAR_END;
	public static int S_MAIN_START;
	public static int S_MAIN_END;
	public static int S_ECHEST;
	public static int S_PEARL;
	public static int S_CLOCK;
	public static int S_COMPASS;
	public static int S_BOTTLE;
	public static int S_UNCRAFT;
	
	
	public static void checkSlotsEmpty(IOverpoweredGui self, IOverpoweredInventory invo, TextureManager tm)
	{
		final int s = 16;

		if(invo.getStack(ModConfig.enderChestSlot) == null)
		{
			self.btnEnder().enabled = false;
			self.btnEnder().visible = false;
 
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_enderchest.png",InventoryBuilder.echestX, InventoryBuilder.echestY,s,s); 
		}
		else 
		{ 
			self.btnEnder().enabled = true; 
			self.btnEnder().visible = true;
		}
		
		if(invo.getStack(ModConfig.uncraftSlot) == null)
		{ 
			self.btnUncraft().enabled = false;
			self.btnUncraft().visible = false; 
		}
		else 
		{ 
			self.btnUncraft().enabled = true; 
			self.btnUncraft().visible = true;
		}
		
		if(invo.getStack(ModConfig.bottleSlot) == null || 
		   invo.getStack(ModConfig.bottleSlot).getItem() == Items.experience_bottle	)
		{
			self.btnExp().enabled = false;
			self.btnExp().visible = false;
  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_bottle.png",InventoryBuilder.bottleX, InventoryBuilder.bottleY,s,s); 
		}
		else 
		{ 
			self.btnExp().enabled = true; 
			self.btnExp().visible = true;
		}

		if(invo.getStack(ModConfig.enderPearlSlot) == null)
		{  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_enderpearl.png",InventoryBuilder.pearlX, InventoryBuilder.pearlY,s,s);
		}

		if(invo.getStack(ModConfig.compassSlot) == null)
		{ 
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_compass.png",InventoryBuilder.compassX, InventoryBuilder.compassY,s,s);
		}

		if(invo.getStack(ModConfig.clockSlot) == null)
		{  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_clock.png",InventoryBuilder.clockX, InventoryBuilder.clockY,s,s);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void setupGui(IOverpoweredGui self, EntityPlayer thePlayer, List buttonList)
	{
		//int xSize = Const.texture_width;
		//int ySize = Const.texture_height;
		
		
		final int height = 20;
		int width = 26;
		final int widthlrg = 58;
		final int padding = 6;
		//final int tiny = 12;
		int button_id = 99;
		 
		if(ModConfig.showMergeDeposit)
		{
			buttonList.add(new GuiButtonDump(button_id++,
					self.guiLeft() + ModConfig.texture_width - widthlrg - padding, 
					self.guiTop() + padding,
					widthlrg,height));

			buttonList.add(new GuiButtonFilter(button_id++,
					self.guiLeft() + ModConfig.texture_width - widthlrg - 2*padding - widthlrg, 
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

		GuiOpenEnder btnEnder = new GuiOpenEnder(button_id++, 
				self.guiLeft() + InventoryBuilder.echestX + 19, 
				self.guiTop() + InventoryBuilder.echestY - 1,
				12,height); 
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
			int x = self.guiLeft() + ModConfig.texture_width - 5*x_spacing - padding+1;
			int y = self.guiTop() + ModConfig.texture_height - height - padding;
			 
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
	
	public static void setupContainer(IOverpoweredContainer self,final EntityPlayer thePlayer,IInventory playerInventory, 
			InventoryCrafting craftMatrix,
			IInventory craftResult)
	{
		 int i,j,cx,cy;//rows and cols of vanilla, not extra
        
        InventoryBuilder.S_RESULT = self.getSlotCount();
        self.addSlot(new SlotCrafting(thePlayer, craftMatrix, craftResult, 0, 
        		200,  
        		40));
        
        InventoryBuilder.S_CRAFT_START = self.getSlotCount();
        for (i = 0; i < Const.SIZE_CRAFT; ++i)
        { 
            for (j = 0; j < Const.SIZE_CRAFT; ++j)
            {  
    			cx = 114 + j * Const.SQ ; 
    			cy = 20 + i * Const.SQ ;

    			self.addSlot(new Slot(craftMatrix, j + i * Const.SIZE_CRAFT, cx , cy)); 
            }
        }
        InventoryBuilder.S_CRAFT_END = self.getSlotCount() - 1;
		
		InventoryBuilder.S_ARMOR_START = self.getSlotCount();
        for (i = 0; i < Const.SIZE_ARMOR; ++i)
        {
        	cx = 8;
        	cy = 8 + i * Const.SQ;
            final int k = i;
 
            self.addSlot(new Slot(playerInventory,  ModConfig.sizeGrid - 1 - i, cx, cy)
            { 
            	public int getSlotStackLimit()
	            {
	                return 1;
	            }
	            public boolean isItemValid(ItemStack stack)
	            {
	                if (stack == null) return false;
	                return stack.getItem().isValidArmor(stack, k, thePlayer);
	            }
	            @SideOnly(Side.CLIENT)
	            public String getSlotTexture()
	            {
	                return ItemArmor.EMPTY_SLOT_NAMES[k];
	            }
            }); 
        }
        InventoryBuilder.S_ARMOR_END = self.getSlotCount() - 1;
        
        InventoryBuilder.S_BAR_START = self.getSlotCount();
        for (i = 0; i < Const.SIZE_HOTBAR; ++i)
        { 
        	cx = 8 + i * Const.SQ;
        	cy = 142 + (Const.SQ * ModConfig.moreRows);
 
        	self.addSlot(new Slot(playerInventory, i, cx, cy));
        }
        InventoryBuilder.S_BAR_END = self.getSlotCount() - 1;
        
        
        InventoryBuilder.S_MAIN_START = self.getSlotCount();
        int slotIndex = Const.SIZE_HOTBAR;
        
        for( i = 0; i < ModConfig.allRows; i++)
		{
            for ( j = 0; j < ModConfig.allCols; ++j)
            { 
            	cx = 8 + j * Const.SQ;
            	cy = 84 + i * Const.SQ;
            	self.addSlot(new Slot(playerInventory, slotIndex, cx, cy));
            	slotIndex++;
            }
        }
        InventoryBuilder.S_MAIN_END = self.getSlotCount() - 1;
        
        
        InventoryBuilder.S_PEARL =  self.getSlotCount() ;
        self.addSlot(new SlotEnderPearl(playerInventory, ModConfig.enderPearlSlot, InventoryBuilder.pearlX, InventoryBuilder.pearlY));

        InventoryBuilder.S_ECHEST =  self.getSlotCount() ;
        self.addSlot(new SlotEnderChest(playerInventory, ModConfig.enderChestSlot, InventoryBuilder.echestX, InventoryBuilder.echestY)); 

        InventoryBuilder.S_CLOCK =  self.getSlotCount();
        self.addSlot(new SlotClock(playerInventory, ModConfig.clockSlot, InventoryBuilder.clockX, InventoryBuilder.clockY)); 

        InventoryBuilder.S_COMPASS = self.getSlotCount() ;
        self.addSlot(new SlotCompass(playerInventory, ModConfig.compassSlot, InventoryBuilder.compassX, InventoryBuilder.compassY)); 
        
        InventoryBuilder.S_BOTTLE = self.getSlotCount() ;
        self.addSlot(new SlotBottle(playerInventory, ModConfig.bottleSlot, InventoryBuilder.bottleX, InventoryBuilder.bottleY)); 
        
        InventoryBuilder.S_UNCRAFT =self.getSlotCount() ;
        self.addSlot(new Slot(playerInventory, ModConfig.uncraftSlot, InventoryBuilder.uncraftX, InventoryBuilder.uncraftY)); 

        
	}
	
	
	
	public static ItemStack transferStackInSlot(IOverpoweredContainer self, EntityPlayer p, int slotNumber)
	{
		
		

		//Thanks to coolAlias on the forums : 
		//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
		//above is from 2013 but still relevant
        ItemStack stackCopy = null;
        Slot slot = self.getSlot(slotNumber);

		if (slot != null && slot.getHasStack())
        {
            ItemStack stackOrig = slot.getStack();
            stackCopy = stackOrig.copy();
            if (slotNumber == InventoryBuilder.S_RESULT)  
            { 
            	//System.out.printf("\ntest result %d %d ___  ",S_MAIN_START,S_MAIN_END);
                if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_MAIN_START, InventoryBuilder.S_MAIN_END, false))//was starting at S_BAR_START
                {
                    return null;
                }

                slot.onSlotChange(stackOrig, stackCopy);
            }
            else if (slotNumber >= InventoryBuilder.S_CRAFT_START && slotNumber <= InventoryBuilder.S_CRAFT_END) 
            { 
                if (!self.mergeItemStack(stackOrig,  InventoryBuilder.S_BAR_START, InventoryBuilder.S_MAIN_END, false))//was 9,45
                {
                    return null;
                }
            }
            else if (slotNumber >= InventoryBuilder.S_ARMOR_START && slotNumber <= InventoryBuilder.S_ARMOR_END) 
            { 
                if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_MAIN_START, InventoryBuilder.S_MAIN_END, false))
                {
                    return null;
                }
            }
            else if (stackCopy.getItem() instanceof ItemArmor 
            		&& !(self.getSlot(InventoryBuilder.S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType)).getHasStack()) // Inventory to armor
            { 
            	int j = InventoryBuilder.S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType;
           
            	if (!self.mergeItemStack(stackOrig, j, j+1, false))
                {
                    return null;
                } 
            }
            else if (slotNumber >= InventoryBuilder.S_MAIN_START && slotNumber <= InventoryBuilder.S_MAIN_END) // main inv grid
            { 
            	//only from here are we doing the special items
            	
            	if(stackCopy.getItem() == Items.ender_pearl && 
            		(
        			p.inventory.getStackInSlot(ModConfig.enderPearlSlot) == null || 
        			p.inventory.getStackInSlot(ModConfig.enderPearlSlot).stackSize < Items.ender_pearl.getItemStackLimit(stackCopy))
        			)
        		{
            		 
            		if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_PEARL, InventoryBuilder.S_PEARL+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Item.getItemFromBlock(Blocks.ender_chest) && 
            		(
        			p.inventory.getStackInSlot(ModConfig.enderChestSlot) == null || 
        			p.inventory.getStackInSlot(ModConfig.enderChestSlot).stackSize < 1)
        			)
        		{ 
            		if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_ECHEST, InventoryBuilder.S_ECHEST+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.compass && 
            		(
        			p.inventory.getStackInSlot(ModConfig.compassSlot) == null || 
        			p.inventory.getStackInSlot(ModConfig.compassSlot).stackSize < 1)
        			)
        		{ 
            		if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_COMPASS, InventoryBuilder.S_COMPASS+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.clock && 
            		(
        			p.inventory.getStackInSlot(ModConfig.clockSlot) == null || 
        			p.inventory.getStackInSlot(ModConfig.clockSlot).stackSize < 1)
        			)
        		{ 
            		if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_CLOCK, InventoryBuilder.S_CLOCK+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.glass_bottle )
        		{ 
            		if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_BOTTLE, InventoryBuilder.S_BOTTLE+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_BAR_START, InventoryBuilder.S_BAR_END+1, false)            			)
            	{
            		
                    return null;
                }
            }
            else if (slotNumber >= InventoryBuilder.S_BAR_START && slotNumber <= InventoryBuilder.S_BAR_END) // Hotbar
            { 
            	if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_MAIN_START, InventoryBuilder.S_MAIN_END, false))
            	{
                    return null;
                }
            }
            else if(slotNumber == InventoryBuilder.S_PEARL || slotNumber == InventoryBuilder.S_ECHEST  
            		|| slotNumber == InventoryBuilder.S_COMPASS  || slotNumber == InventoryBuilder.S_CLOCK || slotNumber == InventoryBuilder.S_BOTTLE
            		|| slotNumber == InventoryBuilder.S_UNCRAFT)
            { 
            	if (!self.mergeItemStack(stackOrig, InventoryBuilder.S_MAIN_START, InventoryBuilder.S_MAIN_END, false))
            	{
                    return null;
                }
            }
            else if (!self.mergeItemStack(stackOrig, Const.SIZE_HOTBAR, ModConfig.sizeGrid + Const.SIZE_HOTBAR, false)) // Full range
            {
                return null;
            }
            if (stackOrig.stackSize == 0)
            { 
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stackOrig.stackSize == stackCopy.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, stackOrig);
        }

        return stackCopy;
	}

	
	public static void drawTextureSimple(TextureManager tm,String texture,double x, double y, double width, double height)
	{
		//wrapper for drawTexturedQuadFit
		tm.bindTexture(new ResourceLocation(Const.MODID, texture)); 
		InventoryBuilder.drawTexturedQuadFit(x,y,width,height,0);
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

	public static void onContainerClosed(EntityPlayer playerIn,	InventoryCrafting craftMatrix, IInventory craftResult)
	{ 
// we COULD not empty it BUT then it gets erased on logout, etc
        for (int i = 0; i < Const.SIZE_CRAFT*Const.SIZE_CRAFT; ++i) // was 4
        {
            ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        craftResult.setInventorySlotContents(0, (ItemStack)null);
	}
}
