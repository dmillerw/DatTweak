package me.dmillerw.tweak.core.torch;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class EntityBlockItem extends EntityItem {

    public EntityBlockItem(World worldIn) {
        super(worldIn);
    }

    public EntityBlockItem(EntityItem entityItem) {
        super(entityItem.worldObj, entityItem.posX, entityItem.posY, entityItem.posZ, entityItem.getEntityItem());
        this.motionX = entityItem.motionX;
        this.motionY = entityItem.motionY;
        this.motionZ = entityItem.motionZ;
        this.lifespan = entityItem.lifespan;
        this.setDefaultPickupDelay();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.onGround) {
            final BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ));
            if (worldObj.getBlockState(pos).getBlock().isBlockSolid(worldObj, pos, EnumFacing.UP)) {
                final IBlockState state = Block.getBlockFromItem(getEntityItem().getItem()).getDefaultState();
                worldObj.setBlockState(pos.up(), state);
                this.setDead();
            }
        }
    }
}
