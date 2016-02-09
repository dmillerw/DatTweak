package me.dmillerw.tweak.wiggle;

import me.dmillerw.tweak.core.network.PacketHandler;
import me.dmillerw.tweak.core.util.RenderUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author dmillerw
 */
public class WiggleEventHandler {

    @SubscribeEvent
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
            return;

        final ItemStack held = event.entityPlayer.getHeldItem();
        if (held == null || !(held.getItem() instanceof ItemBlock))
            return;

        final StateManager.State state = StateManager.getState(event.entityPlayer);
        if (state == null || state.facing == null)
            return;

        event.setCanceled(true);

        BlockPos pos;
        if (state.offset) {
            pos = event.pos.offset(event.face).offset(state.facing);
        } else {
            pos = event.pos.offset(state.facing);
        }
        final EnumFacing opposite = state.facing.getOpposite();

        held.onItemUse(event.entityPlayer, event.world, pos, opposite, 0, 0, 0);
    }

    @SubscribeEvent
    public void onPlayerLogoff(PlayerEvent.PlayerLoggedOutEvent event) {
        StateManager.updateState(event.player, null);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (TweakWiggle.KEY_WIGGLE.isPressed()) {
            final EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            PacketHandler.INSTANCE.sendToServer(new MessageWiggleKey());
        }
    }

    @SubscribeEvent
    public void onBlockHighlight(DrawBlockHighlightEvent event) {
        final EntityPlayer entityPlayer = FMLClientHandler.instance().getClientPlayerEntity();

        if (event.target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
            return;

        final ItemStack held = entityPlayer.getHeldItem();
        if (held == null || !(held.getItem() instanceof ItemBlock))
            return;

        final StateManager.State state = StateManager.getState(entityPlayer);
        if (state == null || state.facing == null)
            return;

        event.setCanceled(true);

        BlockPos pos;
        if (state.offset) {
            pos = event.target.getBlockPos().offset(event.target.sideHit).offset(state.facing);
        } else {
            pos = event.target.getBlockPos().offset(state.facing);
        }

        RenderUtil.drawBoundingBox(entityPlayer, pos, event.partialTicks);
    }
}
