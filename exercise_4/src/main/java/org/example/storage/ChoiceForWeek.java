package org.example.storage;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChoiceForWeek {

    private final Map<String, String> choices = new HashMap<>();

    public ChoiceForWeek(String mondayChoice, String tuesdayChoice, String wednesdayChoice, String thursdayChoice, String fridayChoice) {
        choices.put("Monday", mondayChoice);
        choices.put("Tuesday", tuesdayChoice);
        choices.put("Wednesday", wednesdayChoice);
        choices.put("Thursday", thursdayChoice);
        choices.put("Friday", fridayChoice);

        // Update the Storage overview of orders
        choices.forEach(this::updateOverview);
    }

    private void updateOverview(String day, String choice) {
        Storage.overview.get(day).put(choice, Storage.overview.get(day).getOrDefault(choice, 0) + 1);
    }

    public String getMonday() {
        String monday = choices.get("Monday");
        return new String(monday.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public String getTuesday() {
        String tuesday = choices.get("Tuesday");
        return new String(tuesday.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public String getWednesday() {
        String wednesday = choices.get("Wednesday");
        return new String(wednesday.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public String getThursday() {
        String thursday = choices.get("Thursday");
        return new String(thursday.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public String getFriday() {
        String friday = choices.get("Friday");
        return new String(friday.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
