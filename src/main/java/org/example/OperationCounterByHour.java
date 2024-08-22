package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationCounterByHour {

    public void countOperationsByHour(List<String> lines) {
        int dayCount = 1;
        boolean isNewDay = false;
        String currentTime = null;

        Map<String, List<String>> operationsByHour = new HashMap<>();

        System.out.println("День " + dayCount);

        for (String line : lines) {
            if (line.contains("AM") && !isNewDay) {
                printOperationsByHour(operationsByHour, dayCount);

                dayCount++;
                operationsByHour.clear();
                isNewDay = true;
                System.out.println("\nДень " + dayCount);
            } else if (line.contains("PM")) {
                isNewDay = false;
            }

            if (line.matches(".*\\d{1,2}:\\d{2}\\s*(AM|PM).*")) {
                currentTime = line.trim();
            }

            if (!line.contains("Cloud bot") && !line.trim().isEmpty() && !line.matches(".*\\d{1,2}:\\d{2}.*")) {
                if (currentTime != null) {
                    operationsByHour.computeIfAbsent(currentTime, k -> new ArrayList<>()).add(line);
                }
            }
        }
        printOperationsByHour(operationsByHour, dayCount);
    }

    // Метод для вывода операций по времени
    private void printOperationsByHour(Map<String, List<String>> operationsByHour, int dayCount) {
        //System.out.println("Операции в день " + dayCount + " по времени:");
        for (Map.Entry<String, List<String>> entry : operationsByHour.entrySet()) {
            String time = entry.getKey();
            List<String> operations = entry.getValue();
            System.out.println(time + ":");
            for (String operation : operations) {
                System.out.println("  - " + operation);
            }
        }
    }
}