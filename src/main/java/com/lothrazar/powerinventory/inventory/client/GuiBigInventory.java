package com.lothrazar.powerinventory.inventory.client;

import com.lothrazar.powerinventory.Const; 
import com.lothrazar.powerinventory.ModConfig;
import com.lothrazar.powerinventory.inventory.BigContainerPlayer;
import com.lothrazar.powerinventory.standalone.ContainerContent;
import com.lothrazar.powerinventory.standalone.IOverpoweredGui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
 
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 */
public class GuiBigInventory extends GuiInventory implements IOverpoweredGui
{
	private BigContainerPlayer container;

	
	
	EntityPlayer thePlayer;
	public GuiBigInventory(EntityPlayer player)
	{
		super(player);
		container = player.inventoryContainer instanceof BigContainerPlayer? (BigContainerPlayer)player.inventoryContainer : null;
		this.xSize = Const.texture_width;
		this.ySize = Const.texture_height;
		thePlayer = player;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
    { 
		super.initGui();
		
		GuiInventory self = this;
		
		if(this.container != null && this.mc.playerController.isInCreativeMode() == false)
		{
			//ContainerContent.setupGui(this, this.buttonList, this.guiLeft, this.guiTop);
			final int height = 20;
			int width = 26;
			final int widthlrg = 58;
			final int padding = 6;
			//final int tiny = 12;
			int button_id = 99;
			 
			if(ModConfig.showMergeDeposit)
			{
				buttonList.add(new GuiButtonDump(button_id++,
						guiLeft + xSize - widthlrg - padding, 
						guiTop + padding,
						widthlrg,height));
	
				buttonList.add(new GuiButtonFilter(button_id++,
						guiLeft + xSize - widthlrg - 2*padding - widthlrg, 
						guiTop + padding,
						widthlrg,height));
			}
			 
			btnUncraft = new GuiButtonUnc(button_id++, 
					guiLeft + ContainerContent.uncraftX - 51 ,
					guiTop + ContainerContent.uncraftY - 1,
					width + 20,height,StatCollector.translateToLocal("button.unc"));
			buttonList.add(btnUncraft); 
			btnUncraft.enabled = false;// turn it on based on ender chest present or not
			btnUncraft.visible = btnUncraft.enabled;

			btnEnder = new GuiButtonOpenInventory(button_id++, 
					guiLeft + ContainerContent.echestX + 19, 
					guiTop + ContainerContent.echestY - 1,
					12,height, "I",Const.INV_ENDER); 
			buttonList.add(btnEnder); 
			btnEnder.enabled = false;// turn it on based on ender chest present or not
			btnEnder.visible = btnEnder.enabled;
			
			btnExp = new GuiButtonExp(button_id++, 
					guiLeft + ContainerContent.bottleX - width - padding+1, 
					guiTop + ContainerContent.bottleY-2,
					width,height,StatCollector.translateToLocal("button.exp"));
			buttonList.add(btnExp);
			btnExp.enabled = false;
			btnExp.visible = btnExp.enabled;
		 
			if(ModConfig.showSortButtons)
			{  
				width = 18;
				int x_spacing = width + padding/2;
				int x = guiLeft + xSize - 5*x_spacing - padding+1;
				int y = guiTop + ySize - height - padding;
				 
				GuiButton btn;
				 //was this.mc.thePlayer
				btn = new GuiButtonSort(thePlayer,button_id++, x, y ,width,height, Const.SORT_LEFTALL,"<<",false);
				this.buttonList.add(btn);

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
    }
	
	private void checkSlotsEmpty()
	{
		final int s = 16;
 
		if(container.invo.getStackInSlot(Const.enderChestSlot) == null)
		{
			btnEnder.enabled = false;
			btnEnder.visible = btnEnder.enabled;
 
			drawTextureSimple("textures/items/empty_enderchest.png",ContainerContent.echestX, ContainerContent.echestY,s,s); 
		}
		else 
		{ 
			btnEnder.enabled = true; 
			btnEnder.visible = btnEnder.enabled;
		}
		
		if(container.invo.getStackInSlot(Const.uncraftSlot) == null)
		{ 
			btnUncraft.enabled = false;
			btnUncraft.visible = btnUncraft.enabled; 
		}
		else 
		{ 
			btnUncraft.enabled = true; 
			btnUncraft.visible = btnUncraft.enabled;
		}
		
		if(container.invo.getStackInSlot(Const.bottleSlot) == null || 
		   container.invo.getStackInSlot(Const.bottleSlot).getItem() == Items.experience_bottle	)
		{
			btnExp.enabled = false;
			btnExp.visible = btnExp.enabled;
  
			drawTextureSimple("textures/items/empty_bottle.png",ContainerContent.bottleX, ContainerContent.bottleY,s,s); 
		}
		else 
		{ 
			btnExp.enabled = true; 
			btnExp.visible = btnExp.enabled;
		}

		if(container.invo.getStackInSlot(Const.enderPearlSlot) == null)
		{  
			drawTextureSimple("textures/items/empty_enderpearl.png",ContainerContent.pearlX, ContainerContent.pearlY,s,s);
		}

		if(container.invo.getStackInSlot(Const.compassSlot) == null)
		{ 
			drawTextureSimple("textures/items/empty_compass.png",ContainerContent.compassX, ContainerContent.compassY,s,s);
		}

		if(container.invo.getStackInSlot(Const.clockSlot) == null)
		{  
			drawTextureSimple("textures/items/empty_clock.png",ContainerContent.clockX, ContainerContent.clockY,s,s);
		}
	}
	 
	public void drawTextureSimple(String texture,double x, double y, double width, double height)
	{
		//wrapper for drawTexturedQuadFit
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, texture)); 
		ContainerContent.drawTexturedQuadFit(x,y,width,height,0);
	}
 
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, Const.INVENTORY_TEXTURE));

        ContainerContent.drawTexturedQuadFit(this.guiLeft, this.guiTop,this.xSize,this.ySize,0);
 
        if(ModConfig.showCharacter)//drawEntityOnScreen
        	func_147046_a(this.guiLeft + 51, this.guiTop + 75, 30, (float)(this.guiLeft + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{ 
		this.checkSlotsEmpty();
		 
		//this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 87, 32, 4210752);
		
/*
		Slot s;
		int show;
		for(Object o : this.container.inventorySlots)
		{
			//vanilla code does not declare ArrayList<Slot>, even though every object in there really is one
			s = (Slot)o;
	 
			//each slot has two different numbers. the slotNumber is UNIQUE, the index is not
			show = s.getSlotIndex();
			//show = s.slotNumber;
			this.drawString(this.fontRendererObj, "" + show, s.xDisplayPosition, s.yDisplayPosition +  4, 16777120);
		}*/

	}



	private GuiButton btnEnder;
	private GuiButton btnExp;
	private GuiButton btnUncraft;
	@Override
	public GuiButton btnEnder()
	{
		return btnEnder;
	}

	@Override
	public void btnEnder(GuiButton b)
	{
		btnExp=b;
	}

	@Override
	public GuiButton btnExp()
	{
		return btnExp;
	}

	@Override
	public void btnExp(GuiButton b)
	{
		btnExp=b;
	}

	@Override
	public GuiButton btnUncraft()
	{
		return btnUncraft;
	}

	@Override
	public void btnUncraft(GuiButton b)
	{
		btnUncraft=b;
	}
}
