
// TODO: Copy code from your Assignment 1 as starter code.

import java.util.ArrayList;

public class World {

	private char[][] gameWorld; 
	private int maxHeight;
	private int maxWidth;
	
	/**
	* Default constructor to create World with '.'
	*/
	public World() {
		maxHeight = 4;
		maxWidth = 6;
		gameWorld = new char[maxHeight][maxWidth];
		
		for(int height = 0; height < maxHeight; height++) {
			for(int width = 0; width < maxWidth; width++) {
				gameWorld[height][width] = '.';
			}
		}
	}
	
	// Constructor for game world
	public World(char[][] gameWorld, int maxHeight, int maxWidth) {
		this.maxHeight = maxHeight;
		this.maxWidth = maxWidth;
		this.gameWorld = gameWorld;
	}

	/**
	* Reads in player and monster positions to print the first character of their names onto the game world 
	* @param player is the player created by user
	* @param monster is the monster created by user
	*/

	public void printWorld(Player player, Monster monster) {
		
		gameWorld[player.getY()][player.getX()] = Character.toUpperCase(player.getName().charAt(0));
		gameWorld[monster.getY()][monster.getX()] = Character.toLowerCase(monster.getName().charAt(0));
				
		for(int height = 0; height < maxHeight; height++) {
			for(int width = 0; width < maxWidth; width++) {
				System.out.print(gameWorld[height][width]);
			}
			System.out.println();
		}
	}

	/**
	* Reads in player, monsters and items from the file specified by user, prints these objects based on positions in the file
	*/
 	public void printWorld(Player player, ArrayList<Monster> monsters, ArrayList<Item> items) {
		
		gameWorld[player.getY()][player.getX()] = Character.toUpperCase(player.getName().charAt(0));
		
		for(int i = 0; i < monsters.size(); i++) {
			gameWorld[monsters.get(i).getY()][monsters.get(i).getX()] = Character.toLowerCase(monsters.get(i).getName().charAt(0));
		}
		
		for(int i = 0; i < items.size(); i++) {
			gameWorld[items.get(i).getY()][items.get(i).getX()] = Character.toLowerCase(items.get(i).getSymbol());	
		}
		
		for(int height = 0; height < maxHeight; height++) {
			for(int width = 0; width < maxWidth; width++) {
				// System.out.println(height);
				// System.out.println(width);
				System.out.print(gameWorld[height][width]);
			}
			System.out.println();
		}
	}
 	
	/**
	* Updates the game world array to a '.' for a given position
	* @param x relates to the column position in the array
	* @param y relates to the row position in the array
	*/
 	public void updateDot(int x, int y) {
		gameWorld[y][x] = '.';
 	}

	public boolean obstacleExists(Player player, int x, int y){
		// Checks if the cell contains non-traversable terrain, else returns false
		if(gameWorld[y][x] == '#' || gameWorld[y][x] == '~'){
			return true;
		}
		return false;
	}

 	public int getMaxHeight() {
 		return maxHeight;
 	}
 	
 	public void setMaxHeight(int maxHeight) {
 		this.maxHeight = maxHeight;
 	}
 	
 	public int getMaxWidth() {
 		return maxWidth;
 	}
 	
 	public void setMaxWidth(int maxWidth) {
 		this.maxWidth = maxWidth;
 	}
 	
 	public char[][] getGameWorld(){
 		return gameWorld;
 	}
 	
 	public void setGameWorld(char[][] gameWorld) {
 		this.gameWorld = gameWorld;
 	}

}
