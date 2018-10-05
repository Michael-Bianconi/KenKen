/**
 * KenKenCreator
 *
 * This KenKenCreator will create KenKen puzzles.
 *
 * $ javac KenKenCreator.java
 * $ java KenKenCreator size filename
 *
 * For making not shitty puzzles, keep the size under 10. The program should
 * run fine for puzzles up to size 30. Beyond that it kinda shits itself under
 * the load.
 *
 * The output file is option. Leaving it blank or using the filename "stdout"
 * have the puzzle printed to standard output.
 *
 * Step 1: Randomly fill the grid with 1-N unique values.
 * Step 2: Randomly generate regions around those values.
 * Step 3: Randomly choose an operation that creates a
 *         non-negative integer target when applied to the
 *         values.
 * Step 4: Remove the values and leave the regions.
 * Step 5: Write to the output file (or standard output)
 * Step 5: Cry
 *
 * In essence, this creator works backwards. It creates a solved puzzle,
 * without any of the regions. Then, it randomly segments those regions and
 * assigns them operators. Finally, it performs the operations and list the
 * result as the target value.
 *
 * @author Michael Bianconi
 */

import kenken.*;
import java.io.*;
import java.util.ArrayList;

public class KenKenCreator {
    
    public static void main(String[] args) throws FileNotFoundException {

        int size = 0;
        boolean solve = false;
        String filename = "";

        // parse input
        if (args.length == 0 || args.length > 3) {
            System.out.println("Usage: java KenKenCreator size [-s] [file]");
            System.out.println("\tsize\tsize of puzzle");
            System.out.println("\t-s\tshow solution to puzzle (optional)");
            System.out.println("\tfile\toutput to this file (else print)");
            return;
        }

        size = Integer.parseInt(args[0]);

        if (args.length == 2) {
            if (args[1].equals("-s")) {
                solve = true;
            }

            else {
                filename = args[1];
            }
        }

        else if (args.length == 3) {
            solve = true;
            if (args[1].equals("-s")) {
                filename = args[2];
            }

            else {
                filename = args[1];
            }
        }

        // redirect to file
        if (filename != "") {
            System.setOut(new PrintStream(new File(filename)));
        }

        // Generate the grid with values and region numbers
        Grid myKenKenBoard = new Grid(Integer.parseInt(args[0]));
        myKenKenBoard.generateValues();
        myKenKenBoard.generateRegions();

        // Generate a list of all the regions
        Region[] regions = Region.readFromGrid(myKenKenBoard);

        // For each region, create and assign a target and operator
        for (Region r : regions) {
            r.generateOperatorAndTarget();
        }

        writeToFile(myKenKenBoard, regions, solve);

    }

    /**
     * Given all the data, write it to a file. If the file does not exist,
     * it is created. If the file is "stdout", simply print it. The data is
     * written in the following format:
     *
     * 1 | [size] [num_regions]
     * 2 |
     * 3 | [nth region target][nth region operator] (for each region, 1-N)
     * 4 |
     * 5 | [region data array]
     *
     * Example:
     *
     *
     * @param grid The data grid.
     * @param regions Region data holding operators and targets.
     * @param solution boolean, if true, print the solution at the third line.
     */
    public static void writeToFile(Grid grid,
                                   Region[] regions,
                                   boolean solution) {
        System.out.printf("%d %d\n", grid.getSize(), grid.getNumRegions());
        System.out.println("");

        if (solution) {
            grid.printValues();
            System.out.println("");
        }

        for (Region r : regions) {
            System.out.printf("%d %c\n", r.getTarget(), r.getOperator());
        }

        System.out.println("");
        grid.printRegions();
    }


}