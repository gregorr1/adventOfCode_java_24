import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Naloga8 {
    public static List<String> getInput() {
        String path = "input/input8.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga8_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/8 */

        List<String> input = getInput();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        Set<Character> antennas = new HashSet<>();
        int sum = 0;
        char[][] matrix = new char[input.size()][input.get(0).length()];

        // Fill the frequency map only with antennas that occur at least twice. Also fill out a matrix the size of our grid where for now, all chars are '.'
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                matrix[i][j] = '.';
                char current = input.get(i).charAt(j);
                if (current != '.') {
                    frequencyMap.put(current, frequencyMap.getOrDefault(current, 0) + 1);
                    if (frequencyMap.get(current) != 1) {
                        antennas.add(current);
                    }
                }
            }
        }

        // Iterate through the grid to find antennas. Compare other antenna coordinates with current i and j to calculate antinodes.
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (antennas.contains(input.get(i).charAt(j))) {
                    List<int[]> listCoordinates = scanMap(input, input.get(i).charAt(j));
                    for (int[] coordinates : listCoordinates) {
                        if (coordinates[0] != i || coordinates[1] != j) {
                            try {
                                matrix[i - (coordinates[0] - i)][j - (coordinates[1] - j)] = '#';
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        
        for (char[] line : matrix) {
            for (char character : line) {
                if (character == '#') {
                    sum++;
                }
            }
        }
     
        System.out.println("The result is " + sum);
    }

    public static void naloga8_2() {
        List<String> input = getInput();
        Map<Character, Integer> frequencyMap = new HashMap<>();
        Set<Character> antennas = new HashSet<>();
        int sum = 0;
        char[][] matrix = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                matrix[i][j] = '.';
                char current = input.get(i).charAt(j);
                if (current != '.') {
                    frequencyMap.put(current, frequencyMap.getOrDefault(current, 0) + 1);
                    if (frequencyMap.get(current) != 1) {
                        antennas.add(current);
                    }
                }
            }
        }

        // Immediately mark fields with relevant antennas as '#'. Also, a while loop is added that keeps adding new antinodes until we reach the borders of the grid.
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (antennas.contains(input.get(i).charAt(j))) {
                    matrix[i][j] = '#';
                    List<int[]> listCoordinates = scanMap(input, input.get(i).charAt(j));
                    for (int[] coordinates : listCoordinates) {
                        if (coordinates[0] != i || coordinates[1] != j) {
                            int n = 1;
                            int newI = i - (coordinates[0] - i);
                            int newJ = j - (coordinates[1] - j);
                            while (newI >= 0 && newI < input.size() && newJ >= 0 && newJ < input.get(i).length()) {
                                matrix[newI][newJ] = '#';
                                n++;
                                newI = i - n * (coordinates[0] - i);
                                newJ = j - n * (coordinates[1] - j);
                            }
                        }
                    }
                }
            }
        }
        
        for (char[] line : matrix) {
            for (char character : line) {
                if (character == '#') {
                    sum++;
                }
            }
        }
     
        System.out.println("The result is " + sum);
    }

    // A function that returns a list of coordinate pairs of all occurences of a given character
    public static List<int[]> scanMap (List<String> input, char searchedChar) {
        List<int[]> listCoordinates = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == searchedChar) {
                    int[] coordinates = {i, j};
                    listCoordinates.add(coordinates);
                }
            }
        }
        return listCoordinates;
    }
}