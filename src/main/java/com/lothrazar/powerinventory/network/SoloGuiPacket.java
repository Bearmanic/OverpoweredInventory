package com.lothrazar.powerinventory.network;

import com.lothrazar.powerinventory.GuiHandler;
import com.lothrazar.powerinventory.ModInv;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SoloGuiPacket implements IMessage , IMessageHandler<SoloGuiPacket, IMessage>
{
	public SoloGuiPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public SoloGuiPacket(NBTTagCompound ptags)
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
	public IMessage onMessage(SoloGuiPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		 
		p.openGui(ModInv.instance, GuiHandler.GUI_CUSTOM_INV, p.worldObj, (int) p.posX, (int) p.posY, (int) p.posZ);

 

		return null;
	}
}
