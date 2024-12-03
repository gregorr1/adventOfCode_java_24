import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Naloga3 {
    public static List<String> getInput() {
        String path = "input/input3.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga3_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/3 */

        List<String> input = getInput();
        long sum = 0;

        // Create a regex to find mul(x,y)
        for (String line : input) {
            Pattern pattern = Pattern.compile("mul\\(\\d+,\\d+\\)");
            Matcher matcher = pattern.matcher(line);
            int startIndex = 0;
            boolean found = matcher.find(startIndex);
            
            // Find all instances of mul(x,y) and parse integers from results, multiplying both sides and adding them up 
            while (found) {
                startIndex = matcher.start();
                String result = matcher.group();
                String[] strNumbers = result.split(",");
                int[] numbers = new int[2];
                for (int i = 0; i < numbers.length; i++) {
                    try {
                        numbers[i] = Integer.parseInt(strNumbers[i].replaceAll("[^0-9]", ""));
                    } catch (Exception e) {
                        System.out.println("Error, cannot parse " + strNumbers[i]);
                        return;
                    }
                }
                sum += numbers[0] * numbers[1];
                found = matcher.find(startIndex + 1);
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga3_2() {
        List<String> input = getInput();
        long sum = 0;
        boolean enabled = true;

        // Create a regex to find either mul(x,y), do() or don't()
        for (String line : input) {
            String regex = "(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don\\'t\\(\\))";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            int startIndex = 0;
            boolean found = matcher.find(startIndex);
            
            while (found) {
                startIndex = matcher.start();
                String result = matcher.group();
                // If result is do() or don't(), enable or disable respectively
                if (result.equals("do()")) {
                    enabled = true;
                } else if (result.equals("don't()")) {
                    enabled = false;
                } else {
                    String[] strNumbers = result.split(",");
                    int[] numbers = new int[2];
                    for (int i = 0; i < numbers.length; i++) {
                        try {
                            numbers[i] = Integer.parseInt(strNumbers[i].replaceAll("[^0-9]", ""));
                        } catch (Exception e) {
                            System.out.println("Error, cannot parse " + strNumbers[i]);
                            return;
                        }
                    }
                    // Multiply and add up only if enabled
                    if (enabled) {
                        sum += numbers[0] * numbers[1];
                    }
                }
                found = matcher.find(startIndex + 1);
            }
        }

        System.out.println("The result is " + sum);
    }
}
