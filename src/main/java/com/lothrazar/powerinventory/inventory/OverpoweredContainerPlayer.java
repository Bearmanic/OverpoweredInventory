package com.lothrazar.powerinventory.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;
import com.lothrazar.powerinventory.Const;
import com.lothrazar.powerinventory.ModInv;
import com.lothrazar.powerinventory.inventory.slot.SlotBottle;
import com.lothrazar.powerinventory.inventory.slot.SlotClock;
import com.lothrazar.powerinventory.inventory.slot.SlotCompass;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderChest;
import com.lothrazar.powerinventory.inventory.slot.SlotEnderPearl;

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
	
	
	private final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
    private final EntityPlayer thePlayer;
 
	public OverpoweredInventoryPlayer invo;
    public boolean isLocalWorld;

	//these get used here for actual slot, and in GUI for texture
    //ender pearl is in the far bottom right corner, and the others move left relative to this


	
//store slot numbers  (not indexes) as we go. so that transferStack.. is actually readable

	
	public OverpoweredContainerPlayer(OverpoweredInventoryPlayer playerInventory, boolean isLocal, EntityPlayer player)
	{
		super(playerInventory, isLocal, player);
        this.thePlayer = player;
		inventorySlots = Lists.newArrayList();//undo everything done by super()
		craftMatrix = new InventoryCrafting(this, craftSize, craftSize);
 

		
        int i,j,cx,cy;//rows and cols of vanilla, not extra
        InventoryBuilder.S_RESULT = this.getSlotCount();
        this.addSlot(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 
        		200,  
        		40));
        
        InventoryBuilder.S_CRAFT_START = this.getSlotCount();
        for (i = 0; i < craftSize; ++i)
        { 
            for (j = 0; j < craftSize; ++j)
            {  
    			cx = 114 + j * Const.square ; 
    			cy = 20 + i * Const.square ;

    			this.addSlot(new Slot(this.craftMatrix, j + i * this.craftSize, cx , cy)); 
            }
        }
        InventoryBuilder.S_CRAFT_END = this.getSlotCount() - 1;
        
		//OverpoweredContainerPlayer self = this;
		InventoryBuilder.setupContainer(this,thePlayer,playerInventory);
		


        
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
// we COULD not empty it BUT then it gets erased on logout, etc
        for (int i = 0; i < craftSize*craftSize; ++i) // was 4
        {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null)
            {
                playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack)null);
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
		 
		IOverpoweredContainer self = this;
		
		return InventoryBuilder.transferStackInSlot(self, p, slotNumber);
		
    }
}
