package me.dmillerw.tweak.torch;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author dmillerw
 */
public class TorchEventHandler {

    private boolean isTorch(Block block) {
        return block == Blocks.torch || block == Blocks.redstone_torch || block == Blocks.unlit_redstone_torch;
    }

    @SubscribeEvent
    public void onItemDrop(ItemTossEvent event) {
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
