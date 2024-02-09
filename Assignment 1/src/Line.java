/**
 * Represents a line in a two-dimensional grid.
 *
 * @author Justin Dhillon JDHILL94 251348823
 */
public class Line {

    private int[] start;
    private int[] end;

    /**
     * Constructs a Line object with the specified parameters.
     *
     * @param row        the starting row of the line
     * @param col        the starting column of the line
     * @param horizontal a boolean indicating whether the line is horizontal or vertical
     * @param length     the length of the line
     */
    public Line(int row, int col, boolean horizontal, int length) {
        // Set the starting coordinates of the line
        start = new int[]{row, col};

        // Calculate and set the ending coordinates based on the line's orientation
        if (horizontal) {
            end = new int[]{row, col + length - 1};
        } else {
            end = new int[]{row + length - 1, col};
        }
    }

    /**
     * Gets the starting coordinates of the line.
     *
     * @return an array containing the row and column of the starting point
     */
    public int[] getStart() {
        return new int[]{start[0], start[1]};
    }

    /**
     * Calculates and returns the length of the line.
     *
     * @return the length of the line
     */
    public int length() {

        return Math.abs(end[0] - start[0]) + Math.abs(end[1] - start[1]) + 1;
    }

    /**
     * Checks whether the line is horizontal.
     *
     * @return true if the line is horizontal, false otherwise
     */
    public boolean isHorizontal() {

        return start[0] == end[0];
    }

    /**
     * Checks if the given coordinates are within the boundaries of the line.
     *
     * @param row the row coordinate to check
     * @param col the column coordinate to check
     * @return true if the coordinates are within the line, false otherwise
     */
    public boolean inLine(int row, int col) {
        return (row >= start[0] && row <= end[0]) && (col >= start[1] && col <= end[1]);
    }

    /**
     * Returns a string representation of the line.
     *
     * @return a formatted string representing the line's start and end coordinates
     */
    public String toString() {
        return String.format("Line:[%d,%d]->[%d,%d]", start[0], start[1], end[0], end[1]);
    }

}
