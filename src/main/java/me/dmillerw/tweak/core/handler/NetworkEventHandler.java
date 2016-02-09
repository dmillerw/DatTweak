package me.dmillerw.tweak.core.handler;

import me.dmillerw.tweak.core.network.PacketHandler;
import me.dmillerw.tweak.core.network.message.MessageServerConfig;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author dmillerw
 */
public class NetworkEventHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // Send config packet from server
        PacketHandler.INSTANCE.sendTo(new MessageServerConfig(), (EntityPlayerMP) event.player);
    }


}
