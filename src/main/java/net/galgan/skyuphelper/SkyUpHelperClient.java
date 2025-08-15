package net.galgan.skyuphelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.Set;

public class SkyUpHelperClient implements ClientModInitializer {

    private static final Set<String> questLevels = Set.of(
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

    private static String currentQuest;
    private static String questDescription;

    private static final Identifier EXAMPLE_LAYER_ID =
            Identifier.of("skyuphelper", "top_left_text");

    @Override
    public void onInitializeClient() {

        ScreenEvents.BEFORE_INIT.register((client, screen, w, h) -> {
            //Check if you're inside a container
            if (!(screen instanceof HandledScreen<?> handled)) return;
            //Check if the name of the container is valid
            if (!questLevels.contains(handled.getTitle().getString())) return;

            // Register per-screen listeners
            ScreenMouseEvents.beforeMouseClick(screen).register((s, mouseX, mouseY, button) -> {
                //Check if the button clicked was a middle button
                if (button != 2) return;
                //Check if you clicked a valid slot
                Slot slot = ScreenUtil.getSlotAt(handled, mouseX, mouseY);
                if (slot == null) return;

                //If every check passed, change the currentQuest to the selected one
                currentQuest = slot.getStack().getName().getString();
                questDescription = QuestList.questList(currentQuest);

                client.inGameHud.getChatHud().addMessage(net.minecraft.text.Text.literal("[SkyUpHelper] Wybrano zadanie: " + currentQuest));

            });
        });

        // Render just before the chat layer so it’s visible above most vanilla elements.
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer ->
                layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, EXAMPLE_LAYER_ID, SkyUpHelperClient::render));
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        //Initialize text renderer
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        //Stop if the item was not on the quest list
        if (questDescription == null) return;
        //Split the description into multiple lines
        String[] lines = questDescription.split("\n");

        //Draw the quest in the top-left corner.
        int x = 5;
        int y = 5;
        Text line;
        for (int i = 0; i < lines.length; i++) {
            if (i == 0) {
                line = Text.literal(lines[i]).formatted(Formatting.BOLD, Formatting.GOLD);
            } else {
                line = Text.literal(lines[i]).formatted(Formatting.AQUA);
            }

            context.drawTextWithShadow(tr, line, x, y, 0xFFFFFF);
            y += tr.fontHeight + 2;
        }
    }
}
