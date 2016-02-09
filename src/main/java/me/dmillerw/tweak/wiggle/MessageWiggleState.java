package me.dmillerw.tweak.wiggle;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author dmillerw
 */
public class MessageWiggleState implements IMessage {

    public boolean offset = false;
    public EnumFacing facing = null;

    @Override
    public void fromBytes(ByteBuf buf) {
        buf.writeBoolean(offset);
        if (facing == null) {
            buf.writeBoolean(false);
        } else {
            buf.writeBoolean(true);
            buf.writeInt(facing.ordinal());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        offset = buf.readBoolean();
        if (buf.readBoolean()) {
            facing = EnumFacing.values()[buf.readInt()];
        } else {
            facing = null;
        }
    }

    public static class Handler implements IMessageHandler<MessageWiggleState, IMessage> {

        @Override
        public IMessage onMessage(final MessageWiggleState message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    final StateManager.State state = new StateManager.State();
                    state.offset = message.offset;
                    state.facing = message.facing;
                    StateManager.updateState(FMLClientHandler.instance().getClientPlayerEntity(), state);
                }
            });
            return null;
        }
    }
}
