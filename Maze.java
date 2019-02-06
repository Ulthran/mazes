import java.io.*;
import java.util.*;

/**
 * Maze.java
 * Solution to the Assignment 3: Mazes (Part 1).
 * CS 201: Data Structures - Winter 2019
 *
 * @author Eric Alexander
 * @author Sneha Narayan (modified Jan 31, 2019)
 * @author Charlie Bushman (modified Feb 03, 2019)
 */
public class Maze {
    private ArrayList<ArrayList<MazeSquare>> rowList;
    private int w, h;
    private int startRow, startCol, endRow, endCol;

    // MazeSquare is implemented as an inner class
    // to simplify the file structure a little bit.
    private class MazeSquare {
        private int r, c;
        private boolean top, bottom, left, right,
                start, end;
        private boolean visited;

        private MazeSquare(int r, int c,
                           boolean top, boolean bottom, boolean left, boolean right,
                           boolean start, boolean end) {
            this.r = r;
            this.c = c;
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
            this.start = start;
            this.end = end;
            this.visited = false;
        }

        boolean hasTopWall() {
            return top;
        }
        boolean hasBottomWall() {
            return bottom;
        }
        boolean hasLeftWall() {
            return left;
        }
        boolean hasRightWall() {
            return right;
        }
        boolean isStart() {
            return start;
        }
        boolean isEnd() {
            return end;
        }
        int getRow() {
            return r;
        }
        int getCol() {
            return c;
        }
        boolean isVisited() {
          return visited;
        }
        void visit() {
          visited = true;
        }

        /**
         * Returns the string representation of the MazeSquare object
         *
         * @return is the string representation
         */
         public String toString() {
           return "(" + Integer.toString(r) + " " + Integer.toString(c) + ")";
         }
    }

    /**
     * Construct a new Maze
     */
    public Maze() {
        rowList = new ArrayList<ArrayList<MazeSquare>>();
    }

    /**
     * Load Maze in from given file
     *
     * @param fileName the name of the file containing the Maze structure
     */
    public void load(String fileName) {

        // Create a scanner for the given file
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }

        // First line of file is "w h"
        String[] lineParams = scanner.nextLine().split(" ");
        w = Integer.parseInt(lineParams[0]);
        h = Integer.parseInt(lineParams[1]);

        // Second line of file is "startCol startRow"
        lineParams = scanner.nextLine().split(" ");
        startCol = Integer.parseInt(lineParams[0]);
        startRow = Integer.parseInt(lineParams[1]);

        // Third line of file is "endCol endRow"
        lineParams = scanner.nextLine().split(" ");
        endCol = Integer.parseInt(lineParams[0]);
        endRow = Integer.parseInt(lineParams[1]);

        // Read the rest of the lines (L or | or _ or -)
        String line;
        int rowNum = 0;
        boolean top, bottom, left, right;
        boolean start, end;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            rowList.add(new ArrayList<MazeSquare>());

            // Loop through each cell, creating MazeSquares
            for (int i = 0; i < line.length(); i++) {
                // For top, check row above, if there is one
                if (rowNum > 0) {
                    top = rowList.get(rowNum-1).get(i).hasBottomWall();
                } else {
                    top = true;
                }

                // For right, check cell to the right, if there is one
                if (i < line.length() - 1 ) {
                    char nextCell = line.charAt(i+1);
                    if (nextCell == 'L' || nextCell == '|') {
                        right = true;
                    } else {
                        right = false;
                    }
                } else {
                    right = true;
                }

                // For left and bottom, switch on the current character
                switch (line.charAt(i)) {
                    case 'L':
                        left = true;
                        bottom = true;
                        break;
                    case '_':
                        left = false;
                        bottom = true;
                        break;
                    case '|':
                        left = true;
                        bottom = false;
                        break;
                    case '-':
                        left = false;
                        bottom = false;
                        break;
                    default:
                        left = false;
                        bottom = false;
                }

                // Check to see if this is the start or end spot
                start = startCol == i && startRow == rowNum;
                end = endCol == i && endRow == rowNum;

                // Add a new MazeSquare
                rowList.get(rowNum).add(new MazeSquare(rowNum, i, top, bottom, left, right, start, end));
            }

            rowNum++;
        }
    }

    /**
     * Print the Maze to the Console
     */
    public void print() {
        LLStack<MazeSquare> soln = getSolution();
        ArrayList<MazeSquare> currRow;
        MazeSquare currSquare;

        // Print each row of text based on top and left
        for (int r = 0; r < rowList.size(); r++) {
            currRow = rowList.get(r);

            // First line of text: top wall
            for (int c = 0; c < currRow.size(); c++) {
                System.out.print("+");
                if (currRow.get(c).hasTopWall()) {
                    System.out.print("-----");
                } else {
                    System.out.print("     ");
                }
            }
            System.out.println("+");

            // Second line of text: left wall then space
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");

            // Third line of text: left wall, then space, then start/end/sol, then space
            for (int c = 0; c < currRow.size(); c++) {
                currSquare = currRow.get(c);

                if (currSquare.hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }

                System.out.print("  ");

		// YOU WILL ADD CODE HERE
		// If currSquare is part of the solution, mark it with *
                if (currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("SE ");
                } else if (currSquare.isStart() && !currSquare.isEnd()) {
                    System.out.print("S  ");
                } else if (!currSquare.isStart() && currSquare.isEnd()) {
                    System.out.print("E  ");
                } else {
                  if(soln.contains(currSquare)) {
                    System.out.print("*  ");
                  } else {
                    System.out.print("   ");
                  }
                }
            }
            System.out.println("|");

            // Fourth line of text: same as second
            for (int c = 0; c < currRow.size(); c++) {
                if (currRow.get(c).hasLeftWall()) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
                System.out.print("     ");
            }
            System.out.println("|");
        }

        // Print last row of text as straight wall
        for (int c = 0; c < rowList.get(0).size(); c++) {
            System.out.print("+-----");
        }
        System.out.println("+");
    }

    /**
     * Determines what the next square over is in the input direction from the
     * input square.
     *
     * @param s is the square whose neighbor will be returned
     * @param direction is the direction that the neighbor should be from s
     * @return is the neighbor to s in direction direction
     */
    public MazeSquare getNeighbor(MazeSquare s, String direction) {
      switch(direction) {
        case "right":
          if(!s.hasRightWall()) {
            return rowList.get(s.getRow()).get(s.getCol() + 1);
          }
          break;
        case "left":
          if(!s.hasLeftWall()) {
            return rowList.get(s.getRow()).get(s.getCol() - 1);
          }
          break;
        case "top":
          if(!s.hasTopWall()) {
            return rowList.get(s.getRow() - 1).get(s.getCol());
          }
          break;
        case "bottom":
          if(!s.hasBottomWall()) {
            return rowList.get(s.getRow() + 1).get(s.getCol());
          }
          break;
      }
      return null; // There is a wall in the way
    }

    /**
    * Computes and returns a solution to this maze. If there are multiple
    * solutions, only one is returned, and getSolution() makes no guarantees about
    * which one. However, the returned solution will not include visits to dead
    * ends or any backtracks, even if backtracking occurs during the solution
    * process.
    *
    * @return a LLStack of MazeSquare objects containing the sequence of squares
    *         visited to go from the start square (bottom of the stack) to the
    *         finish square (top of the stack).
    */
    public LLStack<MazeSquare> getSolution() {
      LLStack<MazeSquare> soln = new LLStack<MazeSquare>();

      soln.push(rowList.get(startRow).get(startCol));
      rowList.get(startRow).get(startCol).isVisited();
      MazeSquare top = null;

      while(!soln.isEmpty()) {
        top = soln.peek();

        if(top.isEnd()) {
          return soln; // We're finished!
        } else if((getNeighbor(top, "right") != null
        && !getNeighbor(top, "right").isVisited())) {
          soln.push(getNeighbor(top, "right")); // Has a neighbor
          getNeighbor(top, "right").visit();
        } else if((getNeighbor(top, "left") != null
        && !getNeighbor(top, "left").isVisited())) {
          soln.push(getNeighbor(top, "left"));
          getNeighbor(top, "left").visit();
        } else if((getNeighbor(top, "top") != null
        && !getNeighbor(top, "top").isVisited())) {
          soln.push(getNeighbor(top, "top"));
          getNeighbor(top, "top").visit();
        } else if((getNeighbor(top, "bottom") != null
        && !getNeighbor(top, "bottom").isVisited())) {
          soln.push(getNeighbor(top, "bottom"));
          getNeighbor(top, "bottom").visit();
        } else {
          soln.pop(); // No unvisited neighbors
        }
      }

      System.out.println("THIS MAZE IS BOGUS");
      return new LLStack<MazeSquare>(); // Maze is unsolvable
    }

    // This main program acts as a simple unit test for the
    // load-from-file and print-to-System.out Maze capabilities.
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Maze mazeFile");
            System.exit(1);
        }

        Maze maze = new Maze();
        maze.load(args[0]);
        //maze.print();

        boolean solvable = false;
        if(!maze.getSolution().isEmpty()) {
          solvable = true;
        }

        // Writes maze to file
        BufferedWriter bw = null;
          try {
             //Specify the file name and path here
    	       File file = new File("moreMazeResults.txt");

    	       FileWriter fw = new FileWriter(file, true);
    	       bw = new BufferedWriter(fw);
             bw.append(maze.w +  " " + maze.h + " " + String.valueOf(solvable)
                      + "\n");
          } catch (IOException ioe) {
    	       ioe.printStackTrace();
    	    } finally {
    	       try{
    	          if(bw!=null) { bw.close(); }
    	       } catch(Exception ex) {
    	          System.out.println("Error in closing the BufferedWriter "+ex);
    	       }
    	    }
    }
}
