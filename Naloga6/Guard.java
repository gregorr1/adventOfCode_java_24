package Naloga6;

import java.util.ArrayList;
import java.util.List;

public class Guard {
    private int row;
    private int column;
    private int direction;
    private char[][] path;
    private boolean[][] visited;

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
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
    public char[][] getPath() {
        return path;
    }
    public boolean[][] getVisited() {
        return visited;
    }

    public Guard(int row, int column, List<String> input) {
        this.row = row;
        this.column = column;
        this.direction = 0;
        this.path = new char[input.size()][input.get(0).length()];
        this.visited = new boolean[input.size()][input.get(0).length()];
        fillPath(input);
    }

    // Copy constructor for Guard object
    public Guard(Guard guard, List<String> input) {
        this.row = guard.row;
        this.column = guard.column;
        this.direction = guard.direction;
        this.path = new char[input.size()][input.get(0).length()];
        this.visited = new boolean[input.size()][input.get(0).length()];
        fillPath(input);
    }

    public void fillPath(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                this.path[i][j] = 96;
            }
        }
    }
    
    public int rowModifier() {
        switch (this.direction % 4) {
            case 0:
                return -1;
            case 2:
                return 1;
            default:
                return 0;
        }
    }

    public int columnModifier() {
        switch (this.direction % 4) {
            case 1:
                return 1;
            case 3:
                return -1;
            default:
                return 0;
        }
    }

    // Based on row and column modifier, determined from object's direction, check if the next field on the object's path is within bounds
    public boolean checkGrid(List<String> input) {
        this.visited[this.row][this.column] = true;
        if (this.row + rowModifier() >= 0
        && this.row + rowModifier() < input.get(this.row).length()
        && this.column + columnModifier() >= 0
        && this.column + columnModifier() < input.size()) {
            return true;
        } else {
            return false;
        }
    }

    // The rest of the functions in this class may trigger an Index out of bounds error, so they must be either surrounded by a try_catch block or called only after checkGrid returns true

    public boolean checkObstruction(List<String> input) {
        if (input.get(this.row + rowModifier()).charAt(this.column + columnModifier()) == '#') {
            return true;
        } else {
            return false;
        }
    }

    // Check if the next field has already been visited. If yes, it should not be changed to '#' and checked for loop, because it would also change the object's previous path and might not be able to reach this field 
    public boolean checkVisited(List<String> input) {
        if (this.visited[this.row + rowModifier()][this.column + columnModifier()]) {
            return true;
        } else {
            return false;
        }
    }

    // Check whether the next field is '#' and direction should be changed or is the path ahead clear and the object can move
    public void move(List<String> input) {
        if (checkObstruction(input)) {
            this.direction++;
        } else {
            this.path[this.row][this.column] += (int)Math.pow(2, direction % 4);
            this.row += rowModifier();
            this.column += columnModifier();
        }
    }

    // At each object's position, if the path ahead is clear and in bounds, the input should be changed to test for a loop in case the next field on the path would be '#'
    public List<String> modifyInput(List<String> input) {
        List<String> newInput = new ArrayList<>(input);
        StringBuilder sb = new StringBuilder(newInput.get(this.row + rowModifier()));
        sb.setCharAt(this.column + columnModifier(), '#');
        newInput.set(this.row + rowModifier(), sb.toString());
        return newInput;
    }
}
