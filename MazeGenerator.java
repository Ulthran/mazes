/**
 * Charlie Bushman
 * 02-04-19
 *
 * A class that works with Maze.java to randomly generate mazes of the
 * dimensions input through the command line.
 */

import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MazeGenerator {
  public static void main(String[] args) {
    System.out.println("HEYEYEYEYEYEYEYE");

    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);
    Random rand = new Random();

    if(args.length != 3) {
      System.err.println("Usage: java MazeGenerator");
      System.exit(1);
    }

    String firstLine = Integer.toString(width) + " " + Integer.toString(height);
    String secondLine = "0 0";
    String thirdLine = Integer.toString(width - 1) + " " +
                       Integer.toString(height - 1);
    List<String> lines = new ArrayList<String>();
    lines.add(firstLine);
    lines.add(secondLine); // Sets up the first three lines of the maze file
    lines.add(thirdLine);

    for(int i = 0; i < height - 1; i++) {
      char mazeChar = 'a';
      StringBuilder nextLine = new StringBuilder();

      switch(rand.nextInt(2)) { // Set the leftmost square
        case 0:
          mazeChar = 'L';
          break;
        case 1:
          mazeChar = '|';
          break;
      }
      nextLine.append(mazeChar);

      for(int j = 1; j < width; j++) { // Set the rest of the line of squares
        switch(rand.nextInt(4)) {
          case 0:
            mazeChar = 'L';
            break;
          case 1:
            mazeChar = '|';
            break;
          case 2:
            mazeChar = '_';
            break;
          case 3:
            mazeChar = '-';
            break;
        }

        nextLine.append(mazeChar);
      }

      lines.add(nextLine.toString());
    }

    // Set the bottom row of the maze
    char mazeChar = 'a';
    StringBuilder nextLine = new StringBuilder();
    nextLine.append('L'); // Set the leftmost square

    for(int j = 1; j < width; j++) { // Set the rest of the line of squares
      switch(rand.nextInt(2)) {
        case 0:
          mazeChar = 'L';
          break;
        case 1:
          mazeChar = '_';
          break;
      }

      nextLine.append(mazeChar);
    }

    lines.add(nextLine.toString());

    // Writes maze to file
    BufferedWriter bw = null;
      try {
         //Specify the file name and path here
	       File file = new File(args[2]);

	       if (!file.exists()) {
	          file.createNewFile();
	       }

	       FileWriter fw = new FileWriter(file);
	       bw = new BufferedWriter(fw);
         bw.write(lines.get(0) + "\n");
         for(int i = 1; i < lines.size(); i++) {
           bw.append(lines.get(i) + "\n");
         }

      } catch (IOException ioe) {
	       ioe.printStackTrace();
	    } finally {
	       try{
	          if(bw!=null) { bw.close(); }
	       } catch(Exception ex) {
	          System.out.println("Error in closing the BufferedWriter"+ex);
	       }
	    }
  }
}
