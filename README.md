# mazes
An analysis of randomly generated mazes of varying dimensions.

All the analysis is done in the mathematica notebook SolvabilityOfRandomMaze.nb.

MazeGenerator.java generates new mazes. Takes as input from command line 1) width 2) height and 3) fileName for where the maze
should be stored. mazeGenerator.sh generates lots of mazes.

Maze.java solves the maze using a stack-based algorithm. Not optimized for path length. solveMazes.sh solves lots of mazes.

mazeResults.txt and moreMazeResults.txt are the output of solveMazes.sh (destination file is selected in Maze.java main function)
and are imported by the mathematica notebook.

All the generated mazes are stored in a subdirectory called Mazes/ (which can be selected in mazeGenerator.sh).
