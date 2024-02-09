/**
 * Represents a grid-based game with letters that can be removed in lines, and gravity causes remaining letters to fall.
 *
 * @author Justin Dhillon JDHILL94 251348823
 */
public class LetterCrush {
    /**
     * Constant representing an empty cell in the grid.
     */
    public static final char EMPTY = ' ';
    private char[][] grid;

    /**
     * Constructs a LetterCrush game grid with the specified dimensions and initial letters.
     *
     * @param width   the width of the grid
     * @param height  the height of the grid
     * @param initial a string representing the initial letters of the grid
     */
    public LetterCrush(int width, int height, String initial) {
        // Initialize the game grid with the specified dimensions
        grid = new char[height][width];

        // Counter to keep track of the character index in the initial string
        int counter = 0;

        // Add the letters from the initial string to the grid
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                try {
                    // Set the current cell to the corresponding character in the initial string
                    grid[i][j] = initial.charAt(counter);
                } catch (StringIndexOutOfBoundsException e) {
                    // If the initial string is shorter than expected, fill the remaining cells with EMPTY
                    grid[i][j] = EMPTY;
                }
                counter += 1;
            }
        }
    }

    /**
     * Generates a string representation of the LetterCrush game grid.
     *
     * @return a formatted string representing the current state of the game grid
     */
    public String toString() {
        // StringBuilder to build the formatted string
        StringBuilder out = new StringBuilder("LetterCrush\n");

        // Loop through each row of the game grid
        for (int i = 0; i < grid.length; i++) {
            out.append("|");

            // Loop through each column of the game grid
            for (int j = 0; j < grid[0].length; j++) {
                // Append the character at the current grid cell to the string
                out.append(grid[i][j]);
            }

            // Append the row index to the string and start a new line
            out.append("|").append(i).append("\n");
        }

        out.append("+");
        for (int i = 0; i < grid[0].length; i++) {
            out.append(i);
        }
        out.append("+");

        // Convert the StringBuilder to a String and return
        return out.toString();
    }

    /**
     * Checks if the game grid is stable, meaning no empty cell has a non-empty cell directly above it.
     *
     * @return true if the grid is stable, false otherwise
     */
    public boolean isStable() {
        // Iterate through each row (except the bottom row) and each column of the game grid
        for (int i = grid.length - 2; i > 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                // Check if the current cell is not empty and the cell directly below it is empty
                if (grid[i][j] != EMPTY && grid[i + 1][j] == EMPTY) {
                    // If found, the grid is not stable
                    return false;
                }
            }
        }
        // If no unstable condition is found, the grid is stable
        return true;
    }

    /**
     * Applies gravity to the game grid, causing letters to fall to fill empty cells.
     */
    public void applyGravity() {
        // Check if the grid is already stable, and if so, there's no need to apply gravity
        if (isStable()) return;

        // Iterate through each row, starting from the bottom, and each column of the game grid
        for (int i = grid.length - 1; i > 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                // If the current cell is empty, swap it with the cell directly above it
                if (grid[i][j] == EMPTY) {
                    grid[i][j] = grid[i - 1][j];
                    grid[i - 1][j] = EMPTY;
                }
            }
        }
    }

    /**
     * Removes the letters in the specified line from the game grid.
     *
     * @param theLine the line to be removed from the grid
     * @return true if the removal is successful, false otherwise
     */
    public boolean remove(Line theLine) {
        // Check if the line is outside the bounds of the grid, and if so, return false
        if (theLine.inLine(grid.length + 1, grid[0].length + 1)) return false;


        // Extract the starting coordinates of the line
        int startRow = theLine.getStart()[0];
        int startCol = theLine.getStart()[1];

        // Remove letters based on the line's orientation (horizontal or vertical)
        if (theLine.isHorizontal()) {
            for (int i = 0; i < theLine.length(); i++) {
                // Set each cell in the line to empty
                grid[startRow][startCol + i] = EMPTY;
            }
        } else {
            for (int i = 0; i < theLine.length(); i++) {
                // Set each cell in the line to empty
                grid[startRow + i][startCol] = EMPTY;
            }
        }

        // Return true to indicate successful removal
        return true;
    }

    /**
     * Generates a string representation of the game grid after applying a line crush.
     *
     * @param theLine the line that was crushed
     * @return a formatted string representing the grid after the line crush
     */
    public String toString(Line theLine) {
        // Extract the starting coordinates of the line
        int startRow = theLine.getStart()[0];
        int startCol = theLine.getStart()[1];

        // Create a temporary grid for output by copying the original grid
        char[][] outputGrid = new char[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, outputGrid[i], 0, grid[i].length);
        }

        // Change the characters in the crushed line to lowercase for visual representation
        if (theLine.isHorizontal()) {
            for (int i = 0; i < theLine.length(); i++) {
                outputGrid[startRow][startCol + i] = Character.toLowerCase(outputGrid[startRow][startCol + i]);
            }
        } else {
            for (int i = 0; i < theLine.length(); i++) {
                outputGrid[startRow + i][startCol] = Character.toLowerCase(outputGrid[startRow + i][startCol]);
            }
        }

        // Build the formatted string for the crushed grid
        StringBuilder out = new StringBuilder("CrushLine\n");
        for (int i = 0; i < outputGrid.length; i++) {
            out.append("|");
            for (int j = 0; j < outputGrid[0].length; j++) {
                out.append(outputGrid[i][j]);
            }
            out.append("|").append(i).append("\n");
        }

        out.append("+");

        for (int i = 0; i < outputGrid[0].length; i++) {
            out.append(i);
        }
        out.append("+");

        // Convert the StringBuilder to a String and return
        return out.toString();
    }

    /**
     * Finds the longest horizontal or vertical line of matching letters in the game grid.
     *
     * @return the longest line in the grid, or null if no such line exists
     */
    public Line longestLine() {
        // Initialize a default Line object with length 1
        Line longLine = new Line(0, 0, true, 1);

        // Variable to store the length of the longest line found
        int largest = 0;

        // Check for the longest horizontal line
        for (int i = grid.length - 1; i >= 0; i--) {
            char letter = grid[i][0];
            int adjacent = 1;
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][j] == letter && letter != EMPTY) {
                    adjacent++;
                    if (adjacent > largest) {
                        largest = adjacent;
                        longLine = new Line(i, j - adjacent + 1, true, adjacent);
                    }
                } else {
                    letter = grid[i][j];
                    adjacent = 1;
                }
            }
        }

        // Check for the longest vertical line
        for (int j = 0; j < grid[0].length; j++) {
            char letter = grid[grid.length - 1][j];
            int adjacent = 1;
            for (int i = grid.length - 2; i >= 0; i--) {
                if (grid[i][j] == letter && letter != EMPTY) {
                    adjacent++;
                    if (adjacent > largest) {
                        largest = adjacent;
                        longLine = new Line(i, j, false, adjacent);
                    }
                } else {
                    letter = grid[i][j];
                    adjacent = 1;
                }
            }
        }

        // Return the longest line if its length is greater than 2, otherwise, return null
        if (longLine.length() > 2) return longLine;
        return null;
    }

    /**
     * Performs a cascade operation, repeatedly removing the longest line and applying gravity until the grid is stable.
     */
    public void cascade() {
        // Continue cascading as long as there is a longest line to remove
        while (this.longestLine() != null) {
            // Remove the longest line from the grid
            this.remove(this.longestLine());

            // Keep applying gravity until the grid becomes stable
            while (!this.isStable()) {
                applyGravity();
            }
        }
    }


}
