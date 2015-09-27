package com.lothrazar.powerinventory;

import net.minecraft.item.ItemStack;
 
//any final constants
public class Const
{ 
	public static final int craftSize = 3;//did not exist before, was magic'd as 2 everywhere
	
    public static final String MODID = "powerinventory";
    
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int square = 18;
	public static final int hotbarSize = 9;
	public static final int armorSize = 4;  
	
	//different types for the sort buttons
	public static final int SORT_LEFT = 1;
	public static final int SORT_RIGHT = 2;
	public static final int SORT_SMART = 7;
	public static final int SORT_LEFTALL = -1;
	public static final int SORT_RIGHTALL = -2;
}
