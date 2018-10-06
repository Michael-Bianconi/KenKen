# KenKen

This KenKen puzzle generator creates KenKen puzzles.

## Rules
KenKen puzzles are comprised of a grid (around 4x4 usually) and several regions
within it. Each region has a target number and an operator. The goal of the
puzzle is fill each space in the grid, such that:

1. Each row and column has every digit from 1 to N.
2. No row or column has duplicate digits.
3. If the given operation is performed on the values of a region, it will equal
   the target.

The operations are performed such that:

1. Addition/Multiplication: add or multiply all of the values
2. Subtraction/Division: subtract or divide the largest value by all the others.
3. No operator: Only used in size-1 regions, solution value is always the target


## Storing the puzzles
These puzzles are stored in the following format:

(size) (number of regions)

(solution, if requested)

(region 1's target) (region 1's operator)

(region 2's target) (region 2's operator)

(...)

(region N's target) (region N's operator)

(region grid)

### Example:

3, 2

1 2 3

3 1 2

2 3 1

10 +

12 *

1 1 1

1 1 2

2 2 2


## Running the program
1. git clone https://www.github.com/Michael-Bianconi/KenKen
2. $ cd KenKen
3. $ javac *.java
4. $ java KenKenCreator size [-s] [file]

        size        size of the grid

        -s          show solution

        file        output to this file (if not given, print instead)