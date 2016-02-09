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

    @Override
    public void initialize() {
        MinecraftForge.EVENT_BUS.register(new ShiftEventHandler());
    }
}
