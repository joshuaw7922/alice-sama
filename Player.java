
// TODO: Copy code from your Assignment 1 as starter code.
public class Player extends Unit {
	private int level;

	/**
	* Default constructor initialises player attributes to the below default values when player object is created
	*/
	public Player() {
		this.name = "[None]";
		this.level = 1;
		this.X = 0;
		this.Y = 0;
	}

	public Player(String playerName) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth() {
		maxHealth = 17 + level * 3;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage() { 
		this.damage = 1 + level;
	}
	
	// NEW SETTER HERE WITH DAMAGE PARAMETER
	public void setDamage(int damage) {
		this.damage = 1 + level + damage;
		
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int health) {
		this.currentHealth = health;
	}

	/** 
	* Default name is set as "[None]" until monster name is input by the user
	*/
	public boolean isCreated() {
		if(!name.equals("[None]")) {
			return true;
		}
		return false;
	}

	public void setIsCreated() {
		this.isCreated = true;
	}

	public boolean getIsCreated() {
		return isCreated;
	}

}
	
