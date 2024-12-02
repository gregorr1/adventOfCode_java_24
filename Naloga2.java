import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga2 {
    public static List<String> getInput() {
        String path = "input/input2.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga2_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/2 */

        List<String> input = getInput();
        List<List<Integer>> reports = new ArrayList<>();
        int sum = 0;

        // Create lists of integers with the numbers from input
        for (String line : input) {
            List<Integer> levels = new ArrayList<>();
            String[] stLevels = line.split(" ");
            for (int i = 0; i < stLevels.length; i++) {
                levels.add(Integer.parseInt(stLevels[i]));
            }
            reports.add(levels);
        }

        // For each report, check if it's increasing or decreasing according to the instructions
        for (List<Integer> report : reports) {
            if (checkReport(report)) {
                sum++;
            }
        }

        System.out.println("The result is " + sum);       
    }

    public static void naloga2_2() {
        List<String> input = getInput();
        List<List<Integer>> reports = new ArrayList<>();
        int sum = 0;

        for (String line : input) {
            List<Integer> levels = new ArrayList<>();
            String[] stLevels = line.split(" ");
            for (int i = 0; i < stLevels.length; i++) {
                levels.add(Integer.parseInt(stLevels[i]));
            }
            reports.add(levels);
        }

        for (List<Integer> report : reports) {
            if (checkReport(report)) {
                sum++;
            } else {
                // If the report is not increasing or decreasing, repeat the check with each level removed from the current report
                for (int i = 0; i < report.size(); i++) {
                    List<Integer> dampenedReport = new ArrayList<>(report);
                    dampenedReport.remove(i);
                    if (checkReport(dampenedReport)) {
                        sum++;
                        break;
                    }
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    public static boolean checkReport(List<Integer> report) {
        boolean checkIncreasing = true;
        boolean checkDecreasing = true;
        for (int i = 0; i < report.size() - 1; i++) {
            if (report.get(i + 1) <= report.get(i) || report.get(i + 1) > report.get(i) + 3) {
                checkIncreasing = false;
            }
        }
        for (int i = 0; i < report.size() - 1; i++) {
            if (report.get(i + 1) >= report.get(i) || report.get(i + 1) < report.get(i) - 3) {
                checkDecreasing = false;
            }
        }
        if (checkIncreasing || checkDecreasing) {
            return true;
        } else {
            return false;
        }
    }
}