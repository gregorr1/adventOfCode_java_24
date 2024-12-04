import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Naloga4 {
    public static List<String> getInput() {
        String path = "input/input4.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga4_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/3 */

        List<String> input = getInput();
        int sum = 0;
        char[] searchedWord = new char[] {'X', 'M', 'A', 'S'};

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'X') {
                    sum += checkMatches(i, j, input, searchedWord);
                }
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga4_2() {
        List<String> input = getInput();
        int sum = 0;

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'A') {
                    if (checkMatchesForA(i, j, input)) {
                        sum++;
                    }
                }
            }
        }
        
        System.out.println("The result is " + sum);
    }
    
    public static int checkMatches (int lineNumber, int lineIndex, List<String> input, char[] searchedWord) {
        int matches = 0;
        boolean match = true;

        // Check going upward left
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber - i).charAt(lineIndex - i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going up
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber - i).charAt(lineIndex) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going upward right
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber - i).charAt(lineIndex + i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going right
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber).charAt(lineIndex + i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going downward right
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber + i).charAt(lineIndex + i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going down
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber + i).charAt(lineIndex) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going downward left
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber + i).charAt(lineIndex - i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        // Check going left
        for (int i = 0; i < searchedWord.length; i++) {
            try {
                if (input.get(lineNumber).charAt(lineIndex - i) != searchedWord[i]) {
                    match = false;
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                match = false;
                break;
            }
        }
        if (match) {
            matches++;
        } else {
            match = true;
        }

        return matches;
    }

    public static boolean checkMatchesForA (int lineNumber, int lineIndex, List<String> input) {
        try {
            if (input.get(lineNumber - 1).charAt(lineIndex - 1) == 'M') {
                if (input.get(lineNumber + 1).charAt(lineIndex + 1) == 'S') {
                    if (input.get(lineNumber + 1).charAt(lineIndex - 1) == 'S') {
                        if (input.get(lineNumber - 1).charAt(lineIndex + 1) == 'M') {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (input.get(lineNumber + 1).charAt(lineIndex - 1) == 'M') {
                        if (input.get(lineNumber - 1).charAt(lineIndex + 1) == 'S') {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else if (input.get(lineNumber - 1).charAt(lineIndex - 1) == 'S') {
                if (input.get(lineNumber + 1).charAt(lineIndex + 1) == 'M') {
                    if (input.get(lineNumber + 1).charAt(lineIndex - 1) == 'S') {
                        if (input.get(lineNumber - 1).charAt(lineIndex + 1) == 'M') {
                            return true;
                        } else {
                            return false;
                        }
                    } else if (input.get(lineNumber + 1).charAt(lineIndex - 1) == 'M') {
                        if (input.get(lineNumber - 1).charAt(lineIndex + 1) == 'S') {
                            return true;
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}
