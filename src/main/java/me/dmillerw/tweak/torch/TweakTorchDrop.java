package me.dmillerw.tweak.torch;

import me.dmillerw.tweak.DatTweak;
import me.dmillerw.tweak.core.Tweak;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * @author dmillerw
 */
public class TweakTorchDrop implements Tweak {

    @Override
    public void initialize() {
        MinecraftForge.EVENT_BUS.register(new TorchEventHandler());
        EntityRegistry.registerModEntity(EntityBlockItem.class, "blockItem", 1, DatTweak.instance, 64, 64, true);
    }
}
