package Naloga6;

import java.util.List;

public class Guard {
    private int line;
    private int column;
    private int direction;
    private boolean insideGrid;
    private char[][] path;

    public int getLine() {
        return line;
    }
    public void setLine(int line) {
        this.line = line;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public boolean getInsideGrid() {
        return insideGrid;
    }
    public void setInsideGrid(boolean insideGrid) {
        this.insideGrid = insideGrid;
    }
    public char[][] getPath() {
        return path;
    }

    public Guard(List<String> input) {
        this.direction = 0;
        this.insideGrid = true;
        this.path = new char[input.size()][input.get(0).length()];
        fillPath(input);
    }

    public void fillPath(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                this.path[i][j] = '.';
            }
        }
    }

    public void move(List<String> input) {
        while (this.insideGrid) {
            this.path[this.line][this.column] = 'X';
            switch (this.direction % 4) {
                case 0:
                    moveUp(input);
                    break;
                case 1:
                    moveRight(input);
                    break;
                case 2:
                    moveDown(input);
                    break;
                case 3:
                    moveLeft(input);
                    break;
                default:
                    System.out.println("Invalid direction");
                    this.insideGrid = false;
                    break;
            }
        }
    }

    public void moveUp(List<String> input) {
        try {
            if (input.get(this.line - 1).charAt(this.column) == '#') {
                this.direction++;
            } else {
                this.line--;
            }
        } catch (Exception e) {
            this.insideGrid = false;
        }
    }

    public void moveRight(List<String> input) {
        try {
            if (input.get(this.line).charAt(this.column + 1) == '#') {
                this.direction++;
            } else {
                this.column++;
            }
        } catch (Exception e) {
            this.insideGrid = false;
        } 
    }

    public void moveDown(List<String> input) {
        try {
            if (input.get(this.line + 1).charAt(this.column) == '#') {
                this.direction++;
            } else {
                this.line++;
            }
        } catch (Exception e) {
            this.insideGrid = false;
        }
    }

    public void moveLeft(List<String> input) {
        try {
            if (input.get(this.line).charAt(this.column - 1) == '#') {
                this.direction++;
            } else {
                this.column--;
            }
        } catch (Exception e) {
            this.insideGrid = false;
        } 
    }
}
