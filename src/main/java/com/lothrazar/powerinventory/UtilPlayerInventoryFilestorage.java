package com.lothrazar.powerinventory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import com.google.common.io.Files;
import com.lothrazar.powerinventory.inventory.InventoryOverpowered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

/*Thank you so much for the help azanor
 * for basically writing this class and releasing it open source
 * 
 * https://github.com/Azanor/Baubles
 * 
 * which is under Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-NC-SA 3.0) license.
 * so i was able to use parts of that to make this
 * **/
public class UtilPlayerInventoryFilestorage {
  private static HashMap<String, InventoryOverpowered> playerItems = new HashMap<String, InventoryOverpowered>();
  public static void clearPlayerInventory(EntityPlayer player) {
    playerItems.remove(player.getDisplayNameString());
  }
  public static InventoryOverpowered getPlayerInventory(EntityPlayer player) {
    if (!playerItems.containsKey(player.getDisplayNameString())) {
      InventoryOverpowered inventory = new InventoryOverpowered(player);
      playerItems.put(player.getDisplayNameString(), inventory);
    }
    return playerItems.get(player.getDisplayNameString());
  }
  public static void setPlayerInventory(EntityPlayer player, InventoryOverpowered inventory) {
    playerItems.put(player.getDisplayNameString(), inventory);
  }
  public static void loadPlayerInventory(EntityPlayer player, File file1, File file2) {
    if (player != null && !player.worldObj.isRemote) {
      try {
        NBTTagCompound data = null;
        boolean save = false;
        if (file1 != null && file1.exists()) {
          try {
            FileInputStream fileinputstream = new FileInputStream(file1);
            data = CompressedStreamTools.readCompressed(fileinputstream);
            fileinputstream.close();
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
        if (file1 == null || !file1.exists() || data == null || data.hasNoTags()) {
          ModInv.logger.warn("Data not found for " + player.getDisplayNameString() + ". Trying to load backup data.");
          if (file2 != null && file2.exists()) {
            try {
              FileInputStream fileinputstream = new FileInputStream(file2);
              data = CompressedStreamTools.readCompressed(fileinputstream);
              fileinputstream.close();
              save = true;
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
        if (data != null) {
          InventoryOverpowered inventory = new InventoryOverpowered(player);
          inventory.readFromNBT(data);
          playerItems.put(player.getDisplayNameString(), inventory);
          if (save)
            savePlayerItems(player, file1, file2);
        }
      }
      catch (Exception e) {
        ModInv.logger.error("Error loading player extended inventory");
        e.printStackTrace();
      }
    }
  }
  public static void savePlayerItems(EntityPlayer player, File file1, File file2) {
    if (player != null && !player.worldObj.isRemote) {
      try {
        if (file1 != null && file1.exists()) {
          try {
            Files.copy(file1, file2);
          }
          catch (Exception e) {
            ModInv.logger.error("Could not backup old file for player " + player.getDisplayNameString());
          }
        }
        try {
          if (file1 != null) {
            InventoryOverpowered inventory = getPlayerInventory(player);
            NBTTagCompound data = new NBTTagCompound();
            inventory.writeToNBT(data);
            FileOutputStream fileoutputstream = new FileOutputStream(file1);
            CompressedStreamTools.writeCompressed(data, fileoutputstream);
            fileoutputstream.close();
          }
        }
        catch (Exception e) {
          ModInv.logger.error("Could not save file for player " + player.getDisplayNameString());
          e.printStackTrace();
          if (file1.exists()) {
            try {
              file1.delete();
            }
            catch (Exception e2) {
            }
          }
        }
      }
      catch (Exception exception1) {
        ModInv.logger.error("Error saving inventory");
        exception1.printStackTrace();
      }
    }
  }
}
