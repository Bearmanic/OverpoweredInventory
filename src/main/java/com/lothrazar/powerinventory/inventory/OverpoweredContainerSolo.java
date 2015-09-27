package com.lothrazar.powerinventory.inventory;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.inventory.slot.SlotBottle;
import com.lothrazar.powerinventory.inventory.slot.SlotClock;
import com.lothrazar.powerinventory.inventory.slot.SlotCompass;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderChest;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderPearl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.IIcon;

public class OverpoweredContainerSolo extends Container  implements IOverpoweredContainer//TODO
{ 
    public IInventory craftResult = new InventoryCraftResult();
	private EntityPlayer thePlayer;
	private InventoryCrafting craftMatrix;

	public void addSlot(Slot s)
	{
        super.addSlotToContainer(s);
	}

	public int getSlotCount()
	{
		return inventorySlots.size();
	}

	public boolean mergeItemStack(ItemStack is, int x, int y, boolean f)
	{
		return super.mergeItemStack(is, x, y, f);
	}

	@Override
	public Slot getSlot(int slotNumber)
	{
		return (Slot)this.inventorySlots.get(slotNumber);
	}

	public OverpoweredContainerSolo(EntityPlayer player, InventoryPlayer playerInventory, OverpoweredInventorySolo inventoryCustom)
	{
		thePlayer = player;
		inventorySlots = Lists.newArrayList();
		craftMatrix = new InventoryCrafting(this,  Const.SIZE_CRAFT,  Const.SIZE_CRAFT);
		 
		InventoryBuilder.setupContainer(this,thePlayer,playerInventory,craftMatrix,craftResult);

        this.onCraftMatrixChanged(this.craftMatrix);
	}
	
	public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
	 //COPIED FROM ContainerPlayer.class 
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }
	
	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        InventoryBuilder.onContainerClosed(playerIn,craftMatrix,craftResult);
    }
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int slotNumber)
	{ 
		return InventoryBuilder.transferStackInSlot(this, p, slotNumber);
	}
}
