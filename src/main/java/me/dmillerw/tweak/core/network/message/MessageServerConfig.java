package me.dmillerw.tweak.core.network.message;

import io.netty.buffer.ByteBuf;
import me.dmillerw.tweak.core.Tweak;
import me.dmillerw.tweak.core.TweakLoader;
import me.dmillerw.tweak.core.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author dmillerw
 */
public class MessageServerConfig implements IMessage {

    public static void sendServerConfig(EntityPlayer player) {
        final MessageServerConfig message = new MessageServerConfig();
        message.tweaks.addAll(TweakLoader.getEnabledTweaks());
        PacketHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }

    public static void sendReset(EntityPlayer player) {
        final MessageServerConfig message = new MessageServerConfig();
        message.reset = true;
        PacketHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }

    public boolean reset = false;
    public Set<Tweak.Type> tweaks = EnumSet.noneOf(Tweak.Type.class);

    @Override
    public void toBytes(ByteBuf buf) {
        if (reset) {
            buf.writeBoolean(true);
            return;
        }

        buf.writeInt(tweaks.size());
        for (Tweak.Type type : tweaks)
            buf.writeInt(type.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        if (buf.readBoolean()) {
            reset = true;
            return;
        }

        final int size = buf.readInt();
        for (int i=0; i<size; i++) {
            tweaks.add(Tweak.Type.values()[buf.readInt()]);
        }
    }

    public static class Handler implements IMessageHandler<MessageServerConfig, IMessage> {

        @Override
        public IMessage onMessage(final MessageServerConfig message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TweakLoader.handleServerConfig(message);
                }
            });
            return null;
        }
    }
}
