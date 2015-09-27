package com.lothrazar.powerinventory;

import net.minecraftforge.common.config.Configuration;
 
public class ModConfig
{  
	public static Configuration config;
	
	//settings from the (public) config file
	
	public static boolean showSortButtons;
	public static boolean showCornerButtons;
	public static boolean showCharacter;
	public static boolean showMergeDeposit;
	public static boolean enderPearl64;
	
	public static int filterRange; 
	public static int expPerBottle;
	
	public static String smallMedLarge="med";
	
	
	
	//private configs, that is, not in the config file, so not user changeable Directly
 
	

    public static String INVENTORY_TEXTURE = "textures/gui/inventory_gui.png";
	public static int texture_width = 464;
	public static int texture_height = 382;
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
    public static int enderPearlSlot = sizeGridHotbarExtras - 1; //was just arbitrarily 77777
    public static int enderChestSlot = sizeGridHotbarExtras - 2;
    public static int clockSlot = sizeGridHotbarExtras - 3;
    public static int compassSlot = sizeGridHotbarExtras - 4;
    public static int bottleSlot = sizeGridHotbarExtras - 5;
    public static int uncraftSlot = sizeGridHotbarExtras - 6;// six extra slots
    public static int extras = 6;
     

	public static void loadConfig() 
	{
		
	
    	config.load();
    	 
    	String category = Configuration.CATEGORY_GENERAL;
		
    	ModConfig.filterRange = config.getInt("button_filter_range", category, 12, 1, 32, "Range of the filter button to reach nearby chests");
		ModConfig.showCharacter = config.getBoolean("show_character",category,true,"Show or hide the animated character text in the inventory");
		ModConfig.showSortButtons = config.getBoolean("move_inventory_buttons",category,true,"Show or hide the inventory shifting buttons << >>");
		ModConfig.showCornerButtons = config.getBoolean("show_corner_buttons",category,true,"Show or hide the corner inventory buttons in other GUI's");
		ModConfig.enderPearl64 = config.getBoolean("ender_pearl_64", category, true, "Stack to 64 instead of 16");
		ModConfig.showMergeDeposit = config.getBoolean("merge_deposit_buttons", category, true, "Show or hide the merge deposit buttons in upper right corner.");
		ModConfig.expPerBottle = config.getInt("exp_per_bottle", category, 10, 1, 11, "The exp cost of filling a single bottle.  Remember, the Bottle 'o Enchanting gives 3-11 experience when used, so it is never an exact two-way conversion.  ");
		  
		
		ModConfig.smallMedLarge = config.getString("normal_small", category, "normal", "Valid values are only exactly normal/small.  WARNING: BACKUP YOUR WORLD BEFORE CHANGING THIS.  Changes your inventory size, for use if your GUI Scale requirements are different.  normal = regular 15x25 inventory size, small = 6x18");
 
		//if(ModConfig.smallMedLarge == "large")//TODO: possible new feature 

		if(ModConfig.smallMedLarge.equalsIgnoreCase("normal"))
		{

			MORE_ROWS = 12;//texture 15x25
		 
			MORE_COLS = 16;

			texture_width = 464;
			texture_height = 382;
		    INVENTORY_TEXTURE = "textures/gui/inventory_15x25.png";//375 total
		}	
		else//assume its small
		{
			MORE_ROWS = 3;
		 
			MORE_COLS = 9;

			texture_width = 338;
			texture_height = 221;
		    INVENTORY_TEXTURE = "textures/gui/inventory_6x18.png";//6*18 is 108..so yeah?
		}

		ALL_COLS = 9 + MORE_COLS;
		ALL_ROWS = 3 + MORE_ROWS;
		
		sizeGrid  = ALL_COLS * ALL_ROWS;
		sizeGridHotbar = sizeGrid + Const.hotbarSize; 
		sizeGridHotbarExtras = sizeGridHotbar + extras;
		
		/*System.out.println(" sizeGrid  = "+sizeGrid);
		System.out.println(" sizeGridHotbar  = "+sizeGridHotbar);
		System.out.println(" sizeGridHotbarExtras  = "+sizeGridHotbarExtras);*/
		
		//i guess ender chest is at 121?
		enderPearlSlot = sizeGridHotbarExtras - 1; //was just arbitrarily 77777
	    enderChestSlot = sizeGridHotbarExtras - 2;
	    clockSlot = sizeGridHotbarExtras - 3;
	    compassSlot = sizeGridHotbarExtras - 4;
	    bottleSlot = sizeGridHotbarExtras - 5;
	    uncraftSlot = sizeGridHotbarExtras - 6;// six extra slots
		
		if(config.hasChanged()){config.save();}
	}
	
}
