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
	IOverpoweredInventory inventory;
	private EntityPlayer thePlayer;
	
	public GuiOverpoweredPlayer(EntityPlayer player)
	{
		super(player);
		container = player.inventoryContainer instanceof OverpoweredContainerPlayer? (OverpoweredContainerPlayer)player.inventoryContainer : null;
		inventory = container.invo;
		this.xSize = ModConfig.texture_width;
		this.ySize = ModConfig.texture_height;
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
	
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{ 
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
        this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, ModConfig.INVENTORY_TEXTURE));

        InventoryBuilder.drawTexturedQuadFit(this.guiLeft(), this.guiTop(),ModConfig.texture_width,ModConfig.texture_height ,0);
 
        if(ModConfig.showCharacter)//drawEntityOnScreen
        	func_147046_a(this.guiLeft() + 51, this.guiTop() + 75, 30, (float)(this.guiLeft() + 51) - (float)mouseX, (float)(this.guiTop + 75 - 50) - (float)mouseY, this.mc.thePlayer);
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{ 
		
		InventoryBuilder.checkSlotsEmpty(this,inventory,this.mc.getTextureManager());
			
		 
		 
		//this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 87, 32, 4210752);
		
/*
 //TESTING AREA
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
