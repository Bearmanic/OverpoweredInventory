package com.lothrazar.powerinventory.inventory.button;
import com.lothrazar.powerinventory.net.SwapInvoPacket;
import com.lothrazar.powerinventory.ModInv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiButtonRotate extends GuiButton implements IGuiTooltip {
  private int invoGroup;
  private String tooltip;
  public GuiButtonRotate(int buttonId, int x, int y, int ig) {
    super(buttonId, x, y, 8, 20, ""+ig);// ig for test
    invoGroup = ig;
    this.setTooltip(ModInv.lang("tooltip.swap") +" ["+ invoGroup+"]");
  }
  @SideOnly(Side.CLIENT)
  @Override
  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    if (pressed) {
      NBTTagCompound tags = new NBTTagCompound();
      tags.setInteger("i", invoGroup);
      ModInv.instance.network.sendToServer(new SwapInvoPacket(tags));
    }
    return pressed;
  }
  @Override
  public String getTooltip() {
    return tooltip;
  }
  @Override
  public void setTooltip(String s) {
    tooltip = s;
  }
}
