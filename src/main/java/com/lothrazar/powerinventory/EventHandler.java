package com.lothrazar.powerinventory;
 
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.lothrazar.powerinventory.config.ModConfig;
import com.lothrazar.powerinventory.inventory.button.GuiButtonOpenInventory;
import com.lothrazar.powerinventory.inventory.button.GuiButtonRotate;
import com.lothrazar.powerinventory.net.EnderChestPacket;
import com.lothrazar.powerinventory.net.EnderPearlPacket;
import com.lothrazar.powerinventory.net.HotbarSwapPacket;
import com.lothrazar.powerinventory.proxy.ClientProxy;

import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler
{
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyEnderpearl.isPressed() )
        { 	     
        	 ModInv.instance.network.sendToServer( new EnderPearlPacket());   
        }  
        if(ClientProxy.keyEnderchest.isPressed())
        { 	      
        	 ModInv.instance.network.sendToServer( new EnderChestPacket());   
        }  
        if(ClientProxy.keyHotbar.isPressed())
        { 	      
        	 ModInv.instance.network.sendToServer( new HotbarSwapPacket());   
        }   
    }
	
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) 
	{
		if (event.modID.equals(Const.MODID)) ModConfig.syncConfig();
	}

	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event) // More reliable than on entity join
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(PlayerPersistProperty.get(player) == null)
			{
				PlayerPersistProperty.register(player);
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		if(event.wasDeath == false || //changing dimensions -> so always do it
				(ModConfig.persistUnlocksOnDeath && event.wasDeath))//or it was a death => maybe do it
		{
			PlayerPersistProperty.clonePlayerData(event.original, event.entityPlayer);
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.isRemote)
		{
			EntityPlayer p = (EntityPlayer)event.entityLiving;

			PlayerPersistProperty prop = PlayerPersistProperty.get(p);
			//the vanilla inventory stuff (incl hotbar hotbar) already drops by default
			for (int i = Const.V_INVO_SIZE+Const.HOTBAR_SIZE; i < prop.inventory.getSizeInventory(); ++i)  
	        {
				prop.inventory.dropStackInSlot(p, i);
	        }

			prop.inventory.dropStackInSlot(p, Const.SLOT_ECHEST);
			prop.inventory.dropStackInSlot(p, Const.SLOT_EPEARL);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiPostInit(InitGuiEvent.Post event)
	{
		if(event.gui == null){return;}//probably doesnt ever happen
		 
		
		if(event.gui instanceof net.minecraft.client.gui.inventory.GuiInventory)
		{
			//omg thanks so much to this guy
			//http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/mods-discussion/1390983-making-guis-scale-to-screen-width-height
			ScaledResolution res = new ScaledResolution( event.gui.mc);
	        
		   	int screenWidth = res.getScaledWidth();
		   	int screenHeight = res.getScaledHeight();
		   	 
			int button_id = 256;
			int padding = 10, h = 10, w = 20,x,y;
		 
			
			x = screenWidth/2  + Const.VWIDTH/2 - w;//align tight to top of inventory
			y = screenHeight/2 - Const.VHEIGHT/2 - 2*h - 1;
			event.buttonList.add(new GuiButtonOpenInventory(button_id++, x,y));
	
			padding = 5;
			h = 10;
			w = 10;

		    //position them exactly on players inventory
			x = screenWidth/2  + Const.VWIDTH/2 - w*6;
			y = screenHeight/2 - Const.VHEIGHT/2 + padding;
			
			event.buttonList.add(new GuiButtonRotate(button_id++,x,y, w,h,Const.STORAGE_3BOTRIGHT));

			x -= 2*w - padding;//move left
			
			event.buttonList.add(new GuiButtonRotate(button_id++,x,y, w,h,Const.STORAGE_2BOTLEFT));

			x -= 2*w - padding;//move left
			
			event.buttonList.add(new GuiButtonRotate(button_id++,x,y, w,h,Const.STORAGE_1TOPRIGHT));
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent//(priority = EventPriority.NORMAL)
    public void onRenderOverlay(RenderGameOverlayEvent event)
    {
		//force it to always show food - otherwise its hidden when riding a horse
		
		if(ModConfig.alwaysShowHungerbar)
		{
			GuiIngameForge.renderFood = true;
		}
    } 
}
