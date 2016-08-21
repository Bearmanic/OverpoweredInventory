package com.lothrazar.powerinventory;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import com.google.common.io.Files;
import com.lothrazar.powerinventory.inventory.InventoryOverpowered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;

public class EventExtendedInventory {
  //	public static final boolean dropOnDeath = false;
  static HashSet<Integer> playerEntityIds = new HashSet<Integer>();
  @SubscribeEvent
  public void playerLoggedInEvent(PlayerLoggedInEvent event) {
    Side side = FMLCommonHandler.instance().getEffectiveSide();
    if (side == Side.SERVER) {
      EventExtendedInventory.playerEntityIds.add(event.player.getEntityId());
    }
  }
  public static void syncItems(EntityPlayer player) {
    int size = InventoryOverpowered.INV_SIZE;
    for (int a = 0; a < size; a++) {
      UtilPlayerInventoryFilestorage.getPlayerInventory(player).syncSlotToClients(a);
    }
  }
  @SubscribeEvent
  public void playerTick(PlayerEvent.LivingUpdateEvent event) {
    // player events
    if (event.getEntity() instanceof EntityPlayer) {
      EntityPlayer player = (EntityPlayer) event.getEntity();
      if (!playerEntityIds.isEmpty() && playerEntityIds.contains(player.getEntityId())) {
        syncItems(player);
        playerEntityIds.remove(player.getEntityId());
      }
    }
  }
  //	@SubscribeEvent
  //	public void playerDeath(PlayerDropsEvent event) {
  //		if(dropOnDeath == false){
  //			return;
  //		}
  //		//else drop on death is true, so do it
  //		Entity entity = event.getEntity();
  //		World world = entity.getEntityWorld();
  //		
  //		if (entity instanceof EntityPlayer && !world.isRemote && !world.getGameRules().getBoolean("keepInventory")) {
  //			UtilPlayerInventoryFilestorage.getPlayerInventory(event.getEntityPlayer()).dropItemsAt(event.getDrops(), event.getEntityPlayer());
  //		}
  //	}
  @SubscribeEvent
  public void playerLoad(PlayerEvent.LoadFromFile event) {
    UtilPlayerInventoryFilestorage.clearPlayerInventory(event.getEntityPlayer());
    File playerFile = getPlayerFile(ext, event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString());
    if (!playerFile.exists()) {
      File fileNew = event.getPlayerFile(ext);
      if (fileNew.exists()) {
        try {
          Files.copy(fileNew, playerFile);
          ModInv.logger.info("Using and converting UUID  savefile for " + event.getEntityPlayer().getDisplayNameString());
          fileNew.delete();
          File fb = event.getPlayerFile(extback);
          if (fb.exists())
            fb.delete();
        }
        catch (IOException e) {
        }
      }
    }
    UtilPlayerInventoryFilestorage.loadPlayerInventory(event.getEntityPlayer(), playerFile, getPlayerFile(extback, event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()));
    EventExtendedInventory.playerEntityIds.add(event.getEntityPlayer().getEntityId());
  }
  final String ext = "opi";
  final String extback = "opibackup";
  public File getPlayerFile(String suffix, File playerDirectory, String playername) {
    //	if ("dat".equals(suffix))
    //throw new IllegalArgumentException("The suffix 'dat' is reserved");
    return new File(playerDirectory, "_" + playername + "." + suffix);
  }
  @SubscribeEvent
  public void playerSave(PlayerEvent.SaveToFile event) {
    UtilPlayerInventoryFilestorage.savePlayerItems(event.getEntityPlayer(), getPlayerFile(ext, event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()), getPlayerFile(extback, event.getPlayerDirectory(), event.getEntityPlayer().getDisplayNameString()));
  }
}
