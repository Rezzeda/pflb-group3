package org.example;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UniqueOperationCounter {

    public void countUniqueOperations(List<String> lines) {
        int dayCount = 1;
        boolean isNewDay = false;
        Map<String, Integer> operationCountMap = new HashMap<>();

        System.out.println("День " + dayCount);

        for (String line : lines) {
            if (line.contains("AM") && !isNewDay) {
                printOperationCounts(operationCountMap, dayCount);

                dayCount++;
                operationCountMap.clear();
                isNewDay = true;
                System.out.println("\nДень " + dayCount);
            } else if (line.contains("PM")) {
                isNewDay = false;
            }

            if (!line.contains("Cloud bot") && !line.trim().isEmpty() && !line.matches(".*\\d{1,2}:\\d{2}.*") && containsCyrillic(line)) {
                String operation = extractOperation(line);
                operationCountMap.put(operation, operationCountMap.getOrDefault(operation, 0) + 1);
            }
        }

        printOperationCounts(operationCountMap, dayCount);
    }

    private boolean containsCyrillic(String str) {
        return str.chars().anyMatch(ch -> Character.UnicodeBlock.of(ch) == Character.UnicodeBlock.CYRILLIC);
    }

    private String extractOperation(String line) {
        Pattern pattern = Pattern.compile("(команда создана в графане|команда создана|подписка для команды создана|запустил test|стартовал grafana platform|перешел в status\\s\\w+|другая операция|зарегистрировался)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group();
        }

        return line;
    }

    private void printOperationCounts(Map<String, Integer> operationCountMap, int dayCount) {
        for (Map.Entry<String, Integer> entry : operationCountMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " раз");
        }
    }
}
