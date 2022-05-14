import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class GameEngine {
	private Player player;
	private Monster monster;
	private ArrayList<Monster> monsters;
	private ArrayList<Item> items;
	private World world;
	private Scanner scanner;
	private boolean isFileExists;
	private FileHandler worldFile;
	private FileHandler playerFile;
	private boolean continueGame;
	private String playerFileName;

	/** 
	* Creates new player, monster and world objects to run the game loop
	*/ 
	public GameEngine() {
		player = new Player();
		monster = new Monster();
		world = new World();
		monsters = new ArrayList<Monster>();
		items = new ArrayList<Item>();
		isFileExists = false;
		worldFile = new FileHandler();
		playerFile = new FileHandler();
		continueGame = true;
		playerFileName = "player.dat";
	}

	public static void main(String[] args) {

		// TODO: Some starter code has been provided below.
		// Edit this method as you find appropriate.

		// Creates an instance of the game engine.
		
		GameEngine gameEngine = new GameEngine();

		// Runs the main game loop.
		// gameEngine.readWorldFile();
		gameEngine.runGameLoop();
	}

	/*
	 *  Logic for running the main game loop.
	 */
	
	private void readWorldFile(String fileName) {
		try {
			String data = ""; 
			data = worldFile.readFile(fileName); 
			isFileExists = true;
			// read the map height and width from file
			String[] lines = data.split("\n");
			String[] firstLine = lines[0].split(" ");
			world.setMaxWidth(Integer.valueOf(firstLine[0]));
			world.setMaxHeight(Integer.valueOf(firstLine[1]));	
			// reads the terrain of map
			char[][] gameWorld = new char[world.getMaxHeight()][world.getMaxWidth()];
			for(int i = 0; i < world.getMaxHeight(); i++){
				for(int j = 0; j < world.getMaxWidth(); j++) {
					gameWorld[i][j] = lines[i+1].charAt(j);
				}
			}
			world.setGameWorld(gameWorld);	
			// reads player position in map
			String[] playerPosition = lines[world.getMaxHeight() + 1].split(" ");
			player.setX(Integer.valueOf(playerPosition[1]));
			player.setY(Integer.valueOf(playerPosition[2]));
			// reads monster position in map
			monsters = new ArrayList<Monster>();

			for(int i = world.getMaxHeight() + 2; i < lines.length; i++){
				String[] currentLine = lines[i].split(" ");
				
				if(currentLine[0].equals("monster")) {
					// make array list of monsters and each will have own attributes
					Monster m = new Monster();
					m.setX(Integer.valueOf(currentLine[1]));
					m.setY(Integer.valueOf(currentLine[2]));
					m.setName(currentLine[3]);
					m.setMaxHealth(Integer.valueOf(currentLine[4]));
					m.setCurrentHealth(Integer.valueOf(currentLine[4]));
					m.setDamage(Integer.valueOf(currentLine[5]));
					monsters.add(m);
				} else if(currentLine[0].equals("item")) {
					Item item = new Item();
					item.setX(Integer.valueOf(currentLine[1]));
					item.setY(Integer.valueOf(currentLine[2]));
					item.setSymbol(currentLine[3].charAt(0));
					items.add(item);
				}
			}
		} catch (Exception e) {
			// System.out.println(e.getMessage());
		}
	}

	/** 
	* Resets player and monster health back to max health and 
	* resets the player and monster positions in game world
	*/
	private void resetGame() {
		player.setCurrentHealth(player.getMaxHealth());
		monster.setCurrentHealth(monster.getMaxHealth());

		player.setX(0);
		player.setY(0);

		monster.setX(4);
		monster.setY(2);
	}

	private void runGameLoop() {

		// Print out the title text.
		displayTitleText();

		// TODO: Implement your code here.
		
		scanner = new Scanner(System.in);
		System.out.print("> ");

		while (continueGame == true) {

			String line = scanner.nextLine();
			String[] lineArr = line.split(" ");
			String command = "";
			
			if(lineArr.length >= 1) {
				command = lineArr[0];
			}
			
			// checks whether player inputs 'help' command
			if(command.equals("help")) {
				System.out.println("Type 'commands' to list all available commands");
				System.out.println("Type 'start' to start a new game");
				System.out.println("Create a character, battle monsters, and find treasure!");
				System.out.println();
			}

			// checks whether player inputs  'commands' to list all commands
			if(command.equals("commands")) {
				System.out.println("help");
				System.out.println("player");
				System.out.println("monster");
				System.out.println("start");
				System.out.println("exit");
				System.out.println("save");
				System.out.println("load");
			}

			// checks whether player inputs 'player' to create new player
			if(command.equals("player")) {
				// if player has not been created then go through the creation process
				if(player.isCreated() == false) {
					System.out.println("What is your character's name?");
					String playerName = scanner.nextLine();
					player.setName(playerName);
					System.out.println("Player " + "'" + playerName + "'" + " created.");
					System.out.println();
					System.out.println("(Press enter key to return to main menu)");
					player.setIsCreated();
					player.setMaxHealth();
					player.setCurrentHealth(player.getMaxHealth());
					player.setDamage();
				} else {
					// if player is already created then display the name, damage, health
					System.out.println(player.getName() + " (Lv. " + player.getLevel() + ")");
					player.setDamage();
					System.out.println("Damage: " + player.getDamage());
					player.setMaxHealth();
					player.setCurrentHealth(player.getCurrentHealth());
					System.out.println("Health: " + player.getCurrentHealth() + "/" + player.getMaxHealth());
					System.out.println();
					System.out.println("(Press enter key to return to main menu)");
				}
			}

			// checks whether user inputs 'monster' to create a new monster
			if(command.equals("monster")) {
				System.out.print("Monster name: ");
				String monsterName = scanner.nextLine();
				monster.setName(monsterName);
				System.out.print("Monster health: ");
				int monsterHealth = scanner.nextInt();
				System.out.print("Monster damage: ");
				int monsterDamage = scanner.nextInt();
				System.out.println("Monster " + "'" + monsterName + "'" + " created.");
				System.out.println();
				System.out.println("(Press enter key to return to main menu)");
				monster.setMaxHealth(monsterHealth);
				monster.setCurrentHealth(monsterHealth);
				monster.setDamage(monsterDamage);
			}

			// checks if user inputs the 'start' command and file name
			if(command.equals("start") && lineArr.length >= 2) {
				String fileName = lineArr[1] + ".dat";
				File myFile = new File(fileName);

				if(myFile.exists()) {
					readWorldFile(fileName);
				}
				startCommandWithMap();
			} else if(command.equals("start")) {
				// checks if user inputs only 'start' command
				startCommand();
			}

			// checks if user inputs "home"
			if(command.equals("home")) {
				System.out.println("Returning home...");
				System.out.println();
				System.out.println("(Press enter key to return to main menu)");
			}

			// return to main menu when user presses enter
			if(command.equals("")) {
				displayTitleText();
				System.out.print("> ");
			}

			if(command.equals("save")){

				if(player.isCreated() == true){
					String data = player.getName() + " " + Integer.valueOf(player.getLevel());
					playerFile.createFile(playerFileName);
					playerFile.writeFile(playerFileName, data);
					System.out.println("Player data saved.");
				} else {
					System.out.println("No player data to save.");
				}
			}

			if(command.equals("load")){
				try {
					if(playerFile.checkPlayerFileExists(playerFileName) == true){
						// System.out.println(playerFile.loadPlayerFile(playerFileName));
						playerFile.loadPlayerFile(playerFileName);
						System.out.println("Player data loaded.");
					}
				} catch (FileNotFoundException e){
					System.out.println("No player data found.");
				}
				/*
				if(playerFile.checkPlayerFileExists(playerFileName) == true){
					System.out.println(playerFile.loadPlayerFile(playerFileName));
					System.out.println("Player data loaded.");
				} else {
					System.out.println("No player data found.");
				}
				*/
			}

			// checks if user inputs "exit" and ends the game
			if(command.equals("exit")) {
				System.out.println("Thank you for playing Rogue!");
				break;
			}
		}
	}
	
	private int startCommand() {

		if(player.isCreated() == false) {
			// checks if player has been created, if not prompts user to create one first
			System.out.println("No player found, please create a player with 'player' first.\n");
			System.out.println("(Press enter key to return to main menu)");
		} else if(player.isCreated() == true && monster.isCreated() == false) {
			// checks if monster has been created, if not prompts user to create a monster
			if(isFileExists == false) {
				System.out.println("No monster found, please create a monster with 'monster' first.\n");
				System.out.println("(Press enter key to return to main menu)");
				
			} else if(isFileExists == true) {
				world.printWorld(player, monsters, items);
				System.out.println();
				startCommandLoop();
			} else {
				System.out.println("Map not found.");
				System.out.println();
				System.out.println("(Press enter key to return to main menu)");
			}
		} else {
			resetGame();
			checkMonsterCollision();
			world.printWorld(player, monster); // UPDATED THIS SO DEFAULT WORLD FROM ASSIGNMENT 1 STILL RENDERS WITH MONSTER AND PLAYER POSITIONS
			startCommandLoop();
		}
		return 0;
	}

	private int startCommandWithMap() {

		if(player.isCreated() == false) {
			// checks if player has been created, if not prompts user to create one first
			System.out.println("No player found, please create a player with 'player' first.\n");
			System.out.println("(Press enter key to return to main menu)");
		} else if(player.isCreated() == true && monster.isCreated() == false) {
			// checks if monster has been created, if not prompts user to create a monster

			if(isFileExists == false) {
				System.out.println("Map not found.");
				System.out.println();
				System.out.println("(Press enter key to return to main menu)");
				
			} else if(isFileExists == true) {
				world.printWorld(player, monsters, items);
				System.out.println();
				startCommandLoop();
				
			} else {
				System.out.println("No monster found, please create a monster with 'monster' first.\n");
				System.out.println("(Press enter key to return to main menu)");
			}

		} else {
			resetGame();	
			world.printWorld(player, monster); // UPDATED THIS SO DEFAULT WORLD FROM ASSIGNMENT 1 STILL RENDERS WITH MONSTER AND PLAYER POSITIONS
			// world.printWorld(player, monsters, items); 
			startCommandLoop();
		}
		return 0;
	}
	
	private void startCommandLoop(){

		while(true) {

			if(player.getCurrentHealth() > 0) {
				System.out.print("> ");	
			}
			String movementInput = scanner.nextLine();

			if(movementInput.equals("home")) {
				System.out.println("Returning home...");
				System.out.println();
				System.out.println("(Press enter key to return to main menu)");
				break;

			} else if(!movementInput.contains("w") && !movementInput.contains("a") && !movementInput.contains("s") && !movementInput.contains("d") && !movementInput.contains("")){
				// checks for empty input by user, reprints the same map with no changes
				world.printWorld(player, monsters, items);
			} 

			if(movementInput.equals("exit")) {
				System.out.println("> Thank you for playing Rogue!");	
				continueGame = false;
				break;		
			} else {
				playerMovement(movementInput, items);

				/*
				if(checkItemCollision() == false) {
					break;
				}
				*/
			}
		} 
	}

	private void monstersMovement(){
		int rangeFromPlayer = 2;
		for (int i = 0; i < monsters.size(); i++){
			Monster currentMonster = monsters.get(i);
			int monX = currentMonster.getX();
			int monY = currentMonster.getY();
			if (Math.abs(monX - player.getX()) <= rangeFromPlayer && Math.abs(monY - player.getY()) <= rangeFromPlayer){
				world.updateDot(monX, monY);
				// if monster right of player, move left
				if (monX > player.getX() && (checkObstacleCollisionHorizontal(currentMonster, monX - 1) == false)) {
					// move left
					monX--;
					currentMonster.setX(monX);
				} else if (monX < player.getX() && checkObstacleCollisionHorizontal(currentMonster, monX + 1) == false){
					// move right
					monX++;
					currentMonster.setX(monX);
				} else if (monY > player.getY() && checkObstacleCollisionVertical(currentMonster, monY - 1) == false) {
					// move up
					monY--;
					currentMonster.setY(monY);
				} else if (monY < player.getY() && checkObstacleCollisionVertical(currentMonster, monY + 1) == false){
					// move down
					monY++;
					currentMonster.setY(monY);
				}
			}
		}
	}

	private void playerMovement(String movementInput, ArrayList<Item> items) {
		char move;
        if(movementInput.isBlank()){
            move = ' ';
        } else {
            move = movementInput.charAt(0);
        }
		int newY;
		int newX;
		world.updateDot(player.getX(), player.getY());

		if(isFileExists == false){
			checkMonsterCollision();
		}
		monstersMovement();

		if(move == 'w') {
			// moves the player 1 position up
			newY = player.getY() - 1;
			checkObstacleCollisionVertical(player, newY);
		} else if(move == 'a') {
			// moves the player 1 position to the left
			newX = player.getX() - 1;
			checkObstacleCollisionHorizontal(player, newX);
		} else if(move == 's') {
			// moves the player 1 position down if there is non-traversable terrain 1 cell down from player's current position
			newY = player.getY() + 1;
			checkObstacleCollisionVertical(player, newY);
		} else if(move == 'd') {
			// moves the player 1 position to the right
			newX = player.getX() + 1;
			checkObstacleCollisionHorizontal(player, newX);
		}
		// checkItemCollision();
		checkMonstersCollision();

		if(player.getCurrentHealth() > 0 && checkItemCollision() == true){
			// checkItemCollision();
			world.printWorld(player, monsters, items);
			System.out.println();		
		}
	}

	private boolean checkObstacleCollisionVertical(Unit unit, int newY){
		if(isValidMovement(unit.getX(), newY)) {
			if(world.obstacleExists(unit.getX(), newY) == true) {
				// No change to player Y position
				unit.setY(unit.getY());
				return true;
			} else {
				unit.setY(newY);
			}
		}
		return false;
	}

	private boolean checkObstacleCollisionHorizontal(Unit unit, int newX){
		if(isValidMovement(newX, unit.getY())){
			if(world.obstacleExists(newX, unit.getY()) == true) {
				// No change to player X position if there is non-traversable terrain 1 cell to the right of player's current position
				unit.setX(unit.getX());
				return true;
			} else {
				unit.setX(newX);
			}
		}
		return false;
	}

	private boolean checkItemCollision(){
		for(int i = 0; i < items.size(); i++) {
			if (items.get(i).getX() == player.getX() && items.get(i).getY() == player.getY()) {
				items.get(i).readSymbol(player);

				if(items.get(i).getSymbol() == '@'){
					System.out.println();
					System.out.println("(Press enter to return to main menu)");
					return false;
				}
				items.remove(i);
			} 		
		}
		return true;
	}

	private void checkMonsterCollision(){
		// Enter battle loop when player and monster positions are the same
		if(player.getX() == monster.getX() && player.getY() == monster.getY()) {
			battleLoop(monster);
		}
	}

	private void checkMonstersCollision(){
		for(int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i).getX() == player.getX() && monsters.get(i).getY() == player.getY()) {
				battleLoop(monsters.get(i));
				monsters.remove(i);
			}
		}
	}

	private void battleLoop(Monster monster){
		System.out.println(player.getName() + " encountered a " + monster.getName() + "!");
		System.out.println();
		System.out.println(player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " | " + monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());
		System.out.println(player.getName() + " attacks " + monster.getName() + " for " + player.getDamage() + " damage.");
		System.out.println(monster.getName() + " attacks " + player.getName() + " for " + monster.getDamage() + " damage.");
		System.out.println();

		while(player.getCurrentHealth() > 0 || monster.getCurrentHealth() > 0) {
			player.setCurrentHealth(player.getCurrentHealth() - monster.getDamage()); // decreases player current health by monster's damage
			monster.setCurrentHealth(monster.getCurrentHealth() - player.getDamage()); // decreases monster current health by player's damage
			System.out.println(player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " | " + monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());

			// keep printing message for player attack monster until monster current health - player damage <=0
			// then print the player wins message directly, do not print monster attacks player
			if(player.getCurrentHealth() >= 0){
				System.out.println(player.getName() + " attacks " + monster.getName() + " for " + player.getDamage() + " damage.");
				
				if(monster.getCurrentHealth() < player.getDamage()) {
				// player wins when monster's current health reaches 0 or less
					System.out.println(player.getName() + " wins!");
					System.out.println();
					monster.setCurrentHealth(monster.getCurrentHealth() - player.getDamage());
					break;
				}
			}

			if(monster.getCurrentHealth() - player.getDamage() >= 0){
				System.out.println(monster.getName() + " attacks " + player.getName() + " for " + monster.getDamage() + " damage.");
				
				if(player.getCurrentHealth() < monster.getDamage()) {
				// monster wins when player's current health reaches 0 or less
					System.out.println(monster.getName() + " wins!");
					System.out.println();
					player.setCurrentHealth(player.getCurrentHealth() - monster.getDamage());
					System.out.println("(Press enter key to return to main menu)");
					displayTitleText();
					break;
				}
			}
			System.out.println();
		}

		/*
		while(player.getCurrentHealth() > 0 || monster.getCurrentHealth() > 0) {

			player.setCurrentHealth(player.getCurrentHealth() - monster.getDamage()); // decreases player current health by monster's damage
			monster.setCurrentHealth(monster.getCurrentHealth() - player.getDamage()); // decreases monster current health by player's damage

			System.out.println(player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth() + " | " + monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());
			System.out.println(player.getName() + " attacks " + monster.getName() + " for " + player.getDamage() + " damage.");
			System.out.println(monster.getName() + " attacks " + player.getName() + " for " + monster.getDamage() + " damage.");
			System.out.println();
			
			if(player.getCurrentHealth() < monster.getDamage()) {
				// monster wins when player's current health reaches 0 or less
				System.out.println(monster.getName() + " wins!");
				player.setCurrentHealth(player.getCurrentHealth() - monster.getDamage());
				break;
			}

			if(monster.getCurrentHealth() < player.getDamage()) {
				// player wins when monster's current health reaches 0 or less
				System.out.println(player.getName() + " wins!");
				monster.setCurrentHealth(monster.getCurrentHealth() - player.getDamage());
				break;
			}
			
		}
		*/
	}

	/** 
	* Checks whether player moves beyond the height or width of the game world 
	* Returns false if move is out of bounds and true if movement is within the world boundary
	*/ 
	private boolean isValidMovement(int newX, int newY) {
		if(newX < 0 || newX >= world.getMaxWidth()) {
			return false; 
		}
		if(newY < 0 || newY >= world.getMaxHeight()) {
			return false; 
		}
		return true; 
	}

	/*
	 *  Displays the title text.
	 */
	private void displayTitleText() {

		
		String titleText = " ____                        \n" + 
				"|  _ \\ ___   __ _ _   _  ___ \n" + 
				"| |_) / _ \\ / _` | | | |/ _ \\\n" + 
				"|  _ < (_) | (_| | |_| |  __/\n" + 
				"|_| \\_\\___/ \\__, |\\__,_|\\___|\n" + 
				"COMP90041   |___/ Assignment ";

		System.out.println(titleText);
		System.out.println();

		/** 
		* Updates the title screen output depending on whether player and monster creation status
		*/
		if(player.isCreated() == true && monster.isCreated() == false){

			System.out.println("Player: " + player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth() + "  | " + "Monster: " + monster.getName());

		} else if(monster.isCreated() == true && player.isCreated() == false) {

			System.out.println("Player: " + player.getName() + "  | " + "Monster: " + monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());

		} else if(player.isCreated() == true && monster.isCreated() == true) {

			System.out.println("Player: " + player.getName() + " " + player.getCurrentHealth() + "/" + player.getMaxHealth() + "  | " + "Monster: " + monster.getName() + " " + monster.getCurrentHealth() + "/" + monster.getMaxHealth());

		} else {

			System.out.println("Player: " + player.getName() + "  | " + "Monster: " + monster.getName());
		}
		System.out.println();
		System.out.println("Please enter a command to continue." );
		System.out.println("Type 'help' to learn how to get started.");
		System.out.println();
	}
}