package Naloga12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Naloga12 {
    public static List<String> getInput() {
        String path = "input/input12.txt";
        List<String> input = Collections.emptyList();
        try {
            input = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            System.out.println("This file does not exist.");
        }
        return input;
    }

    public static void naloga12_1() {
        /* Instructions available here: https://adventofcode.com/2024/day/12 */

        List<String> input = getInput();
        List<Region> listRegions = new ArrayList<>();
        int price = 0;

        // Create a matrix in the form of a list of lists of booleans the size of the input with all initially set as true
        List<List<Boolean>> matrix = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            List<Boolean> matrixRow = new ArrayList<>();
            for (int j = 0; j < input.get(i).length(); j++) {
                matrixRow.add(true);
            }
            matrix.add(matrixRow);
        }

        // Iterate through the input looking for place where the corresponding field in the matrix is 'true'. Whenever a new 'true' field in the matrix is found, a new region is created and completed by findRegion().
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (matrix.get(i).get(j) == true) {
                    Region region = new Region();
                    findRegion(region, input.get(i).charAt(j), input, matrix, i, j);
                    listRegions.add(region);
                }
            }
        }

        // Get the area and the full number of fences needed for each region, then calculate the price for each region
        for (Region region : listRegions) {
            price += (region.getArea() * (region.getUpperFence() + region.getRightFence() + region.getLowerFence() + region.getLeftFence()));
        }

        System.out.println("The result is " + price);
    }

    public static void naloga12_2() {
        List<String> input = getInput();
        List<Region> listRegions = new ArrayList<>();
        int price = 0;

        // Create a matrix in the form of a list of lists of booleans the size of the input with all initially set as true
        List<List<Boolean>> matrix = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            List<Boolean> matrixRow = new ArrayList<>();
            for (int j = 0; j < input.get(i).length(); j++) {
                matrixRow.add(true);
            }
            matrix.add(matrixRow);
        }

        // Iterate through the input looking for place where the corresponding field in the matrix is 'true'. Whenever a new 'true' field in the matrix is found, a new region is created and completed by findRegionDiscount().
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (matrix.get(i).get(j) == true) {
                    Region region = new Region();
                    findRegionDiscount(region, input.get(i).charAt(j), input, matrix, i, j);
                    listRegions.add(region);
                }
            }
        }

        // Get the area and the full number of fences needed for each region, then calculate the price for each region
        for (Region region : listRegions) {
            price += (region.getArea() * (region.getUpperFence() + region.getRightFence() + region.getLowerFence() + region.getLeftFence()) / 2);
        }

        System.out.println("The result is " + price);
    }

    // Recursive function that runs once for each field of the region, using matrix in order not to move to the same field more than once. Based on the surroundings, either an upper, left, lower or right fence is added or none at all. 
    public static void findRegion(Region region, char plant, List<String> input, List<List<Boolean>> matrix, int rowIndex, int columnIndex) {
        matrix.get(rowIndex).set(columnIndex, false);
        region.setArea(region.getArea() + 1);
        
        if (rowIndex > 0 && input.get(rowIndex - 1).charAt(columnIndex) == plant) {
            if (matrix.get(rowIndex - 1).get(columnIndex) == true) {
                findRegion(region, plant, input, matrix, rowIndex - 1, columnIndex);  
            }
        } else {
            region.setUpperFence(region.getUpperFence() + 1);
        }
        
        if (columnIndex + 1 < input.get(rowIndex).length() && input.get(rowIndex).charAt(columnIndex + 1) == plant) {
            if (matrix.get(rowIndex).get(columnIndex + 1) == true) {
                findRegion(region, plant, input, matrix, rowIndex, columnIndex + 1);
            }
        } else {
            region.setRightFence(region.getRightFence() + 1);
        }

        if (rowIndex + 1 < input.size() && input.get(rowIndex + 1).charAt(columnIndex) == plant) {
            if (matrix.get(rowIndex + 1).get(columnIndex) == true) {
                findRegion(region, plant, input, matrix, rowIndex + 1, columnIndex);
            }
        } else {
            region.setLowerFence(region.getLowerFence() + 1);
        }

        if (columnIndex > 0 && input.get(rowIndex).charAt(columnIndex - 1) == plant) {
            if (matrix.get(rowIndex).get(columnIndex - 1) == true) {
                findRegion(region, plant, input, matrix, rowIndex, columnIndex - 1);
            }
        } else {
            region.setLeftFence(region.getLeftFence() + 1);
        }
    }

    // Recursive function that runs once for each field of the region, using matrix in order not to move to the same field more than once. It checks the surroundings of each field to determine if it's the beginning or the end of any fence or straight fence between the two. Each fence is then counted twice, once at each end, so the final number of fences must be halved.
    public static void findRegionDiscount(Region region, char plant, List<String> input, List<List<Boolean>> matrix, int rowIndex, int columnIndex) {
        matrix.get(rowIndex).set(columnIndex, false);
        region.setArea(region.getArea() + 1);
        
        if (rowIndex > 0 && input.get(rowIndex - 1).charAt(columnIndex) == plant) {
            if (matrix.get(rowIndex - 1).get(columnIndex) == true) {
                findRegionDiscount(region, plant, input, matrix, rowIndex - 1, columnIndex);  
            }
        } else {
            try {
                if (input.get(rowIndex).charAt(columnIndex - 1) != plant) {
                    region.setUpperFence(region.getUpperFence() + 1);
                } 
            } catch (Exception e) {
                region.setUpperFence(region.getUpperFence() + 1);
            }
            
            try {
                if (input.get(rowIndex - 1).charAt(columnIndex - 1) == plant && input.get(rowIndex).charAt(columnIndex - 1) == plant && input.get(rowIndex - 1).charAt(columnIndex) != plant) {
                    region.setUpperFence(region.getUpperFence() + 1);
                }                
            } catch (Exception e) { }

            try {
                if (input.get(rowIndex).charAt(columnIndex + 1) != plant) {
                    region.setUpperFence(region.getUpperFence() + 1);
                } 
            } catch (Exception e) {
                region.setUpperFence(region.getUpperFence() + 1);
            }

            try {
                if (input.get(rowIndex - 1).charAt(columnIndex + 1) == plant && input.get(rowIndex).charAt(columnIndex + 1) == plant && input.get(rowIndex - 1).charAt(columnIndex) != plant) {
                    region.setUpperFence(region.getUpperFence() + 1);
                }                
            } catch (Exception e) { }
        }
        
        if (columnIndex + 1 < input.get(rowIndex).length() && input.get(rowIndex).charAt(columnIndex + 1) == plant) {
            if (matrix.get(rowIndex).get(columnIndex + 1) == true) {
                findRegionDiscount(region, plant, input, matrix, rowIndex, columnIndex + 1);
            }
        } else {
            try {
                if (input.get(rowIndex - 1).charAt(columnIndex) != plant) {
                    region.setRightFence(region.getRightFence() + 1);
                }
            } catch (Exception e) {
                region.setRightFence(region.getRightFence() + 1);
            }

            try {
                if (input.get(rowIndex - 1).charAt(columnIndex + 1) == plant && input.get(rowIndex - 1).charAt(columnIndex) == plant && input.get(rowIndex).charAt(columnIndex + 1) != plant) {
                    region.setRightFence(region.getRightFence() + 1);
                }
            } catch (Exception e) { }
            
            try {
                if (input.get(rowIndex + 1).charAt(columnIndex) != plant) {
                    region.setRightFence(region.getRightFence() + 1);
                }
            } catch (Exception e) {
                region.setRightFence(region.getRightFence() + 1);
            }

            try {
                if (input.get(rowIndex + 1).charAt(columnIndex + 1) == plant && input.get(rowIndex + 1).charAt(columnIndex) == plant && input.get(rowIndex).charAt(columnIndex + 1) != plant) {
                    region.setRightFence(region.getRightFence() + 1);
                }
            } catch (Exception e) { }
        }

        if (rowIndex + 1 < input.size() && input.get(rowIndex + 1).charAt(columnIndex) == plant) {
            if (matrix.get(rowIndex + 1).get(columnIndex) == true) {
                findRegionDiscount(region, plant, input, matrix, rowIndex + 1, columnIndex);
            }
        } else {
            try {
                if (input.get(rowIndex).charAt(columnIndex - 1) != plant) {
                    region.setLowerFence(region.getLowerFence() + 1);
                }
            } catch (Exception e) {
                region.setLowerFence(region.getLowerFence() + 1);
            }

            try {
                if (input.get(rowIndex + 1).charAt(columnIndex - 1) == plant && input.get(rowIndex).charAt(columnIndex - 1) == plant && input.get(rowIndex + 1).charAt(columnIndex) != plant) {
                    region.setLowerFence(region.getLowerFence() + 1);
                }
            } catch (Exception e) { }
            
            try {
                if (input.get(rowIndex).charAt(columnIndex + 1) != plant) {
                    region.setLowerFence(region.getLowerFence() + 1);
                }
            } catch (Exception e) {
                region.setLowerFence(region.getLowerFence() + 1);
            }

            try {
                if (input.get(rowIndex + 1).charAt(columnIndex + 1) == plant && input.get(rowIndex).charAt(columnIndex + 1) == plant && input.get(rowIndex + 1).charAt(columnIndex) != plant) {
                    region.setLowerFence(region.getLowerFence() + 1);
                }
            } catch (Exception e) { }
        }

        if (columnIndex > 0 && input.get(rowIndex).charAt(columnIndex - 1) == plant) {
            if (matrix.get(rowIndex).get(columnIndex - 1) == true) {
                findRegionDiscount(region, plant, input, matrix, rowIndex, columnIndex - 1);
            }
        } else {
            try {
                if (input.get(rowIndex - 1).charAt(columnIndex) != plant) {
                    region.setLeftFence(region.getLeftFence() + 1);
                }
            } catch (Exception e) {
                region.setLeftFence(region.getLeftFence() + 1);
            }

            try {
                if (input.get(rowIndex - 1).charAt(columnIndex - 1) == plant && input.get(rowIndex - 1).charAt(columnIndex) == plant && input.get(rowIndex).charAt(columnIndex - 1) != plant) {
                    region.setLeftFence(region.getLeftFence() + 1);
                }
            } catch (Exception e) { }
            
            try {
                if (input.get(rowIndex + 1).charAt(columnIndex) != plant) {
                    region.setLeftFence(region.getLeftFence() + 1);
                }
            } catch (Exception e) {
                region.setLeftFence(region.getLeftFence() + 1);
            }
            
            try {
                if (input.get(rowIndex + 1).charAt(columnIndex - 1) == plant && input.get(rowIndex + 1).charAt(columnIndex) == plant && input.get(rowIndex).charAt(columnIndex - 1) != plant) {
                    region.setLeftFence(region.getLeftFence() + 1);
                }
            } catch (Exception e) { }
        }
    }
}
