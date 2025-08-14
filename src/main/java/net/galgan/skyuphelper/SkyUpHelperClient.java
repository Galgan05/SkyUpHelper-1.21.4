package net.galgan.skyuphelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.Set;

public class SkyUpHelperClient implements ClientModInitializer {

    private static final Set<String> NazwyEtapow = Set.of(
            "\uE003\uE150\uE002Wyzwania",
            "\uE001\uE151\uE002Wyzwania » Nowicjusz",
            "\uE001\uE152\uE002Wyzwania » Wtajemniczony",
            "\uE001\uE153\uE002Wyzwania » Zaawansowany",
            "\uE001\uE154\uE002Wyzwania » Znawca",
            "\uE001\uE155\uE002Wyzwania » Ekspert",
            "\uE001\uE156\uE002Wyzwania » Mistrz",
            "\uE001\uE157\uE002Wyzwania » Guru",
            "\uE001\uE158\uE002Wyzwania » Legenda",
            "\uE001\uE159\uE002Wyzwania » Wirtuoz",
            "\uE001\uE15A\uE002Wyzwania » Bonus"
    );

    @Override
    public void onInitializeClient() {
        RejestrZadan.load();

        ScreenEvents.BEFORE_INIT.register((client, screen, w, h) -> {
            if (screen instanceof HandledScreen<?> handled) {
                //Detect if not inside a valid GUI
                if (!NazwyEtapow.contains(handled.getTitle().getString())) return;
                // Register per-screen listeners
                ScreenMouseEvents.beforeMouseClick(screen).register((s, mouseX, mouseY, button) -> {
                    Slot slot = ScreenUtil.getSlotAt(handled, mouseX, mouseY); // accessor below
                    if (slot != null) {
                        if (button == 2) {
                            // MIDDLE click inside container
                            onContainerMiddleClick(handled, slot);
                        }
                    }
                });
            }
        });
    }

    private void onContainerMiddleClick(HandledScreen<?> screen, Slot slot) {
        ItemStack stack = slot.getStack();
        String description = RejestrZadan.get(stack.getName().getString());
        MinecraftClient.getInstance().inGameHud.getChatHud().addMessage(Text.literal(description));
    }
}
