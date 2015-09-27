package com.lothrazar.powerinventory;

import net.minecraft.init.Items;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Logger;

import com.lothrazar.powerinventory.network.*;
import com.lothrazar.powerinventory.proxy.CommonProxy; 

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
/**
 * @author https://github.com/Funwayguy/InfiniteInvo
 * @author Initially Forked and altered by https://github.com/PrinceOfAmber/InfiniteInvo
 * before later being merged into my main project
 */
@Mod(modid = Const.MODID, useMetadata=true)
public class ModInv
{
	//??POSSIBLE additions? 
	
	//a back button in inventory. shows only IF we use I, then back goes back into where we were??
	
	// on item pickup (pearl/chest) put it in the special slot by default-if possible
	//shift click out of hotbar should go directly to special slots, work same way as armor
	// left/right buttons could merge stacks ? OR add a middle button that does some sort of merge/sort?

	 
	//idea: liquid//potion storage? bucket slot, fillButton, drainButton, and a # showing whats stored (leaves empty behind)
		//but only one type of lq at a time, and have a max /64
	
	//Display exact exp numbers? such as  450/5200 = for next level (90453= total)  
 
	@Instance(Const.MODID)
	public static ModInv instance;
	
	@SidedProxy(clientSide = "com.lothrazar.powerinventory.proxy.ClientProxy", serverSide = "com.lothrazar.powerinventory.proxy.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
	public static VersionChecker versionChecker ;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
    	
		ModConfig.config = new Configuration(event.getSuggestedConfigurationFile(), true);
		ModConfig.loadConfig();

    	
    	int packetID = 0;
    	network.registerMessage(EnderChestPacket.class,  EnderChestPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(SortButtonPacket.class,  SortButtonPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(FilterButtonPacket.class,FilterButtonPacket.class,packetID++, Side.SERVER);
    	network.registerMessage(EnderPearlPacket.class,  EnderPearlPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(ExpButtonPacket.class,   ExpButtonPacket.class,   packetID++, Side.SERVER);
    	network.registerMessage(DumpButtonPacket.class,  DumpButtonPacket.class,  packetID++, Side.SERVER);
    	network.registerMessage(UncButtonPacket.class,   UncButtonPacket.class,   packetID++, Side.SERVER);
    	network.registerMessage(SoloGuiPacket.class,     SoloGuiPacket.class,     packetID++, Side.SERVER);
    	
    	proxy.registerHandlers();
		//MinecraftForge.EVENT_BUS.register(instance);
		//FMLCommonHandler.instance().bus().register(instance);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	 NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	
    	 //then when you need to 
    	 //player.openGui(TutorialMain.instance, guiID, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    	 
    	if(ModConfig.enderPearl64)
    	{
    		Items.ender_pearl.setMaxStackSize(64);
    	}
    }
  

    
    @EventHandler
    public void postInit(FMLPostInitializationEvent  event)
    {
    	versionChecker = new VersionChecker();
    	Thread versionCheckThread = new Thread(versionChecker, "Version Check");
    	versionCheckThread.start();
    }
}
