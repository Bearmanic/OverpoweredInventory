package com.lothrazar.powerinventory;

import net.minecraft.util.ResourceLocation;
 
public class Const
{ 
    public static final String MODID = "powerinventory";
    public static final ResourceLocation emptyBoots = new ResourceLocation("textures/items/empty_armor_slot_boots.png");
    public static final ResourceLocation emptyLegs = new ResourceLocation("textures/items/empty_armor_slot_leggings.png");  
    public static final ResourceLocation emptyChest = new ResourceLocation("textures/items/empty_armor_slot_chestplate.png");
    public static final ResourceLocation emptyHelmet = new ResourceLocation("textures/items/empty_armor_slot_helmet.png");

	public static ResourceLocation slot = new ResourceLocation(Const.MODID,"textures/gui/inventory_slot.png");


	public static final int SQ = 18;
	public static final int HOTBAR_SIZE = 9;
	public static final int ARMOR_SIZE = 4; 
	public static final int ROWS_VANILLA = 3; 
	public static final int COLS_VANILLA = 9; 
	public static final int VSIZE = ROWS_VANILLA*COLS_VANILLA; //36
	
	//width of the black box with the character in it (with the armor slots and stuff too)
	public static final int WIDTH_CHARARMOR = 72; 
 
	//public static int INVOSIZE;

	//these are slot indices. different than slot numbers (important)
    public static final int enderPearlSlot = 77777; 
    public static final int enderChestSlot = enderPearlSlot+1;
    
	
	
	

	public final static int padding = 6;
	public final static int paddingLrg = 8; 
}
