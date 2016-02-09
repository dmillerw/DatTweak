package me.dmillerw.tweak.core;

import com.google.common.collect.Maps;
import me.dmillerw.tweak.core.network.message.MessageServerConfig;
import me.dmillerw.tweak.shift.TweakShift;
import me.dmillerw.tweak.torch.TweakTorchDrop;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * @author dmillerw
 */
public class TweakLoader {

    private static String CATEGORY = "tweaks";

    public static enum Type {
        TORCH_DROP(TweakTorchDrop.class),
        SHIFT(TweakShift.class);

        private Class<? extends Tweak> clazz;
        private Type(Class<? extends Tweak> clazz) {
            this.clazz = clazz;
        }

        public Tweak newInstance() {
            try {
                return clazz.newInstance();
            } catch (Exception ex) {
                return null;
            }
        }
    }

    public static Configuration configuration;
    private static final EnumMap<Type, Tweak> tweaks = Maps.newEnumMap(Type.class);
    private static final EnumSet<Type> enabledTweaks = EnumSet.noneOf(Type.class);

    static {
        for (TweakLoader.Type type : TweakLoader.Type.values()) {
            Tweak tweak = type.newInstance();
            if (tweak != null)
                tweaks.put(type, tweak);
        }
    }

    public static void enable(boolean callMethod) {
        enabledTweaks.clear();
        for (Map.Entry<Type, Tweak> entry : tweaks.entrySet()) {
            if (configuration.get(CATEGORY, entry.getKey().name(), true).getBoolean(true)) {
                enabledTweaks.add(entry.getKey());
            }
            if (callMethod)
                entry.getValue().register();
        }
    }

    public static Set<Type> getEnabledTweaks() {
        return enabledTweaks;
    }

    public static boolean isTweakEnabled(Type type) {
        return enabledTweaks.contains(type);
    }

    @SideOnly(Side.CLIENT)
    public static void handleServerConfig(MessageServerConfig message) {
        if (message.reset) {
            TweakLoader.enable(false);
        } else {
            enabledTweaks.clear();
            enabledTweaks.addAll(message.tweaks);
        }
    }
}
