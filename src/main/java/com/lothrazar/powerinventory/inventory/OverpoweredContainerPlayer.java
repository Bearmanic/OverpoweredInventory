package com.lothrazar.powerinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;

public class OverpoweredContainerPlayer extends ContainerPlayer implements IOverpoweredContainer
{	
	@Override
	public void addSlot(Slot s)
	{
        super.addSlotToContainer(s);
	}
	@Override
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
	
    private final EntityPlayer thePlayer;
 
	public OverpoweredInventoryPlayer invo;
    public boolean isLocalWorld;

	public OverpoweredContainerPlayer(OverpoweredInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, Const.SIZE_CRAFT,  Const.SIZE_CRAFT);
 
		InventoryBuilder.setupContainer(this,thePlayer,playerInventory,craftMatrix,craftResult,null);
		
		this.invo = playerInventory; 
        this.onCraftMatrixChanged(this.craftMatrix);
	}
  
	@Override
	public Slot getSlotFromInventory(IInventory invo, int id)
	{
		Slot slot = super.getSlotFromInventory(invo, id);
	
		return slot;
	}

	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        InventoryBuilder.onContainerClosed(playerIn,craftMatrix,craftResult);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
	@Override
    public ItemStack transferStackInSlot(EntityPlayer p, int slotNumber)
    {  
		if(p.capabilities.isCreativeMode)
		{
			return super.transferStackInSlot(p, slotNumber);
		}
		  
		return InventoryBuilder.transferStackInSlot(this, p, slotNumber);
		
    }
}
