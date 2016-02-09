package me.dmillerw.tweak.wiggle;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;

import java.util.Map;

/**
 * @author dmillerw
 */
public class StateManager {

    private static Map<String, State> stateMap = Maps.newHashMap();
    public static void updateState(EntityPlayer entityPlayer, State facing) {
        stateMap.put(entityPlayer.getName(), facing);
    }

    public static State getState(EntityPlayer entityPlayer) {
        State state = stateMap.get(entityPlayer.getName());
        if (state == null) {
            state = new State();
            updateState(entityPlayer, state);
        }

        return stateMap.get(entityPlayer.getName());
    }

    public static State onWiggle(EntityPlayer entityPlayer) {
        State state = getState(entityPlayer);

        if (entityPlayer.isSneaking()) {
            state.offset = !state.offset;
        } else {
            if (state.facing == null) {
                state.facing = EnumFacing.DOWN;
            } else if (state.facing == EnumFacing.EAST) {
                state.facing = null;
            } else {
                state.facing = EnumFacing.values()[state.facing.ordinal() + 1];
            }
        }

        return state;
    }

    public static class State {

        public boolean offset;
        public EnumFacing facing;
    }
}
