package org.example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class OperationCounter {
//    public void countOperations(List<String> lines) {
//        int dayCount = 1;
//        int operationCount = 0;
//        boolean isNewDay = false;
//
//        System.out.println("День " + dayCount);
//
//        for (String line : lines) {
//            if (line.contains("AM") && !isNewDay) {
//                System.out.println("Количество операций в день " + dayCount + ": " + operationCount);
//
//                dayCount++;
//                operationCount = 0;
//                isNewDay = true;
//                System.out.println("\nДень " + dayCount);
//            } else if (line.contains("PM")) {
//                isNewDay = false;
//            }
//
//            if (!line.contains("Cloud bot") && !line.trim().isEmpty() && !line.matches(".*\\d{1,2}:\\d{2}.*")) {
//                operationCount++;
//                //раскомментить если нужно посмотреть операции в день
//                System.out.println(line);
//            }
//        }
//
//        System.out.println("Количество операций в день " + dayCount + ": " + operationCount);
//    }
//}

public class OperationCounter {
    public void countOperations(List<String> lines) {
        int dayCount = 1;
        int operationCount = 0;
        boolean isNewDay = false;
        String currentTime = null;

        System.out.println("День " + dayCount);

        for (String line : lines) {
            if (line.contains("AM") && !isNewDay) {
                System.out.println("Количество операций в день " + dayCount + ": " + operationCount);

                dayCount++;
                operationCount = 0;
                isNewDay = true;
                System.out.println("\nДень " + dayCount);
            } else if (line.contains("PM")) {
                isNewDay = false;
            }

            // Если строка содержит время, сохраняем его
            if (line.matches(".*\\d{1,2}:\\d{2}\\s*(AM|PM)?")) {
                currentTime = line.trim();
            }

            // Если строка является операцией, выводим её вместе с последним найденным временем
            if (!line.contains("Cloud bot") && !line.trim().isEmpty() && !line.matches(".*\\d{1,2}:\\d{2}.*")) {
                operationCount++;
                if (currentTime != null) {
                    System.out.println(currentTime + " - " + line.trim());
                } else {
                    System.out.println(line.trim());
                }
            }
        }

        System.out.println("Количество операций в день " + dayCount + ": " + operationCount);
    }
}