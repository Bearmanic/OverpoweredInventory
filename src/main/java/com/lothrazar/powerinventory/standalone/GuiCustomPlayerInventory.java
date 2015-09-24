package com.lothrazar.powerinventory.standalone;

import org.lwjgl.opengl.GL11;

import com.lothrazar.powerinventory.Const;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiCustomPlayerInventory extends GuiContainer implements IOverpoweredGui
{
	ResourceLocation res = new ResourceLocation(Const.MODID, Const.INVENTORY_TEXTURE);
	private final InventoryCustomPlayer inventory;
	//private final EntityPlayer thePlayer;
	
	public GuiCustomPlayerInventory(EntityPlayer player, InventoryPlayer inventoryPlayer, InventoryCustomPlayer inventoryCustom)
	{
		super(new ContainerCustomPlayer(player, inventoryPlayer, inventoryCustom));
		inventory = inventoryCustom;
		//thePlayer = player;
		
		this.xSize = Const.texture_width;
		this.ySize = Const.texture_height;

	}
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
	}
	@Override
	protected void drawGuiContainerForegroundLayer(	int p_146976_2_, int p_146976_3_)
	{ 
		//drawing text and such on screen
		
	}
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_,	int p_146976_2_, int p_146976_3_)
	{ 
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glScalef(1.0F, 1.0F, 1.0F);//so it does not change scale
		this.mc.getTextureManager().bindTexture(new ResourceLocation(Const.MODID, Const.INVENTORY_TEXTURE));
		
		ContainerContent.drawTexturedQuadFit(this.guiLeft, this.guiTop,this.xSize,this.ySize,0);
 
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
