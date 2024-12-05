import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga5 {
    public static List<String> getInput() {
        String path = "input/input5.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga5_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/5 */

        List<String> input = getInput();
        int sum = 0;
        List<int[]> ordersList = new ArrayList<>();
        List<int[]> pagesList = new ArrayList<>();
        List<int[]> correctPagesList = new ArrayList<>();

        // Check for strings containing '|' or ',' and sort these strings in two lists 
        for (String string : input) {
            if (string.contains("|")) {
                String[] substrings = string.split("\\|");
                int[] order = new int[substrings.length];
                for (int i = 0; i < substrings.length; i++) {
                    try {
                        order[i] = Integer.parseInt(substrings[i]); 
                    } catch (Exception e) {
                        System.out.println("Cannot parse substring " + substrings[i]);
                    }
                }
                ordersList.add(order);
            } else if (string.contains(",")) {
                String[] substrings = string.split("\\,");
                int[] pages = new int[substrings.length];
                for (int i = 0; i < substrings.length; i++) {
                    try {
                        pages[i] = Integer.parseInt(substrings[i]); 
                    } catch (Exception e) {
                        System.out.println("Cannot parse substring " + substrings[i]);
                    }
                }
                pagesList.add(pages);
            }
        }

        for (int[] pages : pagesList) {
            if (checkPages(pages, ordersList)) {
                correctPagesList.add(pages);
            }
        }

        for (int[] correctPages : correctPagesList) {
            sum += correctPages[correctPages.length / 2];
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga5_2() {
        List<String> input = getInput();
        int sum = 0;
        List<int[]> ordersList = new ArrayList<>();
        List<int[]> pagesList = new ArrayList<>();
        List<int[]> incorrectPagesList = new ArrayList<>();
        List<int[]> reorderedPagesList = new ArrayList<>();

        for (String string : input) {
            if (string.contains("|")) {
                String[] substrings = string.split("\\|");
                int[] order = new int[substrings.length];
                for (int i = 0; i < substrings.length; i++) {
                    try {
                        order[i] = Integer.parseInt(substrings[i]); 
                    } catch (Exception e) {
                        System.out.println("Cannot parse substring " + substrings[i]);
                    }
                }
                ordersList.add(order);
            } else if (string.contains(",")) {
                String[] substrings = string.split("\\,");
                int[] pages = new int[substrings.length];
                for (int i = 0; i < substrings.length; i++) {
                    try {
                        pages[i] = Integer.parseInt(substrings[i]); 
                    } catch (Exception e) {
                        System.out.println("Cannot parse substring " + substrings[i]);
                    }
                }
                pagesList.add(pages);
            }
        }

        for (int[] pages : pagesList) {
            if (!checkPages(pages, ordersList)) {
                incorrectPagesList.add(pages);
            }
        }

        // Reoder all incorrect pages
        for (int[] incorrectPages : incorrectPagesList) {
            reorderedPagesList.add(reorderPages(incorrectPages, ordersList));
        }

        for (int[] reorderedPages : reorderedPagesList) {
            sum += reorderedPages[reorderedPages.length / 2];
        }

        System.out.println("The result is " + sum);
    }

    // Create a list of numbers that should not precede the given number and return false if any forbidden number does
    public static boolean checkPages (int[] pages, List<int[]> ordersList) {
        List<Integer> forbiddenNumbers = new ArrayList<>();
        for (Integer page : pages) {
            for (Integer forbidden : forbiddenNumbers) {
                if (forbidden.intValue() == page.intValue()) {
                    return false;
                }
            }
            for (int[] order : ordersList) {
                if (page.intValue() == order[1]) {
                    forbiddenNumbers.add(order[0]);
                }
            }
        }
        return true;
    }

    // Sort by swapping numbers in the array until reorderedPages is in correct order 
    public static int[] reorderPages (int[] incorrectPages, List<int[]> ordersList) {
        int[] reorderedPages = incorrectPages.clone();
        while (!checkPages(reorderedPages, ordersList)) {
            for (int i = 0; i < reorderedPages.length - 1; i++) {
                for (int[] order : ordersList) {
                    if (reorderedPages[i]  == order[1] && reorderedPages[i + 1] == order[0]) {
                        int temp = reorderedPages[i];
                        reorderedPages[i] = reorderedPages[i + 1];
                        reorderedPages[i + 1] = temp;
                    }
                }
            }
        }
        return reorderedPages;
    }
}
