package net.galgan.skyuphelper;

public class QuestList {

    public static String questList(String currentQuest)  {

        String questDescription;

        switch (currentQuest) {
            case "» Wirtuoz piekła «":
                questDescription = "» Wirtuoz piekła «\nZbierz:\n» Netherrack: 15000";
                return questDescription;
            case "» Leśny potwór «":
                questDescription = "» Leśny potwór «\n" +
                        "Zbierz:\n" +
                        "» Tropikalny pień: 6000\n" +
                        "» Bladodębowy pień: 6000\n" +
                        "» Brzozowy pień: 6000\n" +
                        "» Świerkowy pień: 18000\n" +
                        "» Dębowy pień: 6000\n" +
                        "» Ciemnodębowy pień: 6000\n" +
                        "» Akacjowy pień: 6000";
                return questDescription;
            default:
                return "";
        }
    }
}
