package me.dmillerw.tweak.torch;

import me.dmillerw.tweak.DatTweak;
import me.dmillerw.tweak.core.Tweak;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * @author dmillerw
 */
public class TweakTorchDrop extends Tweak {

    private static boolean isTorch(Block block) {
        return block == Blocks.torch || block == Blocks.redstone_torch || block == Blocks.unlit_redstone_torch;
    }

    @Override
    public void register() {
        super.register();
        EntityRegistry.registerModEntity(EntityBlockItem.class, "blockItem", 1, DatTweak.instance, 64, 64, true);
    }

    @SubscribeEvent
    public void onItemDrop(ItemTossEvent event) {
        if (!enabled())
            return;

        if (event.player.worldObj.isRemote)
            return;

        ItemStack item = event.entityItem.getEntityItem();
        if (item != null && isTorch(Block.getBlockFromItem(item.getItem()))) {
            if (event.player.isSneaking()) {
                EntityBlockItem entity = new EntityBlockItem(event.entityItem);
                event.entityItem.worldObj.spawnEntityInWorld(entity);
                event.entityItem.setDead();
            }
        }
    }
}
