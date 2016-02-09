package me.dmillerw.tweak.core;

import com.google.common.collect.Sets;
import me.dmillerw.tweak.core.torch.TweakTorchDrop;
import net.minecraftforge.common.config.Configuration;

import java.util.Set;

/**
 * @author dmillerw
 */
public class TweakLoader {

    private static String CATEGORY = "tweaks";

    public static Configuration configuration;
    private static final Set<Tweak> tweaks = Sets.newHashSet();

    static {
        tweaks.add(new TweakTorchDrop());
    }

    public static void init() {
        for (Tweak t : tweaks) {
            if (configuration.getBoolean(getName(t), CATEGORY, true, "")) {
                t.init();
            }
        }
    }

    private static String getName(Tweak tweak) {
        final StringBuilder builder = new StringBuilder();
        String name = tweak.getClass().getSimpleName().substring(5);

        for (int i=0; i<name.length(); i++) {
            final char c = name.charAt(i);
            if (i != 0 && Character.isUpperCase(c)) {
                builder.append("_");
            }
            builder.append(Character.toLowerCase(c));
        }

        return builder.toString();
    }
}
