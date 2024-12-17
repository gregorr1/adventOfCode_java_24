package Naloga6;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga6 {
    public static List<String> getInput() {
        String path = "input/input6.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga6_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/6 */

        List<String> input = getInput();
        int sum = 0;
        Guard guard = new Guard(input);

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '^') {
                    guard.setLine(i);
                    guard.setColumn(j);
                    break;
                }
            }
        }

        guard.move(input);
        for (char[] line : guard.getPath()) {
            for (char field : line) {
                if (field == 'X') {
                    sum++;
                }
            }
        }

        System.out.println("The result is " + sum);
    }
}