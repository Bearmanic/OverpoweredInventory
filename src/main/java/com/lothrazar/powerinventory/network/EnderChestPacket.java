package com.lothrazar.powerinventory.network;

import com.lothrazar.powerinventory.ModConfig;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
/** 
 * @author Lothrazar at https://github.com/PrinceOfAmber
 */
public class EnderChestPacket implements IMessage , IMessageHandler<EnderChestPacket, IMessage>
{
	public EnderChestPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public EnderChestPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}
 
	@Override
	public IMessage onMessage(EnderChestPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		 
 		
		if( p.inventory.getStackInSlot(ModConfig.enderChestSlot) != null)
			p.displayGUIChest(p.getInventoryEnderChest());
		else 
			p.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("slot.enderchest")));
	 
	

		return null;
	}
}
