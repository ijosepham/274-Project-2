/**
 * Author: Joey Pham
 * Date: 15 October 2018
 * Description: a user chooses a certain grid they want, program shows the grid and then
 * 				prints out how many "o" areas there are, and how many o's each area has
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Project2 {
	public static void main( String [] args ) {
		String fileName = getGrid(); // gets grid txt file user wants or quits
		while ( !( fileName.equals( "grid0.txt" ) ) ) { // if the user hasn't quit
			String [][] gridArray = readFile( fileName ); // reads file, writes array
			String [][] spottedArray =  gridArray; // array to keep track of which spots we've looked at already
			displayGrid( spottedArray ); // displays the grid the user asked for 
			giveAreaCount( spottedArray ); // returns the "o" count of the board the user requested
			fileName = getGrid(); //restart
		}
		System.out.print( "Goodbye!" );
	}
	
	/**
	 * prompts user for the grid number they want and returns the corresponding grid file name
	 * @return corresponding grid file name
	 */
	public static String getGrid() {
		System.out.print( "Enter a Grid Number 1-4 or 0 to Quit: " ); // prompt
		int gridNumber = CheckInput.getIntRange( 0, 4 ); // validate
		return ( "grid" + gridNumber + ".txt" ); // returns the corresponding grid file name
	}
	
	/**
	 * transfers the values from the file into a 2d array
	 * @param fileName grid the user wants to check 
	 * @return a 2d string array of the file values placed into the array
	 */
	public static String [][] readFile( String fileName ) {
		String [][] gridArray = new String[ 10 ][ 10 ]; // initialize
		String nextInput = ""; // initialize
		try {
			Scanner read = new Scanner( new File( fileName ) ); // read in file
			do {
				for ( int y = 0; y < 10; y ++ ) { // iterate through file
					if ( y != 0 && y != 9 ) { // if not on the top/bot border, doesn't take in the next line
						nextInput = read.next();
					}
					for ( int x = 0; x < 10; x ++ ) { 
						if ( y != 0 && y != 9 && x != 0 && x != 9 ) { // if not on any border, assign the o/#
							gridArray[ y ][ x ] = nextInput.substring( x - 1, x ); // assigns the corresponding character to the current place
							// ex, since inside the array starts at 1, you'd want the first char of the line which is from 0-1
						} else { // is the border
							gridArray[ y ][ x ] = "#"; // assign a #
						}
					}
				}
			} while ( read.hasNext() ); // while still has tokens
			read.close();
		} catch ( FileNotFoundException fnf ) {
			System.out.print( "File was not found" );
		}
		return gridArray;
	}
	
	/**
	 * displays the grid the user chose
	 * @param gridArray the grid the user chose
	 */
	public static void displayGrid( String [][] gridArray ) {
		for ( int y = 1; y < 9; y ++ ) { // iterate
			for( int x = 1; x < 9; x ++) {
				if ( y != 1 && x == 1 ) { // if not the first line and is first value of line
					System.out.print( "\n" + gridArray[ y ][ x ] + " " ); // prints on a new line after every new line in the txt
				} else { // if first line or isn't first value of a line
					System.out.print( gridArray[ y ][ x ] + " " ); // doesnt start a new line
				}
			}
		}
	}
	
	/**
	 * prints out the area along with the "o" count of that certain area
	 * @param spottedArray array that updates every time am "o" is found
	 */
	public static void giveAreaCount( String [][] spottedArray ) { 
		int areaNumber = 1; // area counter for display
		System.out.print( "\n" );
		for ( int y = 1; y < 9 ; y ++ ) { // iterate through the actual grid
			for ( int x = 1; x < 9; x ++) {
				if ( spottedArray[ y ][ x ].equals( "O" ) ) { // if is a hit, print out the area number along with the "o" count
					if ( areaNumber < 10 ) {
						System.out.print( "\n" + "Area  " + areaNumber + ": " + countOs( y, x, spottedArray ) );
					} else {
						System.out.print( "\n" + "Area " + areaNumber + ": " + countOs( y, x, spottedArray ) );
					}
					areaNumber = ( areaNumber + 1 ); // increment area number
				}
			}
		}
		System.out.print( "\n" + "\n" );
	}
	
	/**
	 * returns the "o" count from the starting location and updates the 
	 * spottedArray so it doesn't go over already checked spots
	 * @param y current y location we're checking
	 * @param x current x location we're checking
	 * @param spottedArray array that'll update when we find an "o"
	 * @return the "o" count starting from the given position
	 */
	public static int countOs( int y, int x, String [][] spottedArray ) {
		if ( spottedArray[ y ][ x ].equals( "O" ) ) { // if current location is an "o"
			spottedArray[ y ][ x ] = "#"; // change it to a "#" so it won't look at this spot again in the future
			return 1 + countOs( ( y + 1 ), x, spottedArray ) + countOs( y, ( x + 1 ), spottedArray ) + countOs( ( y - 1 ), x, spottedArray ) + countOs( y, ( x - 1 ), spottedArray ); 
			// checks all directions of the initial spot, then all directions of the next hit, and next, until all become a dead end and return 0
		} else { // if the current location is a "#"
			return 0; // doesn't add to the "o" count
		}
	}

}
