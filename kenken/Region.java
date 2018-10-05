/**
 * KenKenCreator.java
 *
 * This file holds the Region class.
 */

package kenken;

import java.util.ArrayList;
import java.util.Random;

public class Region {
    
    private int region_number;
    private char operator;
    private int target;
    private ArrayList<Integer> values;

    /**
     * Constructor with only the region number.
     * @param region_number Identifier.
     */
    public Region(int region_number) {
        this(region_number, ' ', -1, new ArrayList<Integer>());
    }

    /**
     * Constructor with all data fields at parameters.
     * @param region_number Region identifier.
     * @param operator + * - /, or ' ' for no operator.
     * @param target Target value.
     * @param values ArrayList of int values in the region, or null.
     */
    public Region(int region_number,
                  char operator,
                  int target,
                  ArrayList<Integer> values) {

            this.region_number = region_number;
            this.operator = operator;
            this.target = target;
            this.values = (values == null) ? new ArrayList<Integer>() : values;
    }

    /**
     * Given a Grid, read its region data into an Array.
     * @param grid Grid to read from
     * @return Array of regions with only values, no ops or targets.
     */
    public static Region[] readFromGrid(Grid grid) {
        int size = grid.getSize();
        Region[] regions = new Region[grid.getNumRegions()];
        int[][] valuesGrid = grid.getValues();
        int[][] regionsGrid = grid.getRegions();

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {

                Region r = new Region(regionsGrid[row][col]);
                int v = valuesGrid[row][col];

                // invalid region
                if (r.getRegionNumber() > grid.getNumRegions()) {
                    System.out.println("Something went wrong!");
                    return null;
                }

                // new region
                else if (regions[r.getRegionNumber()-1] == null) {
                    r.addValue(v);
                    regions[r.getRegionNumber()-1] = r;
                }

                else {
                    regions[r.getRegionNumber()-1].addValue(v);
                }
            }
        }

        System.out.println(regions == null);
        return regions;
    }

    //== ACCESSORS ===========================================================//

    /**
     * Add a value to the list.
     * @param v Value to add.
     */
    public void addValue(int v) {
        this.values.add(v);
    }

    /**
     * Get this region's region number.
     * @return Region number.
     */
    public int getRegionNumber() {
        return this.region_number;
    }


    /**
     * Return string in form of:
     * Region: #
     * Operator: #
     * Target: #
     * Values: [#, #, #, ..., #]
     * @return String representation of this object.
     */
    public String toString() {
        return "Region: " + Integer.toString(this.region_number) + "\n" +
               "Operator: " + this.operator + "\n" +
               "Target: " + Integer.toString(this.target) + "\n" +
               "Values: " + this.values.toString();
    }
}