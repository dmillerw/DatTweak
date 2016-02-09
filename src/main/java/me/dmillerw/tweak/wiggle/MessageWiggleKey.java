package me.dmillerw.tweak.wiggle;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author dmillerw
 */
public class MessageWiggleKey implements IMessage {

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<MessageWiggleKey, IMessage> {

        @Override
        public IMessage onMessage(MessageWiggleKey message, final MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    final EntityPlayer player = ctx.getServerHandler().playerEntity;
                    StateManager.State state = StateManager.onWiggle(player);

                    if (player.isSneaking()) {
                        if (state.offset) {
                            player.addChatMessage(new ChatComponentText("Offsettin' the wiggle"));
                        } else {
                            player.addChatMessage(new ChatComponentText("Keepin' the wiggle to a minimum"));
                        }
                    } else {
                        if (state.facing == null) {
                            player.addChatMessage(new ChatComponentText("Ceasing to wiggle"));
                        } else if (state.facing == EnumFacing.UP || state.facing == EnumFacing.DOWN) {
                            player.addChatMessage(new ChatComponentText("Wigglin' " + state.facing.getName().toLowerCase()));
                        } else {
                            player.addChatMessage(new ChatComponentText("Wigglin' to the " + state.facing.getName().toLowerCase()));
                        }
                    }
                }
            });
            return null;
        }
    }
}
