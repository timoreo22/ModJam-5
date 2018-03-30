package com.srkw.tweakoni.events;

import com.srkw.tweakoni.network.PacketHandler;
import com.srkw.tweakoni.network.PacketSendLoc;
import com.srkw.tweakoni.proxy.ClientProxy;
import com.srkw.tweakoni.utils.RayTrace;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InteractEvent {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
        Item item = event.getItemStack().getItem();
        if (item instanceof ItemBlock && ClientProxy.alt.isPressed()){
            RayTraceResult result = RayTrace.rayTrace(event.getWorld(),event.getEntityPlayer(),false);
            if (result.sideHit.equals(EnumFacing.UP)){
                PacketHandler.INSTANCE.sendToServer(new PacketSendLoc());
            }
        }
    }
}
