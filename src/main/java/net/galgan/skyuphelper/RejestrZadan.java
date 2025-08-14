package net.galgan.skyuphelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class RejestrZadan {
    private static final Gson GSON = new Gson();
    private static Map<String, String> QUESTS = Map.of();

    public static void load() {
        try (var in = RejestrZadan.class.getResourceAsStream("/assets/skyuphelper/zadania.json");
             var reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            QUESTS = GSON.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
            QUESTS = Map.of(); // safe fallback
        }
    }

    public static String get(String displayName) {
        return QUESTS.get(displayName); // simplest version; add normalization later if needed
    }

    private RejestrZadan() {}
}
