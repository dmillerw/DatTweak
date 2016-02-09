package me.dmillerw.tweak.core;

import me.dmillerw.tweak.shift.TweakShift;
import me.dmillerw.tweak.sign.TweakPassthroughSigns;
import me.dmillerw.tweak.torch.TweakTorchDrop;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public abstract class Tweak {

    public static enum Type {
        TORCH_DROP(TweakTorchDrop.class),
        SHIFT(TweakShift.class),
        PASSTHROUGH_SIGNS(TweakPassthroughSigns.class);

        private Class<? extends Tweak> clazz;
        private Type(Class<? extends Tweak> clazz) {
            this.clazz = clazz;
        }

        public Tweak newInstance() {
            try {
                Tweak tweak = clazz.newInstance();
                if (tweak != null)
                    tweak.selfType = this;
                return tweak;
            } catch (Exception ex) {
                return null;
            }
        }
    }

    private Type selfType;

    public void register() {
        if (isEventHandler())
            MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean isEventHandler() {
        return true;
    }

    public final boolean enabled() {
        return TweakLoader.isTweakEnabled(selfType);
    }
}
