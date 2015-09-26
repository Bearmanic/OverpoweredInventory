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
import net.minecraft.util.IIcon;

public class OverpoweredContainerSolo extends Container  implements IOverpoweredContainer//TODO
{ 
	//@Override
	public void addSlot(Slot s)
	{
        super.addSlotToContainer(s);
	}
	//@Override
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


    public IInventory craftResult = new InventoryCraftResult();
	
	private EntityPlayer thePlayer;
	private InventoryCrafting craftMatrix;
	public OverpoweredContainerSolo(EntityPlayer player, InventoryPlayer playerInventory, OverpoweredInventorySolo inventoryCustom)
	{
		thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this,  Const.craftSize,  Const.craftSize);
		 


		InventoryBuilder.setupContainer(this,thePlayer,playerInventory,craftMatrix,craftResult);

        this.onCraftMatrixChanged(this.craftMatrix);
	}

	@Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        //TODO: move to InventoryBuilder
// we COULD not empty it BUT then it gets erased on logout, etc
        for (int i = 0; i < Const.craftSize*Const.craftSize; ++i) // was 4
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
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
		
/*
		//Thanks to coolAlias on the forums : 
		//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
		//above is from 2013 but still relevant
        ItemStack stackCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack())
        {
            ItemStack stackOrig = slot.getStack();
            stackCopy = stackOrig.copy();  if (slotNumber >= S_MAIN_START && slotNumber <= S_MAIN_END) // main inv grid
            { 
            	//only from here are we doing the special items
            	
            	if(stackCopy.getItem() == Items.ender_pearl && 
            		(
        			p.inventory.getStackInSlot(Const.enderPearlSlot) == null || 
        			p.inventory.getStackInSlot(Const.enderPearlSlot).stackSize < Items.ender_pearl.getItemStackLimit(stackCopy))
        			)
        		{
            		 
            		if (!this.mergeItemStack(stackOrig, S_PEARL, S_PEARL+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Item.getItemFromBlock(Blocks.ender_chest) && 
            		(
        			p.inventory.getStackInSlot(Const.enderChestSlot) == null || 
        			p.inventory.getStackInSlot(Const.enderChestSlot).stackSize < 1)
        			)
        		{ 
            		if (!this.mergeItemStack(stackOrig, S_ECHEST, S_ECHEST+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.compass && 
            		(
        			p.inventory.getStackInSlot(Const.compassSlot) == null || 
        			p.inventory.getStackInSlot(Const.compassSlot).stackSize < 1)
        			)
        		{ 
            		if (!this.mergeItemStack(stackOrig, S_COMPASS, S_COMPASS+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.clock && 
            		(
        			p.inventory.getStackInSlot(Const.clockSlot) == null || 
        			p.inventory.getStackInSlot(Const.clockSlot).stackSize < 1)
        			)
        		{ 
            		if (!this.mergeItemStack(stackOrig, S_CLOCK, S_CLOCK+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if(stackCopy.getItem() == Items.glass_bottle )
        		{ 
            		if (!this.mergeItemStack(stackOrig, S_BOTTLE, S_BOTTLE+1, false))
                	{ 
                        return null;
                    }  
        		}
            	else if (!this.mergeItemStack(stackOrig, S_BAR_START, S_BAR_END+1, false)            			)
            	{
            		
                    return null;
                }
            }
            else if (slotNumber >= S_BAR_START && slotNumber <= S_BAR_END) // Hotbar
            { 
            	if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))
            	{
                    return null;
                }
            }
            else if(slotNumber == S_PEARL || slotNumber == S_ECHEST  || slotNumber == S_COMPASS  || slotNumber == S_CLOCK || slotNumber == S_BOTTLE
            		|| slotNumber == S_UNCRAFT)
            { 
            	if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))
            	{
                    return null;
                }
            }
            else if (!this.mergeItemStack(stackOrig, Const.hotbarSize, Const.INVOSIZE + Const.hotbarSize, false)) // Full range
            {
            	
                return null;
            }
            if (stackOrig.stackSize == 0)
            { 
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (stackOrig.stackSize == stackCopy.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(p, stackOrig);
        }

        return stackCopy;
        */
	
	} //end transfer function



}
