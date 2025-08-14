package net.galgan.skyuphelper;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.galgan.skyuphelper.mixin.HandledScreenAccess;

public final class ScreenUtil {
    private ScreenUtil() {}

    public static Slot getSlotAt(HandledScreen<?> screen, double x, double y) {
        return ((HandledScreenAccess) (Object) screen).invokeGetSlotAt(x, y);
    }
}