package com.lothrazar.powerinventory;
 
//any final constants
public class Const
{ 
    public static final String MODID = "powerinventory";
    
	public static final String NBT_SLOT = "Slot";
	public static final String NBT_PLAYER = "Player";
	public static final String NBT_WORLD = "World";
	public static final String NBT_ID = "ID";
	public static final String NBT_Settings = "Settings";
	public static final String NBT_Unlocked = "Unlocked";
	public static final String NBT_INVENTORY = "Inventory";
	public static final String NBT_INVOSIZE = "invoSize";

	public static final int SQ = 18;//square , it means size of square texture. 2 letters so long code lines more readable

	//TODO: maybe a config setting so this can be 2x?
	public static final int SIZE_CRAFT = 3;//did not exist before, was magic'd as 2 everywhere
	public static final int SIZE_HOTBAR = 9; // never changes
	public static final int SIZE_ARMOR = 4;  // never changes
	
	//different types for the sort buttons
	public static final int SORT_LEFT = 1;
	public static final int SORT_RIGHT = 2;
	public static final int SORT_SMART = 7;
	public static final int SORT_LEFTALL = -1;
	public static final int SORT_RIGHTALL = -2;
}
