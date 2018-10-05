/**
 * KenKenCreator
 *
 * This KenKenCreator will create KenKen puzzles.
 *
 * $ javac KenKenCreator.java
 * $ java KenKenCreator #
 *
 * where # is the size of the board.
 * For making not shitty puzzles, keep the size under 10. The program should
 * run fine for puzzles up to size 30. Beyond that it kinda shits itself under
 * the load.
 *
 * Step 1: Randomly fill the grid with 1-N unique values.
 * Step 2: Randomly generate regions around those values.
 * Step 3: Randomly choose an operation that creates a
 *         non-negative integer target when applied to the
 *         values.
 * Step 4: Remove the values and leave the regions.
 * Step 5: Cry
 *
 * In essence, this creator works backwards. It creates a solved puzzle,
 * without any of the regions. Then, it randomly segments those regions and
 * assigns them operators. Finally, it performs the operations and list the
 * result as the target value.
 *
 * Afterwards, of course, it clears the values.
 *
 * Next step: Compile a list of the regions and generate their ops and targets.
 *
 * @author Michael Bianconi
 */

import kenken.*;
import java.util.ArrayList;

public class KenKenCreator {
    
    public static void main(String[] args) {
        Grid myKenKenBoard = new Grid(Integer.parseInt(args[0]));
        myKenKenBoard.generateValues();
        myKenKenBoard.generateRegions();
        Region[] regions = Region.readFromGrid(myKenKenBoard);

        myKenKenBoard.printValues();
        System.out.println("");
        myKenKenBoard.printRegions();
        for (Region r : regions) {
            System.out.println(r.toString() + "\n");
        }
    }

}