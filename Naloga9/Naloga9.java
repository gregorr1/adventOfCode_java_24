package Naloga9;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga9 {
    public static List<String> getInput() {
        String path = "input/input9.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga9_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/9 */

        char[] arrayInput = getInput().get(0).toCharArray();
        List<ListElement> listInput = new ArrayList<>();
        boolean file = true;
        long sum = 0;
        int id = 0;

        for (char c : arrayInput) {
            if (Character.isDigit(c)) {
                int noOfElements = c - '0';

                // If "file" equals true, the given number of elements with the current id is added to listInput, otherwise that number of list elements with null id is added
                for (int i = 0; i < noOfElements; i++) {
                    if (file) {
                        listInput.add(new ListElement(id));
                    } else {
                        listInput.add(new ListElement());
                    }
                }
                if (file) {
                    id++;
                }
                // "file" should alternate between true and false in order to have empty spaces (null id) between two list elements with set ids
                file = !file;
            }            
        }

        int lastFileIndex = listInput.size() - 1;
        // Loop through listInput. If the element at the current index has null id, replace it with the last element with set id from listInput. Then, set lastFileIndex to be one less than the previous last element with set id. 
        for (int i = 0; i < listInput.size(); i++) {
            if (i > lastFileIndex) {
                break;
            } 
            if (listInput.get(i).getId() == null) {
                for (int j = lastFileIndex; j > i; j--) {
                    if (listInput.get(j).getId() != null) {
                        Collections.swap(listInput, i, j);
                        lastFileIndex = j - 1;
                        break;
                    }
                }
            }
        }

        // Add up all set ids multiplied by index at listInput
        for (int i = 0; i < listInput.size(); i++) {
            if (listInput.get(i).getId() != null) {
                sum += i * listInput.get(i).getId();
            }
        }

        System.out.println("The result is " + sum);
    }

    public static void naloga9_2() {
        char[] arrayInput = getInput().get(0).toCharArray();
        List<FileElement> listInput = new ArrayList<>();
        boolean file = true;
        long sum = 0;
        int id = 0;

        for (char c : arrayInput) {
            if (Character.isDigit(c)) {
                int noOfElements = c - '0';

                // If "file" equals true, a new FileElement with the current id and length from input is added to listInput, otherwise an element with null id is added
                if (file) {
                    listInput.add(new FileElement(id, noOfElements));
                    id++;
                } else {
                    listInput.add(new FileElement(noOfElements));
                }
                // "file" should alternate between true and false in order to have empty spaces (null id) between two list elements with set ids
                file = !file;
            }            
        }

        // Loop through listInput starting at the end. If the element at the current index does not have null id, find the first free space that is large enough to fit the FileElement and calculate the remaining free space.
        for (int i = listInput.size() - 1; i > 0; i--) {
            if (listInput.get(i).getId() != null) {
                for (int j = 0; j < i; j++) {
                    if (listInput.get(j).getId() == null) {
                        int remainingLength = listInput.get(j).getLength() - listInput.get(i).getLength();
                        if (remainingLength == 0) {
                            Collections.swap(listInput, i, j);
                            break;
                        } else if (remainingLength > 0) {
                            Collections.swap(listInput, i, j);
                            listInput.get(i).setLength(listInput.get(j).getLength());
                            listInput.add(j + 1, new FileElement(remainingLength));
                            i++;
                            break;
                        }
                    }
                }
            }
        }
        
        // Add up all set ids multiplied by index at listInput
        int position = 0;
        for (int i = 0; i < listInput.size(); i++) {
            for (int j = 0; j < listInput.get(i).getLength(); j++) {
                if (listInput.get(i).getId() != null) {
                    sum += position * listInput.get(i).getId();
                }
                position++;
            }
            
        }
        
        System.out.println("The result is " + sum);
    }
}
