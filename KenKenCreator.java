/**
 * KenKenCreator
 *
 * This KenKenCreator will create KenKen puzzles.
 *
 * Step 1: Randomly fill the grid with 1-N unique values.
 * Step 2: Randomly generate regions around those values.
 * Step 3: Randomly choose an operation that creates a
 *         non-negative integer target when applied to the
 *         values.
 * Step 4: Remove the values.
 *
 * @author Michael Bianconi
 */

import kenken.*;
public class KenKenCreator {
    
    public static void main(String[] args) {
        Grid g = new Grid(Integer.parseInt(args[0]));
        
        g.fillValues();
        g.printValues();
    }

}