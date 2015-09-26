package com.lothrazar.powerinventory.inventory;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public interface IOverpoweredContainer
{
	 public void addSlot(Slot s);
	 public int getSlotCount();
	 
	 public Slot getSlot(int slotNumber);
	 
	 public boolean mergeItemStack(ItemStack is, int x, int y, boolean f);
}
