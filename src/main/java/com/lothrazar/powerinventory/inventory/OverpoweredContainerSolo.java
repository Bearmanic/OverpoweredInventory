package com.lothrazar.powerinventory.inventory;

import java.util.ArrayList;
import java.util.List;

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
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class OverpoweredContainerSolo extends Container // implements IOverpoweredContainer//TODO
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


	int S_BAR_START;
	int S_BAR_END;
	int S_MAIN_START;
	int S_MAIN_END;
	int S_ECHEST;
	int S_PEARL;
	int S_CLOCK;
	int S_COMPASS;
	int S_BOTTLE;
	int S_UNCRAFT;
	
	private EntityPlayer thePlayer;
	public OverpoweredContainerSolo(EntityPlayer player, InventoryPlayer playerInventory, OverpoweredInventorySolo inventoryCustom)
	{
		thePlayer = player;

		//IOverpoweredContainer self = this;
		OverpoweredContainerSolo self = this;

        int i,j,cx,cy;//rows and cols of vanilla, not extra
		/*
		self.S_ARMOR_START = self.getSlotCount();
        for (i = 0; i < Const.armorSize; ++i)
        {
        	cx = 8;
        	cy = 8 + i * Const.square;
            final int k = i;
 
            int size = Const.INVOSIZE + Const.hotbarSize;// playerInventory.getSizeInventory()
            self.addSlot(new Slot(playerInventory,  size - 1 - i, cx, cy)
            { 
            	public int getSlotStackLimit()
	            {
	                return 1;
	            }
	            public boolean isItemValid(ItemStack stack)
	            {
	                if (stack == null) return false;
	                return stack.getItem().isValidArmor(stack, k, thePlayer);
	            }
	            @SideOnly(Side.CLIENT)
	            public String getSlotTexture()
	            {
	                return ItemArmor.EMPTY_SLOT_NAMES[k];
	            }
            }); 
        }
        S_ARMOR_END = self.getSlotCount() - 1;*/
        
        S_BAR_START = self.getSlotCount();
        for (i = 0; i < Const.hotbarSize; ++i)
        { 
        	cx = 8 + i * Const.square;
        	cy = 142 + (Const.square * Const.MORE_ROWS);
 
        	self.addSlot(new Slot(playerInventory, i, cx, cy));
        }
        S_BAR_END = self.getSlotCount() - 1;
        
        
        S_MAIN_START = self.getSlotCount();
        int slotIndex = Const.hotbarSize;
        
        for( i = 0; i < Const.ALL_ROWS; i++)
		{
            for ( j = 0; j < Const.ALL_COLS; ++j)
            { 
            	cx = 8 + j * Const.square;
            	cy = 84 + i * Const.square;
            	self.addSlot(new Slot(playerInventory, slotIndex, cx, cy));
            	slotIndex++;
            }
        }
        S_MAIN_END = self.getSlotCount() - 1;
        
        
        S_PEARL =  self.getSlotCount() ;
        self.addSlot(new SlotEnderPearl(playerInventory, Const.enderPearlSlot, InventoryBuilder.pearlX, InventoryBuilder.pearlY));

        S_ECHEST =  self.getSlotCount() ;
        self.addSlot(new SlotEnderChest(playerInventory, Const.enderChestSlot, InventoryBuilder.echestX, InventoryBuilder.echestY)); 

        S_CLOCK =  self.getSlotCount();
        self.addSlot(new SlotClock(playerInventory, Const.clockSlot, InventoryBuilder.clockX, InventoryBuilder.clockY)); 

        S_COMPASS = self.getSlotCount() ;
        self.addSlot(new SlotCompass(playerInventory, Const.compassSlot, InventoryBuilder.compassX, InventoryBuilder.compassY)); 
        
        S_BOTTLE = self.getSlotCount() ;
        self.addSlot(new SlotBottle(playerInventory, Const.bottleSlot, InventoryBuilder.bottleX, InventoryBuilder.bottleY)); 
        
        S_UNCRAFT =self.getSlotCount() ;
        self.addSlot(new Slot(playerInventory, Const.uncraftSlot, InventoryBuilder.uncraftX, InventoryBuilder.uncraftY)); 

        
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{

		return true;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer p, int slotNumber)
	{
		//TODO: this is copied over from container, need a way to share, but it doesnt have crafting
		 

		//Thanks to coolAlias on the forums : 
		//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571051-custom-container-how-to-properly-override-shift
		//above is from 2013 but still relevant
        ItemStack stackCopy = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack())
        {
            ItemStack stackOrig = slot.getStack();
            stackCopy = stackOrig.copy();
            /*
            if (slotNumber == S_RESULT)  
            { 
            	//System.out.printf("\ntest result %d %d ___  ",S_MAIN_START,S_MAIN_END);
                if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))//was starting at S_BAR_START
                {
                    return null;
                }

                slot.onSlotChange(stackOrig, stackCopy);
            }
            else if (slotNumber >= S_CRAFT_START && slotNumber <= S_CRAFT_END) 
            { 
                if (!this.mergeItemStack(stackOrig,  S_BAR_START, S_MAIN_END, false))//was 9,45
                {
                    return null;
                }
            }
            else if (slotNumber >= S_ARMOR_START && slotNumber <= S_ARMOR_END) 
            { 
                if (!this.mergeItemStack(stackOrig, S_MAIN_START, S_MAIN_END, false))
                {
                    return null;
                }
            }
            else if (stackCopy.getItem() instanceof ItemArmor 
            		&& !((Slot)this.inventorySlots.get(S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType)).getHasStack()) // Inventory to armor
            { 
            	int j = S_ARMOR_START + ((ItemArmor)stackCopy.getItem()).armorType;
           
            	if (!this.mergeItemStack(stackOrig, j, j+1, false))
                {
                    return null;
                } 
            }
            else  */ if (slotNumber >= S_MAIN_START && slotNumber <= S_MAIN_END) // main inv grid
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
	
	} //end transfer function



}
