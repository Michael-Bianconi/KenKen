/**
 * Grid
 *
 * The grid class holds the grid of values in a KenKen puzzle.
 *
 * @author Michael Bianconi
 */
package kenken;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {

    protected int size; // x, in a grid with x rows and x columns.
    protected int[][] values; // the solution's values
    protected int[][] regions; // the region numbers
    protected ArrayList<Integer> unique_values; // all numbers 1 through N

 
    /**
     * Constructor for the Grid class.
     * @param size The boards will always be squares with this length
     */
    public Grid(int size) {
        this.size = size;
        this.values = new int[size][size];
        this.regions = new int[size][size];
        this.unique_values = new ArrayList<Integer>(size);

        for (int i = 1; i <= size; i++) {
            unique_values.add(i);
        }
    }

    //== VALUE FUNCTIONS =====================================================//
    /**
     * Starter method for fillValues, calls with (0,0).
     */
    public void fillValues() {
        if (!fillValues(0,0)) {
            System.out.println("Something went wrong");
        }
    }

    /**
     * Fill the values array with unique digits such that each row and column
     * in the array contains all integers from 1 to this.size.
     * This is done recursively and slowly, but hey, it works. It's fine for
     * grids under size 10, gets a little slow between 10 and 14, and then
     * it shits itself. It'll still run, assuming it doesn't reach a stack
     * overflow, but it'll take awhile.
     * @param row Row to fill.
     * @param col Column to fill.
     * @return Will return true if all tiles are valid. Since it's recursive, if
     *         the top level returns false, something went wrong.
     */
    protected boolean fillValues(int row, int col) {

        // we've reached the end of the grid
        if (row == size) {
            return true;
        }

        // Seed the new random, and copy over a fresh set of unique values
        Random rand = new Random();
        ArrayList<Integer> uniques = new ArrayList<Integer>(this.unique_values);

        // Having created an ArrayList with all values 1...N, randomly choose
        // and remove one. If it's valid in the current grid, move on to the
        // next tile. If not, keep going.
        // If no numbers fit, then we've created an unsolvable puzzle, and
        // need to go up one recursion level.
        while (uniques.size() != 0) {
            int index = rand.nextInt(uniques.size());
            int value = uniques.remove(index);
            this.values[row][col] = value;

            // so, we found a value that works with the current grid
            // next, find the next grid position to work on
            // call fillValues on that tile. If fillValues returns true,
            // we're done. All recursions will return true at once.
            if (checkValidValue(row, col)) {
                int next_row = (col == this.size-1) ? row + 1 : row;
                int next_col = (col == this.size-1) ? 0 : col + 1;

                if (fillValues(next_row, next_col)) {
                    return true;
                }
            }

            // have to reset the tile
            else {
                this.values[row][col] = 0;
            }
        }

        return false;
    }

    /**
     * Check if the current value in the values array conflicts with other
     * values in the same row and column.
     * @param row y-coordinate
     * @param col x-coordinate
     * @return True if valid
     */
    protected boolean checkValidValue(int row, int col) {
        int value = this.values[row][col];

        // check if there's the same value on this row
        for (int x = 0; x < this.size; x++) {
            if (this.values[row][x] == value && x != col) {
                return false;
            }
        }

        // check if there's the same value on this column
        for (int y = 0; y < this.size; y++) {
            if (this.values[y][col] == value && y != row) {
                return false;
            }
        }

        return true;
    }

    /**
     * Print out the values grid.
     */
    public void printValues() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                System.out.printf("%d ", this.values[y][x]);
            }

            System.out.println("");
        }
    }

    //== REGION FUNCTIONS ====================================================//
    /**
     * Sprinkle single tile regions across the board, and then recursively
     * expand them until the grid is filled.
     * @see sprinkleRegions
     * @see generateRegionsRecursive
     */
    public void generateRegions() {

        sprinkleRegions();

        if (!generateRegionsRecursive()) {
            System.out.println("Something went wrong!");
        }
    }

    /**
     * Generate the regions of the grid. Do so randomly selecting single tiles
     * in the grid, placing different regions, then going through again and
     * expanding those regions until all spaces are filled.
     * @param row x-coordinate to generate
     * @param col y-coordinate to generate
     * @param num region num (value in this.regions array)
     * @return true if generation is completed.
     */
    protected boolean generateRegionsRecursive() {

        ArrayList<Integer> expanded_regions = new ArrayList<Integer>(this.size);

        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {

                // get the current region number
                int region = this.regions[y][x];

                // this tile has a region in it that hasn't been expanded yet
                if (region != 0 && !expanded_regions.contains(region)) {

                    // if we can expand it into an empty tile
                    if (findAndExpandRegion(y, x)) {

                        // mark off this region as expanded
                        expanded_regions.add(region);
                    }
                }
            }
        }

        // There are no more tiles to expand into
        if (expanded_regions.size() == 0) {
            return true;
        }

        // There are more tiles to expand into
        else {
            return generateRegionsRecursive();
        }
    }

    /**
     * Randomly place 2N - 1 region spots around the board.
     */
    protected void sprinkleRegions() {
        Random rand = new Random();

        for (int i = 1; i < (this.size * 2); i++) {

            int row = rand.nextInt(this.size);
            int col = rand.nextInt(this.size);
            int region_number = i;

            this.regions[row][col] = region_number;
        }
    }

    /**
     * Given a grid coordinate, find an empty (zero) adjacent tile and
     * expand the region into it.
     * @param row x-coordinate
     * @param col y-coordinate
     * @return true if the region could be expanded
     */
    protected boolean findAndExpandRegion(int row, int col) {
        int region_num = this.regions[row][col];

        // north
        if (row > 0 && this.regions[row-1][col] == 0) {
            this.regions[row-1][col] = region_num;
            return true;
        }

        // south
        else if (row < this.size - 1 && this.regions[row+1][col] == 0) {
            this.regions[row+1][col] = region_num;
            return true;
        }

        // east
        else if (col < this.size - 1 && this.regions[row][col+1] == 0) {
            this.regions[row][col+1] = region_num;
            return true;
        }

        // west
        else if (col > 0 && this.regions[row][col-1] == 0) {
            this.regions[row][col-1] = region_num;
            return true;
        }

        // no empty tiles
        else {
            return false;
        }
    }

    /**
     * Print out the regions grid.
     */
    public void printRegions() {
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                System.out.printf("%d ", this.regions[y][x]);
            }

            System.out.println("");
        }
    }
}