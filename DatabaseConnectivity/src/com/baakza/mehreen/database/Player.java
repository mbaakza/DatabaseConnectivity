// This class sets up the blueprint for the player object

package com.baakza.mehreen.database;

public class Player {

	// Declare private instance variables

	private int number;
	private String name;
	private String position;
	private String year;

	// Create get & set methods for each

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getPosition() {
		return position;
	}

	public String getYear() {
		return year;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	// No-arg constructor
	
	public Player() {
		// Empty
	}

	// Constructor: takes String argument & assigns value to name

	public Player(String name) {
		number = 0;
		this.name = name;
		position = "Position";
		year = "Year";
	}

	// Implement toString() method in the form: #number: name (position, year)

	public String toString() {
		return ("#" + number + " " + name + " (" + position + ", " + year + ")");
	}

}
