// This class reads data from a text file to create Player objects, 
// puts the player objects into the LinkedHashSet, 
// feeds them into the DB, selects them and puts them into a HashMap, 
// and then prints the HashMap instance of Map key/value pairs to the console.

package com.baakza.mehreen.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Collection;
import java.sql.DriverManager;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import java.sql.SQLException;

public class DatabaseTest {

	// Create static method called createPlayer()

	// Accept four String arguments
	public static Player createPlayer(String number, String name, String position, String year) {

		// Convert number to integer
		int numberValue = Integer.parseInt(number);

		// Instantiate Player with variable called player
		// Call Player's String constructor by passing it the name value
		Player player = new Player(name);

		// Set player's number, position, year using set methods
		player.setNumber(numberValue); // (use integer value here)
		player.setPosition(position);
		player.setYear(year);

		// Return player
		return player;
	}

	public static void main(String[] args) {

		// Benefits of LinkedHashSet vs. HashSet & ArrayList:

		// HashSet would not preserve the order of insertion
		// ArrayList would allow duplicates
		// LinkedHashSet preserves order in which players were entered, preventing
		// duplicates

		// Create LinkedHashSet called player that will contain Player objects
		Collection<Player> players = new LinkedHashSet<Player>();

		// Declare null scanner here (outside try block)
		Scanner scanner = null;

		// Define conn here so it can be seen from within finally block
		Connection conn = null;

		// Detect any exceptions
		try {

			// Connect to database
			conn = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

			// Create scanner & file objects to access external data
			File file = new File("src/cs520/hw5/team.txt");
			scanner = new Scanner(file);

			// Declare 4 String variables that will serve as createPlayer() arguments
			String line1, line2, line3, line4 = null;

			// Use while-loop to populate object with external file data
			// Only execute while hasNextLine() returns true
			while (scanner.hasNextLine()) {

				// Assign the data on each line to 4 separate String variables
				line1 = scanner.nextLine();
				line2 = scanner.nextLine();
				line3 = scanner.nextLine();
				line4 = scanner.nextLine();

				// Assign returned Player object from createPlayer() to local variable player
				// Call createPlayer() with the 4 variables declared above
				Player player = createPlayer(line1, line2, line3, line4);

				// Add player to the players list
				players.add(player);

				// Use if-statement to check if 5th line exists
				if (scanner.hasNextLine()) {

					// (If true) Skip 5th line
					scanner.nextLine();
				}

				// Iteration complete, while-loop checks condition to enter next iteration
				// Cycles through 10 iterations

				// If 5th line is absent, immediately leave while-loop
				else {
					break;
				}
			}

			// Instantiate Database class
			Database database = new Database();

			// Call insertPlayers() - passing players to it
			database.insertPlayers(players);

			// Call selectPlayers() method which will return a HashMap<String,Player>
			// Assign returned value to selectedPlayers
			Map<String, Player> selectedPlayers = database.selectPlayers();

			// Assign list of keys to variable nameKeys
			Collection<String> nameKeys = selectedPlayers.keySet();

			// Use for-each loop to print map key+value pairs
			for (String key : nameKeys) {
				System.out.println(key + ": " + selectedPlayers.get(key));
			}

			// Handle exceptions
		} catch (SQLException | FileNotFoundException e) {

			e.printStackTrace();
		} finally {
			scanner.close();
			try {
				if (conn != null) {
					// Don't try to close connection if null (connection failed)
					conn.close();
				}
			} catch (Exception e2) {
				// Nested try/catch because trying to close connection could throw another
				// exception
				e2.printStackTrace();
			}
		}

	}

}
