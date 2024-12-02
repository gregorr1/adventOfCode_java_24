import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga1 {
    public static List<String> getInput() {
        String path = "input/input1.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga1_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/1 */

        List<String> input = getInput();
        List<Integer> leftNumbers = new ArrayList<>();
        List<Integer> rightNumbers = new ArrayList<>();
        int sum = 0;

        // Create lists of numbers and sort them
        for (String line : input) {
            String[] stNumbers = new String[2];
            stNumbers = line.split("   ");
            leftNumbers.add(Integer.parseInt(stNumbers[0]));
            rightNumbers.add(Integer.parseInt(stNumbers[1]));
        }
        leftNumbers.sort(null);
        rightNumbers.sort(null);

        for (int i = 0; i < leftNumbers.size(); i++) {
            sum += Math.abs(leftNumbers.get(i) - rightNumbers.get(i));
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga1_2() {
        List<String> input = getInput();
        List<Integer> leftNumbers = new ArrayList<>();
        List<Integer> rightNumbers = new ArrayList<>();
        int score = 0;

        for (String line : input) {
            String[] stNumbers = new String[2];
            stNumbers = line.split("   ");
            leftNumbers.add(Integer.parseInt(stNumbers[0]));
            rightNumbers.add(Integer.parseInt(stNumbers[1]));
        }
        
        // For each left number, count the number of repetitions
        for (Integer leftNumber : leftNumbers) {
            int repetitions = 0;
            for (Integer rightNumber : rightNumbers) {
                if (leftNumber.intValue() == rightNumber.intValue()) {
                    repetitions++;
                }
            }
            score += leftNumber * repetitions;
        }

        System.out.println("The result is " + score);
    }
}
