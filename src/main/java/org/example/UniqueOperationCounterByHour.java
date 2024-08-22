package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueOperationCounterByHour {

    public void countUniqueOperationsByHour(List<String> lines) {
        int dayCount = 1;
        boolean isNewDay = false;
        String currentHour = null;

        // Карта для хранения операций и их количества по часам
        Map<String, Map<String, Integer>> operationsByHour = new HashMap<>();

        System.out.println("=================== День " + dayCount + " ===================");

        for (String line : lines) {
            // Проверка на начало нового дня
            if (line.contains("AM") && !isNewDay) {
                printOperationCountsByHour(operationsByHour, dayCount);

                dayCount++;
                operationsByHour.clear();
                isNewDay = true;
                System.out.println("\n=================== День " + dayCount + " ===================");
            } else if (line.contains("PM")) {
                isNewDay = false;
            }

            // Если строка содержит время (AM/PM), сохраняем час (без минут)
            if (line.matches(".*\\d{1,2}:\\d{2}\\s*(AM|PM).*")) {
                currentHour = extractHour(line.trim());
            }

            // Если строка является операцией, увеличиваем счётчик для этой операции в текущем часу
            if (!line.contains("Cloud bot") && !line.trim().isEmpty() && !line.matches(".*\\d{1,2}:\\d{2}.*") && containsCyrillic(line)) {
                if (currentHour != null) {
                    String operation = extractOperation(line);
                    operationsByHour
                            .computeIfAbsent(currentHour, k -> new HashMap<>())
                            .merge(operation, 1, Integer::sum);
                }
            }
        }

        // Вывод операций по времени для последнего дня
        printOperationCountsByHour(operationsByHour, dayCount);
    }

    // Метод для извлечения только часа из строки времени
    private String extractHour(String time) {
        Pattern pattern = Pattern.compile("(\\d{1,2})\\:\\d{2}\\s*(AM|PM)");
        Matcher matcher = pattern.matcher(time);
        if (matcher.find()) {
            return matcher.group(1) + " " + matcher.group(2); // Возвращаем только час и AM/PM
        }
        return time;
    }

    // Метод для вывода операций по времени
    private void printOperationCountsByHour(Map<String, Map<String, Integer>> operationsByHour, int dayCount) {
//        System.out.println("Операции в день " + dayCount + " по часам:");
        for (Map.Entry<String, Map<String, Integer>> entry : operationsByHour.entrySet()) {
            String hour = entry.getKey();
            Map<String, Integer> operations = entry.getValue();
            System.out.println(hour + ":");
            for (Map.Entry<String, Integer> operationEntry : operations.entrySet()) {
                String operation = operationEntry.getKey();
                int count = operationEntry.getValue();
                System.out.println("  - " + operation + " (" + count + " раз)");
            }
        }
    }

    // Метод для проверки наличия кириллических символов
    private boolean containsCyrillic(String str) {
        return str.chars().anyMatch(ch -> Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC);
    }

    // Метод для извлечения операции из строки
    private String extractOperation(String line) {
        Pattern pattern = Pattern.compile("(команда создана в графане|команда создана|подписка для команды создана|запустил test|стартовал grafana platform|перешел в status\\s\\w+|другая операция|зарегистрировался)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group();
        }

        return line;
    }
}
