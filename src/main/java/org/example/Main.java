package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
public static void main(String[] args) {
    // Чтение файла
    List<String> logLines = readLogFile("3monthslog.txt");

    // Создание экземпляров классов
    LogParser logParser = new LogParser();
    OperationCounter operationCounter = new OperationCounter();
    UniqueOperationCounter uniqueOperationCounter = new UniqueOperationCounter();
    OperationCounterByHour operationCounterByHour = new OperationCounterByHour();
    UniqueOperationCounterByHour uniqueOperationCounterByHour = new UniqueOperationCounterByHour();

    // Запуск каждого из вариантов
//    System.out.println("=== Лог по дням ===");
//    logParser.parseLog(logLines);

//    System.out.println("\n=== Количество операций в день ===");
//    operationCounter.countOperations(logLines);

//    System.out.println("\n=== Уникальные операции по дням ===");
//    uniqueOperationCounter.countUniqueOperations(logLines);

    System.out.println("\n=== Количество операций в день по часам ===");
//    operationCounterByHour.countOperationsByHour(logLines);
    uniqueOperationCounterByHour.countUniqueOperationsByHour(logLines);

}

    private static List<String> readLogFile(String filePath) {
        Path logFilePath = Paths.get(filePath);
        try {
            return Files.readAllLines(logFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
