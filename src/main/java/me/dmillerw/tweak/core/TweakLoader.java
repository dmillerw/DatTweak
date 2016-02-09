package me.dmillerw.tweak.core;

import com.google.common.collect.Maps;
import me.dmillerw.tweak.core.network.message.MessageServerConfig;
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

    public static Configuration configuration;
    private static final EnumMap<Tweak.Type, Tweak> tweaks = Maps.newEnumMap(Tweak.Type.class);
    private static final EnumSet<Tweak.Type> enabledTweaks = EnumSet.noneOf(Tweak.Type.class);

    static {
        for (Tweak.Type type : Tweak.Type.values()) {
            Tweak tweak = type.newInstance();
            if (tweak != null)
                tweaks.put(type, tweak);
        }
    }

    public static void enable(boolean callMethod) {
        enabledTweaks.clear();
        for (Map.Entry<Tweak.Type, Tweak> entry : tweaks.entrySet()) {
            if (configuration.get(CATEGORY, entry.getKey().name(), true).getBoolean(true)) {
                enabledTweaks.add(entry.getKey());
            }
            if (callMethod)
                entry.getValue().register();
        }
    }

    public static Set<Tweak.Type> getEnabledTweaks() {
        return enabledTweaks;
    }

    public static boolean isTweakEnabled(Tweak.Type type) {
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
