package com.lothrazar.powerinventory.inventory;

import com.lothrazar.powerinventory.Const; 
import com.lothrazar.powerinventory.ModConfig;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
 
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiOverpoweredPlayer extends GuiInventory implements IOverpoweredGui
{
	private OverpoweredContainerPlayer container;
	private EntityPlayer thePlayer;
	
	public GuiOverpoweredPlayer(EntityPlayer player)
	{
		super(player);
		container = player.inventoryContainer instanceof OverpoweredContainerPlayer? (OverpoweredContainerPlayer)player.inventoryContainer : null;
		this.xSize = Const.texture_width;
		this.ySize = Const.texture_height;
		thePlayer = player;
	}

	@Override
	public void initGui()
    { 
		super.initGui();
		  
		if(this.container != null && this.mc.playerController.isInCreativeMode() == false)
		{
			InventoryBuilder.setupGui(this, this.thePlayer,this.buttonList);
		}
    }
	
	private void checkSlotsEmpty()
	{
		final int s = 16;
		TextureManager tm = this.mc.getTextureManager();
		if(container.invo.getStackInSlot(Const.enderChestSlot) == null)
		{
			btnEnder().enabled = false;
			btnEnder().visible = btnEnder.enabled;
 
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_enderchest.png",InventoryBuilder.echestX, InventoryBuilder.echestY,s,s); 
		}
		else 
		{ 
			btnEnder().enabled = true; 
			btnEnder().visible = btnEnder.enabled;
		}
		
		if(container.invo.getStackInSlot(Const.uncraftSlot) == null)
		{ 
			btnUncraft().enabled = false;
			btnUncraft().visible = btnUncraft.enabled; 
		}
		else 
		{ 
			btnUncraft().enabled = true; 
			btnUncraft().visible = btnUncraft.enabled;
		}
		
		if(container.invo.getStackInSlot(Const.bottleSlot) == null || 
		   container.invo.getStackInSlot(Const.bottleSlot).getItem() == Items.experience_bottle	)
		{
			btnExp().enabled = false;
			btnExp().visible = btnExp.enabled;
  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_bottle.png",InventoryBuilder.bottleX, InventoryBuilder.bottleY,s,s); 
		}
		else 
		{ 
			btnExp().enabled = true; 
			btnExp().visible = btnExp.enabled;
		}

		if(container.invo.getStackInSlot(Const.enderPearlSlot) == null)
		{  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_enderpearl.png",InventoryBuilder.pearlX, InventoryBuilder.pearlY,s,s);
		}

		if(container.invo.getStackInSlot(Const.compassSlot) == null)
		{ 
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_compass.png",InventoryBuilder.compassX, InventoryBuilder.compassY,s,s);
		}

		if(container.invo.getStackInSlot(Const.clockSlot) == null)
		{  
			InventoryBuilder.drawTextureSimple(tm,"textures/items/empty_clock.png",InventoryBuilder.clockX, InventoryBuilder.clockY,s,s);
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, Const.INVENTORY_TEXTURE));

        InventoryBuilder.drawTexturedQuadFit(this.guiLeft(), this.guiTop(),Const.texture_width,Const.texture_height ,0);
 
        if(ModConfig.showCharacter)//drawEntityOnScreen
        	func_147046_a(this.guiLeft() + 51, this.guiTop() + 75, 30, (float)(this.guiLeft() + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
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
		btnEnder=b;
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
	@Override
	public int guiLeft()
	{
		return this.guiLeft;
	}
	@Override
	public int guiTop()
	{
		return this.guiTop;
	}
}
