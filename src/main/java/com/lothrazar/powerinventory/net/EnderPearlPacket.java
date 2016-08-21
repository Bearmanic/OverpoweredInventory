package com.lothrazar.powerinventory.net;
import com.lothrazar.powerinventory.*;
import com.lothrazar.powerinventory.CapabilityRegistry.IPlayerExtendedProperties;
import com.lothrazar.powerinventory.inventory.InventoryOverpowered;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EnderPearlPacket implements IMessage, IMessageHandler<EnderPearlPacket, IMessage> {
  public EnderPearlPacket() {
  }
  NBTTagCompound tags = new NBTTagCompound();
  public EnderPearlPacket(NBTTagCompound ptags) {
    tags = ptags;
  }
  @Override
  public void fromBytes(ByteBuf buf) {
    tags = ByteBufUtils.readTag(buf);
  }
  @Override
  public void toBytes(ByteBuf buf) {
    ByteBufUtils.writeTag(buf, this.tags);
  }
  @Override
  public IMessage onMessage(EnderPearlPacket message, MessageContext ctx) {
    EntityPlayer player = ctx.getServerHandler().playerEntity;
    IPlayerExtendedProperties prop = CapabilityRegistry.getPlayerProperties(player);

    InventoryOverpowered invo = UtilPlayerInventoryFilestorage.getPlayerInventory(player);
    ItemStack pearls = invo.getStackInSlot(Const.SLOT_EPEARL);
    if (pearls != null) {
      World world = player.worldObj;
      EntityEnderPearl entityenderpearl = new EntityEnderPearl(world, player);
      entityenderpearl.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
      world.spawnEntityInWorld(entityenderpearl);
      ModInv.playSound(player, SoundEvents.ENTITY_ARROW_SHOOT);
      if (player.capabilities.isCreativeMode == false) {
        invo.decrStackSize(Const.SLOT_EPEARL, 1);
      }
    }
    return null;
  }
}
