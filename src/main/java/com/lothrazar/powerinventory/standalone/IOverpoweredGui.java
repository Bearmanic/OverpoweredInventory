package com.lothrazar.powerinventory.standalone;

import net.minecraft.client.gui.GuiButton;

public interface IOverpoweredGui
{
//varaibles not allowed in an interface.
	//multiple inheritance not allowed
	//so we use getters and setters
	public GuiButton btnEnder();
	public void btnEnder(GuiButton b);
	
	public GuiButton btnExp();
	public void btnExp(GuiButton b);
	
	
	public GuiButton btnUncraft();
	public void btnUncraft(GuiButton b);
	
	//getters for privates
	public int guiLeft();
	public int guiTop();
}
