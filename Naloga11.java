import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Naloga11 {
    public static List<String> getInput() {
        String path = "input/input11.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga11_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/11 */

        String input = getInput().get(0);
        List<Long> listStones = new ArrayList<>();
        int sum = 0;
        int blinks = 25;
        
        // Create the initial list of stones 
        String[] substrings = input.split(" ");
        for (String string : substrings) {
            try {
                listStones.add(Long.parseLong(string));
            } catch (Exception e) {
                System.out.println("Input is not a number.");
            }
        }

        for (Long stone : listStones) {
            sum += replaceNumber(stone, 1, blinks);
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga11_2() {
        String input = getInput().get(0);
        Map<Long, Long> mapStones = new HashMap<Long, Long>();
        long sum = 0;
        int blinks = 75;
        
        // Create the initial map of stones. Due to the limited number of possible stones, it is much more efficient to have map of stones with stone numbers being the keys and stone quantities being the values.
        String[] substrings = input.split(" ");
        for (String string : substrings) {
            try {
                mapStones.put(Long.parseLong(string), mapStones.getOrDefault(Long.parseLong(string), 0L) + 1);
            } catch (Exception e) {
                System.out.println("Input is not a number.");
            }
        }

        for (int i = 0; i < blinks; i++) {
            Map<Long, Long> mapNewStones = new HashMap<Long, Long>();

            // Each stone in the map of existing stones is mapped to the map of new stones by searching if the corresponding new stone already exists and just increasing its quantity or adding a new stone. 
            mapStones.forEach((key, value) -> {
                if (key == 0) {
                    mapNewStones.put(1L, value);
                } else if ((int)Math.log10(key) % 2 != 0) {
                    String strKey = String.valueOf(key);
                    try {
                        Long newKeyA = Long.parseLong(strKey.substring(0, (strKey.length() / 2)));
                        Long newKeyB = Long.parseLong(strKey.substring((strKey.length() / 2), strKey.length()));
                        mapNewStones.put(newKeyA, mapNewStones.getOrDefault(newKeyA, 0L) + value);
                        mapNewStones.put(newKeyB, mapNewStones.getOrDefault(newKeyB, 0L) + value);
                    } catch (Exception e) {
                        System.out.println("Cannot parse input number.");
                    }
                } else {
                    mapNewStones.put(key * 2024, value);
                }
            });
            mapStones = mapNewStones;
        }
        
        // As we cannot access the sum variable from mapStones.forEach, a new list of map values is created and all values are added to the sum in the next step
        List<Long> values = new ArrayList<>();
        mapStones.forEach((key, value) -> {
            values.add(value);
        });
        for (Long val : values) {
            sum += val;
        }
       
        System.out.println("The result is " + sum);
    }

    // A function that takes a number and splits it in two parts. This only gets called on numbers with even number of digits, so that both resulting numbers have the same number of digits.
    public static long[] splitNumber(long num) {
        String strNum = String.valueOf(num);
        long[] arrNum = new long[2];
        try {            
            arrNum[0] = Long.parseLong(strNum.substring(0, (strNum.length() / 2)));
            arrNum[1] = Long.parseLong(strNum.substring((strNum.length() / 2), strNum.length()));   
        } catch (Exception e) {
            System.out.println("Cannot parse input number.");
        }
        return arrNum;
    }

    // Recursive, "brute-force" function that creates a list of all resulting tones from the number of blinks. It works for the first part of the exercise due to the smaller number of blinks, but such calculation is not suitable for 40-50 blinks or more as it is too time consuming. 
    public static long replaceNumber(long num, long stones, int blinks) {
        if (blinks > 0) {
            if (num == 0) {
                stones = replaceNumber(1, stones, blinks - 1);
            } else if ((int)Math.log10(num) % 2 != 0) {
                long[] arrNum = splitNumber(num);
                stones++;
                stones = replaceNumber(arrNum[0], stones, blinks - 1);
                stones = replaceNumber(arrNum[1], stones, blinks - 1);
            } else {
                stones = replaceNumber(num * 2024, stones, blinks - 1);
            }
        }
        return stones;
    } 
}
