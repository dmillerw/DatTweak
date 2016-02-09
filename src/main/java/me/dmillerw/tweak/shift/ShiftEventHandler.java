package me.dmillerw.tweak.shift;

import me.dmillerw.tweak.core.util.RenderUtil;
import net.minecraft.client.Minecraft;
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

/**
 * @author dmillerw
 */
public class ShiftEventHandler {

    public static class State {
        public boolean offset;
        public EnumFacing facing;
    }

    private static State state = new State();
    private static boolean ignoreNext = false;

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (TweakShift.KEY_SHIFT.isPressed()) {
            final EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();
            if (player.isSneaking()) {
                state.offset = !state.offset;
            } else {
                if (state.facing == null) {
                    state.facing = EnumFacing.DOWN;
                } else if (state.facing == EnumFacing.EAST) {
                    state.facing = null;
                } else {
                    state.facing = EnumFacing.values()[state.facing.ordinal() + 1];
                }
            }
        }
    }

    @SubscribeEvent
    public void onBlockInteract(PlayerInteractEvent event) {
        if (!event.world.isRemote)
            return;

        if (ignoreNext && event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            ignoreNext = false;
            return;
        }

        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)
            return;

        final ItemStack held = event.entityPlayer.getHeldItem();
        if (held == null || !(held.getItem() instanceof ItemBlock))
            return;

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

        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, held, pos, opposite, mc.objectMouseOver.hitVec)) {
            mc.thePlayer.swingItem();
            ignoreNext = true;
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

        if (state == null || state.facing == null)
            return;

        BlockPos pos;
        if (state.offset) {
            pos = event.target.getBlockPos().offset(event.target.sideHit).offset(state.facing);
        } else {
            pos = event.target.getBlockPos().offset(state.facing);
        }

        RenderUtil.drawBoundingBox(entityPlayer, pos, event.partialTicks);
    }
}
