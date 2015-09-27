package com.lothrazar.powerinventory;

import com.lothrazar.powerinventory.inventory.GuiOverpoweredSolo;
import com.lothrazar.powerinventory.inventory.OverpoweredContainerSolo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler 
{
	private static int modGuiIndex = 0;
	public static final int GUI_CUSTOM_INV = modGuiIndex++;

	 //then when you need to 
	 //player.openGui(TutorialMain.instance, guiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	 
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z)
	{ 
		if (ID == GUI_CUSTOM_INV) 
			return new OverpoweredContainerSolo(player, player.inventory, PlayerProperties.get(player).inventory);
		else
			return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z)
	{ 
		if (ID == GUI_CUSTOM_INV)
			return new GuiOverpoweredSolo(player, player.inventory, PlayerProperties.get(player).inventory);
		else
			return null;
	}

}
