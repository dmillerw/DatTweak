package me.dmillerw.tweak.core.network;

import me.dmillerw.tweak.core.network.message.MessageServerConfig;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * @author dmillerw
 */
public class NetworkEventHandler {

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        MessageServerConfig.sendServerConfig(event.player);
    }

    @SubscribeEvent
    public void onPlayerLogoff(PlayerEvent.PlayerLoggedOutEvent event) {
        MessageServerConfig.sendReset(event.player);
    }
}
