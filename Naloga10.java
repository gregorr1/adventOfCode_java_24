import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Naloga10 {
    public static List<String> getInput() {
        String path = "input/input10.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga10_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/10 */

        List<String> input = getInput();
        int sum = 0;

        // Iterate through input and for every '0' found, run findNext(). All results are added to a list and then converted to a set to get all distinct reachable summits.
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '0') {
                    List<Integer> listSummits = new ArrayList<>();
                    findNext(input, listSummits, i, j, 0);
                    Set<Integer> setSummits = new HashSet<>(listSummits);
                    sum += setSummits.size();
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga10_2() {
        List<String> input = getInput();
        int sum = 0;

        // Iterate through input and for every '0' found, run findNext(). All results are added to a list to get all possible paths to reachable summits.
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '0') {
                    List<Integer> listSummits = new ArrayList<>();
                    findNext(input, listSummits, i, j, 0);
                    sum += listSummits.size();
                }
            }
        }
        
        System.out.println("The result is " + sum);
    }

    // Recursive function that checks the surroundings and looks for a value higher exactly by 1. It recurses until a summit is found, then adds this summit to a list, calculating its ID with its rowIndex and columnIndex. If the same summit can be reached via different paths, same summit ID will be added multiple times.
    public static void findNext(List<String> input, List<Integer> listSummits, int rowIndex, int columnIndex, int currentValue) {
        int summitHeight = 9;
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            if (i == rowIndex) {
                for (int j = columnIndex - 1; j <= columnIndex + 1; j += 2) {
                    try {
                        if (input.get(i).charAt(j) - '0' == currentValue + 1) {
                            if (currentValue + 1 == summitHeight) {
                                listSummits.add(i * 1000 + j);
                            } else {
                                findNext(input, listSummits, i, j, currentValue + 1);
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            } else {
                try {
                    if (input.get(i).charAt(columnIndex) - '0' == currentValue + 1) {
                        if (currentValue + 1 == summitHeight) {
                            listSummits.add(i * 1000 + columnIndex);
                        } else {
                            findNext(input, listSummits, i, columnIndex, currentValue + 1);
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }
}
