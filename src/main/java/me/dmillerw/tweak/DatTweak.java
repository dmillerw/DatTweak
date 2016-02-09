package me.dmillerw.tweak;

import me.dmillerw.tweak.core.TweakLoader;
import me.dmillerw.tweak.core.network.PacketHandler;
import me.dmillerw.tweak.core.network.message.MessageServerConfig;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author dmillerw
 */
@Mod(modid = DatTweak.ID, name = DatTweak.ID, version = DatTweak.VERSION)
public class DatTweak {

    public static final String ID = "DatTweak";
    public static final String VERSION = "%MOD_VERSION%";

    @Mod.Instance(ID)
    public static DatTweak instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        TweakLoader.configuration = new Configuration(event.getSuggestedConfigurationFile());
        TweakLoader.configuration.load();

        TweakLoader.enable(true);

        if (TweakLoader.configuration.hasChanged())
            TweakLoader.configuration.save();

        PacketHandler.registerMessage(MessageServerConfig.Handler.class, MessageServerConfig.class, Side.CLIENT);
    }
}
