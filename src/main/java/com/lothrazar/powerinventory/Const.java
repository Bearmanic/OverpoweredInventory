package com.lothrazar.powerinventory;

import net.minecraft.item.ItemStack;
 
/** 
 * @author Sam Bassett aka Lothrazar
 */
public class Const
{ 
	public static final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
	
    public static final String MODID = "powerinventory";
    public static String INVENTORY_TEXTURE = "textures/gui/inventory_gui.png";
	public static int texture_width = 464;
	public static int texture_height = 382;
    
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int square = 18;
	public final static int hotbarSize = 9;
	public final static int armorSize = 4; 
 
	public static int MORE_ROWS;
	public static int MORE_COLS;
	public static int ALL_COLS;
	public static int ALL_ROWS;
	
	//its used like this  new ItemStack[sizeGridHotbar];
	
	public static int sizeGrid;//size of the main inner rectangle
	public static int sizeGridHotbar;//not armor
	
	//TODO is mainInventory arrays we want to contain the extars as well
	public static int sizeGridHotbarExtras; //still without armor
	
	

	//these are slot indices. different than slot numbers (important)
    public static final int enderPearlSlot = 77777; 
    public static final int enderChestSlot = enderPearlSlot+1;
    public static final int clockSlot = enderPearlSlot+2;
    public static final int compassSlot = enderPearlSlot+3;
    public static final int bottleSlot = enderPearlSlot+4;
    public static final int uncraftSlot = enderPearlSlot+5;// six extra slots
    public static final int extras = 6;
     
	
	public final static int SORT_LEFT = 1;
	public final static int SORT_RIGHT = 2;
	public final static int SORT_SMART = 7;
	public final static int SORT_LEFTALL = -1;
	public final static int SORT_RIGHTALL = -2;
}
