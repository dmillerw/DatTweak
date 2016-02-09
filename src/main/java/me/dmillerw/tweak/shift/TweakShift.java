package me.dmillerw.tweak.shift;

import me.dmillerw.tweak.core.Tweak;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

/**
 * @author dmillerw
 */
public class TweakShift implements Tweak {

    public static KeyBinding KEY_SHIFT = new KeyBinding("key.block_shift", Keyboard.KEY_G, "key.misc");
    private static ShiftEventHandler eventHandler = new ShiftEventHandler();

    @Override
    public void register() {
        MinecraftForge.EVENT_BUS.register(eventHandler);
    }
}
