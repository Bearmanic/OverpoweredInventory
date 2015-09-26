package com.lothrazar.powerinventory.inventory;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class OverpoweredContainerSolo extends Container implements IOverpoweredContainer
{
    public List inventorySlots = new ArrayList();
	@Override
	public void addSlot(Slot s)
	{ 
	}		
	@Override
	public int getSlotCount()
	{
		return inventorySlots.size();
	}
	/*
	
	private static final int ARMOR_START = OverpoweredInventorySolo.INV_SIZE, ARMOR_END = ARMOR_START+3,

			INV_START = ARMOR_END+1, INV_END = INV_START+26, HOTBAR_START = INV_END+1,

			HOTBAR_END = HOTBAR_START+8;
	*/
	private EntityPlayer thePlayer;
	public OverpoweredContainerSolo(EntityPlayer player, InventoryPlayer inventoryPlayer, OverpoweredInventorySolo inventoryCustom)
	{
		thePlayer = player;
 ///use addSlotToContainer
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{

		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		 
		return null;
	
	} //end transfer function



}
