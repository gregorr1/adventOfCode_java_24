package Naloga6;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        Guard guard = new Guard(0, 0, input);

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '^') {
                    guard.setRow(i);
                    guard.setColumn(j);
                    break;
                }
            }
        }

        // while the object is inside bounds, we track all the fields it visits and output the final number
        while (guard.checkGrid(input)) {
            guard.move(input);
        }
        for (boolean[] line : guard.getVisited()) {
            for (boolean field : line) {
                if (field) {
                    sum++;
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga6_2() {
        List<String> input = getInput();
        int sum = 0;
        Guard guard = new Guard(0, 0, input);

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '^') {
                    guard.setRow(i);
                    guard.setColumn(j);;
                    break;
                }
            }
        }
        
        while (guard.checkGrid(input)) {
            // If the next field is '#' or already visited, we skip this, otherwise, we create a copy object with a new input that inserts a '#' on the object's next step, checking if this modification creates a loop. We must add together all possible input changes that result in a loop
            if (!guard.checkObstruction(input) && !guard.checkVisited(input)) {
                List<String> tempInput = new ArrayList<>(input);
                Guard tempGuard = new Guard(guard, tempInput);
                tempInput = tempGuard.modifyInput(tempInput);
                while(tempGuard.checkGrid(tempInput)) {
                    // The highest value a field on path can have is 111 (starting value is 0b1100000 so adding a bit once for each direction amounts to 0b1101111). Based on testing, this code works slightly faster than running bitwise AND to check for loops
                    if (tempGuard.getPath()[tempGuard.getRow()][tempGuard.getColumn()] > 111) {
                        sum++;
                        break;
                    }
                    tempGuard.move(tempInput);  
                }
            } 
            guard.move(input);
        }
        
        System.out.println("The result is " + sum);
    }
}