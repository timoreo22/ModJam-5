package com.srkw.tweakoni.events;

import java.util.Random;

import com.srkw.tweakoni.BlockRotate;
import com.srkw.tweakoni.network.PacketHandler;
import com.srkw.tweakoni.network.PacketRotate;
import com.srkw.tweakoni.network.PacketSendLoc;
import com.srkw.tweakoni.proxy.ClientProxy;
import com.srkw.tweakoni.utils.RayTrace;
import com.srkw.tweakoni.utils.handlers.ConfigHandler;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ClientEvents {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static World world = mc.world;
    private static boolean rotating = false;
    
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isRemote) {
            Item item = event.getItemStack().getItem();
            if (item instanceof ItemBlock && ClientProxy.block_below.isKeyDown()) {
                RayTraceResult result = RayTrace.rayTrace(event.getWorld(), event.getEntityPlayer(), false);
                if (result.sideHit == EnumFacing.UP) {
                    PacketHandler.INSTANCE.sendToServer(new PacketSendLoc());
                }
            }
        }
        
        
        
        
         //rotating = true;
         /*if(rotating) {
         System.out.println("rotate key have been pressed!");
         EntityPlayer player = event.getEntityPlayer();
         System.out.println("player get! name: " + player.getName() );
         RayTraceResult result2 = RayTrace.rayTrace(event.getWorld(), player, false);
         BlockPos bp = result2.getBlockPos();
         System.out.println("Block looking at: " + bp);
         IBlockState state = event.getWorld().getBlockState(bp);
         System.out.println("Block state : " + state);
         Block block = state.getBlock();
         System.out.println("The block is :" + block.getUnlocalizedName());
         IBlockState s = BlockRotate.rotateVanillaBlock(event.getWorld(), state, bp);
         try {
         boolean b = event.getWorld().setBlockState(bp,s);
         System.out.println("Block rotated state : " + s);
         System.out.println(b ? "This Block can't be rotated!":"This Block have Been rotated!");
         }catch(IllegalArgumentException e) {
        	 System.out.println("Bad Mouvement!");
         }
         } */
    }

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent.Post event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
            ScaledResolution resolution = event.getResolution();
            int x = resolution.getScaledWidth() / 100;
            int y = resolution.getScaledHeight() / 100;
            for (ItemStack stack : mc.player.inventory.armorInventory) {
                if (stack.getItem() instanceof ItemElytra) {
                    int i = stack.getMaxDamage() - stack.getItemDamage();
                    String text = "Elytra : " + i + " / " + stack.getMaxDamage();
                    int x1;
                    int y1;
                    switch (ConfigHandler.elytraPos) {
                        case 1:
                            x1 = x / 95;
                            y1 = y / 100;
                            mc.fontRenderer.drawStringWithShadow(text, x1, y1, 0xffffff);
                            break;
                        case 2:
                            x1 = x / 100;
                            y1 = y * 100;
                            mc.fontRenderer.drawStringWithShadow(text, x1, y1, 0xffffff);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }


@SideOnly(Side.CLIENT)
@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
public static void onEvent(KeyInputEvent event)
{
    // make local copy of key binding 
    KeyBinding rotate = ClientProxy.block_rotate;
   
     
     //System.out.println(rotating ? "The rotating is now on!":"The rotating is now off");
     if(rotate.isPressed()) {
     System.out.println("rotate key have been pressed!");
     EntityPlayer player = world.getPlayerEntityByName(mc.getSession().getUsername());
     //player.sendMessage(new TextComponentString(rotating ? "The rotating is now off!":"The rotating is now on"));
     System.out.println("player get! name: " + player.getName() );
     RayTraceResult result2 = RayTrace.rayTrace(world, player, false);
     BlockPos bp = result2.getBlockPos();
     PacketHandler.INSTANCE.sendToServer(new PacketRotate(bp));
     /*System.out.println("Block looking at: " + bp);
     IBlockState state = world.getBlockState(bp);
     System.out.println("Block state : " + state);
     Block block = state.getBlock();
     System.out.println("The block is :" + block.getUnlocalizedName());
     IBlockState s = BlockRotate.rotateVanillaBlock(world, state, bp);
     boolean b = world.setBlockState(bp,s);
     System.out.println("Block rotated state : " + s);
     System.out.println(b ? "This Block can't be rotated!":"This Block have Been rotated!");*/
     }
    
    
}

}
