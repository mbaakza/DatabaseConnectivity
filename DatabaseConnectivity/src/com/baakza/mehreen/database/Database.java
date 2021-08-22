// This class runs SQL DB queries pertaining to Player objects 
// and creates a HashMap of players

package com.baakza.mehreen.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.sql.PreparedStatement;

public class Database {

	// No-arg constructor
	public Database() {
		// Empty
	}


	public void insertPlayers(Collection<Player> players) {

		// Declare instance of Connection outside of try/catch
		Connection conn = null;

		// Test block for exceptions
		try {

			// Create database connection & assign to Connection instance
			conn = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);
			
			// Create parameterized query templates
			String insertQuery = "insert into players values (?, ?, ?, ?)";
			String deleteQuery = "delete from players where number = ?"; 

			// Get 2 PreparedStatement instances from Connection, passing them to the appropriate queries
			PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery);
			PreparedStatement pstmtDelete = conn.prepareStatement(deleteQuery);

			// Iterate through players
			Iterator<Player> Itr = players.iterator();

			while (Itr.hasNext()) {
				Player player = Itr.next();

				// Create SQL queries to remove and insert data at each run
				
				// DELETE data
				pstmtDelete.setInt(1, player.getNumber());
				pstmtDelete.execute();

				// INSERT data
				pstmtInsert.setInt(1, player.getNumber());
				pstmtInsert.setString(2, player.getName());
				pstmtInsert.setString(3, player.getPosition());
				pstmtInsert.setString(4, player.getYear());
				pstmtInsert.executeUpdate();
				System.out.println("Inserting player into database: " + player);
			}

			// Close PreparedStatements
			pstmtInsert.close();
			pstmtDelete.close();

			// Handle exceptions
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
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

	// A method that returns map of players
	public Map<String, Player> selectPlayers() {

		Map<String, Player> players = new HashMap<String, Player>();

		// Declare instances of Connection & ResultSet
		Connection conn = null;
		ResultSet rs = null;

		try {
			// Create database connection & assign to Connection instance
			conn = DriverManager.getConnection(Constants.URL, Constants.USERNAME, Constants.PASSWORD);

			// Run SELECT query
			String query = "Select * from PLAYERS";
			
			// Create Statement instance
			Statement stmt = conn.createStatement();

			// Assign ResultSet value to rs
			rs = stmt.executeQuery(query);

			// Declare Player object
			Player player = null;

			// Iterate through the ResultSet
			while (rs.next()) {

				// Instantiate player
				player = new Player();

				// Add player
				player.setNumber(rs.getInt("NUMBER"));
				player.setName(rs.getString("NAME"));
				player.setPosition(rs.getString("POSITION"));
				player.setYear(rs.getString("YEAR"));

				// Add each Player to players HashMap
				players.put(player.getName(), player);
			}

			// Close PreparedStatement ResultSet
			stmt.close();
			rs.close();

		} catch (Exception e) {
			// Print stack trace to console
			e.printStackTrace();
		} finally {
			try {
				// Close the connection if it is not null
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return players;

	}

}
