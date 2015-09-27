package com.lothrazar.powerinventory.inventory;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

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
		 System.out.println("OverpoweredContainerSolo constr");
		InventoryBuilder.setupContainer(this,thePlayer,playerInventory,craftMatrix,craftResult,inventoryCustom);

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
