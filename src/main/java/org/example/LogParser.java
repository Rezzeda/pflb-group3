package org.example;
import java.util.List;

public class LogParser {
    public void parseLog(List<String> lines) {
        int dayCount = 1;
        boolean isNewDay = false;

        System.out.println("День " + dayCount);
        for (String line : lines) {
            if (line.contains("AM") && !isNewDay) {
                dayCount++;
                isNewDay = true;
                System.out.println("\nДень " + dayCount);
            } else if (line.contains("PM")) {
                isNewDay = false;
            }

            if (!line.contains("Cloud bot")) {
                System.out.println(line);
            }
        }
    }
}
