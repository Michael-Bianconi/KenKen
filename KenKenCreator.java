/**
 * KenKenCreator
 *
 * This KenKenCreator will create KenKen puzzles.
 *
 * $ javac KenKenCreator.java
 * $ java KenKenCreator #
 *
 * where # is the size of the board.
 *
 * Step 1: Randomly fill the grid with 1-N unique values.
 * Step 2: Randomly generate regions around those values.
 * Step 3: Randomly choose an operation that creates a
 *         non-negative integer target when applied to the
 *         values.
 * Step 4: Remove the values.
 *
 * In essence, this creator works backwards. It creates a solved puzzle,
 * without any of the regions. Then, it randomly segments those regions and
 * assigns them operators. Finally, it performs the operations and list the
 * result as the target value.
 *
 * Afterwards, of course, it clears the values.
 *
 * Next step: create Region class and generate from the grid.
 *
 * @author Michael Bianconi
 */

import kenken.*;
public class KenKenCreator {
    
    public static void main(String[] args) {
        Grid myKenKenBoard = new Grid(Integer.parseInt(args[0]));
        myKenKenBoard.fillValues();
        myKenKenBoard.generateRegions();

        myKenKenBoard.printRegions();
    }

}