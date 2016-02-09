package me.dmillerw.tweak.wiggle;

import me.dmillerw.tweak.core.Tweak;
import me.dmillerw.tweak.core.network.PacketHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.input.Keyboard;

/**
 * @author dmillerw
 */
public class TweakWiggle implements Tweak {

    public static KeyBinding KEY_WIGGLE = new KeyBinding("key.wiggle", Keyboard.KEY_G, "key.misc");

    @Override
    public void initialize() {
        MinecraftForge.EVENT_BUS.register(new WiggleEventHandler());
        PacketHandler.registerMessage(MessageWiggleKey.Handler.class, MessageWiggleKey.class, Side.SERVER);
        PacketHandler.registerMessage(MessageWiggleState.Handler.class, MessageWiggleState.class, Side.CLIENT);
    }
}
