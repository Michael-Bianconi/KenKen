/**
 * KenKenCreator.java
 *
 * This file holds the Region class.
 *
 * @author Michael Bianconi
 */

package kenken;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class Region {
    
    private int region_number;
    private char operator;
    private int target;
    private ArrayList<Integer> values;

    //== CONSTRUCTORS ========================================================//
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

    //== GENERATORS ==========================================================//
    /**
     * Given a Grid, read its region data into an Array of regions.
     * Does NOT generate operators or targets.
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

                // existing region
                else {
                    regions[r.getRegionNumber()-1].addValue(v);
                }
            }
        }
        return regions;
    }

    /**
     * Randomly choose an operator and attempt to create a valid target (non-
     * negative integer). If impossible, choose a different operator. If
     * region is of size 1, use the ' ' operator.
     *
     * Note: the division operator appears much less than the others, as it
     *       is fairly uncommon for several random numbers to divide into
     *       an integer.
     */
    public void generateOperatorAndTarget() {
        Random rand = new Random();
        ArrayList<Character> operations = new ArrayList<Character>();
        operations.add('+');
        operations.add('-');
        operations.add('*');
        operations.add('/');

        // size 1 region:
        if (this.values.size() == 1) {
            this.operator = ' ';
            this.target = this.values.get(0);
            return;
        }

        while (operations.size() != 0) {

            // Get and remove a random operation from the list
            char op = operations.remove(rand.nextInt(operations.size()));
            this.target = findTarget(op);

            // we've found a valid target
            if (this.target != -1) {
                this.operator = op;
                break;
            }
        }
    }

    /**
     * Given an operator, see if it's possible to create a valid target.
     * Will ALWAYS be valid if the operator is * or +.
     * @param op operator to attempt
     * @return the valid target, or -1
     */
    private int findTarget(char op) {

        // addition operator, add all values and return
        if (op == '+') {
            int target = 0;
            for (int v : this.values) {
                target += v;
            }
            return target;
        }

        // multiplication operator, multiple all values and return
        else if (op == '*') {
            int target = 1;
            for (int v : this.values) {
                target *= v;
            }
            return target;
        }

        // subtraction operator, take largest value and subtract all others
        // if the result is negative, return -1
        else if (op == '-') {
            int operand = 0;
            int max = 0;

            for (int v : this.values) {
                if (v > max) {
                    operand += max;
                    max = v;
                }

                else {
                    operand += v;
                }
            }

            int target = max - operand;
            return (target < 0) ? -1 : target;
        }

        // division operator, take the largest value and divide it by all
        // the others. If the result isn't an integer, return -1.
        else if (op == '/') {
            int divisor = 1;
            int max = 1;

            for (int v : this.values) {
                if (v > max) {
                    divisor *= max;
                    max = v;
                }

                else {
                    divisor *= v;
                }
            }

            // if there's no loss of precision, then it's an integer
            return (max % divisor == 0) ? (int) (max / divisor) : -1;
        }

        return -1;
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
     * Get this region's target.
     * @return int
     */
    public int getTarget() {
        return this.target;
    }

    /**
     * Get this region's operator.
     * @return char
     */
    public char getOperator() {
        return this.operator;
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